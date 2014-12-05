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

import java.util.Properties;

/**
 * @author Edward C. Han
 */
public interface LDAPSettings {

	public String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception;

	public Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception;

	public Properties getContactMappings(long ldapServerId, long companyId)
		throws Exception;

	public Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception;

	public long getPreferredLDAPServerId(long companyId, String screenName);

	public String getPropertyPostfix(long ldapServerId);

	public Properties getUserExpandoMappings(long ldapServerId, long companyId)
		throws Exception;

	public Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception;

	public boolean isExportEnabled(long companyId);

	public boolean isExportGroupEnabled(long companyId);

	public boolean isImportEnabled(long companyId);

	public boolean isImportOnStartup(long companyId);

	public boolean isPasswordPolicyEnabled(long companyId);

}