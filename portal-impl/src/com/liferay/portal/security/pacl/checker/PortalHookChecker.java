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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;

import java.security.Permission;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalHookChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initCustomJspDir();
		initIndexers();
		initLanguagePropertiesLocales();
		initPortalPropertiesKeys();
		initServletFilters();
		initServices();
		initStrutsActionPaths();
	}

	public void checkPermission(Permission permission) {
		PortalHookPermission portalHookPermission =
			(PortalHookPermission)permission;

		String name = portalHookPermission.getName();
		Object subject = portalHookPermission.getSubject();

		if (name.equals(PORTAL_HOOK_PERMISSION_CUSTOM_JSP_DIR)) {
			if (!_customJspDir) {
				throwSecurityException(_log, "Attempted to set custom jsp dir");
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_INDEXER)) {
			String indexerClassName = (String)subject;

			if (!_indexers.contains(indexerClassName)) {
				throwSecurityException(
					_log, "Attempted to add indexer " + indexerClassName);
			}
		}
		else if (name.equals(
					PORTAL_HOOK_PERMISSION_LANGUAGE_PROPERTIES_LOCALE)) {

			Locale locale = (Locale)subject;

			if (!_languagePropertiesLanguageIds.contains(
					locale.getLanguage()) &&
				!_languagePropertiesLanguageIds.contains(
					locale.getLanguage() + "_" + locale.getCountry())) {

				throwSecurityException(
					_log, "Attempted to override locale " + locale);
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_PORTAL_PROPERTIES_KEY)) {
			String key = (String)subject;

			if (!_portalPropertiesKeys.contains(key)) {
				throwSecurityException(
					_log, "Attempted to set portal property " + key);
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVICE)) {
			String serviceType = (String)subject;

			if (!_services.contains(serviceType)) {
				throwSecurityException(
					_log, "Attempted to override service " + serviceType);
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_SERVLET_FILTERS)) {
			if (!_servletFilters) {
				throwSecurityException(
					_log, "Attempted to override serlvet filters");
			}
		}
		else if (name.equals(PORTAL_HOOK_PERMISSION_STRUTS_ACTION_PATH)) {
			String strutsActionPath = (String)subject;

			if (!_strutsActionPaths.contains(strutsActionPath)) {
				throwSecurityException(
					_log,
					"Attempted to use struts action path " + strutsActionPath);
			}
		}
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