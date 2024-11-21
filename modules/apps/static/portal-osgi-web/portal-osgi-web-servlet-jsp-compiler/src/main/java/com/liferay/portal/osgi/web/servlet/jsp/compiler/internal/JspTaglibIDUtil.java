/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Collections;
import java.util.Map;

import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.descriptor.XmlIdentifiers;

/**
 * @author Dante Wang
 */
public class JspTaglibIDUtil {

	public static final Map<String, String> servletApiPublicIdsMap;
	public static final Map<String, String> servletApiSystemIdsMap;

	static {
		Map<String, String> publicIdsMap = HashMapBuilder.putAll(
			DigesterFactory.SERVLET_API_PUBLIC_IDS
		).build();

		_resolveId(
			publicIdsMap, XmlIdentifiers.TLD_11_PUBLIC,
			"web-jsptaglibrary_1_1.dtd", false);
		_resolveId(
			publicIdsMap, XmlIdentifiers.TLD_12_PUBLIC,
			"web-jsptaglibrary_1_2.dtd", false);

		Map<String, String> systemIdsMap = HashMapBuilder.putAll(
			DigesterFactory.SERVLET_API_SYSTEM_IDS
		).build();

		_resolveId(
			systemIdsMap, XmlIdentifiers.TLD_20_XSD,
			"web-jsptaglibrary_2_0.xsd", false);
		_resolveId(
			systemIdsMap, XmlIdentifiers.TLD_21_XSD,
			"web-jsptaglibrary_2_1.xsd", false);

		_resolveId(systemIdsMap, "jsp_2_0.xsd", "jsp_2_0.xsd", true);
		_resolveId(systemIdsMap, "jsp_2_1.xsd", "jsp_2_1.xsd", true);
		_resolveId(systemIdsMap, "jsp_2_2.xsd", "jsp_2_2.xsd", true);
		_resolveId(systemIdsMap, "jsp_2_3.xsd", "jsp_2_3.xsd", true);

		servletApiPublicIdsMap = Collections.unmodifiableMap(publicIdsMap);
		servletApiSystemIdsMap = Collections.unmodifiableMap(systemIdsMap);
	}

	private static void _resolveId(
		Map<String, String> ids, String id, String name, boolean addSelf) {

		if (ids.containsKey(id)) {
			return;
		}

		Class<?> clazz = JspTaglibIDUtil.class;

		URL url = clazz.getResource(
			"/javax/servlet/jsp/resources/".concat(name));

		String location = null;

		if (url != null) {
			location = url.toExternalForm();
		}

		if (location != null) {
			ids.put(id, location);

			if (addSelf) {
				ids.put(location, location);
			}
		}
	}

}