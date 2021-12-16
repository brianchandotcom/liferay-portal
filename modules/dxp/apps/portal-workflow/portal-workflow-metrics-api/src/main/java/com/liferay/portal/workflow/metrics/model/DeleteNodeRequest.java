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
public class DeleteNodeRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public static class Builder {

		public DeleteNodeRequest build() {
			return _deleteNodeRequest;
		}

		public DeleteNodeRequest.Builder companyId(long companyId) {
			_deleteNodeRequest._companyId = companyId;

			return this;
		}

		public DeleteNodeRequest.Builder nodeId(long nodeId) {
			_deleteNodeRequest._nodeId = nodeId;

			return this;
		}

		private final DeleteNodeRequest _deleteNodeRequest =
			new DeleteNodeRequest();

	}

	private long _companyId;
	private long _nodeId;

}