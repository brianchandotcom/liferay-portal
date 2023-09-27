/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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