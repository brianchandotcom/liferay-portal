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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;

import java.util.Locale;

/**
 * @author Andrew Betts
 */
public class BaseBackgroundTaskDisplay implements BackgroundTaskDisplay {

	public BaseBackgroundTaskDisplay(
		BackgroundTask backgroundTask, Locale locale) {

		_backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		_details = null;
		_locale = locale;
		_message = null;
		_percentage = -1;
	}

	@Override
	public JSONObject getDetails() {
		return _details;
	}

	@Override
	public String getMessage() {
		return _message;
	}

	@Override
	public int getPercentage() {
		return _percentage;
	}

	@Override
	public boolean hasBackgroundTaskStatus() {
		if (_backgroundTaskStatus != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasDetails() {
		if (_details != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasMessage() {
		if (Validator.isNotNull(_message)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPercentage() {
		if (_percentage >= 0) {
			return true;
		}

		return false;
	}

	protected void addDetailItem(
		JSONArray detailItems, String message, JSONArray itemsList) {

		JSONObject detailItem = JSONFactoryUtil.createJSONObject();

		detailItem.put("message", message);
		detailItem.put("itemsList", itemsList);

		detailItems.put(detailItem);
	}

	protected JSONObject createDetailsJSON(
		String detailHeader, JSONArray detailItems, int status) {

		JSONObject detailsJSON = JSONFactoryUtil.createJSONObject();

		detailsJSON.put("detailHeader", detailHeader);
		detailsJSON.put("detailItems", detailItems);
		detailsJSON.put("status", status);

		return detailsJSON;
	}

	protected BackgroundTaskStatus getBackgroundTaskStatus() {
		return _backgroundTaskStatus;
	}

	protected Locale getLocale() {
		return _locale;
	}

	protected void setDetails(JSONObject details) {
		_details = details;
	}

	protected void setMessage(String message) {
		_message = message;
	}

	protected void setPercentage(int percentage) {
		_percentage = percentage;
	}

	private final BackgroundTaskStatus _backgroundTaskStatus;
	private JSONObject _details;
	private final Locale _locale;
	private String _message;
	private int _percentage;

}