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

package com.liferay.site.initializer.extender.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Rafael Praxedes
 */
public class SiteInitializerUtil {

	public static String read(Bundle bundle, String fileName, URL url)
		throws Exception {

		String urlPath = url.getPath();

		URL entryURL = bundle.getEntry(
			urlPath.substring(0, urlPath.lastIndexOf("/") + 1) + fileName);

		try (InputStream inputStream = entryURL.openStream()) {
			return StringUtil.read(entryURL.openStream());
		}
	}

	public static String read(
			String resourcePath, ServletContext servletContext)
		throws Exception {

		try (InputStream inputStream = servletContext.getResourceAsStream(
				resourcePath)) {

			if (inputStream == null) {
				return null;
			}

			return StringUtil.read(inputStream);
		}
	}

	public static Map<Locale, String> toMap(String values) {
		return toMap(StringPool.BLANK, values);
	}

	public static Map<Locale, String> toMap(String prefix, String values) {
		if (Validator.isBlank(values)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> map = new HashMap<>();

		Map<String, String> valuesMap = ObjectMapperUtil.readValue(
			HashMap.class, values);

		for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
			map.put(
				LocaleUtil.fromLanguageId(entry.getKey()),
				prefix + entry.getValue());
		}

		return map;
	}

}