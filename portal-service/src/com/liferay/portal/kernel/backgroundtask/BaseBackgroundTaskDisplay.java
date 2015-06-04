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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;

import java.util.Locale;

/**
 * @author Andrew Betts
 */
public abstract class BaseBackgroundTaskDisplay
	implements BackgroundTaskDisplay {

	public BaseBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		_backgroundTask = backgroundTask;
		_backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());
	}

	@Override
	public JSONObject getDetails() {
		return getDetails(LocaleUtil.getDefault());
	}

	@Override
	public JSONObject getDetails(Locale locale) {
		JSONObject detailsJSON = createDetails(_backgroundTask);

		return BackgroundTaskDisplayJSONTransformer.translateDetailsJSON(
			locale, detailsJSON);
	}

	@Override
	public String getMessage() {
		return getMessage(Locale.getDefault());
	}

	@Override
	public String getMessage(Locale locale) {
		return LanguageUtil.get(locale, createMessageKey());
	}

	@Override
	public abstract int getPercentage();

	@Override
	public boolean hasBackgroundTaskStatus() {
		if (_backgroundTaskStatus != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasDetails() {
		if (createDetails(_backgroundTask) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasMessage() {
		if (Validator.isNotNull(createMessageKey())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPercentage() {
		if (getPercentage() >= 0) {
			return true;
		}

		return false;
	}

	protected abstract JSONObject createDetails(BackgroundTask backgroundTask);

	protected abstract String createMessageKey();

	protected BackgroundTask getBackgroundTask() {
		return _backgroundTask;
	}

	protected BackgroundTaskStatus getBackgroundTaskStatus() {
		return _backgroundTaskStatus;
	}

	protected long getBackgroundTaskStatusAttributeLong(String attributeKey) {
		return GetterUtil.getLong(
			_backgroundTaskStatus.getAttribute(attributeKey));
	}

	protected String getBackgroundTaskStatusAttributeString(
		String attributeKey) {

		return GetterUtil.getString(
			_backgroundTaskStatus.getAttribute(attributeKey));
	}

	protected static final int _MAX_PERCENTAGE = 100;

	protected static final int _NO_PERCENTAGE = -1;

	private final BackgroundTask _backgroundTask;
	private final BackgroundTaskStatus _backgroundTaskStatus;

}