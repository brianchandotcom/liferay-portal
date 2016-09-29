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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Map;
import java.util.Set;

/**
 * @author Bruno Basto
 */
public class SoyJavaScriptRenderer {

	public SoyJavaScriptRenderer() throws Exception {
		_javaScriptTPL = _getJavaScriptTPL();
	}

	public String getJavaScript(
		Map<String, Object> context, String id, Set<String> modules) {

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String contextString = jsonSerializer.serializeDeep(context);

		String modulesString = jsonSerializer.serialize(modules);

		return StringUtil.replace(
			_javaScriptTPL, new String[] {"$CONTEXT", "$ID", "$MODULES"},
			new String[] {contextString, id, modulesString});
	}

	private String _getJavaScriptTPL() throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/bootstrap.js.tpl");

		return StringUtil.read(inputStream);
	}

	private final String _javaScriptTPL;

}