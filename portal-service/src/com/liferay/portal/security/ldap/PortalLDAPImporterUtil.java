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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.User;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalLDAPImporterUtil {

	public static void importFromLDAP() throws Exception {
		_portalLDAPImporter.importFromLDAP();
	}

	public static void importFromLDAP(long companyId) throws Exception {
		_portalLDAPImporter.importFromLDAP(companyId);
	}

	public static void importFromLDAP(long ldapServerId, long companyId)
		throws Exception {

		_portalLDAPImporter.importFromLDAP(ldapServerId, companyId);
	}

	public static User importLDAPUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		return _portalLDAPImporter.importLDAPUser(
			ldapServerId, companyId, ldapContext, attributes, password);
	}

	public static User importLDAPUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception {

		return _portalLDAPImporter.importLDAPUser(
			ldapServerId, companyId, emailAddress, screenName);
	}

	public static User importLDAPUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		return _portalLDAPImporter.importLDAPUser(
			companyId, emailAddress, screenName);
	}

	public static User importLDAPUserByScreenName(
			long companyId, String screenName)
		throws Exception {

		return _portalLDAPImporter.importLDAPUserByScreenName(
			companyId, screenName);
	}

	public void setPortalLDAPImporter(PortalLDAPImporter portalLDAPImporter) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalLDAPImporter = portalLDAPImporter;
	}

	private static PortalLDAPImporter _portalLDAPImporter;

}