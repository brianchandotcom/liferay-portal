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
 * @author Selton Guedes
 */
public class DeleteProcessRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getProcessId() {
		return _processId;
	}

	public static class Builder {

		public DeleteProcessRequest build() {
			return _deleteProcessRequest;
		}

		public Builder companyId(long companyId) {
			_deleteProcessRequest._companyId = companyId;

			return this;
		}

		public Builder processId(long processId) {
			_deleteProcessRequest._processId = processId;

			return this;
		}

		private final DeleteProcessRequest _deleteProcessRequest =
			new DeleteProcessRequest();

	}

	private long _companyId;
	private long _processId;

}