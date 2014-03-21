/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.diff;

/**
 * @author Eudaldo Alonso
 */
public class DiffVersion {

	public DiffVersion(long userId, double version) {
		_userId = userId;
		_version = version;
	}

	public DiffVersion(
		long userId, double version, String summary, String extraInfo) {

		_extraInfo = extraInfo;
		_version = version;
		_userId = userId;
		_summary = summary;
	}

	public String getExtraInfo() {
		return _extraInfo;
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

	public void setExtraInfo(String extraInfo) {
		_extraInfo = extraInfo;
	}

	public void setSummary(String summary) {
		_summary = summary;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setVersion(double version) {
		_version = version;
	}

	private String _extraInfo;
	private String _summary;
	private long _userId;
	private double _version;

}