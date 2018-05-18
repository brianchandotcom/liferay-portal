/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.server.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class Project implements JsonSerializable {

	public Project(JsonObject jsonObject) {
		ProjectConverter.fromJson(jsonObject, this);
	}

	public String getApiKey() {
		return _apiKey;
	}

	public String getCallbackHost() {
		return _callbackHost;
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

	public void setCallbackHost(String callbackHost) {
		_callbackHost = callbackHost;
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
	private String _callbackHost;
	private String _id;
	private String _name;

}