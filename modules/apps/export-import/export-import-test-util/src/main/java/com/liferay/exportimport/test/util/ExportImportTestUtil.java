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

package com.liferay.exportimport.test.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class ExportImportTestUtil {

	public static PortletDataContext getExportPortletDataContext()
		throws Exception {

		return getExportPortletDataContext(TestPropsValues.getGroupId());
	}

	public static PortletDataContext getExportPortletDataContext(long groupId)
		throws Exception {

		return getExportPortletDataContext(
			TestPropsValues.getCompanyId(), groupId);
	}

	public static PortletDataContext getExportPortletDataContext(
			long companyId, long groupId)
		throws Exception {

		return getExportPortletDataContext(
			companyId, groupId, new HashMap<String, String[]>());
	}

	public static PortletDataContext getExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap)
		throws Exception {

		return getExportPortletDataContext(
			companyId, groupId, parameterMap, null, null);
	}

	public static PortletDataContext getExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				companyId, groupId, parameterMap, startDate, endDate,
				testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		return portletDataContext;
	}

	public static PortletDataContext getImportPortletDataContext()
		throws Exception {

		return getImportPortletDataContext(TestPropsValues.getGroupId());
	}

	public static PortletDataContext getImportPortletDataContext(long groupId)
		throws Exception {

		return getImportPortletPreferences(
			TestPropsValues.getCompanyId(), groupId);
	}

	public static PortletDataContext getImportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				companyId, groupId, parameterMap, new TestUserIdStrategy(),
				testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		return portletDataContext;
	}

	public static PortletDataContext getImportPortletPreferences(
			long companyId, long groupId)
		throws Exception {

		return getImportPortletDataContext(
			companyId, groupId, new HashMap<String, String[]>());
	}

}