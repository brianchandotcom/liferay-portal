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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.PortalLDAPImporterUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 * @author Wesley Gong
 */
public class SiteMinderAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Company company = PortalUtil.getCompany(request);

		long companyId = company.getCompanyId();

		if (!AuthSettingsUtil.isSiteMinderEnabled(companyId)) {
			return null;
		}

		String siteMinderUserHeader = request.getHeader(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.SITEMINDER_USER_HEADER,
				PropsValues.SITEMINDER_USER_HEADER));

		if (Validator.isNull(siteMinderUserHeader)) {
			return null;
		}

		String authType = company.getAuthType();

		User user = null;

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.SITEMINDER_IMPORT_FROM_LDAP,
				PropsValues.SITEMINDER_IMPORT_FROM_LDAP)) {

			try {
				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					user = PortalLDAPImporterUtil.importLDAPUser(
						companyId, siteMinderUserHeader, StringPool.BLANK);
				}
				else {
					user = PortalLDAPImporterUtil.importLDAPUser(
						companyId, StringPool.BLANK, siteMinderUserHeader);
				}
			}
			catch (SystemException se) {
			}
		}

		if (user == null) {
			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				user = UserLocalServiceUtil.fetchUserByEmailAddress(
					companyId, siteMinderUserHeader);
			}
			else {
				user = UserLocalServiceUtil.fetchUserByScreenName(
					companyId, siteMinderUserHeader);
			}
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

}