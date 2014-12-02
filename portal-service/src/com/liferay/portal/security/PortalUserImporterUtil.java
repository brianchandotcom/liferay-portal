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

package com.liferay.portal.security;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.User;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalUserImporterUtil {

	public static PortalUserImporter getPortalUserImporter() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalUserImporterUtil.class);

		return _portalUserImporter;
	}

	public static User importUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		return getPortalUserImporter().importUser(
			ldapServerId, companyId, ldapContext, attributes, password);
	}

	public static User importUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception {

		return getPortalUserImporter().importUser(
			ldapServerId, companyId, emailAddress, screenName);
	}

	public static User importUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		return getPortalUserImporter().importUser(
			companyId, emailAddress, screenName);
	}

	public static User importUserByScreenName(long companyId, String screenName)
		throws Exception {

		return getPortalUserImporter().importUserByScreenName(
			companyId, screenName);
	}

	public static void importUsers() throws Exception {
		getPortalUserImporter().importUsers();
	}

	public static void importUsers(long companyId) throws Exception {
		getPortalUserImporter().importUsers(companyId);
	}

	public static void importUsers(long ldapServerId, long companyId)
		throws Exception {

		getPortalUserImporter().importUsers(ldapServerId, companyId);
	}

	public void setPortalUserImporter(PortalUserImporter portalUserImporter) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalUserImporter = portalUserImporter;
	}

	private static PortalUserImporter _portalUserImporter;

}