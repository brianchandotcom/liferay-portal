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

import com.liferay.frontend.js.loader.support.JSLoaderSupport;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class ReactRendererUtil {

	public static void renderReact(
			ComponentDescriptor componentDescriptor, Map<String, Object> props,
			HttpServletRequest httpServletRequest,
			JSLoaderSupport jsLoaderSupport, Portal portal, Writer writer)
		throws IOException {

		String placeholderId = StringUtil.randomId();

		_renderPlaceholder(writer, placeholderId);

		_renderJavaScript(
			componentDescriptor, props, httpServletRequest, placeholderId,
			jsLoaderSupport, portal, writer);
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

	private static void _renderJavaScript(
			ComponentDescriptor componentDescriptor, Map<String, Object> props,
			HttpServletRequest httpServletRequest, String placeholderId,
			JSLoaderSupport jsLoaderSupport, Portal portal, Writer writer)
		throws IOException {

		StringBundler dependenciesSB = new StringBundler(7);

		dependenciesSB.append(componentDescriptor.getModule());
		dependenciesSB.append(" as renderFunction");
		dependenciesSB.append(placeholderId);

		String propsTransformer = componentDescriptor.getPropsTransformer();

		if (Validator.isNotNull(propsTransformer)) {
			dependenciesSB.append(", ");
			dependenciesSB.append(propsTransformer);
			dependenciesSB.append(" as propsTransformer");
			dependenciesSB.append(placeholderId);
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		StringBundler javascriptSB = new StringBundler(11);

		javascriptSB.append("render(renderFunction");
		javascriptSB.append(placeholderId);
		javascriptSB.append(".default, ");

		if (Validator.isNotNull(propsTransformer)) {
			javascriptSB.append("propsTransformer");
			javascriptSB.append(placeholderId);
			javascriptSB.append(".default(");
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
		javascriptSB.append("');");

		String scriptBody = jsLoaderSupport.getScriptBody(
			"portal-template-react-renderer-impl", "{render}",
			javascriptSB.toString());

		if (componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				portal.getPortletId(httpServletRequest), scriptBody,
				dependenciesSB.toString(), ScriptData.ModulesType.ES6);

			scriptData.writeTo(writer);
		}
		else {
			ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
				WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {
				scriptData = new ScriptData();

				httpServletRequest.setAttribute(
					WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			scriptData.append(
				portal.getPortletId(httpServletRequest), scriptBody,
				dependenciesSB.toString(), ScriptData.ModulesType.ES6);
		}
	}

	private static void _renderPlaceholder(Writer writer, String placeholderId)
		throws IOException {

		writer.append("<div id=\"");
		writer.append(placeholderId);
		writer.append("\"></div>");
	}

}