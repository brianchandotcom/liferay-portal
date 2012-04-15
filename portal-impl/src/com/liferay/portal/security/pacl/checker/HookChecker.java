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
import com.liferay.portal.security.pacl.PACLPolicy;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class HookChecker extends BaseChecker {

	public HookChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		initCustomJspDir();
		initLanguagePropertiesLocales();
		initPortalPropertiesKeys();
		initServletFilters();
		initServices();
	}

	public boolean hasCustomJspDir() {
		return _customJspDir;
	}

	public boolean hasLanguagePropertiesLocale(Locale locale) {
		if (_languagePropertiesLanguageIds.contains(locale.getLanguage())) {
			return true;
		}

		if (_languagePropertiesLanguageIds.contains(
				locale.getLanguage() + "_" + locale.getCountry())) {

			return true;
		}

		return false;
	}

	public boolean hasPortalPropertiesKey(String key) {
		return _portalPropertiesKeys.contains(key);
	}

	public boolean hasService(String className) {
		return _services.contains(className);
	}

	public boolean hasServletFilters() {
		return _servletFilters;
	}

	protected void initCustomJspDir() {
		_customJspDir = getPropertyBoolean(
			"security-manager-hook-custom-jsp-dir-enabled");
	}

	protected void initLanguagePropertiesLocales() {
		_languagePropertiesLanguageIds = getPropertySet(
			"security-manager-hook-language-properties-locales");

		if (_log.isDebugEnabled()) {
			Set<String> languageIds = new TreeSet<String>(
				_languagePropertiesLanguageIds);

			for (String languageId : languageIds) {
				_log.debug("Allowing language " + languageId);
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
	}

	protected void initServletFilters() {
		_servletFilters = getPropertyBoolean(
			"security-manager-hook-servlet-filters-enabled");
	}

	private static Log _log = LogFactoryUtil.getLog(HookChecker.class);

	private boolean _customJspDir;
	private Set<String> _languagePropertiesLanguageIds = Collections.emptySet();
	private Set<String> _portalPropertiesKeys = Collections.emptySet();
	private Set<String> _services = Collections.emptySet();
	private boolean _servletFilters;

}