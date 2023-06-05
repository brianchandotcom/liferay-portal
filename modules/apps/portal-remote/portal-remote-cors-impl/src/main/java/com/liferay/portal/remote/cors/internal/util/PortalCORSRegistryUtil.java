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