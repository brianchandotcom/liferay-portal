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

package com.liferay.commerce.cloud.server.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class Project implements JsonSerializable {

	public Project() {
	}

	public Project(JsonObject jsonObject) {
		ProjectConverter.fromJson(jsonObject, this);
	}

	public String getApiKey() {
		return _apiKey;
	}

	public String getCallbackUrl() {
		return _callbackUrl;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setCallbackUrl(String callbackUrl) {
		_callbackUrl = callbackUrl;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ProjectConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private boolean _active;
	private String _apiKey;
	private String _callbackUrl;
	private String _id;
	private String _name;

}