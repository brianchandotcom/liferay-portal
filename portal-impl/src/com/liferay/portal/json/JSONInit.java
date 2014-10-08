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
import com.liferay.portal.kernel.json.JSONTransformer;
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

	private static void _registerDefaultTransformers() throws Exception {
		JoddIntrospector.introspector = new CachingIntrospector(
			true, true, true, new String[] {"_"});

		JoddJson.jsonAnnotation = JSON.class;

		JoddJson.excludedTypes = new Class[] {
			ExpandoBridge.class, InputStream.class, LiferayPortletRequest.class,
			LiferayPortletResponse.class, OutputStream.class,
			PortletDisplayModel.class, PortletURL.class
		};

		JoddJson.excludedTypeNames = new String[] {
			"javax.*"
		};

		TypeJsonSerializerMap typeSerializerMap = JoddJson.defaultSerializers;

		Class<?>[][] classesArray = new Class<?>[][] {
			new Class[] {File.class, FileJSONTransformer.class},
			new Class[] {JSONArray.class, JSONArrayJSONTransformer.class},
			new Class[] {JSONObject.class, JSONObjectJSONTransformer.class},
			new Class[] {
				JSONSerializable.class, JSONSerializableJSONTransformer.class
			},
			new Class[] {
				RepositoryModel.class, RepositoryModelJSONTransformer.class
			},
			new Class[] {User.class, UserJSONTransformer.class}
		};

		for (Class<?>[] classes : classesArray) {
			typeSerializerMap.register(
				classes[0],
				new JoddJsonTransformer(
					(JSONTransformer)classes[1].newInstance()));
		}
	}

	private static boolean _initalized = false;

}