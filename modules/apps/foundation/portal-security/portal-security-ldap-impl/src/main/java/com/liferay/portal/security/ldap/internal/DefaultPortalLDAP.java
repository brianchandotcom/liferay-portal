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

package com.liferay.portal.security.ldap.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.UserConverterKeys;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Jerry Niu
 * @author Scott Lee
 * @author Hervé Ménage
 * @author Samuel Kong
 * @author Ryan Park
 * @author Wesley Gong
 * @author Marcellus Tavares
 * @author Hugo Huijser
 * @author Edward Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.ldap.configuration.LDAPConfiguration",
	immediate = true, service = PortalLDAP.class
)
public class DefaultPortalLDAP implements PortalLDAP {

	@Override
	public LdapContext getContext(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String baseProviderURL = ldapServerConfiguration.baseProviderURL();
		String securityPrincipal = ldapServerConfiguration.securityPrincipal();
		String securityCredential =
			ldapServerConfiguration.securityCredential();

		return getContext(
			companyId, baseProviderURL, securityPrincipal, securityCredential);
	}

	@Override
	public LdapContext getContext(
			long companyId, String providerURL, String principal,
			String credentials)
		throws Exception {

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(companyId);

		Properties environmentProperties = new Properties();

		environmentProperties.put(
			Context.INITIAL_CONTEXT_FACTORY,
			systemLDAPConfiguration.factoryInitial());
		environmentProperties.put(Context.PROVIDER_URL, providerURL);
		environmentProperties.put(
			Context.REFERRAL, systemLDAPConfiguration.referral());
		environmentProperties.put(Context.SECURITY_CREDENTIALS, credentials);
		environmentProperties.put(Context.SECURITY_PRINCIPAL, principal);

		String[] connectionProperties =
			systemLDAPConfiguration.connectionProperties();

		for (String connectionProperty : connectionProperties) {
			String[] connectionPropertySplit = StringUtil.split(
				connectionProperty, CharPool.EQUAL);

			if (connectionPropertySplit.length != 2) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid LDAP connection property: " +
							connectionProperty);

					continue;
				}
			}

			environmentProperties.put(
				connectionPropertySplit[0], connectionPropertySplit[1]);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				MapUtil.toString(
					environmentProperties, null, Context.SECURITY_CREDENTIALS));
		}

		LdapContext ldapContext = null;

		try {
			ldapContext = new InitialLdapContext(environmentProperties, null);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to bind to the LDAP server");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return ldapContext;
	}

	@Override
	public Binding getGroup(long ldapServerId, long companyId, String groupName)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		LdapContext ldapContext = getContext(ldapServerId, companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ldapContext == null) {
				return null;
			}

			String groupsDN = ldapServerConfiguration.groupsDN();

			String groupFilter = ldapServerConfiguration.groupSearchFilter();

			_ldapFilterValidator.validate(
				groupFilter, "SystemLDAPConfiguration.groupSearchFilter");

			StringBundler sb = new StringBundler(
				Validator.isNotNull(groupFilter) ? 9 : 5);

			if (Validator.isNotNull(groupFilter)) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.AMPERSAND);
			}

			sb.append(StringPool.OPEN_PARENTHESIS);

			Properties groupMappings = _ldapSettings.getGroupMappings(
				ldapServerId, companyId);

			sb.append(groupMappings.getProperty("groupName"));

			sb.append(StringPool.EQUAL);
			sb.append(groupName);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (Validator.isNotNull(groupFilter)) {
				sb.append(groupFilter);
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ldapContext.search(groupsDN, sb.toString(), searchControls);

			if (enu.hasMoreElements()) {
				return enu.nextElement();
			}

			return null;
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		return getGroupAttributes(
			ldapServerId, companyId, ldapContext, fullDistinguishedName, false);
	}

	@Override
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName, boolean includeReferenceAttributes)
		throws Exception {

		Properties groupMappings = _ldapSettings.getGroupMappings(
			ldapServerId, companyId);

		List<String> mappedGroupAttributeIds = new ArrayList<>();

		mappedGroupAttributeIds.add(groupMappings.getProperty("groupName"));
		mappedGroupAttributeIds.add(groupMappings.getProperty("description"));

		if (includeReferenceAttributes) {
			mappedGroupAttributeIds.add(groupMappings.getProperty("user"));
		}

		Attributes attributes = _getAttributes(
			ldapContext, fullDistinguishedName,
			mappedGroupAttributeIds.toArray(
				new String[mappedGroupAttributeIds.size()]));

		if (_log.isDebugEnabled()) {
			if ((attributes == null) || (attributes.size() == 0)) {
				_log.debug(
					"No LDAP group attributes found for " +
						fullDistinguishedName);
			}
			else {
				for (String attributeId : mappedGroupAttributeIds) {
					Attribute attribute = attributes.get(attributeId);

					if (attribute == null) {
						continue;
					}

					_log.debug("LDAP group attribute " + attribute.toString());
				}
			}
		}

		return attributes;
	}

	@Override
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			List<SearchResult> searchResults)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, cookie, maxResults, baseDN, groupFilter,
			null, searchResults);
	}

	@Override
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, cookie, maxResults, baseDN, groupFilter,
			attributeIds, searchResults);
	}

	@Override
	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String groupsDN = ldapServerConfiguration.groupsDN();
		String groupSearchFilter = ldapServerConfiguration.groupSearchFilter();

		return getGroups(
			companyId, ldapContext, cookie, maxResults, groupsDN,
			groupSearchFilter, searchResults);
	}

	@Override
	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String groupsDN = ldapServerConfiguration.groupsDN();
		String groupSearchFilter = ldapServerConfiguration.groupSearchFilter();

		return getGroups(
			companyId, ldapContext, cookie, maxResults, groupsDN,
			groupSearchFilter, attributeIds, searchResults);
	}

	@Override
	public String getGroupsDN(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		return ldapServerConfiguration.groupsDN();
	}

	@Override
	public long getLdapServerId(
			long companyId, String screenName, String emailAddress)
		throws Exception {

		long preferredLDAPServerId = _ldapSettings.getPreferredLDAPServerId(
			companyId, screenName);

		if ((preferredLDAPServerId >= 0) &&
			hasUser(
				preferredLDAPServerId, companyId, screenName, emailAddress)) {

			return preferredLDAPServerId;
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(companyId);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			if (hasUser(
					ldapServerConfiguration.ldapServerId(), companyId,
					screenName, emailAddress)) {

				return ldapServerConfiguration.ldapServerId();
			}
		}

		if (!ListUtil.isEmpty(ldapServerConfigurations)) {
			LDAPServerConfiguration ldapServerConfiguration =
				ldapServerConfigurations.get(0);

			return ldapServerConfiguration.ldapServerId();
		}

		return LDAPServerConfiguration.LDAP_SERVER_ID_DEFAULT;
	}

	@Override
	public Attribute getMultivaluedAttribute(
			long companyId, LdapContext ldapContext, String baseDN,
			String filter, Attribute attribute)
		throws Exception {

		if (attribute.size() > 0) {
			return attribute;
		}

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(companyId);

		String[] attributeIds =
			{_getNextRange(systemLDAPConfiguration, attribute.getID())};

		while (true) {
			List<SearchResult> searchResults = new ArrayList<>();

			searchLDAP(
				companyId, ldapContext, new byte[0], 0, baseDN, filter,
				attributeIds, searchResults);

			if (searchResults.size() != 1) {
				break;
			}

			SearchResult searchResult = searchResults.get(0);

			Attributes attributes = searchResult.getAttributes();

			if (attributes.size() != 1) {
				break;
			}

			NamingEnumeration<? extends Attribute> enu = null;

			try {
				enu = attributes.getAll();

				if (!enu.hasMoreElements()) {
					break;
				}

				Attribute curAttribute = enu.nextElement();

				for (int i = 0; i < curAttribute.size(); i++) {
					attribute.add(curAttribute.get(i));
				}

				if (StringUtil.endsWith(
						curAttribute.getID(), StringPool.STAR) ||
					(curAttribute.size() <
						systemLDAPConfiguration.rangeSize())) {

					break;
				}
			}
			finally {
				if (enu != null) {
					enu.close();
				}
			}

			attributeIds[0] = _getNextRange(
				systemLDAPConfiguration, attributeIds[0]);
		}

		return attribute;
	}

	/**
	 * @deprecated As of 1.0.0
	 */
	@Deprecated
	@Override
	public String getNameInNamespace(
			long ldapServerId, long companyId, Binding binding)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String baseDN = ldapServerConfiguration.baseDN();

		String name = binding.getName();

		if (name.startsWith(StringPool.QUOTE) &&
			name.endsWith(StringPool.QUOTE)) {

			name = name.substring(1, name.length() - 1);
		}

		if (Validator.isNull(baseDN)) {
			return name;
		}
		else {
			return name.concat(StringPool.COMMA).concat(baseDN);
		}
	}

	@Override
	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception {

		return getUser(
			ldapServerId, companyId, screenName, emailAddress, false);
	}

	@Override
	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress, boolean checkOriginalEmail)
		throws Exception {

		LdapContext ldapContext = getContext(ldapServerId, companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ldapContext == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"No LDAP server configuration available for LDAP ",
							"server ", String.valueOf(ldapServerId),
							" and company ", String.valueOf(companyId)));
				}

				return null;
			}

			LDAPServerConfiguration ldapServerConfiguration =
				_ldapServerConfigurationProvider.getConfiguration(
					companyId, ldapServerId);

			String baseDN = ldapServerConfiguration.baseDN();

			String userSearchFilter =
				ldapServerConfiguration.userSearchFilter();

			_ldapFilterValidator.validate(
				userSearchFilter, "LDAPServerConfiguration.userSearchFilter");

			StringBundler sb = new StringBundler(
				Validator.isNotNull(userSearchFilter) ? 9 : 5);

			if (Validator.isNotNull(userSearchFilter)) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.AMPERSAND);
			}

			sb.append(StringPool.OPEN_PARENTHESIS);

			String loginMapping = null;
			String login = null;

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);

			String authType = PrefsPropsUtil.getString(
				companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				_companySecurityAuthType);

			if (authType.equals(CompanyConstants.AUTH_TYPE_SN) &&
				!PrefsPropsUtil.getBoolean(
					companyId,
					PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

				loginMapping = userMappings.getProperty("screenName");
				login = screenName;
			}
			else {
				loginMapping = userMappings.getProperty("emailAddress");
				login = emailAddress;
			}

			sb.append(loginMapping);
			sb.append(StringPool.EQUAL);
			sb.append(login);

			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (Validator.isNotNull(userSearchFilter)) {
				sb.append(userSearchFilter);
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ldapContext.search(baseDN, sb.toString(), searchControls);

			if (enu.hasMoreElements()) {
				return enu.nextElement();
			}

			if (checkOriginalEmail) {
				String originalEmailAddress =
					UserImportTransactionThreadLocal.getOriginalEmailAddress();

				if (Validator.isNotNull(originalEmailAddress) &&
					!emailAddress.equals(originalEmailAddress)) {

					return getUser(
						ldapServerId, companyId, screenName,
						originalEmailAddress, false);
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to retrieve user with LDAP server ",
						String.valueOf(ldapServerId), ", company ",
						String.valueOf(companyId), ", loginMapping ",
						loginMapping, ", and login ", login));
			}

			return null;
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public Attributes getUserAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		Properties userMappings = _ldapSettings.getUserMappings(
			ldapServerId, companyId);
		Properties userExpandoMappings = _ldapSettings.getUserExpandoMappings(
			ldapServerId, companyId);

		PropertiesUtil.merge(userMappings, userExpandoMappings);

		Properties contactMappings = _ldapSettings.getContactMappings(
			ldapServerId, companyId);
		Properties contactExpandoMappings =
			_ldapSettings.getContactExpandoMappings(ldapServerId, companyId);

		PropertiesUtil.merge(contactMappings, contactExpandoMappings);

		PropertiesUtil.merge(userMappings, contactMappings);

		Collection<Object> values = userMappings.values();

		String[] mappedUserAttributeIds = ArrayUtil.toStringArray(
			values.toArray(new Object[userMappings.size()]));

		Attributes attributes = _getAttributes(
			ldapContext, fullDistinguishedName, mappedUserAttributeIds);

		if (_log.isDebugEnabled()) {
			if ((attributes == null) || (attributes.size() == 0)) {
				_log.debug(
					"No LDAP user attributes found for:: " +
						fullDistinguishedName);
			}
			else {
				for (String attributeId : mappedUserAttributeIds) {
					Attribute attribute = attributes.get(attributeId);

					if (attribute == null) {
						continue;
					}

					String attributeID = StringUtil.toLowerCase(
						attribute.getID());

					if (attributeID.indexOf("password") > -1) {
						Attribute clonedAttribute =
							(Attribute)attribute.clone();

						clonedAttribute.clear();

						clonedAttribute.add("********");

						_log.debug(
							"LDAP user attribute " +
								clonedAttribute.toString());

						continue;
					}

					_log.debug("LDAP user attribute " + attribute.toString());
				}
			}
		}

		return attributes;
	}

	@Override
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			List<SearchResult> searchResults)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, cookie, maxResults, baseDN, userFilter,
			null, searchResults);
	}

	@Override
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, cookie, maxResults, baseDN, userFilter,
			attributeIds, searchResults);
	}

	@Override
	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String baseDN = ldapServerConfiguration.baseDN();
		String userSearchFilter = ldapServerConfiguration.userSearchFilter();

		return getUsers(
			companyId, ldapContext, cookie, maxResults, baseDN,
			userSearchFilter, searchResults);
	}

	@Override
	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String baseDN = ldapServerConfiguration.baseDN();
		String userSearchFilter = ldapServerConfiguration.userSearchFilter();

		return getUsers(
			companyId, ldapContext, cookie, maxResults, baseDN,
			userSearchFilter, attributeIds, searchResults);
	}

	@Override
	public String getUsersDN(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		return ldapServerConfiguration.usersDN();
	}

	@Override
	public boolean hasUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception {

		if (getUser(ldapServerId, companyId, screenName, emailAddress) !=
				null) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception {

		LdapContext ldapContext = getContext(ldapServerId, companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ldapContext == null) {
				return false;
			}

			Properties groupMappings = _ldapSettings.getGroupMappings(
				ldapServerId, companyId);

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(groupMappings.getProperty("user"));
			sb.append(StringPool.EQUAL);
			sb.append(userDN);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ldapContext.search(groupDN, sb.toString(), searchControls);

			if (enu.hasMoreElements()) {
				return true;
			}
		}
		catch (NameNotFoundException nnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to determine if user DN ", userDN,
						" is a member of group DN ", groupDN),
					nnfe);
			}
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}

		return false;
	}

	@Override
	public boolean isUserGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception {

		LdapContext ldapContext = getContext(ldapServerId, companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ldapContext == null) {
				return false;
			}

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(userMappings.getProperty(UserConverterKeys.GROUP));
			sb.append(StringPool.EQUAL);
			sb.append(groupDN);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ldapContext.search(userDN, sb.toString(), searchControls);

			if (enu.hasMoreElements()) {
				return true;
			}
		}
		catch (NameNotFoundException nnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to determine if group DN ", groupDN,
						" is a member of user DN ", userDN),
					nnfe);
			}
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}

		return false;
	}

	@Override
	public byte[] searchLDAP(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String filter, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		SearchControls searchControls = new SearchControls(
			SearchControls.SUBTREE_SCOPE, maxResults, 0, attributeIds, false,
			false);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (cookie != null) {
				SystemLDAPConfiguration systemLDAPConfiguration =
					_systemLDAPConfigurationProvider.getConfiguration(
						companyId);

				if (cookie.length == 0) {
					ldapContext.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								systemLDAPConfiguration.pageSize(),
								Control.CRITICAL)
						});
				}
				else {
					ldapContext.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								systemLDAPConfiguration.pageSize(), cookie,
								Control.CRITICAL)
						});
				}

				enu = ldapContext.search(baseDN, filter, searchControls);

				while (enu.hasMoreElements()) {
					searchResults.add(enu.nextElement());
				}

				return _getCookie(ldapContext.getResponseControls());
			}
		}
		catch (OperationNotSupportedException onse) {
			if (enu != null) {
				enu.close();
			}

			ldapContext.setRequestControls(null);

			enu = ldapContext.search(baseDN, filter, searchControls);

			while (enu.hasMoreElements()) {
				searchResults.add(enu.nextElement());
			}
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			ldapContext.setRequestControls(null);
		}

		return null;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY, unbind = "-")
	protected void setLdapFilterValidator(
		LDAPFilterValidator ldapFilterValidator) {

		_ldapFilterValidator = ldapFilterValidator;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setLdapSettings(LDAPSettings ldapSettings) {
		_ldapSettings = ldapSettings;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_companySecurityAuthType = GetterUtil.getString(
			props.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE));
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration)",
		unbind = "-"
	)
	protected void setSystemLDAPConfigurationProvider(
		ConfigurationProvider<SystemLDAPConfiguration>
			systemLDAPConfigurationProvider) {

		_systemLDAPConfigurationProvider = systemLDAPConfigurationProvider;
	}

	private Attributes _getAttributes(
			LdapContext ldapContext, String fullDistinguishedName,
			String[] attributeIds)
		throws Exception {

		Name fullDN = new CompositeName().add(fullDistinguishedName);

		Attributes attributes = null;

		String[] auditAttributeIds = {
			"creatorsName", "createTimestamp", "modifiersName",
			"modifyTimestamp"
		};

		if (attributeIds == null) {

			// Get complete listing of LDAP attributes (slow)

			attributes = ldapContext.getAttributes(fullDN);

			NamingEnumeration<? extends Attribute> enu = null;

			try {
				Attributes auditAttributes = ldapContext.getAttributes(
					fullDN, auditAttributeIds);

				enu = auditAttributes.getAll();

				while (enu.hasMoreElements()) {
					attributes.put(enu.nextElement());
				}
			}
			finally {
				if (enu != null) {
					enu.close();
				}
			}
		}
		else {

			// Get specified LDAP attributes

			int attributeCount = attributeIds.length + auditAttributeIds.length;

			String[] allAttributeIds = new String[attributeCount];

			System.arraycopy(
				attributeIds, 0, allAttributeIds, 0, attributeIds.length);
			System.arraycopy(
				auditAttributeIds, 0, allAttributeIds, attributeIds.length,
				auditAttributeIds.length);

			attributes = ldapContext.getAttributes(fullDN, allAttributeIds);
		}

		return attributes;
	}

	private byte[] _getCookie(Control[] controls) {
		if (controls == null) {
			return null;
		}

		for (Control control : controls) {
			if (control instanceof PagedResultsResponseControl) {
				PagedResultsResponseControl pagedResultsResponseControl =
					(PagedResultsResponseControl)control;

				return pagedResultsResponseControl.getCookie();
			}
		}

		return null;
	}

	private String _getNextRange(
		SystemLDAPConfiguration systemLDAPConfiguration, String attributeId) {

		String originalAttributeId = null;
		int start = 0;
		int end = 0;

		int x = attributeId.indexOf(CharPool.SEMICOLON);

		if (x < 0) {
			originalAttributeId = attributeId;
			end = systemLDAPConfiguration.rangeSize() - 1;
		}
		else {
			int y = attributeId.indexOf(CharPool.EQUAL, x);

			int z = attributeId.indexOf(CharPool.DASH, y);

			originalAttributeId = attributeId.substring(0, x);
			start = GetterUtil.getInteger(attributeId.substring(y + 1, z));
			end = GetterUtil.getInteger(attributeId.substring(z + 1));

			start += systemLDAPConfiguration.rangeSize();
			end += systemLDAPConfiguration.rangeSize();
		}

		StringBundler sb = new StringBundler(6);

		sb.append(originalAttributeId);
		sb.append(StringPool.SEMICOLON);
		sb.append("range=");
		sb.append(start);
		sb.append(StringPool.DASH);
		sb.append(end);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPortalLDAP.class);

	private String _companySecurityAuthType;
	private LDAPFilterValidator _ldapFilterValidator;
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private LDAPSettings _ldapSettings;
	private ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;

}