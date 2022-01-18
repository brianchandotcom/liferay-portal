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

package com.liferay.diff;

import java.util.Date;

/**
 * @author Eudaldo Alonso
 */
public class DiffVersion {

	public DiffVersion(long userId, double version, Date modifiedDate) {
		this(userId, version, modifiedDate, null, null);
	}

	public DiffVersion(
		long userId, double version, Date modifiedDate, String summary,
		String extraInfo) {

		_userId = userId;
		_version = version;
		_modifiedDate = modifiedDate;
		_summary = summary;
		_extraInfo = extraInfo;
	}

	public String getExtraInfo() {
		return _extraInfo;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getSummary() {
		return _summary;
	}

	public long getUserId() {
		return _userId;
	}

	public double getVersion() {
		return _version;
	}

	private final String _extraInfo;
	private final Date _modifiedDate;
	private final String _summary;
	private final long _userId;
	private final double _version;

}