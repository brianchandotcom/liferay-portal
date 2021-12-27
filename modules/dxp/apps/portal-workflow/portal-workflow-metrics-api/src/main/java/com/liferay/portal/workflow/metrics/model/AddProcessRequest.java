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
import java.util.Locale;
import java.util.Map;

/**
 * @author Selton Guedes
 */
public class AddProcessRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public long getProcessId() {
		return _processId;
	}

	public String getTitle() {
		return _title;
	}

	public Map<Locale, String> getTitleMap() {
		return _titleMap;
	}

	public String getVersion() {
		return _version;
	}

	public String[] getVersions() {
		return _versions;
	}

	public boolean isActive() {
		return _active;
	}

	public static class Builder {

		public Builder active(boolean active) {
			_addProcessRequest._active = active;

			return this;
		}

		public AddProcessRequest build() {
			return _addProcessRequest;
		}

		public Builder companyId(long companyId) {
			_addProcessRequest._companyId = companyId;

			return this;
		}

		public Builder createDate(Date createDate) {
			_addProcessRequest._createDate = createDate;

			return this;
		}

		public Builder description(String description) {
			_addProcessRequest._description = description;

			return this;
		}

		public Builder modifiedDate(Date modifiedDate) {
			_addProcessRequest._modifiedDate = modifiedDate;

			return this;
		}

		public Builder name(String name) {
			_addProcessRequest._name = name;

			return this;
		}

		public Builder processId(long processId) {
			_addProcessRequest._processId = processId;

			return this;
		}

		public Builder title(String title) {
			_addProcessRequest._title = title;

			return this;
		}

		public Builder titleMap(Map<Locale, String> titleMap) {
			_addProcessRequest._titleMap = titleMap;

			return this;
		}

		public Builder version(String version) {
			_addProcessRequest._version = version;

			return this;
		}

		public Builder versions(String[] versions) {
			_addProcessRequest._versions = versions;

			return this;
		}

		private final AddProcessRequest _addProcessRequest =
			new AddProcessRequest();

	}

	private boolean _active;
	private long _companyId;
	private Date _createDate;
	private String _description;
	private Date _modifiedDate;
	private String _name;
	private long _processId;
	private String _title;
	private Map<Locale, String> _titleMap;
	private String _version;
	private String[] _versions;

}