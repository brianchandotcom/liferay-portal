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

import java.util.Date;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerLogDisplay {

	public BatchPlannerLogDisplay(
		long batchPlannerLogId, String action, String name,
		String internalClassName, Date createDate, long userId, String status,
		long batchEngineImportTaskId, long batchEngineExportTaskId,
		int totalItemsCount, int processedItemsCount, boolean export) {

		_batchPlannerLogId = batchPlannerLogId;
		_action = action;
		_internalClassName = internalClassName;
		_createDate = createDate;
		_userId = userId;
		_status = status;
		_batchEngineImportTaskId = batchEngineImportTaskId;
		_batchEngineExportTaskId = batchEngineExportTaskId;
		_totalItemsCount = totalItemsCount;
		_processedItemsCount = processedItemsCount;
		_export = export;

		_title = name;
	}

	public String getAction() {
		return _action;
	}

	public long getBatchEngineExportTaskId() {
		return _batchEngineExportTaskId;
	}

	public long getBatchEngineImportTaskId() {
		return _batchEngineImportTaskId;
	}

	public long getBatchPlannerLogId() {
		return _batchPlannerLogId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getInternalClassName() {
		return _internalClassName;
	}

	public int getProcessedItemsCount() {
		return _processedItemsCount;
	}

	public String getStatus() {
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

	public static class Builder {

		public Builder action(String action) {
			_action = action;

			return this;
		}

		public Builder batchEngineExportTaskId(long batchEngineExportTaskId) {
			_batchEngineExportTaskId = batchEngineExportTaskId;

			return this;
		}

		public Builder batchEngineImportTaskId(long batchEngineImportTaskId) {
			_batchEngineImportTaskId = batchEngineImportTaskId;

			return this;
		}

		public Builder batchPlannerLogId(long batchPlannerLogId) {
			_batchPlannerLogId = batchPlannerLogId;

			return this;
		}

		public BatchPlannerLogDisplay build() {
			return new BatchPlannerLogDisplay(
				_batchPlannerLogId, _action, _title, _internalClassName,
				_createDate, _userId, _status, _batchEngineImportTaskId,
				_batchEngineExportTaskId, _totalItemsCount,
				_processedItemsCount, _export);
		}

		public Builder createDate(Date createDate) {
			_createDate = createDate;

			return this;
		}

		public Builder export(boolean export) {
			_export = export;

			return this;
		}

		public Builder internalClassName(String internalClassName) {
			_internalClassName = internalClassName;

			return this;
		}

		public Builder processedItemsCount(int processedItemsCount) {
			_processedItemsCount = processedItemsCount;

			return this;
		}

		public Builder status(String status) {
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
		private long _batchEngineExportTaskId;
		private long _batchEngineImportTaskId;
		private long _batchPlannerLogId;
		private Date _createDate;
		private boolean _export;
		private String _internalClassName;
		private int _processedItemsCount;
		private String _status;
		private String _title;
		private int _totalItemsCount;
		private long _userId;

	}

	private String _action;
	private long _batchEngineExportTaskId;
	private long _batchEngineImportTaskId;
	private long _batchPlannerLogId;
	private Date _createDate;
	private boolean _export;
	private String _internalClassName;
	private int _processedItemsCount;
	private String _status;
	private String _title;
	private int _totalItemsCount;
	private long _userId;

}