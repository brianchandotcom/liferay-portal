/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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