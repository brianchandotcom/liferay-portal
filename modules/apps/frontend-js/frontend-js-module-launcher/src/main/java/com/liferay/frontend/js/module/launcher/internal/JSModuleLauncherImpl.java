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

import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Writer;

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
		String javascriptModule, String javascriptVariable,
		String javascriptCode) {

		_appendScriptData(
			httpServletRequest,
			_getScriptBody(
				javascriptModule, javascriptVariable, javascriptCode),
			portletId);
	}

	@Override
	public void appendScript(
		HttpServletRequest httpServletRequest, String javascriptModule,
		String javascriptVariable, String javascriptCode) {

		_appendScriptData(
			httpServletRequest,
			_getScriptBody(
				javascriptModule, javascriptVariable, javascriptCode),
			null);
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
				javascriptModule, "__module__", javascriptCodeSB.toString()),
			null);
	}

	@Override
	public void writeScript(
		Writer writer, String javascriptModule, String javascriptVariable,
		String javascriptCode) {

		_writeScriptData(
			writer,
			_getScriptBody(
				javascriptModule, javascriptVariable, javascriptCode),
			null);
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
		String javascriptModule, String javascriptVariable,
		String javascriptCode) {

		StringBundler javascriptSB = new StringBundler(10);

		javascriptSB.append("(function() {");

		javascriptSB.append("window[");
		javascriptSB.append("Symbol.for('__LIFERAY_WEBPACK_GET_MODULE__')]('");
		javascriptSB.append(javascriptModule);
		javascriptSB.append("').then((");
		javascriptSB.append(javascriptVariable);
		javascriptSB.append(") => {");
		javascriptSB.append(javascriptCode);
		javascriptSB.append("});");

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