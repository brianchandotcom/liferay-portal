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

import com.liferay.portal.model.User;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

/**
 * @author Michael C. Han
 */
public class LDAPUserImporterUtil {

	public static User importUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		return _getInstance().importUser(
			ldapServerId, companyId, ldapContext, attributes, password);
	}

	private static LDAPUserImporter _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private LDAPUserImporterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(LDAPUserImporter.class);

		_serviceTracker.open();
	}

	private static final LDAPUserImporterUtil _instance =
		new LDAPUserImporterUtil();

	private final ServiceTracker<LDAPUserImporter, LDAPUserImporter>
		_serviceTracker;

}