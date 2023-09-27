/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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