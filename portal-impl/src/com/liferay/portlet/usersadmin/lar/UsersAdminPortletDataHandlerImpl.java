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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.service.UserTrackerLocalServiceUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class UsersAdminPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		AddressLocalServiceUtil.deleteAddressesByCompany(companyId);
		ContactLocalServiceUtil.deleteContactsByCompany(companyId);
		EmailAddressLocalServiceUtil.deleteEmailAddressesByCompany(companyId);
		OrganizationLocalServiceUtil.deleteOrganizationsByCompany(companyId);
		PhoneLocalServiceUtil.deletePhonesByCompany(companyId);
		UserLocalServiceUtil.deleteUsersByCompany(companyId);
		UserGroupLocalServiceUtil.deleteUserGroupsByCompany(companyId);
		UserNotificationEventLocalServiceUtil
			.deleteUserNotificationEventsByCompany(companyId);
		UserTrackerLocalServiceUtil.deleteUserTrackersByCompany(companyId);
		WebsiteLocalServiceUtil.deleteWebsitesByCompany(companyId);

		return null;
	}

}