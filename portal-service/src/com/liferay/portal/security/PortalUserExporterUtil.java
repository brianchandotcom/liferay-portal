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
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPOperation;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Raymond Augé
 */
public class PortalUserExporterUtil {

	public static void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		getPortalUserExporter().exportUser(contact, contactExpandoAttributes);
	}

	public static void exportUser(
			long userId, long userGroupId, LDAPOperation ldapOperation)
		throws Exception {

		getPortalUserExporter().exportUser(userId, userGroupId, ldapOperation);
	}

	public static void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		getPortalUserExporter().exportUser(user, userExpandoAttributes);
	}

	public static PortalUserExporter getPortalUserExporter() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalUserExporterUtil.class);

		return _portalUserExporter;
	}

	public void setPortalUserExporter(PortalUserExporter portalUserExporter) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalUserExporter = portalUserExporter;
	}

	private static PortalUserExporter _portalUserExporter;

}