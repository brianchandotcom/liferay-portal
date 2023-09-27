/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.exportimport;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.SafeLdapName;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.directory.Attributes;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Wesley Gong
 */
@ProviderType
public interface PortalToLDAPConverter {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getGroupSafeLdapName(long, UserGroup, Properties)}
	 */
	@Deprecated
	public String getGroupDNName(
			long ldapServerId, UserGroup userGroup, Properties groupMappings)
		throws Exception;

	public SafeLdapName getGroupSafeLdapName(
			long ldapServerId, UserGroup userGroup, Properties groupMappings)
		throws Exception;

	public Modifications getLDAPContactModifications(
			Contact contact, Map<String, Serializable> contactExpandoAttributes,
			Properties contactMappings, Properties contactExpandoMappings)
		throws Exception;

	public Attributes getLDAPGroupAttributes(
			long ldapServerId, UserGroup userGroup, User user,
			Properties groupMappings, Properties userMappings)
		throws Exception;

	public Modifications getLDAPGroupModifications(
			long ldapServerId, UserGroup userGroup, User user,
			Properties groupMappings, Properties userMappings,
			UserOperation userOperation)
		throws Exception;

	public Attributes getLDAPUserAttributes(
			long ldapServerId, User user, Properties userMappings)
		throws Exception;

	public Modifications getLDAPUserGroupModifications(
			long ldapServerId, List<UserGroup> userGroups, User user,
			Properties userMappings)
		throws Exception;

	public Modifications getLDAPUserModifications(
			User user, Map<String, Serializable> userExpandoAttributes,
			Properties userMappings, Properties userExpandoMappings)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getUserSafeLdapName(long, User, Properties)}
	 */
	@Deprecated
	public String getUserDNName(
			long ldapServerId, User user, Properties userMappings)
		throws Exception;

	public SafeLdapName getUserSafeLdapName(
			long ldapServerId, User user, Properties userMappings)
		throws Exception;

}