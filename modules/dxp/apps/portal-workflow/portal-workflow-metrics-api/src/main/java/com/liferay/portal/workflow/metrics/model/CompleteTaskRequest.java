/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

/**
 * @author Feliphe Marinho
 */
public class CompleteTaskRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCompletionDate() {
		return _completionDate;
	}

	public Long getCompletionUserId() {
		return _completionUserId;
	}

	public long getDuration() {
		return _duration;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long getTaskId() {
		return _taskId;
	}

	public long getUserId() {
		return _userId;
	}

	public static class Builder {

		public CompleteTaskRequest build() {
			return _completeTaskRequest;
		}

		public CompleteTaskRequest.Builder companyId(long companyId) {
			_completeTaskRequest._companyId = companyId;

			return this;
		}

		public CompleteTaskRequest.Builder completionDate(Date completionDate) {
			_completeTaskRequest._completionDate = completionDate;

			return this;
		}

		public CompleteTaskRequest.Builder completionUserId(
			Long completionUserId) {

			_completeTaskRequest._completionUserId = completionUserId;

			return this;
		}

		public CompleteTaskRequest.Builder completionUserId(
			UnsafeSupplier<Long, Exception> completionUserIdUnsafeSupplier) {

			try {
				_completeTaskRequest._completionUserId =
					completionUserIdUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}

			return this;
		}

		public CompleteTaskRequest.Builder duration(long duration) {
			_completeTaskRequest._duration = duration;

			return this;
		}

		public CompleteTaskRequest.Builder duration(
			UnsafeSupplier<Long, Exception> durationUnsafeSupplier) {

			try {
				_completeTaskRequest._duration = durationUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}

			return this;
		}

		public CompleteTaskRequest.Builder modifiedDate(Date modifiedDate) {
			_completeTaskRequest._modifiedDate = modifiedDate;

			return this;
		}

		public CompleteTaskRequest.Builder taskId(long taskId) {
			_completeTaskRequest._taskId = taskId;

			return this;
		}

		public CompleteTaskRequest.Builder userId(long userId) {
			_completeTaskRequest._userId = userId;

			return this;
		}

		private final CompleteTaskRequest _completeTaskRequest =
			new CompleteTaskRequest();

	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompleteTaskRequest.class);

	private long _companyId;
	private Date _completionDate;
	private Long _completionUserId;
	private long _duration;
	private Date _modifiedDate;
	private long _taskId;
	private long _userId;

}