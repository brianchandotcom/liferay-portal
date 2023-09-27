/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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