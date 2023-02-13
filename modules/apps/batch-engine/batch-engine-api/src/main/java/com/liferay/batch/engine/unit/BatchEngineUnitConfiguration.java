/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.unit;

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
		return callbackURL;
	}

	public String getClassName() {
		return className;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Map<String, String> getFieldNameMappingMap() {
		return fieldNameMappingMap;
	}

	public Map<String, Serializable> getParameters() {
		return parameters;
	}

	public String getTaskItemDelegateName() {
		return taskItemDelegateName;
	}

	public long getUserId() {
		return userId;
	}

	public String getVersion() {
		return version;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setFieldNameMappingMap(
		Map<String, String> fieldNameMappingMap) {

		if (fieldNameMappingMap == null) {
			fieldNameMappingMap = Collections.emptyMap();
		}

		this.fieldNameMappingMap = new HashMap<>(fieldNameMappingMap);
	}

	public void setParameters(Map<String, Serializable> parameters) {
		if (parameters == null) {
			parameters = Collections.emptyMap();
		}

		this.parameters = new HashMap<>(parameters);
	}

	public void setTaskItemDelegateName(String taskItemDelegateName) {
		this.taskItemDelegateName = taskItemDelegateName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty
	public String callbackURL;

	@JsonProperty
	public String className;

	@JsonProperty
	public long companyId;

	@JsonProperty
	public Map<String, String> fieldNameMappingMap;

	@JsonProperty
	public Map<String, Serializable> parameters;

	@JsonProperty
	public String taskItemDelegateName;

	@JsonProperty
	public long userId;

	@JsonProperty
	public String version;

}