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

package com.liferay.portal.json;

import com.liferay.portal.json.transformer.FileJSONTransformer;
import com.liferay.portal.json.transformer.JSONArrayJSONTransformer;
import com.liferay.portal.json.transformer.JSONObjectJSONTransformer;
import com.liferay.portal.json.transformer.JSONSerializableJSONTransformer;
import com.liferay.portal.json.transformer.RepositoryModelJSONTransformer;
import com.liferay.portal.json.transformer.UserJSONTransformer;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletDisplayModel;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.model.User;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.PortletURL;

import jodd.introspector.CachingIntrospector;
import jodd.introspector.JoddIntrospector;

import jodd.json.JoddJson;
import jodd.json.TypeJsonSerializerMap;

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

			_registerDefaultTransformers();

			_initalized = true;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void _registerDefaultTransformers() {

		// todo move this line to somewhere else (e.g. BeanUtilInit) ?

		JoddIntrospector.introspector = new CachingIntrospector(
			true, true, true, "_");

		JoddJson.jsonAnnotation = JSON.class;

		JoddJson.excludedTypes = new Class[] {
			ExpandoBridge.class, InputStream.class, LiferayPortletRequest.class,
			LiferayPortletResponse.class, OutputStream.class,
			PortletDisplayModel.class, PortletURL.class
		};

		JoddJson.excludedTypeNames = new String[] {
			"javax.*"
		};

		TypeJsonSerializerMap defaultTypeSerializerMap =
			JoddJson.defaultSerializers;

		defaultTypeSerializerMap.register(
			File.class, new JoddJsonTransformer(new FileJSONTransformer()));

		defaultTypeSerializerMap.register(
			JSONArray.class,
			new JoddJsonTransformer(new JSONArrayJSONTransformer()));

		defaultTypeSerializerMap.register(
			JSONObject.class,
			new JoddJsonTransformer(new JSONObjectJSONTransformer()));

		defaultTypeSerializerMap.register(
			JSONSerializable.class,
			new JoddJsonTransformer(new JSONSerializableJSONTransformer()));

		defaultTypeSerializerMap.register(
			RepositoryModel.class,
			new JoddJsonTransformer(new RepositoryModelJSONTransformer()));

		defaultTypeSerializerMap.register(
			User.class, new JoddJsonTransformer(new UserJSONTransformer()));
	}

	private static boolean _initalized = false;

}