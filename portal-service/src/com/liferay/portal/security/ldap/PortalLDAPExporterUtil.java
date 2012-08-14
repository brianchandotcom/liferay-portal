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
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Raymond Augé
 */
public class PortalLDAPExporterUtil {

	public static void deleteFromLDAP(
			long ldapServerId, long companyId, long userId) throws Exception {
		getPortalLDAPExporter().deleteFromLDAP(ldapServerId, companyId, userId);
	}

	public static void exportToLDAP(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		getPortalLDAPExporter().exportToLDAP(contact, contactExpandoAttributes);
	}

	public static void exportToLDAP(
			long userId, long userGroupId, LDAPOperation ldapOperation)
		throws Exception {

		getPortalLDAPExporter().exportToLDAP(
			userId, userGroupId, ldapOperation);
	}

	public static void exportToLDAP(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		getPortalLDAPExporter().exportToLDAP(user, userExpandoAttributes);
	}

	public static PortalLDAPExporter getPortalLDAPExporter() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalLDAPExporterUtil.class);

		return _portalLDAPExporter;
	}

	public void setPortalLDAPExporter(PortalLDAPExporter portalLDAPExporter) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalLDAPExporter = portalLDAPExporter;
	}

	private static PortalLDAPExporter _portalLDAPExporter;

}