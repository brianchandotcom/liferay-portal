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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Date;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class PortletDataContextFactoryUtil {

	public static PortletDataContext clonePortletDataContext(
		PortletDataContext portletDataContext) {

		return getPortletDataContextFactory().clonePortletDataContext(
			portletDataContext);
	}

	public static PortletDataContext createExportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		return getPortletDataContextFactory().createExportPortletDataContext(
			companyId, groupId, parameterMap, startDate, endDate, zipWriter);
	}

	public static PortletDataContext createImportPortletDataContext(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			UserIdStrategy userIdStrategy, ZipReader zipReader)
		throws PortletDataException {

		return getPortletDataContextFactory().createImportPortletDataContext(
			companyId, groupId, parameterMap, userIdStrategy, zipReader);
	}

	public static PortletDataContext createPreparePortletDataContext(
			long companyId, long groupId, Date startDate, Date endDate)
		throws PortletDataException {

		return getPortletDataContextFactory().createPreparePortletDataContext(
			companyId, groupId, startDate, endDate);
	}

	public static PortletDataContext createPreparePortletDataContext(
			ThemeDisplay themeDisplay, Date startDate, Date endDate)
		throws PortletDataException {

		return getPortletDataContextFactory().createPreparePortletDataContext(
			themeDisplay, startDate, endDate);
	}

	public static PortletDataContextFactory getPortletDataContextFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletDataContextFactoryUtil.class);

		return _portletDataContextFactory;
	}

	public void setPortletDataContextFactory(
		PortletDataContextFactory portletDataContextFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletDataContextFactory = portletDataContextFactory;
	}

	private static PortletDataContextFactory _portletDataContextFactory;

}