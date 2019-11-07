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

package com.liferay.analytics.message.sender.model;

import com.liferay.portal.kernel.json.JSONObject;

import java.io.Serializable;

/**
 * @author Rachael Koestartyo
 */
public final class AnalyticsMessage implements Serializable {

	public static AnalyticsMessage.Builder builder(
		AnalyticsMessage analyticsMessage) {

		return new AnalyticsMessage.Builder(analyticsMessage);
	}

	public static AnalyticsMessage.Builder builder(
		String dataSourceId, String type) {

		return new AnalyticsMessage.Builder(dataSourceId, type);
	}

	public String getAction() {
		return _action;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public JSONObject getObjectJSONObject() {
		return _objectJSONObject;
	}

	public String getType() {
		return _type;
	}

	public static final class Builder {

		public Builder action(String action) {
			_analyticsMessage._action = action;

			return this;
		}

		public AnalyticsMessage build() {
			return _analyticsMessage;
		}

		public Builder object(JSONObject objectJSONObject) {
			_analyticsMessage._objectJSONObject = objectJSONObject;

			return this;
		}

		protected Builder(AnalyticsMessage analyticsMessage) {
			_analyticsMessage._action = analyticsMessage.getAction();
			_analyticsMessage._dataSourceId =
				analyticsMessage.getDataSourceId();
			_analyticsMessage._objectJSONObject =
				analyticsMessage.getObjectJSONObject();
			_analyticsMessage._type = analyticsMessage.getType();
		}

		protected Builder(String dataSourceId, String type) {
			_analyticsMessage._dataSourceId = dataSourceId;
			_analyticsMessage._type = type;
		}

		private final AnalyticsMessage _analyticsMessage =
			new AnalyticsMessage();

	}

	private AnalyticsMessage() {
	}

	private String _action;
	private String _dataSourceId;
	private JSONObject _objectJSONObject;
	private String _type;

}