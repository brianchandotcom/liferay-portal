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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class StagingBackgroundTaskDisplayHelperUtil {

	public static JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return _stagingBackgroundTaskHelper.getErrorMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public static JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return _stagingBackgroundTaskHelper.getExceptionMessagesJSONObject(
			locale, e, contextMap);
	}

	public static JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return _stagingBackgroundTaskHelper.getWarningMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public void setStagingBackgroundTaskHelper(
		StagingBackgroundTaskDisplayHelper stagingBackgroundTaskHelper) {

		_stagingBackgroundTaskHelper = stagingBackgroundTaskHelper;
	}

	private static StagingBackgroundTaskDisplayHelper
		_stagingBackgroundTaskHelper;

}