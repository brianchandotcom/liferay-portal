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