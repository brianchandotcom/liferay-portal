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

		public CompleteTaskRequest.Builder setCompanyId(long companyId) {
			_completeTaskRequest._companyId = companyId;

			return this;
		}

		public CompleteTaskRequest.Builder setCompletionDate(
			Date completionDate) {

			_completeTaskRequest._completionDate = completionDate;

			return this;
		}

		public CompleteTaskRequest.Builder setCompletionUserId(
			Long completionUserId) {

			_completeTaskRequest._completionUserId = completionUserId;

			return this;
		}

		public CompleteTaskRequest.Builder setCompletionUserId(
			UnsafeSupplier<Long, Exception> completionUserIdUnsafeSupplier) {

			try {
				_completeTaskRequest._completionUserId =
					completionUserIdUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			return this;
		}

		public CompleteTaskRequest.Builder setDuration(long duration) {
			_completeTaskRequest._duration = duration;

			return this;
		}

		public CompleteTaskRequest.Builder setDuration(
			UnsafeSupplier<Long, Exception> durationUnsafeSupplier) {

			try {
				_completeTaskRequest._duration = durationUnsafeSupplier.get();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			return this;
		}

		public CompleteTaskRequest.Builder setModifiedDate(Date modifiedDate) {
			_completeTaskRequest._modifiedDate = modifiedDate;

			return this;
		}

		public CompleteTaskRequest.Builder setTaskId(long taskId) {
			_completeTaskRequest._taskId = taskId;

			return this;
		}

		public CompleteTaskRequest.Builder setUserId(long userId) {
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