/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.json.transformer.CompanyJSONTransformer;
import com.liferay.portal.json.transformer.FlexjsonObjectJSONTransformer;
import com.liferay.portal.json.transformer.JSONArrayJSONTransformer;
import com.liferay.portal.json.transformer.JSONObjectJSONTransformer;
import com.liferay.portal.json.transformer.JSONSerializableJSONTransformer;
import com.liferay.portal.json.transformer.RepositoryModelJSONTransformer;
import com.liferay.portal.json.transformer.UserJSONTransformer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;

import flexjson.TransformerUtil;

import flexjson.transformer.NullTransformer;
import flexjson.transformer.Transformer;
import flexjson.transformer.TransformerWrapper;
import flexjson.transformer.TypeTransformerMap;

import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONInit {

	@SuppressWarnings("rawtypes")
	public static synchronized void init() {
		try {
			if (_initalized) {
				return;
			}

			Field defaultTransformersField =
				TransformerUtil.class.getDeclaredField("defaultTransformers");

			defaultTransformersField.setAccessible(true);

			TypeTransformerMap oldTransformersMap =
				TransformerUtil.getDefaultTypeTransformers();

			TypeTransformerMap newTransformersMap = new TypeTransformerMap();

			for (Map.Entry<Class, Transformer> entry :
					oldTransformersMap.entrySet()) {

				newTransformersMap.put(entry.getKey(), entry.getValue());
			}

			_registerDefaultTransformers(newTransformersMap);

			Field modifiersField = Field.class.getDeclaredField("modifiers");

			modifiersField.setAccessible(true);

			modifiersField.setInt(
				defaultTransformersField,
				defaultTransformersField.getModifiers() & ~Modifier.FINAL);

			defaultTransformersField.set(null, newTransformersMap);

			_initalized = true;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void _registerDefaultTransformers(
		TypeTransformerMap transformersMap) {

		transformersMap.put(
			Company.class,
			new TransformerWrapper(new CompanyJSONTransformer()));

		transformersMap.put(
			InputStream.class, new TransformerWrapper(new NullTransformer()));

		transformersMap.put(
			JSONArray.class,
			new TransformerWrapper(new JSONArrayJSONTransformer()));

		transformersMap.put(
			JSONObject.class,
			new TransformerWrapper(new JSONObjectJSONTransformer()));

		transformersMap.put(
			JSONSerializable.class,
			new TransformerWrapper(new JSONSerializableJSONTransformer()));

		transformersMap.put(
			Object.class,
			new TransformerWrapper(new FlexjsonObjectJSONTransformer()));

		transformersMap.put(
			RepositoryModel.class,
			new TransformerWrapper(new RepositoryModelJSONTransformer()));

		transformersMap.put(
			User.class, new TransformerWrapper(new UserJSONTransformer()));
	}

	private static boolean _initalized = false;

}