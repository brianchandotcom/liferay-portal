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

package com.liferay.portal.template.soy.renderer.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.renderer.ComponentDescriptor;
import com.liferay.portal.template.soy.renderer.SoyRenderer;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class SoyComponentRendererHelper {

	public SoyComponentRendererHelper(
		HttpServletRequest httpServletRequest,
		ComponentDescriptor componentDescriptor, Map<String, ?> context,
		Portal portal, SoyRenderer soyRenderer) {

		_httpServletRequest = httpServletRequest;
		_componentDescriptor = componentDescriptor;
		_context = new HashMap<>(context);
		_portal = portal;
		_soyRenderer = soyRenderer;

		_moduleName = _getModuleName(_componentDescriptor.getModule());

		_wrapperId = _generateWrapperId(
			(String)_context.get("id"), _componentDescriptor.getComponentId());

		_elementSelector = _getElementSelector(
			_wrapperId, _componentDescriptor.isWrapper());

		_prepareContext();
	}

	public void renderSoyComponent(Writer writer)
		throws IOException, TemplateException {

		_renderTemplate(writer);

		if (_componentDescriptor.isRenderJavascript()) {
			_renderJavaScript(writer);
		}
	}

	private static String _generateWrapperId(String id, String componentId) {
		String wrapperId = id;

		if (Validator.isNull(wrapperId)) {
			wrapperId = componentId;

			if (Validator.isNull(wrapperId)) {
				wrapperId = StringUtil.randomId();
			}
		}

		return wrapperId;
	}

	private static String _getElementSelector(
		String wrapperId, boolean wrapper) {

		String selector = StringPool.POUND.concat(wrapperId);

		if (wrapper) {
			selector = selector.concat(" > *:first-child");
		}

		return selector;
	}

	private static String _getModuleName(String module) {
		String moduleName = StringUtil.extractLast(
			module, CharPool.FORWARD_SLASH);

		return StringUtil.strip(moduleName, _UNSAFE_MODULE_NAME_CHARS);
	}

	private void _prepareContext() {
		if (!_context.containsKey("locale")) {
			_context.put("locale", LocaleUtil.getMostRelevantLocale());
		}

		if (!_context.containsKey("portletId")) {
			_context.put(
				"portletId",
				_httpServletRequest.getAttribute(WebKeys.PORTLET_ID));
		}

		if (!_componentDescriptor.isWrapper() &&
			Validator.isNull(_context.get("id"))) {

			_context.put("id", _wrapperId);
		}

		if (_componentDescriptor.isRenderJavascript() &&
			!_context.containsKey("element")) {

			_context.put("element", _elementSelector);
		}
	}

	private void _renderJavaScript(Writer writer) throws IOException {
		String componentJavaScript = SoyJavaScriptRendererUtil.getJavaScript(
			(Map)_context, _wrapperId, _moduleName,
			_componentDescriptor.isWrapper());

		StringBundler sb = new StringBundler(5);

		sb.append(_componentDescriptor.getModule());
		sb.append(" as ");
		sb.append(_moduleName);

		Set<String> dependencies = _componentDescriptor.getDependencies();

		if (!dependencies.isEmpty()) {
			sb.append(StringPool.COMMA);
		}

		sb.append(StringUtil.merge(dependencies));

		if (_componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				_portal.getPortletId(_httpServletRequest), componentJavaScript,
				sb.toString(), ScriptData.ModulesType.ES6);

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
				_portal.getPortletId(_httpServletRequest), componentJavaScript,
				sb.toString(), ScriptData.ModulesType.ES6);
		}
	}

	private void _renderTemplate(Writer writer)
		throws IOException, TemplateException {

		boolean wrapper = _componentDescriptor.isWrapper();

		if (wrapper) {
			writer.append("<div id=\"");
			writer.append(HtmlUtil.escapeAttribute(_wrapperId));
			writer.append("\">");
		}

		_soyRenderer.renderSoy(
			_httpServletRequest, writer,
			_componentDescriptor.getTemplateNamespace(), _context);

		if (wrapper) {
			writer.append("</div>");
		}
	}

	private static final char[] _UNSAFE_MODULE_NAME_CHARS = {
		CharPool.PERIOD, CharPool.DASH
	};

	private final ComponentDescriptor _componentDescriptor;
	private final Map<String, Object> _context;
	private final String _elementSelector;
	private final HttpServletRequest _httpServletRequest;
	private final String _moduleName;
	private final Portal _portal;
	private final SoyRenderer _soyRenderer;
	private final String _wrapperId;

}