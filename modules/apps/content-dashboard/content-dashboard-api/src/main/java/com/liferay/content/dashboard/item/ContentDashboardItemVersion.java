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

package com.liferay.content.dashboard.item;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Date;

/**
 * @author Jürgen Kappler
 */
public class ContentDashboardItemVersion {

	public ContentDashboardItemVersion(
		String changeLog, Date createDate, String label, String style,
		String userName, String version) {

		_changeLog = changeLog;
		_createDate = createDate;
		_label = label;
		_style = style;
		_userName = userName;
		_version = version;
	}

	public String getChangeLog() {
		return _changeLog;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getLabel() {
		return _label;
	}

	public String getStyle() {
		return _style;
	}

	public String getUserName() {
		return _userName;
	}

	public String getVersion() {
		return _version;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"changeLog", getChangeLog()
		).put(
			"createDate", getCreateDate()
		).put(
			"statusLabel", getLabel()
		).put(
			"statusStyle", getStyle()
		).put(
			"userName", getUserName()
		).put(
			"version", getVersion()
		);
	}

	private final String _changeLog;
	private final Date _createDate;
	private final String _label;
	private final String _style;
	private final String _userName;
	private final String _version;

}