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

package com.liferay.frontend.taglib.soy.internal.util;

import com.liferay.frontend.taglib.soy.servlet.taglib.util.ComponentDescriptor;
import com.liferay.frontend.taglib.soy.servlet.taglib.util.SoyComponentRenderer;
import com.liferay.frontend.taglib.soy.servlet.taglib.util.SoyRenderer;
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

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = SoyComponentRenderer.class)
public class SoyComponentRendererImpl implements SoyComponentRenderer {

	public void renderSoyComponent(
			HttpServletRequest request, HttpServletResponse response,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		renderSoyComponent(
			request, response.getWriter(), componentDescriptor, context);
	}

	public void renderSoyComponent(
			HttpServletRequest request, Writer writer,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		Map<String, Object> contextCopy = new HashMap<>(context);

		if (!contextCopy.containsKey("locale")) {
			contextCopy.put("locale", LocaleUtil.getMostRelevantLocale());
		}

		if (!contextCopy.containsKey("portletId")) {
			contextCopy.put(
				"portletId", request.getAttribute(WebKeys.PORTLET_ID));
		}

		if (!componentDescriptor.isWrapper() && !context.containsKey("id")) {
			contextCopy.put("id", _getWrapperId(componentDescriptor, context));
		}

		_renderTemplate(request, writer, componentDescriptor, contextCopy);

		if (componentDescriptor.isRenderJavascript()) {
			if (!context.containsKey("element")) {
				contextCopy.put(
					"element",
					_getElementSelector(componentDescriptor, contextCopy));
			}

			_renderJavaScript(
				request, writer, componentDescriptor, contextCopy);
		}
	}

	private String _getElementSelector(
		ComponentDescriptor componentDescriptor, Map<String, ?> context) {

		String selector = StringPool.POUND.concat(
			_getWrapperId(componentDescriptor, context));

		if (componentDescriptor.isWrapper()) {
			selector = selector.concat(" > *:first-child");
		}

		return selector;
	}

	private String _getModuleName(ComponentDescriptor componentDescriptor) {
		String moduleName = StringUtil.extractLast(
			componentDescriptor.getModule(), CharPool.FORWARD_SLASH);

		return StringUtil.strip(moduleName, _UNSAFE_MODULE_NAME_CHARS);
	}

	private String _getWrapperId(
		ComponentDescriptor componentDescriptor, Map<String, ?> context) {

		String wrapperId = (String)context.get("id");

		if (Validator.isNull(wrapperId)) {
			wrapperId = componentDescriptor.getComponentId();

			if (Validator.isNull(wrapperId)) {
				wrapperId = StringUtil.randomId();
			}
		}

		return wrapperId;
	}

	private void _renderJavaScript(
			HttpServletRequest request, Writer writer,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException {

		String moduleName = _getModuleName(componentDescriptor);

		String componentJavaScript = SoyJavaScriptRendererUtil.getJavaScript(
			(Map)context, _getWrapperId(componentDescriptor, context),
			moduleName, componentDescriptor.isWrapper());

		StringBundler sb = new StringBundler(4);

		sb.append(componentDescriptor.getModule());
		sb.append(" as ");
		sb.append(moduleName);
		sb.append(
			String.join(
				StringPool.COMMA, componentDescriptor.getDependencies()));

		if (componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				_portal.getPortletId(request), componentJavaScript,
				sb.toString(), ScriptData.ModulesType.ES6);

			scriptData.writeTo(writer);
		}
		else {
			ScriptData scriptData = (ScriptData)request.getAttribute(
				WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {
				scriptData = new ScriptData();

				request.setAttribute(WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			scriptData.append(
				_portal.getPortletId(request), componentJavaScript,
				sb.toString(), ScriptData.ModulesType.ES6);
		}
	}

	private void _renderTemplate(
			HttpServletRequest request, Writer writer,
			ComponentDescriptor componentDescriptor, Map<String, ?> context)
		throws IOException, TemplateException {

		boolean wrapper = componentDescriptor.isWrapper();

		if (wrapper) {
			writer.append("<div id=\"");
			writer.append(
				HtmlUtil.escapeAttribute(
					_getWrapperId(componentDescriptor, context)));
			writer.append("\">");
		}

		_soyRenderer.renderSoy(
			request, writer, componentDescriptor.getTemplateNamespace(),
			context);

		if (wrapper) {
			writer.append("</div>");
		}
	}

	private static final char[] _UNSAFE_MODULE_NAME_CHARS = {
		CharPool.PERIOD, CharPool.DASH
	};

	@Reference
	private Portal _portal;

	@Reference
	private SoyRenderer _soyRenderer;

}