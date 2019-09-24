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

package com.liferay.headless.batch.engine.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ivica Cardic
 * @generated
 */
@Generated("")
@GraphQLName("Task")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Task")
public class Task {

	@Schema
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonIgnore
	public void setClassName(
		UnsafeSupplier<String, Exception> classNameUnsafeSupplier) {

		try {
			className = classNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String className;

	@DecimalMin("0")
	@Schema
	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@JsonIgnore
	public void setEndTime(
		UnsafeSupplier<Long, Exception> endTimeUnsafeSupplier) {

		try {
			endTime = endTimeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long endTime;

	@Schema
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@JsonIgnore
	public void setErrorMessage(
		UnsafeSupplier<String, Exception> errorMessageUnsafeSupplier) {

		try {
			errorMessage = errorMessageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String errorMessage;

	@Schema
	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	@JsonIgnore
	public void setExecuteStatus(
		UnsafeSupplier<String, Exception> executeStatusUnsafeSupplier) {

		try {
			executeStatus = executeStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String executeStatus;

	@DecimalMin("0")
	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@Schema
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@JsonIgnore
	public void setOperation(
		UnsafeSupplier<String, Exception> operationUnsafeSupplier) {

		try {
			operation = operationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String operation;

	@DecimalMin("0")
	@Schema
	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	@JsonIgnore
	public void setStartTime(
		UnsafeSupplier<Long, Exception> startTimeUnsafeSupplier) {

		try {
			startTime = startTimeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long startTime;

	@Schema
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@JsonIgnore
	public void setVersion(
		UnsafeSupplier<String, Exception> versionUnsafeSupplier) {

		try {
			version = versionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String version;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Task)) {
			return false;
		}

		Task task = (Task)object;

		return Objects.equals(toString(), task.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (className != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"className\": ");

			sb.append("\"");

			sb.append(_escape(className));

			sb.append("\"");
		}

		if (endTime != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endTime\": ");

			sb.append(endTime);
		}

		if (errorMessage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(_escape(errorMessage));

			sb.append("\"");
		}

		if (executeStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"executeStatus\": ");

			sb.append("\"");

			sb.append(_escape(executeStatus));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (operation != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"operation\": ");

			sb.append("\"");

			sb.append(_escape(operation));

			sb.append("\"");
		}

		if (startTime != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startTime\": ");

			sb.append(startTime);
		}

		if (version != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(version));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}