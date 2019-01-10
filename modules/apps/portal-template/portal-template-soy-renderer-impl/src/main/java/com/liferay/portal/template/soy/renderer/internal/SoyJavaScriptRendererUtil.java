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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class SoyJavaScriptRendererUtil {

	public static String getJavaScript(
		Map<String, Object> context, String id, String module) {

		return getJavaScript(context, id, module, true);
	}

	public static String getJavaScript(
		Map<String, Object> context, String id, String module,
		boolean wrapper) {

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String contextString = jsonSerializer.serializeDeep(context);
		String wrapperString = jsonSerializer.serialize(wrapper);

		return StringUtil.replace(
			_JAVA_SCRIPT_TPL,
			new String[] {"$CONTEXT", "$ID", "$MODULE", "$WRAPPER"},
			new String[] {contextString, id, module, wrapperString});
	}

	private static final String _JAVA_SCRIPT_TPL;

	private static final Log _log = LogFactoryUtil.getLog(
		SoyJavaScriptRendererUtil.class);

	static {
		InputStream inputStream =
			SoyJavaScriptRendererUtil.class.getResourceAsStream(
				"dependencies/bootstrap.js.tpl");

		String js = StringPool.BLANK;

		try {
			js = StringUtil.read(inputStream);
		}
		catch (Exception e) {
			_log.error("Unable to read template", e);
		}

		_JAVA_SCRIPT_TPL = js;
	}

}