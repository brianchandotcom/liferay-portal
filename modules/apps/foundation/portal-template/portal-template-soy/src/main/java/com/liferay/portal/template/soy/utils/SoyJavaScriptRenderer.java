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

package com.liferay.portal.template.soy.utils;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Map;
import java.util.Set;

/**
 * @author Bruno Basto
 */
public class SoyJavaScriptRenderer {

	public SoyJavaScriptRenderer() throws Exception {
		_jsonSerializer = JSONFactoryUtil.createJSONSerializer();
	}

	public String getJavaScript(
		Map<String, Object> context, String id, Set<String> modules) {

		String contextString = _jsonSerializer.serializeDeep(context);

		String modulesString = _jsonSerializer.serialize(modules);

		return StringUtil.replace(
			_javaScriptTPL, new String[] {"$CONTEXT", "$ID", "$MODULES"},
			new String[] {contextString, id, modulesString});
	}

	private static String _getJavaScriptTPL() {
		Class<?> clazz = SoyJavaScriptRenderer.class;

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/bootstrap.js.tpl");

		String js = StringPool.BLANK;

		try {
			js = StringUtil.read(inputStream);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read template.");
			}
		}

		return js;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyJavaScriptRenderer.class);

	private static final String _javaScriptTPL;

	static {
		_javaScriptTPL = _getJavaScriptTPL();
	}

	private final JSONSerializer _jsonSerializer;

}