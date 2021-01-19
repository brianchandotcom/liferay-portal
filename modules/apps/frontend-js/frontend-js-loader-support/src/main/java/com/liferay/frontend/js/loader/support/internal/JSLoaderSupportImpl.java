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

package com.liferay.frontend.js.loader.support.internal;

import com.liferay.frontend.js.loader.support.JSLoaderSupport;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;

import java.io.IOException;
import java.io.Writer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSLoaderSupport.class)
public class JSLoaderSupportImpl implements JSLoaderSupport {

	@Override
	public String getScriptBody(
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

	@Override
	public void writeScript(
		Writer writer, String javascriptModule, String javascriptVariable,
		String javascriptCode) {

		_writeScriptData(
			writer,
			getScriptBody(
				javascriptModule, javascriptVariable, javascriptCode));
	}

	private void _writeScriptData(Writer writer, String content) {
		try {
			ScriptData scriptData = new ScriptData();

			scriptData.append(
				null, content, StringPool.BLANK, ScriptData.ModulesType.ES6);

			scriptData.writeTo(writer);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}