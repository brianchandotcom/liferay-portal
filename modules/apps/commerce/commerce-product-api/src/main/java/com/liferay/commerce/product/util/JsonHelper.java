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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author Igor Beslic
 */
@ProviderType
public interface JsonHelper {

	public String getFirstElementStringValue(String jsonArrayString);

	public JSONArray getJSONArray(String json) throws JSONException;

	public JSONArray getValueAsJSONArray(String key, JSONObject jsonObject);

	public boolean isArray(String json);

	public boolean isEmpty(String json);

	public JSONArray toJSONArray(Map<String, List<String>> keyValues);

}