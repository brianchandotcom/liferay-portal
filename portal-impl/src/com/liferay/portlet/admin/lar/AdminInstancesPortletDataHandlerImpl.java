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

package com.liferay.portlet.admin.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class AdminInstancesPortletDataHandlerImpl
	extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		AccountLocalServiceUtil.deleteAccountsByCompany(companyId);
		VirtualHostLocalServiceUtil.deleteVirtualHostsByCompany(companyId);
		RepositoryLocalServiceUtil.deleteRepositoriesByCompany(companyId);
		TicketLocalServiceUtil.deleteTicketsByCompany(companyId);

		return null;
	}

}