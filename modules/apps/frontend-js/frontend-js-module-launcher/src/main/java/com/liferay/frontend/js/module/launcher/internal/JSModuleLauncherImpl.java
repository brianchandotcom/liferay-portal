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

package com.liferay.frontend.js.module.launcher.internal;

import com.liferay.frontend.js.module.launcher.JSModuleDependency;
import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Writer;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSModuleLauncher.class)
public class JSModuleLauncherImpl implements JSModuleLauncher {

	@Override
	public void appendPortletScript(
		HttpServletRequest httpServletRequest, String portletId,
		Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode) {

		_appendScriptData(
			httpServletRequest,
			_getScriptBody(jsModuleDependencies, javascriptCode), portletId);
	}

	@Override
	public void appendScript(
		HttpServletRequest httpServletRequest,
		Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode) {

		_appendScriptData(
			httpServletRequest,
			_getScriptBody(jsModuleDependencies, javascriptCode), null);
	}

	@Override
	public boolean isValidModule(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if (moduleName.charAt(0) == CharPool.AT) {
			i = moduleName.indexOf(StringPool.SLASH, i + 1);
		}

		String packageName = moduleName.substring(0, i);

		if (packageName.contains(StringPool.AT)) {
			return false;
		}

		return true;
	}

	@Override
	public void writeModuleInvocation(
		Writer writer, String javascriptModule, String... parameters) {

		StringBundler javascriptCodeSB = new StringBundler(3);

		javascriptCodeSB.append("(__module__.default || __module__)(");
		javascriptCodeSB.append(
			StringUtil.merge(parameters, StringPool.COMMA_AND_SPACE));
		javascriptCodeSB.append(");");

		_writeScriptData(
			writer,
			_getScriptBody(
				Arrays.asList(
					new JSModuleDependency(javascriptModule, "__module__")),
				javascriptCodeSB.toString()),
			null);
	}

	@Override
	public void writeScript(
		Writer writer, Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode) {

		_writeScriptData(
			writer, _getScriptBody(jsModuleDependencies, javascriptCode), null);
	}

	private void _appendScriptData(
		HttpServletRequest httpServletRequest, String content,
		String portletId) {

		ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			scriptData = new ScriptData();

			httpServletRequest.setAttribute(
				WebKeys.AUI_SCRIPT_DATA, scriptData);
		}

		scriptData.append(
			portletId, content, StringPool.BLANK, ScriptData.ModulesType.ES6);
	}

	private String _getScriptBody(
		Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode) {

		StringBundler javascriptSB = new StringBundler(10);

		javascriptSB.append("(function() {");

		for (JSModuleDependency jsModuleDependency : jsModuleDependencies) {
			javascriptSB.append("window[");
			javascriptSB.append("Symbol.for('__LIFERAY_WEBPACK_GET_MODULE__')");
			javascriptSB.append("]('");
			javascriptSB.append(jsModuleDependency.getModuleName());
			javascriptSB.append("').then((");
			javascriptSB.append(jsModuleDependency.getVariableName());
			javascriptSB.append(") => {");
		}

		javascriptSB.append(javascriptCode);

		for (int i = 0; i < jsModuleDependencies.size(); i++) {
			javascriptSB.append("});");
		}

		javascriptSB.append("})();");

		return javascriptSB.toString();
	}

	private void _writeScriptData(
		Writer writer, String content, String portletId) {

		try {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				portletId, content, StringPool.BLANK,
				ScriptData.ModulesType.ES6);

			scriptData.writeTo(writer);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}