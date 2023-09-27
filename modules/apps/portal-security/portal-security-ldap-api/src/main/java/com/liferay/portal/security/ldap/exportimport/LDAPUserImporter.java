/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.exportimport;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.portal.security.ldap.SafeLdapContext;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface LDAPUserImporter extends UserImporter {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public User importUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception;

	public User importUser(
			long ldapServerId, long companyId, SafeLdapContext safeLdapContext,
			Attributes attributes, String password)
		throws Exception;

}