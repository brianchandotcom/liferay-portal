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

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Andrew Betts
 */
public interface BackgroundTaskDisplay extends Serializable {

	public JSONObject getDetailsJSONObject();

	public JSONObject getDetailsJSONObject(Locale locale);

	public String getMessage();

	public String getMessage(Locale locale);

	public int getPercentage();

	public boolean hasBackgroundTaskStatus();

	public boolean hasDetails();

	public boolean hasMessage();

	public boolean hasPercentage();

}