/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		portletDataContext.setMissingReferencesElement(
			rootElement.addElement("missing-references"));

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
		portletDataContext.setMissingReferencesElement(
			rootElement.addElement("missing-references"));

		return portletDataContext;
	}

	public static PortletDataContext getImportPortletPreferences(
			long companyId, long groupId)
		throws Exception {

		return getImportPortletDataContext(
			companyId, groupId, new HashMap<String, String[]>());
	}

}