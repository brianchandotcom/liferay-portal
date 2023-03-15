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
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DataSourceProgress {

	public Date getDateRecorded() {
		if (_dateRecorded == null) {
			return null;
		}

		return new Date(_dateRecorded.getTime());
	}

	public long getProcessedOperations() {
		return _processedOperations;
	}

	public String getStatus() {
		return _status;
	}

	public long getTotalOperations() {
		return _totalOperations;
	}

	public void setDateRecorded(Date dateRecorded) {
		if (dateRecorded != null) {
			_dateRecorded = new Date(dateRecorded.getTime());
		}
	}

	public void setProcessedOperations(long processedOperations) {
		_processedOperations = processedOperations;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setTotalOperations(long totalOperations) {
		_totalOperations = totalOperations;
	}

	public enum Status {

		COMPLETED, FAILED, IN_PROGRESS, STARTED

	}

	private Date _dateRecorded;
	private long _processedOperations;
	private String _status;
	private long _totalOperations;

}