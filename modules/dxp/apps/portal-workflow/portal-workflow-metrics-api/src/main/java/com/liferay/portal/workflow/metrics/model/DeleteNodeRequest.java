/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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