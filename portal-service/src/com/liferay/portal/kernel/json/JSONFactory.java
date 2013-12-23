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

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public interface JSONFactory {

	public String convertJSONMLArrayToXML(String jsonml);

	public String convertJSONMLObjectToXML(String jsonml);

	public String convertXMLtoJSONMLArray(String xml);

	public String convertXMLtoJSONMLObject(String xml);

	public JSONTransformer createJavaScriptNormalizerJSONTransformer(
		List<String> javaScriptAttributes);

	public JSONArray createJSONArray();

	public JSONArray createJSONArray(String json) throws JSONException;

	public <T> JSONDeserializer<T> createJSONDeserializer();

	public JSONObject createJSONObject();

	public JSONObject createJSONObject(String json) throws JSONException;

	public JSONSerializer createJSONSerializer();

	public Object deserialize(JSONObject jsonObj);

	public Object deserialize(String json);

	public String getNullJSON();

	public JSONObject getUnmodifiableJSONObject();

	public Object looseDeserialize(String json);

	public <T> T looseDeserialize(String json, Class<T> clazz);

	public Object looseDeserializeSafe(String json);

	public <T> T looseDeserializeSafe(String json, Class<T> clazz);

	public String looseSerialize(Object object);

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz);

	public String looseSerialize(Object object, String... includes);

	public String looseSerializeDeep(Object object);

	public String looseSerializeDeep(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz);

	public String serialize(Object object);

	public String serializeThrowable(Throwable throwable);

}