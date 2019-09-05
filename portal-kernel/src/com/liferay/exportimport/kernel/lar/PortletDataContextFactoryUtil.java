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