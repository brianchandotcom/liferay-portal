/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import java.util.Date;

/**
 * @author Feliphe Marinho
 */
public class AddNodeRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public boolean getInitial() {
		return _initial;
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

	public boolean getTerminal() {
		return _terminal;
	}

	public String getType() {
		return _type;
	}

	public static class Builder {

		public AddNodeRequest build() {
			return _addNodeRequest;
		}

		public AddNodeRequest.Builder companyId(long companyId) {
			_addNodeRequest._companyId = companyId;

			return this;
		}

		public AddNodeRequest.Builder createDate(Date createDate) {
			_addNodeRequest._createDate = createDate;

			return this;
		}

		public AddNodeRequest.Builder initial(boolean initial) {
			_addNodeRequest._initial = initial;

			return this;
		}

		public AddNodeRequest.Builder modifiedDate(Date modifiedDate) {
			_addNodeRequest._modifiedDate = modifiedDate;

			return this;
		}

		public AddNodeRequest.Builder name(String name) {
			_addNodeRequest._name = name;

			return this;
		}

		public AddNodeRequest.Builder nodeId(long nodeId) {
			_addNodeRequest._nodeId = nodeId;

			return this;
		}

		public AddNodeRequest.Builder processId(long processId) {
			_addNodeRequest._processId = processId;

			return this;
		}

		public AddNodeRequest.Builder processVersion(String processVersion) {
			_addNodeRequest._processVersion = processVersion;

			return this;
		}

		public AddNodeRequest.Builder terminal(boolean terminal) {
			_addNodeRequest._terminal = terminal;

			return this;
		}

		public AddNodeRequest.Builder type(String type) {
			_addNodeRequest._type = type;

			return this;
		}

		private final AddNodeRequest _addNodeRequest = new AddNodeRequest();

	}

	private long _companyId;
	private Date _createDate;
	private boolean _initial;
	private Date _modifiedDate;
	private String _name;
	private long _nodeId;
	private long _processId;
	private String _processVersion;
	private boolean _terminal;
	private String _type;

}