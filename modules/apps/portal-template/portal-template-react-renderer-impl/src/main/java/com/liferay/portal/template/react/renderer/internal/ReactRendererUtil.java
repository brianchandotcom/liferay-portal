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

package com.liferay.portal.template.react.renderer.internal;

import com.liferay.frontend.js.module.launcher.JSModuleDependency;
import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class ReactRendererUtil {

	public static void renderReact(
			ComponentDescriptor componentDescriptor, Map<String, Object> props,
			HttpServletRequest httpServletRequest,
			JSModuleLauncher jsModuleLauncher, Portal portal, Writer writer)
		throws IOException {

		String placeholderId = StringUtil.randomId();

		_renderPlaceholder(writer, placeholderId);

		_renderJavaScript(
			componentDescriptor, props, httpServletRequest, placeholderId,
			jsModuleLauncher, portal, writer);
	}

	private static String _getRenderInvocation(
		ComponentDescriptor componentDescriptor, Map<String, Object> props,
		HttpServletRequest httpServletRequest, String placeholderId,
		Portal portal) {

		StringBundler javascriptSB = new StringBundler(7);

		javascriptSB.append("try {\nrender(component.default, ");

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		if (Validator.isNotNull(componentDescriptor.getPropsTransformer())) {
			javascriptSB.append("propsTransformer.default(");
			javascriptSB.append(
				jsonSerializer.serializeDeep(
					_prepareProps(
						componentDescriptor, props, httpServletRequest,
						portal)));
			javascriptSB.append(")");
		}
		else {
			javascriptSB.append(
				jsonSerializer.serializeDeep(
					_prepareProps(
						componentDescriptor, props, httpServletRequest,
						portal)));
		}

		javascriptSB.append(", '");
		javascriptSB.append(placeholderId);
		javascriptSB.append("');\n} catch (err) {console.error(err);}");

		return javascriptSB.toString();
	}

	private static Map<String, Object> _prepareProps(
		ComponentDescriptor componentDescriptor, Map<String, Object> props,
		HttpServletRequest httpServletRequest, Portal portal) {

		Map<String, Object> modifiedProps = null;

		if (!props.containsKey("componentId")) {
			if (modifiedProps == null) {
				modifiedProps = new HashMap<>(props);
			}

			modifiedProps.put(
				"componentId", componentDescriptor.getComponentId());
		}

		if (!props.containsKey("locale")) {
			if (modifiedProps == null) {
				modifiedProps = new HashMap<>(props);
			}

			modifiedProps.put("locale", LocaleUtil.getMostRelevantLocale());
		}

		String portletId = (String)props.get("portletId");

		if (portletId == null) {
			if (modifiedProps == null) {
				modifiedProps = new HashMap<>(props);
			}

			portletId = portal.getPortletId(httpServletRequest);

			modifiedProps.put("portletId", portletId);
		}

		if ((portletId != null) && !props.containsKey("portletNamespace")) {
			if (modifiedProps == null) {
				modifiedProps = new HashMap<>(props);
			}

			modifiedProps.put(
				"portletNamespace", portal.getPortletNamespace(portletId));
		}

		if (modifiedProps == null) {
			return props;
		}

		return modifiedProps;
	}

	private static void _registerJSModuleDependency(
		JSModuleLauncher jsModuleLauncher,
		JSModuleDependency jsModuleDependency,
		List<JSModuleDependency> jsModuleDependencies,
		List<JSModuleDependency> amdJSModuleDependencies) {

		if (jsModuleLauncher.isValidModule(
				jsModuleDependency.getModuleName())) {

			jsModuleDependencies.add(jsModuleDependency);
		}
		else {
			amdJSModuleDependencies.add(jsModuleDependency);
		}
	}

	private static void _renderJavaScript(
		ComponentDescriptor componentDescriptor, Map<String, Object> props,
		HttpServletRequest httpServletRequest, String placeholderId,
		JSModuleLauncher jsModuleLauncher, Portal portal, Writer writer) {

		List<JSModuleDependency> jsModuleDependencies = new ArrayList<>();
		List<JSModuleDependency> amdJSModuleDependencies = new ArrayList<>();

		jsModuleDependencies.add(
			new JSModuleDependency(
				"portal-template-react-renderer-impl", "{render}"));

		_registerJSModuleDependency(
			jsModuleLauncher,
			new JSModuleDependency(
				componentDescriptor.getModule(), "component"),
			jsModuleDependencies, amdJSModuleDependencies);

		String propsTransformer = componentDescriptor.getPropsTransformer();

		if (Validator.isNotNull(propsTransformer)) {
			_registerJSModuleDependency(
				jsModuleLauncher,
				new JSModuleDependency(propsTransformer, "propsTransformer"),
				jsModuleDependencies, amdJSModuleDependencies);
		}

		String javascriptCode = _getRenderInvocation(
			componentDescriptor, props, httpServletRequest, placeholderId,
			portal);

		if (!amdJSModuleDependencies.isEmpty()) {
			StringBundler javascriptSB = new StringBundler(
				5 + (6 * amdJSModuleDependencies.size()) - 2);

			javascriptSB.append("Liferay.Loader.require([");

			for (int i = 0; i < amdJSModuleDependencies.size(); i++) {
				JSModuleDependency jsModuleDependency =
					amdJSModuleDependencies.get(i);

				if (i > 0) {
					javascriptSB.append(StringPool.COMMA_AND_SPACE);
				}

				javascriptSB.append(StringPool.APOSTROPHE);
				javascriptSB.append(jsModuleDependency.getModuleName());
				javascriptSB.append(StringPool.APOSTROPHE);
			}

			javascriptSB.append("], function(");

			for (int i = 0; i < amdJSModuleDependencies.size(); i++) {
				JSModuleDependency jsModuleDependency =
					amdJSModuleDependencies.get(i);

				if (i > 0) {
					javascriptSB.append(StringPool.COMMA_AND_SPACE);
				}

				javascriptSB.append(jsModuleDependency.getVariableName());
			}

			javascriptSB.append(") {\n");
			javascriptSB.append(javascriptCode);
			javascriptSB.append("});");

			javascriptCode = javascriptSB.toString();
		}

		if (componentDescriptor.isPositionInLine()) {
			jsModuleLauncher.writeScript(
				writer, jsModuleDependencies, javascriptCode);
		}
		else {
			jsModuleLauncher.appendPortletScript(
				httpServletRequest, portal.getPortletId(httpServletRequest),
				jsModuleDependencies, javascriptCode);
		}
	}

	private static void _renderPlaceholder(Writer writer, String placeholderId)
		throws IOException {

		writer.append("<div id=\"");
		writer.append(placeholderId);
		writer.append("\"></div>");
	}

}