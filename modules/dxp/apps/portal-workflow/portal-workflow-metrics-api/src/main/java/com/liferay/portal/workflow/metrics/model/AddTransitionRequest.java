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

import java.util.Date;

/**
 * @author Selton Guedes
 */
public class AddTransitionRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public long getProcessId() {
		return _processId;
	}

	public String getProcessVersion() {
		return _processVersion;
	}

	public long getSourceNodeId() {
		return _sourceNodeId;
	}

	public String getSourceNodeName() {
		return _sourceNodeName;
	}

	public long getTargetNodeId() {
		return _targetNodeId;
	}

	public String getTargetNodeName() {
		return _targetNodeName;
	}

	public long getTransitionId() {
		return _transitionId;
	}

	public long getUserId() {
		return _userId;
	}

	public static class Builder {

		public AddTransitionRequest build() {
			return _addTransitionRequest;
		}

		public Builder companyId(long companyId) {
			_addTransitionRequest._companyId = companyId;

			return this;
		}

		public Builder createDate(Date createDate) {
			_addTransitionRequest._createDate = createDate;

			return this;
		}

		public Builder modifiedDate(Date modifiedDate) {
			_addTransitionRequest._modifiedDate = modifiedDate;

			return this;
		}

		public Builder name(String name) {
			_addTransitionRequest._name = name;

			return this;
		}

		public Builder nodeId(long nodeId) {
			_addTransitionRequest._nodeId = nodeId;

			return this;
		}

		public Builder processId(long processId) {
			_addTransitionRequest._processId = processId;

			return this;
		}

		public Builder processVersion(String processVersion) {
			_addTransitionRequest._processVersion = processVersion;

			return this;
		}

		public Builder sourceNodeId(long sourceNodeId) {
			_addTransitionRequest._sourceNodeId = sourceNodeId;

			return this;
		}

		public Builder sourceNodeName(String sourceNodeName) {
			_addTransitionRequest._sourceNodeName = sourceNodeName;

			return this;
		}

		public Builder targetNodeId(long targetNodeId) {
			_addTransitionRequest._targetNodeId = targetNodeId;

			return this;
		}

		public Builder targetNodeName(String targetNodeName) {
			_addTransitionRequest._targetNodeName = targetNodeName;

			return this;
		}

		public Builder transitionId(long transitionId) {
			_addTransitionRequest._transitionId = transitionId;

			return this;
		}

		public Builder userId(long userId) {
			_addTransitionRequest._userId = userId;

			return this;
		}

		private final AddTransitionRequest _addTransitionRequest =
			new AddTransitionRequest();

	}

	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private long _nodeId;
	private long _processId;
	private String _processVersion;
	private long _sourceNodeId;
	private String _sourceNodeName;
	private long _targetNodeId;
	private String _targetNodeName;
	private long _transitionId;
	private long _userId;

}