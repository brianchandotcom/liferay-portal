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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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
			ComponentDescriptor componentDescriptor, Map<String, Object> data,
			HttpServletRequest httpServletRequest,
			String npmResolvedPackageName, Portal portal, Writer writer)
		throws IOException {

		String placeholderId = StringUtil.randomId();

		_renderPlaceholder(writer, placeholderId);

		_renderJavaScript(
			componentDescriptor, data, httpServletRequest,
			npmResolvedPackageName, placeholderId, portal, writer);
	}

	private static Map<String, Object> _prepareData(
		ComponentDescriptor componentDescriptor, Map<String, Object> data,
		HttpServletRequest httpServletRequest) {

		Map<String, Object> modifiedData = null;

		if (!data.containsKey("componentId")) {
			if (modifiedData == null) {
				modifiedData = new HashMap<>(data);
			}

			modifiedData.put(
				"componentId", componentDescriptor.getComponentId());
		}

		if (!data.containsKey("locale")) {
			if (modifiedData == null) {
				modifiedData = new HashMap<>(data);
			}

			modifiedData.put("locale", LocaleUtil.getMostRelevantLocale());
		}

		if (!data.containsKey("portletId")) {
			if (modifiedData == null) {
				modifiedData = new HashMap<>(data);
			}

			modifiedData.put(
				"portletId",
				httpServletRequest.getAttribute(WebKeys.PORTLET_ID));
		}

		if (!data.containsKey("portletNamespace")) {
			if (modifiedData == null) {
				modifiedData = new HashMap<>(data);
			}

			modifiedData.put(
				"portletNamespace",
				httpServletRequest.getAttribute(WebKeys.PORTLET_ID));
		}

		if (modifiedData == null) {
			return data;
		}

		return modifiedData;
	}

	private static void _renderJavaScript(
			ComponentDescriptor componentDescriptor, Map<String, Object> data,
			HttpServletRequest httpServletRequest,
			String npmResolvedPackageName, String placeholderId, Portal portal,
			Writer writer)
		throws IOException {

		StringBundler dependenciesSB = new StringBundler(7);

		dependenciesSB.append(npmResolvedPackageName);
		dependenciesSB.append("/render.es as render");
		dependenciesSB.append(placeholderId);
		dependenciesSB.append(", ");
		dependenciesSB.append(componentDescriptor.getModule());
		dependenciesSB.append(" as renderFunction");
		dependenciesSB.append(placeholderId);

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		StringBundler javascriptSB = new StringBundler(9);

		javascriptSB.append("render");
		javascriptSB.append(placeholderId);
		javascriptSB.append(".default(renderFunction");
		javascriptSB.append(placeholderId);
		javascriptSB.append(".default, ");
		javascriptSB.append(
			jsonSerializer.serializeDeep(
				_prepareData(componentDescriptor, data, httpServletRequest)));
		javascriptSB.append(", '");
		javascriptSB.append(placeholderId);
		javascriptSB.append("');");

		if (componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				portal.getPortletId(httpServletRequest),
				javascriptSB.toString(), dependenciesSB.toString(),
				ScriptData.ModulesType.ES6);

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
				portal.getPortletId(httpServletRequest),
				javascriptSB.toString(), dependenciesSB.toString(),
				ScriptData.ModulesType.ES6);
		}
	}

	private static void _renderPlaceholder(Writer writer, String placeholderId)
		throws IOException {

		writer.append("<div id=\"");
		writer.append(placeholderId);
		writer.append("\"></div>");
	}

}