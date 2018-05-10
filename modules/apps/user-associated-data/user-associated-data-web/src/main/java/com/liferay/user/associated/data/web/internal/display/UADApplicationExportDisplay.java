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

package com.liferay.user.associated.data.web.internal.display;

import java.util.Date;

/**
 * @author Pei-Jung Lan
 */
public class UADApplicationExportDisplay {

	public UADApplicationExportDisplay(
		String applicationKey, int dataCount, boolean exportSupported,
		Date lastExportDate) {

		_applicationKey = applicationKey;
		_dataCount = dataCount;
		_exportSupported = exportSupported;
		_lastExportDate = lastExportDate;
	}

	public String getApplicationKey() {
		return _applicationKey;
	}

	public int getDataCount() {
		return _dataCount;
	}

	public Date getLastExportDate() {
		return _lastExportDate;
	}

	public boolean isExportSupported() {
		return _exportSupported;
	}

	private final String _applicationKey;
	private final int _dataCount;
	private final boolean _exportSupported;
	private final Date _lastExportDate;

}