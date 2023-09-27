/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Selton Guedes
 */
public class UpdateProcessRequest {

	public Boolean getActive() {
		return _active;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDescription() {
		return _description;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
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

	public static class Builder {

		public Builder active(Boolean active) {
			_updateProcessRequest._active = active;

			return this;
		}

		public UpdateProcessRequest build() {
			return _updateProcessRequest;
		}

		public Builder companyId(long companyId) {
			_updateProcessRequest._companyId = companyId;

			return this;
		}

		public Builder description(String description) {
			_updateProcessRequest._description = description;

			return this;
		}

		public Builder modifiedDate(Date modifiedDate) {
			_updateProcessRequest._modifiedDate = modifiedDate;

			return this;
		}

		public Builder processId(long processId) {
			_updateProcessRequest._processId = processId;

			return this;
		}

		public Builder title(String title) {
			_updateProcessRequest._title = title;

			return this;
		}

		public Builder titleMap(Map<Locale, String> titleMap) {
			_updateProcessRequest._titleMap = titleMap;

			return this;
		}

		public Builder version(String version) {
			_updateProcessRequest._version = version;

			return this;
		}

		private final UpdateProcessRequest _updateProcessRequest =
			new UpdateProcessRequest();

	}

	private Boolean _active;
	private long _companyId;
	private String _description;
	private Date _modifiedDate;
	private long _processId;
	private String _title;
	private Map<Locale, String> _titleMap;
	private String _version;

}