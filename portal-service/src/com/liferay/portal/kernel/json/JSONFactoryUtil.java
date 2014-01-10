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

package com.liferay.portal.kernel.json;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class JSONFactoryUtil {

	public static String convertJSONMLArrayToXML(String jsonml) {
		return getJSONFactory().convertJSONMLArrayToXML(jsonml);
	}

	public static String convertJSONMLObjectToXML(String jsonml) {
		return getJSONFactory().convertJSONMLObjectToXML(jsonml);
	}

	public static String convertXMLtoJSONMLArray(String xml) {
		return getJSONFactory().convertXMLtoJSONMLArray(xml);
	}

	public static String convertXMLtoJSONMLObject(String xml) {
		return getJSONFactory().convertXMLtoJSONMLObject(xml);
	}

	public static JSONTransformer createJavaScriptNormalizerJSONTransformer(
		List<String> javaScriptAttributes) {

		return getJSONFactory().createJavaScriptNormalizerJSONTransformer(
			javaScriptAttributes);
	}

	public static JSONArray createJSONArray() {
		return getJSONFactory().createJSONArray();
	}

	public static JSONArray createJSONArray(String json) throws JSONException {
		return getJSONFactory().createJSONArray(json);
	}

	public static <T> JSONDeserializer<T> createJSONDeserializer() {
		return getJSONFactory().createJSONDeserializer();
	}

	public static JSONObject createJSONObject() {
		return getJSONFactory().createJSONObject();
	}

	public static JSONObject createJSONObject(String json)
		throws JSONException {

		return getJSONFactory().createJSONObject(json);
	}

	public static JSONSerializer createJSONSerializer() {
		return getJSONFactory().createJSONSerializer();
	}

	public static Object deserialize(JSONObject jsonObj) {
		return getJSONFactory().deserialize(jsonObj);
	}

	public static Object deserialize(String json) {
		return getJSONFactory().deserialize(json);
	}

	public static JSONFactory getJSONFactory() {
		PortalRuntimePermission.checkGetBeanProperty(JSONFactoryUtil.class);

		return _jsonFactory;
	}

	public static String getNullJSON() {
		return getJSONFactory().getNullJSON();
	}

	public static JSONObject getUnmodifiableJSONObject() {
		return getJSONFactory().getUnmodifiableJSONObject();
	}

	public static Object looseDeserialize(String json) {
		return getJSONFactory().looseDeserialize(json);
	}

	public static <T> T looseDeserialize(String json, Class<T> clazz) {
		return getJSONFactory().looseDeserialize(json, clazz);
	}

	public static Object looseDeserializeSafe(String json) {
		return getJSONFactory().looseDeserializeSafe(json);
	}

	public static <T> T looseDeserializeSafe(String json, Class<T> clazz) {
		return getJSONFactory().looseDeserializeSafe(json, clazz);
	}

	public static String looseSerialize(Object object) {
		return getJSONFactory().looseSerialize(object);
	}

	public static String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		return getJSONFactory().looseSerialize(object, jsonTransformer, clazz);
	}

	public static String looseSerialize(Object object, String... includes) {
		return getJSONFactory().looseSerialize(object, includes);
	}

	public static String looseSerializeDeep(Object object) {
		return getJSONFactory().looseSerializeDeep(object);
	}

	public static String looseSerializeDeep(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		return getJSONFactory().looseSerializeDeep(
			object, jsonTransformer, clazz);
	}

	public static String serialize(Object object) {
		return getJSONFactory().serialize(object);
	}

	public static String serializeThrowable(Throwable throwable) {
		return getJSONFactory().serializeThrowable(throwable);
	}

	public void setJSONFactory(JSONFactory jsonFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_jsonFactory = jsonFactory;
	}

	private static JSONFactory _jsonFactory;

}