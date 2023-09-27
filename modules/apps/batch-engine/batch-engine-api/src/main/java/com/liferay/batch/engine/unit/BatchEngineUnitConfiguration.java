/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class BatchEngineUnitConfiguration {

	public String getCallbackURL() {
		return _callbackURL;
	}

	public String getClassName() {
		return _className;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Map<String, String> getFieldNameMappingMap() {
		return _fieldNameMappingMap;
	}

	public Map<String, Serializable> getParameters() {
		return _parameters;
	}

	public String getTaskItemDelegateName() {
		return _taskItemDelegateName;
	}

	public long getUserId() {
		return _userId;
	}

	public String getVersion() {
		return _version;
	}

	public boolean isCheckPermissions() {
		return _checkPermissions;
	}

	public boolean isMultiCompany() {
		return _multiCompany;
	}

	public void setCallbackURL(String callbackURL) {
		_callbackURL = callbackURL;
	}

	@JsonIgnore
	public void setCheckPermissions(boolean checkPermissions) {
		_checkPermissions = checkPermissions;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setFieldNameMappingMap(
		Map<String, String> fieldNameMappingMap) {

		if (fieldNameMappingMap == null) {
			fieldNameMappingMap = Collections.emptyMap();
		}

		_fieldNameMappingMap = new HashMap<>(fieldNameMappingMap);
	}

	public void setMultiCompany(boolean multiCompany) {
		_multiCompany = multiCompany;
	}

	public void setParameters(Map<String, Serializable> parameters) {
		if (parameters == null) {
			parameters = Collections.emptyMap();
		}

		_parameters = new HashMap<>(parameters);
	}

	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_taskItemDelegateName = taskItemDelegateName;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setVersion(String version) {
		_version = version;
	}

	@JsonInclude
	@JsonProperty("callbackURL")
	private String _callbackURL;

	private boolean _checkPermissions = true;

	@JsonProperty("className")
	private String _className;

	@JsonProperty("companyId")
	private long _companyId;

	@JsonProperty("fieldNameMappingMap")
	private Map<String, String> _fieldNameMappingMap;

	@JsonProperty("multiCompany")
	private boolean _multiCompany;

	@JsonProperty("parameters")
	private Map<String, Serializable> _parameters;

	@JsonProperty("taskItemDelegateName")
	private String _taskItemDelegateName;

	@JsonProperty("userId")
	private long _userId;

	@JsonProperty("version")
	private String _version;

}