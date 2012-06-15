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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class LayoutsAdminPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		LayoutLocalServiceUtil.deleteLayoutsByCompany(companyId);
		LayoutBranchLocalServiceUtil.deleteLayoutBranchesByCompany(companyId);
		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototypesByCompany(
			companyId);
		LayoutRevisionLocalServiceUtil.deleteLayoutRevisionsByCompany(
			companyId);
		LayoutSetLocalServiceUtil.deleteLayoutSetsByCompany(companyId);
		LayoutSetBranchLocalServiceUtil.deleteLayoutSetBranchesByCompany(
			companyId);
		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototypesByCompany(
			companyId);

		return null;
	}

}