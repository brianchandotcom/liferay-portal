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

package com.liferay.portal.security.jaas;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Raymond Augé
 */
public class JAASHelper {

	public static JAASHelper getInstance() {
		return _instance;
	}

	public static long getJaasUserId(long companyId, String name)
		throws PortalException, SystemException {

		return _instance._getJaasUserId(companyId, name);
	}

	public static void setInstance(JAASHelper instance) {
		_instance = instance;
	}

	protected long _getJaasUserId(long companyId, String name)
		throws PortalException, SystemException {

		String jaasAuthType = PropsValues.PORTAL_JAAS_AUTH_TYPE;

		if (jaasAuthType.equals("login")) {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);
			String authType = company.getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				jaasAuthType = "emailAddress";
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				jaasAuthType = "screenName";
			}
			else {
				jaasAuthType = "userId";
			}
		}

		long userId = 0;

		if (jaasAuthType.equals("emailAddress")) {
			User user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, name);

			if (user != null) {
				userId = user.getUserId();
			}
		}
		else if (jaasAuthType.equals("screenName")) {
			User user = UserLocalServiceUtil.fetchUserByScreenName(
				companyId, name);

			if (user != null) {
				userId = user.getUserId();
			}
		}
		else {
			userId = GetterUtil.getLong(name);
		}

		return userId;
	}

	private static JAASHelper _instance = new JAASHelper();

}