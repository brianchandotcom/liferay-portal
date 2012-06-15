/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class SCPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		SCFrameworkVersionLocalServiceUtil.deleteFrameworkVersionsByCompany(
			companyId);
		SCProductEntryLocalServiceUtil.deleteProductEntriesByCompany(companyId);
		SCProductScreenshotLocalServiceUtil.deleteProductScreenshotsByCompany(
			companyId);
		SCProductVersionLocalServiceUtil.deleteProductVersionsByCompany(
			companyId);

		return null;
	}

}