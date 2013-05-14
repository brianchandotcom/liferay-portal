/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.security.Permission;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalHookChecker extends BaseChecker {

	@Override
	public void afterPropertiesSet() {
		initCustomJspDir();
		initIndexers();
		initLanguagePropertiesLocales();
		initPortalPropertiesKeys();
		initServletFilters();
		initServices();
		initStrutsActionPaths();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		PortalHookPermission portalHookPermission =
			(PortalHookPermission)arguments[0];

		String name = portalHookPermission.getName();
		Object subject = portalHookPermission.getSubject();

		String key = null;
		String value = null;

		if (name.equals(PORTAL_HOOK_PERMISSION_CUSTOM_JSP_DIR)) {
			key = "security-manager-hook-custom-jsp-dir-enabled";
			value = "true";
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_INDEXER)) {
			key = "security-manager-hook-indexers";
			value = (String)subject;
		}
		else if (name.equals(
					PORTAL_HOOK_PERMISSION_LANGUAGE_PROPERTIES_LOCALE)) {

			key = "security-manager-hook-language-properties-locales";

			Locale locale = (Locale)subject;

			value = LocaleUtil.toLanguageId(locale);
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_PORTAL_PROPERTIES_KEY)) {
			key = "security-manager-hook-portal-properties-keys";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVICE)) {
			key = "security-manager-hook-services";
			value = (String)subject;
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVLET_FILTERS)) {
			key = "security-manager-hook-servlet-filters-enabled";
			value = "true";
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_STRUTS_ACTION_PATH)) {
			key = "security-manager-hook-struts-action-paths";
			value = (String)subject;
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(value);

		return authorizationProperty;
	}

	@Override
	public boolean implies(Permission permission) {
		PortalHookPermission portalHookPermission =
			(PortalHookPermission)permission;

		String name = portalHookPermission.getName();
		Object subject = portalHookPermission.getSubject();

		if (name.equals(PORTAL_HOOK_PERMISSION_CUSTOM_JSP_DIR)) {
			if (!_customJspDir) {
				logSecurityException(_log, "Attempted to set custom jsp dir");

				return false;
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_INDEXER)) {
			String indexerClassName = (String)subject;

			if (!_indexers.contains(indexerClassName)) {
				logSecurityException(
					_log, "Attempted to add indexer " + indexerClassName);

				return false;
			}
		}
		else if (name.equals(
					PORTAL_HOOK_PERMISSION_LANGUAGE_PROPERTIES_LOCALE)) {

			Locale locale = (Locale)subject;

			if (!_languagePropertiesLanguageIds.contains(
					locale.getLanguage()) &&
				!_languagePropertiesLanguageIds.contains(
					locale.getLanguage() + "_" + locale.getCountry())) {

				logSecurityException(
					_log, "Attempted to override locale " + locale);

				return false;
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_PORTAL_PROPERTIES_KEY)) {
			String key = (String)subject;

			if (!_portalPropertiesKeys.contains(key)) {
				logSecurityException(
					_log, "Attempted to set portal property " + key);

				return false;
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVICE)) {
			String serviceType = (String)subject;

			if (!_services.contains(serviceType)) {
				logSecurityException(
					_log, "Attempted to override service " + serviceType);

				return false;
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVLET_FILTERS)) {
			if (!_servletFilters) {
				logSecurityException(
					_log, "Attempted to override serlvet filters");

				return false;
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_STRUTS_ACTION_PATH)) {
			String strutsActionPath = (String)subject;

			if (!_strutsActionPaths.contains(strutsActionPath)) {
				logSecurityException(
					_log,
					"Attempted to use struts action path " + strutsActionPath);

				return false;
			}
		}

		return true;
	}

	protected void initCustomJspDir() {
		_customJspDir = getPropertyBoolean(
			"security-manager-hook-custom-jsp-dir-enabled");

		if (_log.isDebugEnabled() && _customJspDir) {
			_log.debug("Allowing custom JSP dir");
		}
	}

	protected void initIndexers() {
		_indexers = getPropertySet("security-manager-hook-indexers");

		if (_log.isDebugEnabled()) {
			Set<String> indexers = new TreeSet<String>(_indexers);

			for (String indexer : indexers) {
				_log.debug("Allowing indexer " + indexer);
			}
		}
	}

	protected void initLanguagePropertiesLocales() {
		_languagePropertiesLanguageIds = getPropertySet(
			"security-manager-hook-language-properties-locales");

		if (_log.isDebugEnabled()) {
			Set<String> languageIds = new TreeSet<String>(
				_languagePropertiesLanguageIds);

			for (String languageId : languageIds) {
				_log.debug("Allowing locale " + languageId);
			}
		}
	}

	protected void initPortalPropertiesKeys() {
		_portalPropertiesKeys = getPropertySet(
			"security-manager-hook-portal-properties-keys");

		if (_log.isDebugEnabled()) {
			Set<String> keys = new TreeSet<String>(_portalPropertiesKeys);

			for (String key : keys) {
				_log.debug("Allowing portal.properties key " + key);
			}
		}
	}

	protected void initServices() {
		_services = getPropertySet("security-manager-hook-services");

		if (_log.isDebugEnabled()) {
			Set<String> services = new TreeSet<String>(_services);

			for (String service : services) {
				_log.debug("Allowing service " + service);
			}
		}
	}

	protected void initServletFilters() {
		_servletFilters = getPropertyBoolean(
			"security-manager-hook-servlet-filters-enabled");

		if (_log.isDebugEnabled() && _servletFilters) {
			_log.debug("Allowing servlet filters");
		}
	}

	protected void initStrutsActionPaths() {
		_strutsActionPaths = getPropertySet(
			"security-manager-hook-struts-action-paths");

		if (_log.isDebugEnabled()) {
			Set<String> strutsActionPaths = new TreeSet<String>(
				_strutsActionPaths);

			for (String strutsActionPath : strutsActionPaths) {
				_log.debug("Allowing Struts action path " + strutsActionPath);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalHookChecker.class);

	private boolean _customJspDir;
	private Set<String> _indexers;
	private Set<String> _languagePropertiesLanguageIds;
	private Set<String> _portalPropertiesKeys;
	private Set<String> _services;
	private boolean _servletFilters;
	private Set<String> _strutsActionPaths;

}