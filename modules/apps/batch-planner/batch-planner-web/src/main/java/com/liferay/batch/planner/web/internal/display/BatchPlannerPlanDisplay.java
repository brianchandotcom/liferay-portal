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

package com.liferay.batch.planner.web.internal.display;

import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;

import java.util.Date;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerPlanDisplay {

	public String getAction() {
		return _action;
	}

	public long getBatchPlannerPlanId() {
		return _batchPlannerPlanId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public int getFailedItemsCount() {
		return _failedItemsCount;
	}

	public String getInternalClassName() {
		return _internalClassName;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public int getProcessedItemsCount() {
		return _processedItemsCount;
	}

	public int getStatus() {
		return _status;
	}

	public String getTitle() {
		return _title;
	}

	public int getTotalItemsCount() {
		return _totalItemsCount;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isExport() {
		return _export;
	}

	public boolean isStatusCompleted() {
		if (_status == BatchPlannerPlanConstants.STATUS_COMPLETED) {
			return true;
		}

		return false;
	}

	public static class Builder {

		public Builder action(String action) {
			_action = action;

			return this;
		}

		public Builder batchPlannerPlanId(long batchPlannerPlanId) {
			_batchPlannerPlanId = batchPlannerPlanId;

			return this;
		}

		public BatchPlannerPlanDisplay build() {
			return new BatchPlannerPlanDisplay(
				_action, _batchPlannerPlanId, _createDate, _export,
				_failedItemsCount, _internalClassName, _modifiedDate,
				_processedItemsCount, _status, _title, _totalItemsCount,
				_userId);
		}

		public Builder createDate(Date createDate) {
			_createDate = createDate;

			return this;
		}

		public Builder export(boolean export) {
			_export = export;

			return this;
		}

		public Builder failedItemsCount(int failedItemsCount) {
			_failedItemsCount = failedItemsCount;

			return this;
		}

		public Builder internalClassName(String internalClassName) {
			_internalClassName = internalClassName;

			return this;
		}

		public Builder modifiedDate(Date modifiedDate) {
			_modifiedDate = modifiedDate;

			return this;
		}

		public Builder processedItemsCount(int processedItemsCount) {
			_processedItemsCount = processedItemsCount;

			return this;
		}

		public Builder status(int status) {
			_status = status;

			return this;
		}

		public Builder title(String title) {
			_title = title;

			return this;
		}

		public Builder totalItemsCount(int totalItemsCount) {
			_totalItemsCount = totalItemsCount;

			return this;
		}

		public Builder userId(long userId) {
			_userId = userId;

			return this;
		}

		private String _action;
		private long _batchPlannerPlanId;
		private Date _createDate;
		private boolean _export;
		private int _failedItemsCount;
		private String _internalClassName;
		private Date _modifiedDate;
		private int _processedItemsCount;
		private int _status;
		private String _title;
		private int _totalItemsCount;
		private long _userId;

	}

	private BatchPlannerPlanDisplay(
		String action, long batchPlannerPlanId, Date createDate, boolean export,
		int failedItemsCount, String internalClassName, Date modifiedDate,
		int processedItemsCount, int status, String title, int totalItemsCount,
		long userId) {

		_action = action;
		_batchPlannerPlanId = batchPlannerPlanId;
		_createDate = createDate;
		_export = export;
		_failedItemsCount = failedItemsCount;
		_internalClassName = internalClassName;
		_modifiedDate = modifiedDate;
		_processedItemsCount = processedItemsCount;
		_status = status;
		_title = title;
		_totalItemsCount = totalItemsCount;
		_userId = userId;
	}

	private String _action;
	private long _batchPlannerPlanId;
	private Date _createDate;
	private boolean _export;
	private int _failedItemsCount;
	private String _internalClassName;
	private Date _modifiedDate;
	private int _processedItemsCount;
	private int _status;
	private String _title;
	private int _totalItemsCount;
	private long _userId;

}