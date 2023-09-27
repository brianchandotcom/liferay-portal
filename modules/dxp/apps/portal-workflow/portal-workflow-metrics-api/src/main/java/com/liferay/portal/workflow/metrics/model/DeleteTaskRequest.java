/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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