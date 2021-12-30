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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMConverterTestCase {

	protected <T> T deserialize(
		String serializedDDMFormRules, Class<T> targetType) {

		JSONDeserializer<T> jsonDeserializer =
			jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(serializedDDMFormRules, targetType);
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), _getBasePath() + fileName);
	}

	protected <T> String serialize(List<T> rules) {
		JSONSerializer jsonSerializer = jsonFactory.createJSONSerializer();

		return jsonSerializer.serializeDeep(rules);
	}

	protected final JSONFactory jsonFactory = new JSONFactoryImpl();

	private String _getBasePath() {
		return "com/liferay/dynamic/data/mapping/form/builder/internal" +
			"/converter/dependencies/";
	}

}