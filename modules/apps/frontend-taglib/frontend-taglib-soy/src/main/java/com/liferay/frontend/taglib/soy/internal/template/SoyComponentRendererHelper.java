/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.soy.internal.template;

import com.liferay.frontend.js.loader.modules.extender.esm.ESImportUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESImport;
import com.liferay.portal.kernel.servlet.taglib.aui.JSFragment;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class SoyComponentRendererHelper {

	public SoyComponentRendererHelper(
		AbsolutePortalURLBuilder absolutePortalURLBuilder,
		HttpServletRequest httpServletRequest,
		ComponentDescriptor componentDescriptor, Map<String, ?> context,
		Portal portal) {

		_absolutePortalURLBuilder = absolutePortalURLBuilder;
		_httpServletRequest = httpServletRequest;
		_componentDescriptor = componentDescriptor;
		_context = new HashMap<>(context);
		_portal = portal;

		_wrapperId = _generateWrapperId(
			(String)_context.get("id"), componentDescriptor.getComponentId());

		_elementSelector = _getElementSelector(
			_wrapperId, componentDescriptor.isWrapper());

		_prepareContext();
	}

	public void renderSoyComponent(Writer writer)
		throws IOException, TemplateException {

		_renderTemplate(writer);

		if (_componentDescriptor.isRenderJavascript()) {
			if (ESImportUtil.isESImport(_componentDescriptor.getModule())) {
				_renderEcmaScript(writer);
			}
			else {
				_renderJavaScript(writer);
			}
		}
	}

	private String _generateWrapperId(String id, String componentId) {
		String wrapperId = id;

		if (Validator.isNull(wrapperId)) {
			wrapperId = componentId;

			if (Validator.isNull(wrapperId)) {
				wrapperId = StringUtil.randomId();
			}
		}

		return wrapperId;
	}

	private String _getElementSelector(String wrapperId, boolean wrapper) {
		String selector = StringPool.POUND.concat(wrapperId);

		if (wrapper) {
			selector = selector.concat(" > *:first-child");
		}

		return selector;
	}

	private String _getModuleAlias(String module) {
		String moduleName = StringUtil.extractLast(
			module, CharPool.FORWARD_SLASH);

		return StringUtil.removeChars(moduleName, _UNSAFE_MODULE_NAME_CHARS);
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

	private void _renderEcmaScript(Writer writer) throws IOException {

		// AMD requires and ES imports

		List<AMDRequire> amdRequires = new ArrayList<>();

		List<ESImport> esImports = new ArrayList<>();

		esImports.add(
			ESImportUtil.getESImport(
				_absolutePortalURLBuilder, "componentModule",
				_componentDescriptor.getModule()));

		for (String dependency : _componentDescriptor.getDependencies()) {
			if (ESImportUtil.isESImport(dependency)) {
				esImports.add(
					ESImportUtil.getESImport(
						_absolutePortalURLBuilder, dependency));
			}
			else {
				amdRequires.add(new AMDRequire(dependency));
			}
		}

		// Code

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String componentJavaScript = StringUtil.replace(
			_JAVA_SCRIPT_TPL,
			new String[] {"$CONTEXT", "$ID", "$MODULE", "$WRAPPER"},
			new String[] {
				jsonSerializer.serializeDeep(_context), _wrapperId,
				"componentModule",
				jsonSerializer.serialize(_componentDescriptor.isWrapper())
			});

		// Render the ECMAScript code

		if (_componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				_portal.getPortletId(_httpServletRequest),
				new JSFragment(amdRequires, componentJavaScript, esImports));

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
				new JSFragment(amdRequires, componentJavaScript, esImports));
		}
	}

	private void _renderJavaScript(Writer writer) throws IOException {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String contextString = jsonSerializer.serializeDeep(_context);
		String wrapperString = jsonSerializer.serialize(
			_componentDescriptor.isWrapper());

		String moduleAlias = _getModuleAlias(_componentDescriptor.getModule());

		String componentJavaScript = StringUtil.replace(
			_JAVA_SCRIPT_TPL,
			new String[] {"$CONTEXT", "$ID", "$MODULE", "$WRAPPER"},
			new String[] {
				contextString, _wrapperId, moduleAlias + ".default",
				wrapperString
			});

		StringBundler sb = new StringBundler(5);

		sb.append(_componentDescriptor.getModule());
		sb.append(" as ");
		sb.append(moduleAlias);

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

		String placeholder = (String)_context.get("__placeholder__");

		if (Validator.isNotNull(placeholder)) {
			writer.append(placeholder);
		}
		else {
			writer.append("<div id=\"");
			writer.append((String)_context.get("id"));
			writer.append("\"></div>");
		}

		if (wrapper) {
			writer.append("</div>");
		}
	}

	private static final String _JAVA_SCRIPT_TPL;

	private static final char[] _UNSAFE_MODULE_NAME_CHARS = {
		CharPool.PERIOD, CharPool.DASH
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SoyComponentRendererHelper.class);

	static {
		InputStream inputStream =
			SoyComponentRendererHelper.class.getResourceAsStream(
				"dependencies/bootstrap.js.tpl");

		String js = StringPool.BLANK;

		try {
			js = StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			_log.error("Unable to read template", exception);
		}

		_JAVA_SCRIPT_TPL = js;
	}

	private final AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private final ComponentDescriptor _componentDescriptor;
	private final Map<String, Object> _context;
	private final String _elementSelector;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final String _wrapperId;

}