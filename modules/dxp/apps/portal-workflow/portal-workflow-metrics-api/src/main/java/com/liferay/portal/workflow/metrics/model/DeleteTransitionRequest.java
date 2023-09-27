/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

/**
 * @author Selton Guedes
 */
public class DeleteTransitionRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getTransitionId() {
		return _transitionId;
	}

	public static class Builder {

		public DeleteTransitionRequest build() {
			return _deleteTransitionRequest;
		}

		public Builder companyId(long companyId) {
			_deleteTransitionRequest._companyId = companyId;

			return this;
		}

		public Builder transitionId(long transitionId) {
			_deleteTransitionRequest._transitionId = transitionId;

			return this;
		}

		private final DeleteTransitionRequest _deleteTransitionRequest =
			new DeleteTransitionRequest();

	}

	private long _companyId;
	private long _transitionId;

}