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

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class ReactRendererHelper {

	public ReactRendererHelper(
		HttpServletRequest httpServletRequest, ServletContext servletContext,
		ComponentDescriptor componentDescriptor, Map<String, ?> data,
		Portal portal) {

		_httpServletRequest = httpServletRequest;
		_componentDescriptor = componentDescriptor;
		_data = new HashMap<>(data);

		_npmResolvedPackageName = NPMResolvedPackageNameUtil.get(
			servletContext);

		_placeholderId = StringUtil.randomId();
		_portal = portal;

		_prepareData();
	}

	public void renderReact(Writer writer) throws IOException {
		_renderPlaceholder(writer);
		_renderJavaScript(writer);
	}

	private void _prepareData() {
		if (!_data.containsKey("componentId")) {
			_data.put("componentId", _componentDescriptor.getComponentId());
		}

		if (!_data.containsKey("locale")) {
			_data.put("locale", LocaleUtil.getMostRelevantLocale());
		}

		if (!_data.containsKey("portletId")) {
			_data.put(
				"portletId",
				_httpServletRequest.getAttribute(WebKeys.PORTLET_ID));
		}

		if (!_data.containsKey("portletNamespace")) {
			_data.put(
				"portletNamespace",
				_httpServletRequest.getAttribute(WebKeys.PORTLET_ID));
		}
	}

	private void _renderJavaScript(Writer writer) throws IOException {
		StringBundler dependenciesSB = new StringBundler(7);

		dependenciesSB.append(_npmResolvedPackageName);
		dependenciesSB.append("/render.es as render");
		dependenciesSB.append(_placeholderId);
		dependenciesSB.append(", ");
		dependenciesSB.append(_componentDescriptor.getModule());
		dependenciesSB.append(" as renderFunction");
		dependenciesSB.append(_placeholderId);

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		StringBundler javascriptSB = new StringBundler(9);

		javascriptSB.append("render");
		javascriptSB.append(_placeholderId);
		javascriptSB.append(".default(renderFunction");
		javascriptSB.append(_placeholderId);
		javascriptSB.append(".default, ");
		javascriptSB.append(jsonSerializer.serialize(_data));
		javascriptSB.append(", '");
		javascriptSB.append(_placeholderId);
		javascriptSB.append("');");

		if (_componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				_portal.getPortletId(_httpServletRequest),
				javascriptSB.toString(), dependenciesSB.toString(),
				ScriptData.ModulesType.ES6);

			scriptData.writeTo(writer);
		}
		else {
			ScriptData scriptData =
				(ScriptData)_httpServletRequest.getAttribute(
					WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {
				scriptData = new ScriptData();

				_httpServletRequest.setAttribute(
					WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			scriptData.append(
				_portal.getPortletId(_httpServletRequest),
				javascriptSB.toString(), dependenciesSB.toString(),
				ScriptData.ModulesType.ES6);
		}
	}

	private void _renderPlaceholder(Writer writer) throws IOException {
		writer.append("<div id=\"");
		writer.append(_placeholderId);
		writer.append("\"></div>");
	}

	private final ComponentDescriptor _componentDescriptor;
	private final Map<String, Object> _data;
	private final HttpServletRequest _httpServletRequest;
	private final String _npmResolvedPackageName;
	private final String _placeholderId;
	private final Portal _portal;

}