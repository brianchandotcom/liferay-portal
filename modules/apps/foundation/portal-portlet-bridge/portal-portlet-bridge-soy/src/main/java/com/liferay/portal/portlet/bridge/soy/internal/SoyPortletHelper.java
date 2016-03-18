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

package com.liferay.portal.portlet.bridge.soy.internal;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Bruno Basto
 */
public class SoyPortletControllersManager {

	public SoyPortletControllersManager(Bundle bundle) throws Exception {
		_bundle = bundle;

		_moduleName = getModuleName();

		_javaScriptTpl = getJavaScriptTpl();
	}

	public String getControllerName(String path) {
		String controllerName = _controllersMap.get(path);

		if (controllerName != null) {
			return controllerName;
		}

		URL url = _bundle.getEntry(
			"/META-INF/resources/".concat(path).concat(".es.js"));

		if (url != null) {
			controllerName = path.concat(".es");
		}
		else {
			controllerName = path.concat(".soy");
		}

		_controllersMap.put(path, controllerName);

		return controllerName;
	}

	public String getPortletJavaScript(
		Template template, String path, String portletNamespace) {

		if (_moduleName == null) {
			return StringPool.BLANK;
		}

		JSONObject contextJSONObject = JSONFactoryUtil.createJSONObject();

		for (String key : template.getKeys()) {
			if (Validator.equals(key, TemplateConstants.NAMESPACE)) {
				continue;
			}

			contextJSONObject.put(key, template.get(key));
		}

		contextJSONObject.put(
			"element", getPortletBoundaryId(portletNamespace));

		String javaScript = StringUtil.replace(
			_javaScriptTpl,
			new String[] {
				"$MODULE_NAME", "$CONTROLLER_NAME", "$PORTLET_NAMESPACE",
				"$CONTEXT"
			},
			new String[] {
				_moduleName, getControllerName(path), portletNamespace,
				contextJSONObject.toJSONString()
			});

		return javaScript;
	}

	public String getTemplateNamespace(String path) {
		if (_moduleName == null) {
			return path;
		}

		return "Templates.".concat(path).concat(".render");
	}

	protected String getJavaScriptTpl() throws Exception {
		if (_moduleName == null) {
			return StringPool.BLANK;
		}

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"com/liferay/portal/portlet/bridge/soy/bootstrap.js.tpl");

		return StringUtil.read(inputStream);
	}

	protected String getModuleName() throws Exception {
		URL url = _bundle.getEntry("package.json");

		if (url == null) {
			throw null;
		}

		String json = StringUtil.read(url.openStream());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		String moduleName = jsonObject.getString("name");

		if (Validator.isNull(moduleName)) {
			throw null;
		}

		return moduleName;
	}

	protected String getPortletBoundaryId(String portletNamespace) {
		StringBundler sb = new StringBundler(3);

		sb.append("#p_p_id");
		sb.append(HtmlUtil.escapeJS(portletNamespace));
		sb.append(" .portlet-content-container");

		return sb.toString();
	}

	private final Bundle _bundle;
	private final Map<String, String> _controllersMap = new HashMap<>();
	private final String _javaScriptTpl;
	private final String _moduleName;

}