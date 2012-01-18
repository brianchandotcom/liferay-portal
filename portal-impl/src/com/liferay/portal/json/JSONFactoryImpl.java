/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.alloy.util.json.StringTransformer;
import com.liferay.portal.json.jabsorb.serializer.LiferaySerializer;
import com.liferay.portal.json.jabsorb.serializer.LocaleSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationTargetException;

import java.util.List;

import org.jabsorb.serializer.MarshallException;

import org.json.JSONML;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONFactoryImpl implements JSONFactory {

	public JSONFactoryImpl() {
		JSONInit.init();

		_jsonSerializer = new org.jabsorb.JSONSerializer();

		 try {
			 _jsonSerializer.registerDefaultSerializers();

			 _jsonSerializer.registerSerializer(new LiferaySerializer());
			 _jsonSerializer.registerSerializer(new LocaleSerializer());
		 }
		 catch (Exception e) {
			 _log.error(e, e);
		 }
	}

	public String convertJSONMLArrayToXML(String jsonml) {
		try {
			org.json.JSONArray jsonArray = new org.json.JSONArray(jsonml);

			return JSONML.toString(jsonArray);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException("Unable to convert to XML", e);
		}
	}

	public String convertJSONMLObjectToXML(String jsonml) {
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonml);

			return JSONML.toString(jsonObject);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException("Unable to convert to XML", e);
		}
	}

	public String convertXMLtoJSONMLArray(String xml) {
		try {
			org.json.JSONArray jsonArray = JSONML.toJSONArray(xml);

			return jsonArray.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException("Unable to convert to JSONML", e);
		}
	}

	public String convertXMLtoJSONMLObject(String xml) {
		try {
			org.json.JSONObject jsonObject = JSONML.toJSONObject(xml);

			return jsonObject.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException("Unable to convert to JSONML", e);
		}
	}

	public JSONTransformer createJavaScriptNormalizerJSONTransformer(
		List<String> javaScriptAttributes) {

		StringTransformer stringTransformer = new StringTransformer();

		stringTransformer.setJavaScriptAttributes(javaScriptAttributes);

		return stringTransformer;
	}

	public JSONArray createJSONArray() {
		return new JSONArrayImpl();
	}

	public JSONArray createJSONArray(String json) throws JSONException {
		return new JSONArrayImpl(json);
	}

	public <T> JSONDeserializer<T> createJSONDeserializer() {
		return new JSONDeserializerImpl<T>();
	}

	public JSONObject createJSONObject() {
		return new JSONObjectImpl();
	}

	public JSONObject createJSONObject(String json) throws JSONException {
		return new JSONObjectImpl(json);
	}

	public JSONSerializer createJSONSerializer() {
		return new JSONSerializerImpl();
	}

	public Object deserialize(JSONObject jsonObj) {
		return deserialize(jsonObj.toString());
	}

	public Object deserialize(String json) {
		try {
			return _jsonSerializer.fromJSON(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public String getNullJSON() {
		return _NULL_JSON;
	}

	public Object looseDeserialize(String json) {
		try {
			return createJSONDeserializer().deserialize(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public <T> T looseDeserialize(String json, Class<T> clazz) {
		return (T) createJSONDeserializer().use(null, clazz).deserialize(json);
	}

	public String looseSerialize(Object object) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		return jsonSerializer.serialize(object);
	}

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.transform(jsonTransformer, clazz);

		return jsonSerializer.serialize(object);
	}

	public String looseSerialize(Object object, String... includes) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.include(includes);

		return jsonSerializer.serialize(object);
	}

	public String looseSerializeDeep(Object object) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		return jsonSerializer.serializeDeep(object);
	}

	public String looseSerializeDeep(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.transform(jsonTransformer, clazz);

		return jsonSerializer.serializeDeep(object);
	}

	public String serialize(Object object) {
		try {
			return _jsonSerializer.toJSON(object);
		}
		catch (MarshallException me) {
			_log.error(me, me);

			throw new IllegalStateException("Unable to serialize oject", me);
		}
	}

	public String serializeException(Exception exception) {
		String message = null;

		if (exception instanceof InvocationTargetException) {
			Throwable cause = exception.getCause();

			message = cause.toString();
		}
		else {
			message = exception.getMessage();
		}

		if (message == null) {
			message = exception.toString();
		}

		JSONObject jsonObject = createJSONObject();

		jsonObject.put("exception", message);

		return jsonObject.toString();
	}

	private static final String _NULL_JSON = "{}";

	private static Log _log = LogFactoryUtil.getLog(JSONFactoryImpl.class);

	private org.jabsorb.JSONSerializer _jsonSerializer;

}