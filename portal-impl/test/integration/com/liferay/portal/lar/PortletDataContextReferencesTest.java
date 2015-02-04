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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class PortletDataContextReferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ClassNameLocalServiceUtil.addClassName(
			MockBookmarksEntry.class.getName());
		ClassNameLocalServiceUtil.addClassName(
			MockBookmarksFolder.class.getName());

		_group = GroupTestUtil.addGroup();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				new HashMap<String, String[]>(), null, null, zipWriter);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		_portletDataContext.setExportDataRootElement(rootElement);
		_portletDataContext.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		_mockBookmarksEntry = new MockBookmarksEntry();
		_mockBookmarksFolder = new MockBookmarksFolder();
	}

	@Test
	public void testCleanUpMissingReferences() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortletKeys.ASSET_PUBLISHER);

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

		Assert.assertFalse(missingReferenceElements.isEmpty());
		Assert.assertEquals(1, missingReferenceElements.size());

		StagedModelDataHandlerUtil.exportStagedModel(
			_portletDataContext, assetCategory);

		missingReferenceElements = missingReferencesElement.elements();

		Assert.assertTrue(missingReferenceElements.isEmpty());
	}

	@Test
	public void testMissingNotMissingReference() throws Exception {
		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			true);
		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			false);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(0, missingReferenceElements.size());
	}

	@Test
	public void testMissingReference() throws Exception {
		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(1, missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertEquals(
			_mockBookmarksFolder.getModelClassName(),
			missingReferenceElement.attributeValue("class-name"));
		Assert.assertEquals(
			String.valueOf(_mockBookmarksFolder.getPrimaryKeyObj()),
			missingReferenceElement.attributeValue("class-pk"));
	}

	@Test
	public void testMultipleMissingNotMissingReference() throws Exception {
		Element mockBookmarksEntryElement1 =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement1,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			true);
		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement1,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			false);

		MockBookmarksEntry mockBookmarksEntry = new MockBookmarksEntry();

		Element mockBookmarksEntryElement2 =
			_portletDataContext.getExportDataElement(mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			mockBookmarksEntry, mockBookmarksEntryElement2,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(0, missingReferenceElements.size());
	}

	@Test
	public void testMultipleMissingReferences() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortletKeys.ASSET_PUBLISHER);

		_portletDataContext.addReferenceElement(
			portlet, _portletDataContext.getExportDataRootElement(),
			_mockBookmarksEntry, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);
		_portletDataContext.addReferenceElement(
			portlet, _portletDataContext.getExportDataRootElement(),
			_mockBookmarksEntry, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(missingReferenceElements.isEmpty());
		Assert.assertEquals(1, missingReferenceElements.size());

		Element missingReferenceElement = missingReferenceElements.get(0);

		Assert.assertEquals(
			MockBookmarksEntry.class.getName(),
			missingReferenceElement.attributeValue("class-name"));
		Assert.assertEquals(
			String.valueOf(_mockBookmarksEntry.getPrimaryKeyObj()),
			missingReferenceElement.attributeValue("class-pk"));
	}

	@Test
	public void testNotMissingMissingReference() throws Exception {
		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			false);
		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(0, missingReferenceElements.size());
	}

	@Test
	public void testNotMissingReference() throws Exception {
		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_PARENT,
			false);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(0, missingReferenceElements.size());
	}

	@Test
	public void testNotReferenceMissingReference() throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		_portletDataContext.setZipWriter(zipWriter);

		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		_portletDataContext.addClassedModel(
			mockBookmarksEntryElement,
			ExportImportPathUtil.getModelPath(_mockBookmarksEntry),
			_mockBookmarksEntry);

		Element mockBookmarksFolderElement =
			_portletDataContext.getExportDataElement(_mockBookmarksFolder);

		_portletDataContext.addReferenceElement(
			_mockBookmarksFolder, mockBookmarksFolderElement,
			_mockBookmarksEntry, PortletDataContext.REFERENCE_TYPE_CHILD, true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertTrue(missingReferenceElements.isEmpty());
	}

	@Test
	public void testSameMissingReferenceMultipleTimes() throws Exception {
		Element mockBookmarksEntryElement =
			_portletDataContext.getExportDataElement(_mockBookmarksEntry);

		mockBookmarksEntryElement.addAttribute(
			"path", ExportImportPathUtil.getModelPath(_mockBookmarksEntry));

		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);
		_portletDataContext.addReferenceElement(
			_mockBookmarksEntry, mockBookmarksEntryElement,
			_mockBookmarksFolder, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			true);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(1, missingReferenceElements.size());

		List<Element> referencesElements =
			_portletDataContext.getReferenceElements(
				_mockBookmarksEntry, MockBookmarksFolder.class);

		Assert.assertEquals(2, referencesElements.size());

		for (Element referenceElement : referencesElements) {
			Assert.assertTrue(
				GetterUtil.getBoolean(
					referenceElement.attributeValue("missing")));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private MockBookmarksEntry _mockBookmarksEntry;
	private MockBookmarksFolder _mockBookmarksFolder;
	private PortletDataContext _portletDataContext;

}