/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletDataContextAddReferenceElementTest {

	@Before
	public void setUp() throws Exception {
		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				new HashMap<String, String[]>(), null, null, null);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		_portletDataContext.setExportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		_bookmarksEntry = BookmarksTestUtil.addEntry(true);
		_bookmarksFolder = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), ServiceTestUtil.randomString());
	}

	@Test
	public void testMissingNotMissingReference() throws Exception {
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

		Assert.assertEquals(0, missingReferenceElements.size());
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

		Assert.assertEquals(1, missingReferenceElements.size());

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
		Element bookmarksEntryElement =
			_portletDataContext.getExportDataElement(_bookmarksEntry);

		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(true);

		Element bookmarksEntryElement2 =
			_portletDataContext.getExportDataElement(bookmarksEntry);

		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);
		_portletDataContext.addReferenceElement(
			bookmarksEntry, bookmarksEntryElement2, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, true);
		_portletDataContext.addReferenceElement(
			_bookmarksEntry, bookmarksEntryElement, _bookmarksFolder,
			PortletDataContext.REFERENCE_TYPE_PARENT, false);

		Element missingReferencesElement =
			_portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertEquals(0, missingReferenceElements.size());
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

		Assert.assertEquals(0, missingReferenceElements.size());
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

		Assert.assertEquals(0, missingReferenceElements.size());
	}

	private BookmarksEntry _bookmarksEntry;
	private BookmarksFolder _bookmarksFolder;
	private PortletDataContext _portletDataContext;

}