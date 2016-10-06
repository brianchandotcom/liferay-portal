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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.soy.utils.SoyJavaScriptRenderer;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;

/**
 * @author Bruno Basto
 */
public class SoyPortletHelper {

	public SoyPortletHelper(Bundle bundle) throws Exception {
		_bundle = bundle;
		_moduleName = getModuleName();
		_soyJavaScriptRenderer = new SoyJavaScriptRenderer();
	}

	public String getPortletJavaScript(
		Template template, String path, String portletNamespace,
		Set<String> additionalRequiredModules) {

		if (_moduleName == null) {
			return StringPool.BLANK;
		}

		Set<String> requiredModules = getRequiredModules(
			path, additionalRequiredModules);

		return _soyJavaScriptRenderer.getJavaScript(
			template, portletNamespace, requiredModules);
	}

	public String getTemplateNamespace(String path) {
		return path.concat(".render");
	}

	protected String getControllerName(String path) {
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

	protected String getModuleName() throws Exception {
		URL url = _bundle.getEntry("package.json");

		if (url == null) {
			return null;
		}

		String json = StringUtil.read(url.openStream());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		String moduleName = jsonObject.getString("name");

		if (Validator.isNull(moduleName)) {
			return null;
		}

		return moduleName;
	}

	protected Set<String> getRequiredModules(
		String path, Set<String> additionalRequiredModules) {

		if (_moduleName == null) {
			return Collections.emptySet();
		}

		Set<String> requiredModules = new LinkedHashSet<>();

		String controllerName = getControllerName(path);

		requiredModules.add(
			_moduleName.concat(StringPool.SLASH).concat(controllerName));

		requiredModules.addAll(additionalRequiredModules);

		return requiredModules;
	}

	private final Bundle _bundle;
	private final Map<String, String> _controllersMap = new HashMap<>();
	private final String _moduleName;
	private final SoyJavaScriptRenderer _soyJavaScriptRenderer;

}