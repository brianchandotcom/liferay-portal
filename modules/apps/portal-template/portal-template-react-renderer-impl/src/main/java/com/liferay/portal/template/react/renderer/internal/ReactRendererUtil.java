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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESImport;
import com.liferay.portal.kernel.servlet.taglib.aui.JSFragment;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class ReactRendererUtil {

	public static void renderEcmaScript(
			AbsolutePortalURLBuilder absolutePortalURLBuilder,
			ComponentDescriptor componentDescriptor,
			HttpServletRequest httpServletRequest, String placeholderId,
			Portal portal, Map<String, Object> props, Writer writer)
		throws IOException {

		List<AMDRequire> amdRequires = new ArrayList<>();

		StringBundler contentSB = new StringBundler(9);

		List<ESImport> esImports = new ArrayList<>();

		esImports.add(
			new ESImport(
				absolutePortalURLBuilder.forESModule(
					"portal-template-react-renderer-impl", "index.js"
				).build(),
				"render"));

		String module = componentDescriptor.getModule();

		String[] parts = module.split(" from ");

		esImports.add(
			new ESImport(
				"componentModule",
				absolutePortalURLBuilder.forESModule(
					parts[1], "index.js"
				).build(),
				_getSymbolName(parts[0])));

		String propsTransformer = componentDescriptor.getPropsTransformer();

		if (Validator.isNotNull(propsTransformer)) {
			if (propsTransformer.contains(" from ")) {
				parts = propsTransformer.split(" from ");

				esImports.add(
					new ESImport(
						"propsTransformer",
						absolutePortalURLBuilder.forESModule(
							parts[1], "index.js"
						).build(),
						_getSymbolName(parts[0])));
			}
			else {
				amdRequires.add(
					new AMDRequire("propsTransformer", propsTransformer));
			}
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		contentSB.append("render(componentModule, ");

		if (Validator.isNotNull(propsTransformer)) {
			contentSB.append("propsTransformer");

			if (!propsTransformer.contains(" from ")) {
				contentSB.append(".default");
			}

			contentSB.append(StringPool.OPEN_PARENTHESIS);

			contentSB.append(jsonSerializer.serializeDeep(props));
			contentSB.append(StringPool.CLOSE_PARENTHESIS);
		}
		else {
			contentSB.append(jsonSerializer.serializeDeep(props));
		}

		contentSB.append(", '");
		contentSB.append(placeholderId);
		contentSB.append("');\n");

		if (componentDescriptor.isPositionInLine()) {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				portal.getPortletId(httpServletRequest),
				new JSFragment(amdRequires, contentSB.toString(), esImports));

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
				new JSFragment(amdRequires, contentSB.toString(), esImports));
		}
	}

	public static void renderJavaScript(
			ComponentDescriptor componentDescriptor, Map<String, Object> props,
			HttpServletRequest httpServletRequest,
			String npmResolvedPackageName, String placeholderId, Portal portal,
			Writer writer)
		throws IOException {

		StringBundler dependenciesSB = new StringBundler(11);

		dependenciesSB.append(npmResolvedPackageName);
		dependenciesSB.append(" as index");
		dependenciesSB.append(placeholderId);
		dependenciesSB.append(", ");
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

		StringBundler javascriptSB = new StringBundler(13);

		javascriptSB.append("index");
		javascriptSB.append(placeholderId);
		javascriptSB.append(".render(renderFunction");
		javascriptSB.append(placeholderId);
		javascriptSB.append(".default, ");

		if (Validator.isNotNull(propsTransformer)) {
			javascriptSB.append("propsTransformer");
			javascriptSB.append(placeholderId);
			javascriptSB.append(".default(");
			javascriptSB.append(jsonSerializer.serializeDeep(props));
			javascriptSB.append(")");
		}
		else {
			javascriptSB.append(jsonSerializer.serializeDeep(props));
		}

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

	private static String _getSymbolName(String importedSymbol) {
		importedSymbol = importedSymbol.trim();

		if ((importedSymbol.charAt(0) != CharPool.OPEN_CURLY_BRACE) ||
			(importedSymbol.charAt(importedSymbol.length() - 1) !=
				CharPool.CLOSE_CURLY_BRACE)) {

			throw new IllegalArgumentException(
				"Invalid import syntax: " + importedSymbol);
		}

		importedSymbol = importedSymbol.substring(
			1, importedSymbol.length() - 1);

		return importedSymbol.trim();
	}

}