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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;

import java.util.Locale;

/**
 * @author Andrew Betts
 */
public class StagingBackgroundTaskDisplay implements BackgroundTaskDisplay {

	public StagingBackgroundTaskDisplay(
		BackgroundTask backgroundTask, Locale locale) {

		_backgroundTaskStatus = null;
		_details = null;
		_message = null;
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
		if (_backgroundTaskStatus == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean hasDetails() {
		if (_details == null) {
			return false;
		}

		return true;
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
		if (_percentage > 0) {
			return true;
		}

		return false;
	}

	private final BackgroundTaskStatus _backgroundTaskStatus;
	private final JSONObject _details;
	private final String _message;
	private final int _percentage;

}