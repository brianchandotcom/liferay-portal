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

/**
 * @author Feliphe Marinho
 */
public class DeleteTaskRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getTaskId() {
		return _taskId;
	}

	public static class Builder {

		public DeleteTaskRequest build() {
			return _deleteTaskRequest;
		}

		public DeleteTaskRequest.Builder companyId(long companyId) {
			_deleteTaskRequest._companyId = companyId;

			return this;
		}

		public DeleteTaskRequest.Builder taskId(long taskId) {
			_deleteTaskRequest._taskId = taskId;

			return this;
		}

		private final DeleteTaskRequest _deleteTaskRequest =
			new DeleteTaskRequest();

	}

	private long _companyId;
	private long _taskId;

}