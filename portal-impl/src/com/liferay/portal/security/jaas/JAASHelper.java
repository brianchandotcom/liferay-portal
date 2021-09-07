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

package com.liferay.portal.security.jaas;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Raymond Augé
 * @deprecated As of Cavanaugh (7.4.x), with no replacement
 */
@Deprecated
public class JAASHelper {

	public static JAASHelper getInstance() {
		return _jaasHelper;
	}

	public static long getJaasUserId(long companyId, String name)
		throws PortalException {

		return _jaasHelper.doGetJaasUserId(companyId, name);
	}

	public static void setInstance(JAASHelper instance) {
		_jaasHelper = instance;
	}

	protected long doGetJaasUserId(long companyId, String name)
		throws PortalException {

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

	private static JAASHelper _jaasHelper = new JAASHelper();

}