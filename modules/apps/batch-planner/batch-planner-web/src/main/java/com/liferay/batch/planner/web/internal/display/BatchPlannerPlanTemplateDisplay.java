/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.web.internal.display;

import java.util.Date;

/**
 * @author Carlos Correa
 */
public class BatchPlannerPlanTemplateDisplay {

	public String getAction() {
		return _action;
	}

	public long getBatchPlannerPlanId() {
		return _batchPlannerPlanId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getExternalType() {
		return _externalType;
	}

	public String getInternalClassNameKey() {
		return _internalClassNameKey;
	}

	public String getTitle() {
		return _title;
	}

	public String getUserName() {
		return _userName;
	}

	public boolean isExport() {
		return _export;
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

		public BatchPlannerPlanTemplateDisplay build() {
			return new BatchPlannerPlanTemplateDisplay(
				_action, _batchPlannerPlanId, _createDate, _export,
				_externalType, _internalClassNameKey, _title, _userName);
		}

		public Builder createDate(Date createDate) {
			_createDate = createDate;

			return this;
		}

		public Builder export(boolean export) {
			_export = export;

			return this;
		}

		public Builder externalType(String externalType) {
			_externalType = externalType;

			return this;
		}

		public Builder internalClassNameKey(String internalClassNameKey) {
			_internalClassNameKey = internalClassNameKey;

			return this;
		}

		public Builder title(String title) {
			_title = title;

			return this;
		}

		public Builder userName(String userName) {
			_userName = userName;

			return this;
		}

		private String _action;
		private long _batchPlannerPlanId;
		private Date _createDate;
		private boolean _export;
		private String _externalType;
		private String _internalClassNameKey;
		private String _title;
		private String _userName;

	}

	private BatchPlannerPlanTemplateDisplay(
		String action, long batchPlannerPlanId, Date createDate, boolean export,
		String externalType, String internalClassNameKey, String title,
		String userName) {

		_action = action;
		_batchPlannerPlanId = batchPlannerPlanId;
		_createDate = createDate;
		_export = export;
		_externalType = externalType;
		_internalClassNameKey = internalClassNameKey;
		_title = title;
		_userName = userName;
	}

	private String _action;
	private long _batchPlannerPlanId;
	private Date _createDate;
	private boolean _export;
	private String _externalType;
	private String _internalClassNameKey;
	private String _title;
	private String _userName;

}