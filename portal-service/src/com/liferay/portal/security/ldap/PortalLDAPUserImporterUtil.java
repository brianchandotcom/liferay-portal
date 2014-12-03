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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.User;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

/**
 * @author Michael C. Han
 */
public class PortalLDAPUserImporterUtil {

	public static PortalLDAPUserImporter getPortalLDAPUserImporter() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalLDAPUserImporterUtil.class);

		return _portalLDAPUserImporter;
	}

	public static User importUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		return getPortalLDAPUserImporter().importUser(
			ldapServerId, companyId, ldapContext, attributes, password);
	}

	public void setPortalLDAPUserImporter(
		PortalLDAPUserImporter portalLDAPUserImporter) {

		_portalLDAPUserImporter = portalLDAPUserImporter;
	}

	private static PortalLDAPUserImporter _portalLDAPUserImporter;

}