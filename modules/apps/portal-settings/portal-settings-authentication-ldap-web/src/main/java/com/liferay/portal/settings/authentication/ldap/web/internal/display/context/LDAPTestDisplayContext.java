/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.authentication.ldap.web.internal.display.context;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.fips.FIPSModeValidator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.SafeLdapContext;
import com.liferay.portal.security.ldap.SafeLdapFilter;
import com.liferay.portal.security.ldap.SafeLdapNameFactory;
import com.liferay.portal.security.ldap.SafePortalLDAP;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.settings.authentication.ldap.web.internal.portlet.action.ActionUtil;
import com.liferay.portal.settings.authentication.ldap.web.internal.portlet.util.ConfigurationProviderUtil;
import com.liferay.portal.settings.authentication.ldap.web.internal.util.SafePortalLDAPUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchResult;

/**
 * @author Caio Farias
 */
public class LDAPTestDisplayContext {

	public LDAPTestDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_baseDN = ParamUtil.getString(httpServletRequest, "baseDN");
		_baseProviderURL = ParamUtil.getString(
			httpServletRequest, "baseProviderURL");
		_companyId = ActionUtil.getCompanyId(httpServletRequest);
	}

	public String getBaseProviderURL() {
		return _baseProviderURL;
	}

	public Properties getGroupMappings() throws IOException {
		if (_groupMappings == null) {
			_groupMappings = _getMappings(
				"groupMapping", "description", "groupName", "user");
		}

		return _groupMappings;
	}

	public List<SearchResult> getGroupSearchResults(
			String[] attributeIds, SafeLdapContext safeLdapContext,
			SafeLdapFilter safeLdapFilter)
		throws Exception {

		return _getSearchResults(
			searchResults -> {
				SafePortalLDAP safePortalLDAP =
					SafePortalLDAPUtil.getSafePortalLDAP();

				safePortalLDAP.getGroups(
					_companyId, safeLdapContext, new byte[0], 20,
					SafeLdapNameFactory.fromUnsafe(_baseDN), safeLdapFilter,
					attributeIds, searchResults);
			});
	}

	public Attribute getMultivaluedAttribute(
			Attribute attribute, SafeLdapContext safeLdapContext,
			SafeLdapFilter safeLdapFilter)
		throws Exception {

		SafePortalLDAP safePortalLDAP = SafePortalLDAPUtil.getSafePortalLDAP();

		return safePortalLDAP.getMultivaluedAttribute(
			_companyId, safeLdapContext,
			SafeLdapNameFactory.fromUnsafe(_baseDN), safeLdapFilter, attribute);
	}

	public SafeLdapContext getSafeLdapContext() {
		SafePortalLDAP safePortalLDAP = SafePortalLDAPUtil.getSafePortalLDAP();

		return safePortalLDAP.getSafeLdapContext(
			_companyId, _baseProviderURL,
			ParamUtil.getString(_httpServletRequest, "principal"),
			_getCredentials());
	}

	public Properties getUserMappings() throws IOException {
		if (_userMappings == null) {
			_userMappings = _getMappings(
				"userMapping", "emailAddress", "firstName", "fullName", "group",
				"jobTitle", "lastName", "password", "screenName");
		}

		return _userMappings;
	}

	public List<SearchResult> getUserSearchResults(
			String[] attributeIds, SafeLdapContext safeLdapContext,
			SafeLdapFilter safeLdapFilter)
		throws Exception {

		return _getSearchResults(
			searchResults -> {
				SafePortalLDAP safePortalLDAP =
					SafePortalLDAPUtil.getSafePortalLDAP();

				safePortalLDAP.getUsers(
					_companyId, safeLdapContext, new byte[0], 20,
					SafeLdapNameFactory.fromUnsafe(_baseDN), safeLdapFilter,
					attributeIds, searchResults);
			});
	}

	public boolean isValidBaseProviderURL() {
		try {
			FIPSModeValidator.validateURL(_baseProviderURL);

			return true;
		}
		catch (SecurityException securityException) {
			if (_log.isDebugEnabled()) {
				_log.debug(securityException);
			}

			return false;
		}
	}

	private String _getCredentials() {
		String credentials = ParamUtil.getString(
			_httpServletRequest, "credentials");

		if (!credentials.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
			return credentials;
		}

		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider =
				ConfigurationProviderUtil.getLDAPServerConfigurationProvider();

		LDAPServerConfiguration ldapServerConfiguration =
			ldapServerConfigurationProvider.getConfiguration(
				_companyId,
				ParamUtil.getLong(_httpServletRequest, "ldapServerId"));

		return ldapServerConfiguration.securityCredential();
	}

	private Properties _getMappings(String prefix, String... names)
		throws IOException {

		StringBundler sb = new StringBundler(names.length * 4);

		for (String name : names) {
			sb.append(name);
			sb.append(StringPool.EQUAL);
			sb.append(
				ParamUtil.getString(
					_httpServletRequest,
					prefix + StringUtil.upperCaseFirstLetter(name)));
			sb.append(StringPool.NEW_LINE);
		}

		return PropertiesUtil.load(sb.toString());
	}

	private List<SearchResult> _getSearchResults(
			UnsafeConsumer<List<SearchResult>, Exception> unsafeConsumer)
		throws Exception {

		List<SearchResult> searchResults = new ArrayList<>();

		try {
			unsafeConsumer.accept(searchResults);

			return searchResults;
		}
		catch (InvalidNameException | NameNotFoundException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPTestDisplayContext.class);

	private final String _baseDN;
	private final String _baseProviderURL;
	private final long _companyId;
	private Properties _groupMappings;
	private final HttpServletRequest _httpServletRequest;
	private Properties _userMappings;

}