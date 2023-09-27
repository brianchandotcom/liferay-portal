/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.cors.internal.util;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.remote.cors.internal.CORSSupport;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Joao Victor Alves
 */
public class PortalCORSRegistryUtil {

	public static Map<String, Dictionary<String, ?>>
		getConfigurationPidsProperties() {

		return _configurationPidsProperties;
	}

	public static Set<Long> getKeySetUrlPatternMappers() {
		return _urlPatternMappers.keySet();
	}

	public static URLPatternMapper<CORSSupport> getUrlPatternMappers(
		long companyId) {

		return _urlPatternMappers.get(companyId);
	}

	public static Collection<Dictionary<String, ?>>
		getValuesConfigurationPidsProperties() {

		return _configurationPidsProperties.values();
	}

	public static Dictionary<String, ?> removeConfigurationPidsProperties(
		String pid) {

		return _configurationPidsProperties.remove(pid);
	}

	public static void removeUrlPatternMappers(long companyId) {
		_urlPatternMappers.remove(companyId);
	}

	public static Dictionary<String, ?> updateConfigurationPidsProperties(
		String pid, Dictionary<String, ?> properties) {

		return _configurationPidsProperties.put(pid, properties);
	}

	public static void updateUrlPattensMappers(
		long companyId, URLPatternMapper<CORSSupport> values) {

		_urlPatternMappers.put(companyId, values);
	}

	private static final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = Collections.synchronizedMap(
			new LinkedHashMap<>());
	private static final Map<Long, URLPatternMapper<CORSSupport>>
		_urlPatternMappers = Collections.synchronizedMap(new LinkedHashMap<>());

}