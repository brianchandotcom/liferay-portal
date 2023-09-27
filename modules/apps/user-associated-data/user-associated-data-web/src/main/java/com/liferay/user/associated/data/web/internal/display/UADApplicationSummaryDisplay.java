/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

/**
 * @author Drew Brokke
 */
public class UADApplicationSummaryDisplay {

	public String getApplicationKey() {
		return _applicationKey;
	}

	public int getCount() {
		return _count;
	}

	public boolean hasItems() {
		if (getCount() > 0) {
			return true;
		}

		return false;
	}

	public void setApplicationKey(String applicationKey) {
		_applicationKey = applicationKey;
	}

	public void setCount(int count) {
		_count = count;
	}

	private String _applicationKey;
	private int _count;

}