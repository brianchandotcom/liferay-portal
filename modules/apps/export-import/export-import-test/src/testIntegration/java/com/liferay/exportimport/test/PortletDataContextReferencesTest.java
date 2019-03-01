/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Máté Thurzó
 */
@RunWith(Arquillian.class)
public class PortletDataContextReferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				new HashMap<String, String[]>(), null, null, zipWriter);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("self-path", "dummyPath");

		Element element = rootElement.addElement("PortletDataRootElement");

		_portletDataContext.setExportDataRootElement(element);
		_portletDataContext.setImportDataRootElement(element);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		_bookmarksFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		_bookmarksEntry = BookmarksTestUtil.addEntry(
			_bookmarksFolder.getFolderId(), true, _serviceContext);
	}

	@Test
	public void testCleanUpMissingReferences() throws Exception {
		_portletDataContext.setPortletId(
			JournalContentPortletKeys.JOURNAL_CONTENT);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			JournalContentPortletKeys.JOURNAL_CONTENT);

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_portletDataContext.addReferenceElement(
			portlet, _portletDataContext.getExportDataRootElement(),
			assetCategory, PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		StagedModelDataHandlerUtil.exportStagedModel(
			_portletDataContext, assetCategory);

		missingReferenceElements = missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertNull(missingReferenceElement.attribute("missing"));
		Assert.assertFalse(
			Validator.isBlank(
				missingReferenceElement.attributeValue("element-path")));
	}

	@Test
	public void testMissingNotMissingReference() throws Exception {
		_portletDataContext.setPortletId(BookmarksPortletKeys.BOOKMARKS);

		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);
		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, false);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertNull(missingReferenceElement.attribute("missing"));
		Assert.assertFalse(
			Validator.isBlank(
				missingReferenceElement.attributeValue("element-path")));
	}

	@Test
	public void testMissingReference() throws Exception {
		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertEquals(
			_bookmarksFolder.getModelClassName(),
			missingReferenceElement.attributeValue("class-name"));
		Assert.assertEquals(
			String.valueOf(_bookmarksFolder.getPrimaryKeyObj()),
			missingReferenceElement.attributeValue("class-pk"));
	}

	@Test
	public void testMultipleMissingNotMissingReference() throws Exception {
		_portletDataContext.setPortletId(BookmarksPortletKeys.BOOKMARKS);

		Element bookmarksEntryElement1 =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement1, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);
		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement1, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, false);

		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			_bookmarksFolder.getFolderId(), true, _serviceContext);

		Element bookmarksEntryElement2 =
			_portletDataContext.getExportDataElement(bookmarksEntry);

		_portletDataContext.addReferenceElement(
			bookmarksEntry, bookmarksEntryElement2, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertNull(missingReferenceElement.attribute("missing"));
		Assert.assertFalse(
			Validator.isBlank(
				missingReferenceElement.attributeValue("element-path")));
	}

	@Test
	public void testMultipleMissingReferences() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			JournalContentPortletKeys.JOURNAL_CONTENT);

		_portletDataContext.addReferenceElement(
			portlet, _portletDataContext.getExportDataRootElement(),
			_bookmarksEntry, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);
		_portletDataContext.addReferenceElement(
			portlet, _portletDataContext.getExportDataRootElement(),
			_bookmarksEntry, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertEquals(
			BookmarksEntry.class.getName(),
			missingReferenceElement.attributeValue("class-name"));
		Assert.assertEquals(
			String.valueOf(_bookmarksEntry.getPrimaryKeyObj()),
			missingReferenceElement.attributeValue("class-pk"));
	}

	@Test
	public void testNotMissingMissingReference() throws Exception {
		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, false);
		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(
			missingReferenceElements.toString(), 0,
			missingReferenceElements.size());
	}

	@Test
	public void testNotMissingReference() throws Exception {
		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, false);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(
			missingReferenceElements.toString(), 0,
			missingReferenceElements.size());
	}

	@Test
	public void testNotReferenceMissingReference() throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		_portletDataContext.setZipWriter(zipWriter);

		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		_portletDataContext.addClassedModel(
			bookmarksEntryElement,
			ExportImportPathUtil.getModelPath(_bookmarksEntry),
			_bookmarksEntry);

		Element bookmarksFolderElement =
			_portletDataContext.getExportDataElement(_bookmarksFolder);

		_portletDataContext.addReferenceElement(
			_bookmarksFolder, bookmarksFolderElement, _bookmarksEntry,
			PortletDataContext.REFERENCE_TYPE_CHILD, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertTrue(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
	}

	@Test
	public void testSameMissingReferenceMultipleTimes() throws Exception {
		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		bookmarksEntryElement.addAttribute(
			"path", ExportImportPathUtil.getModelPath(_bookmarksEntry));

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(
			missingReferenceElements.toString(), 1,
			missingReferenceElements.size());

		List<Element> referencesElements =
			_portletDataContext.getReferenceElements(
				_bookmarksEntry, BookmarksFolder.class);

		Assert.assertEquals(
			referencesElements.toString(), 1, referencesElements.size());

		for (Element referenceElement : referencesElements) {
			Assert.assertTrue(
				GetterUtil.getBoolean(
					referenceElement.attributeValue("missing")));
		}
	}

	private BookmarksEntry _bookmarksEntry;
	private BookmarksFolder _bookmarksFolder;

	@DeleteAfterTestRun
	private Group _group;

	private PortletDataContext _portletDataContext;
	private ServiceContext _serviceContext;

}