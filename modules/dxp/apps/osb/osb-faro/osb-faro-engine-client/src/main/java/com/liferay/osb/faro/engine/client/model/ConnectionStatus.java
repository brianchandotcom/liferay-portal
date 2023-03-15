/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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