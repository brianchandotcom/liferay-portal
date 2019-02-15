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

package com.liferay.frontend.js.portlet.extender.internal.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.ManagedService;

/**
 * @author Ray Augé
 * @author Iván Zaera Avellón
 * @author Gustavo Mantuan
 */
public class JSPortlet extends MVCPortlet implements ManagedService {

	public JSPortlet(String packageName, String packageVersion) {
		_packageName = packageName;
		_packageVersion = packageVersion;
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			String portletElementId =
				"js-portlet-" + renderResponse.getNamespace();

			printWriter.print(
				StringUtil.replace(
					_TPL_HTML, new String[] {"[$PORTLET_ELEMENT_ID$]"},
					new String[] {portletElementId}));

			printWriter.print(
				StringUtil.replace(
					_TPL_JAVA_SCRIPT,
					new String[] {
						"[$CONTEXT_PATH$]", "[$PORTLET_ELEMENT_ID$]",
						"[$PORTLET_NAMESPACE$]", "[$PACKAGE_NAME$]",
						"[$PACKAGE_VERSION$]", "[$PORTLET_PREFERENCES$]",
						"[$SETTINGS$]"
					},
					new String[] {
						renderRequest.getContextPath(), portletElementId,
						renderResponse.getNamespace(), _packageName,
						_packageVersion,
						_toJSON(renderRequest.getPreferences()),
						_getSettings()
					}));

			printWriter.flush();
		}
		catch (IOException ioe) {
			_log.error("Unable to render HTML output", ioe);
		}
	}

	@Override
	public void updated(Dictionary<String, ?> properties) {
		if (properties == null) {
			_settings.set(Collections.emptyMap());

			return;
		}

		Map<String, String> settings = new HashMap<>();

		Enumeration<String> keys = properties.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (key.equals("service.pid")) {
				continue;
			}

			settings.put(key, String.valueOf(properties.get(key)));
		}

		_settings.set(settings);
	}

	private static String _loadTemplate(String name) {
		try (InputStream inputStream = JSPortlet.class.getResourceAsStream(
				"dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception e) {
			_log.error("Unable to read template " + name, e);
		}

		return StringPool.BLANK;
	}

	private String _escapeQuotes(String value) {
		return value.replaceAll("'", "\\'");
	}

	private String _getSettings() {
		Map<String, String> settings = _settings.get();

		StringBundler sb = new StringBundler();

		sb.append("{");

		String delimiter = "";

		for (Map.Entry<String, String> entry : settings.entrySet()) {
			sb.append(delimiter);
			sb.append("'");
			sb.append(_escapeQuotes(entry.getKey()));
			sb.append("':'");
			sb.append(_escapeQuotes(entry.getValue()));
			sb.append("'");

			delimiter = ", ";
		}

		sb.append("}");

		return sb.toString();
	}

	private String _toJSON(PortletPreferences portletPreferences) {
		JSONObject portletPreferencesJSONObject =
			JSONFactoryUtil.createJSONObject();

		Enumeration<String> portletPreferencesNames =
			portletPreferences.getNames();

		while (portletPreferencesNames.hasMoreElements()) {
			String key = portletPreferencesNames.nextElement();

			String[] values = portletPreferences.getValues(
				key, StringPool.EMPTY_ARRAY);

			if (values.length > 1) {
				portletPreferencesJSONObject.put(key, values);
			}
			else {
				portletPreferencesJSONObject.put(key, values[0]);
			}
		}

		return portletPreferencesJSONObject.toJSONString();
	}

	private static final String _TPL_HTML;

	private static final String _TPL_JAVA_SCRIPT;

	private static final Log _log = LogFactoryUtil.getLog(JSPortlet.class);

	static {
		_TPL_HTML = _loadTemplate("bootstrap.html.tpl");
		_TPL_JAVA_SCRIPT = _loadTemplate("bootstrap.js.tpl");
	}

	private final String _packageName;
	private final String _packageVersion;
	private final AtomicReference<Map<String, String>> _settings =
		new AtomicReference<>();

}