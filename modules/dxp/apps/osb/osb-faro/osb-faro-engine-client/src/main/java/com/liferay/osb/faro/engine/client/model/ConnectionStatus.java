/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Shinn Lok
 */
public class ConnectionStatus {

	public ConnectionStatus() {
	}

	public ConnectionStatus(long count, Date modifiedDate, int status) {
		_count = count;

		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}

		_status = status;
	}

	public long getCount() {
		return _count;
	}

	public Date getModifiedDate() {
		if (_modifiedDate == null) {
			return null;
		}

		return new Date(_modifiedDate.getTime());
	}

	public int getStatus() {
		return _status;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _count;
	private Date _modifiedDate;
	private int _status;

}