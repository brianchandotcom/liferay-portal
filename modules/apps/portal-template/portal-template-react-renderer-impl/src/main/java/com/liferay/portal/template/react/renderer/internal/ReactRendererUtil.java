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

import com.liferay.frontend.js.loader.modules.extender.esm.ESImportUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
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
			HttpServletRequest httpServletRequest, JSONFactory jsonFactory,
			String placeholderId, Portal portal, Map<String, Object> props,
			Writer writer)
		throws IOException {

		List<AMDRequire> amdRequires = new ArrayList<>();

		StringBundler contentSB = new StringBundler(9);

		List<ESImport> esImports = new ArrayList<>();

		for (String dependency : componentDescriptor.getDependencies()) {
			if (ESImportUtil.isESImport(dependency)) {
				esImports.add(
					ESImportUtil.getESImport(
						absolutePortalURLBuilder, dependency));
			}
			else {
				amdRequires.add(new AMDRequire(dependency));
			}
		}

		esImports.add(
			ESImportUtil.getESImport(
				absolutePortalURLBuilder,
				"{render} from portal-template-react-renderer-impl"));

		esImports.add(
			ESImportUtil.getESImport(
				absolutePortalURLBuilder, "componentModule",
				componentDescriptor.getModule()));

		String propsTransformer = componentDescriptor.getPropsTransformer();

		if (Validator.isNotNull(propsTransformer)) {
			if (ESImportUtil.isESImport(propsTransformer)) {
				esImports.add(
					ESImportUtil.getESImport(
						absolutePortalURLBuilder, "propsTransformer",
						propsTransformer));
			}
			else {
				amdRequires.add(
					new AMDRequire("propsTransformer", propsTransformer));
			}
		}

		JSONSerializer jsonSerializer = jsonFactory.createJSONSerializer();

		contentSB.append("render(componentModule, ");

		if (Validator.isNotNull(propsTransformer)) {
			contentSB.append("propsTransformer");

			if (!ESImportUtil.isESImport(propsTransformer)) {
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
			HttpServletRequest httpServletRequest, JSONFactory jsonFactory,
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

		JSONSerializer jsonSerializer = jsonFactory.createJSONSerializer();

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

}