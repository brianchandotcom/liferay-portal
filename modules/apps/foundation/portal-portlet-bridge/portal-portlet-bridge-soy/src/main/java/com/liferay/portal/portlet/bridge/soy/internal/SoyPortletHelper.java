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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

		_javaScriptTPL = getJavaScriptTPL();
	}

	public String getPortletJavaScript(
		Template template, String path, String portletNamespace,
		Set<String> additionalRequiredModules) {

		if (_moduleName == null) {
			return StringPool.BLANK;
		}

		JSONObject contextJSONObject = createContextJSONObject(
			template, portletNamespace);

		Set<String> requiredModules = getRequiredModules(
			path, additionalRequiredModules);

		return getPortletJavaScript(
			contextJSONObject.toJSONString(), portletNamespace,
			getRequiredModulesString(requiredModules));
	}

	public String getTemplateNamespace(String path) {
		if (_moduleName == null) {
			return path;
		}

		return path.concat(".render");
	}

	protected JSONObject createContextJSONObject(
		Template template, String portletNamespace) {

		JSONObject contextJSONObject = JSONFactoryUtil.createJSONObject();

		for (String key : template.getKeys()) {
			if (Validator.equals(key, TemplateConstants.NAMESPACE)) {
				continue;
			}

			contextJSONObject.put(key, template.get(key));
		}

		return contextJSONObject;
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

	protected String getJavaScriptTPL() throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/bootstrap.js.tpl");

		return StringUtil.read(inputStream);
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

	protected String getPortletJavaScript(
		String context, String portletNamespace, String requiredModulesString) {

		return StringUtil.replace(
			_javaScriptTPL,
			new String[] {
				"$CONTEXT", "$PORTLET_NAMESPACE", "$REQUIRED_MODULES"
			},
			new String[] {context, portletNamespace, requiredModulesString});
	}

	protected Set<String> getRequiredModules(
		String path, Set<String> additionalRequiredModules) {

		if (_moduleName == null) {
			return Collections.emptySet();
		}

		Set<String> requiredModules = new LinkedHashSet<>(
			additionalRequiredModules);

		String controllerName = getControllerName(path);

		requiredModules.add(
			_moduleName.concat(StringPool.SLASH).concat(controllerName));

		return requiredModules;
	}

	protected String getRequiredModulesString(Set<String> requiredModules) {
		StringBundler sb = new StringBundler((requiredModules.size() * 4) - 1);

		Iterator<String> iterator = requiredModules.iterator();

		while (iterator.hasNext()) {
			sb.append(StringPool.QUOTE);
			sb.append(iterator.next());
			sb.append(StringPool.QUOTE);

			if (iterator.hasNext()) {
				sb.append(StringPool.COMMA);
			}
		}

		return sb.toString();
	}

	private final Bundle _bundle;
	private final Map<String, String> _controllersMap = new HashMap<>();
	private final String _javaScriptTPL;
	private final String _moduleName;

}