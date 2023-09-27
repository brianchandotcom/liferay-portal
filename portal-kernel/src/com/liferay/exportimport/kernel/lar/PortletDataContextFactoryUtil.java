/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.Date;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class PortletDataContextFactoryUtil {

	public static PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		return _portletDataContextFactory.clonePortletDataContext(
			portletDataContext);
	}

	public static PortletDataContext createExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		return _portletDataContextFactory.createExportPortletDataContext(
			companyId, groupId, parameterMap, startDate, endDate, zipWriter);
	}

	public static PortletDataContext createImportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			UserIdStrategy userIdStrategy, ZipReader zipReader)
		throws PortletDataException {

		return _portletDataContextFactory.createImportPortletDataContext(
			companyId, groupId, parameterMap, userIdStrategy, zipReader);
	}

	public static PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, Date startDate, Date endDate)
		throws PortletDataException {

		return _portletDataContextFactory.createPreparePortletDataContext(
			companyId, groupId, startDate, endDate);
	}

	public static PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, String range, Date startDate,
			Date endDate)
		throws PortletDataException {

		return _portletDataContextFactory.createPreparePortletDataContext(
			companyId, groupId, range, startDate, endDate);
	}

	public static PortletDataContext createPreparePortletDataContext(
			ThemeDisplay themeDisplay, Date startDate, Date endDate)
		throws PortletDataException {

		return _portletDataContextFactory.createPreparePortletDataContext(
			themeDisplay, startDate, endDate);
	}

	private static volatile PortletDataContextFactory
		_portletDataContextFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletDataContextFactory.class,
				PortletDataContextFactoryUtil.class,
				"_portletDataContextFactory", false);

}