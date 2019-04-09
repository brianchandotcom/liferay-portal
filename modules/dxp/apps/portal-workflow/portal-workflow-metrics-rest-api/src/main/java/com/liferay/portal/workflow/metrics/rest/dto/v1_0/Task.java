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

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("Task")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Task")
public class Task {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	public Long getOnTimeTaskCount() {
		return onTimeTaskCount;
	}

	public void setOnTimeTaskCount(Long onTimeTaskCount) {
		this.onTimeTaskCount = onTimeTaskCount;
	}

	@JsonIgnore
	public void setOnTimeTaskCount(
		UnsafeSupplier<Long, Exception> onTimeTaskCountUnsafeSupplier) {

		try {
			onTimeTaskCount = onTimeTaskCountUnsafeSupplier.get();
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
	protected Long onTimeTaskCount;

	public Long getOverdueTaskCount() {
		return overdueTaskCount;
	}

	public void setOverdueTaskCount(Long overdueTaskCount) {
		this.overdueTaskCount = overdueTaskCount;
	}

	@JsonIgnore
	public void setOverdueTaskCount(
		UnsafeSupplier<Long, Exception> overdueTaskCountUnsafeSupplier) {

		try {
			overdueTaskCount = overdueTaskCountUnsafeSupplier.get();
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
	protected Long overdueTaskCount;

	public Long getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Long taskCount) {
		this.taskCount = taskCount;
	}

	@JsonIgnore
	public void setTaskCount(
		UnsafeSupplier<Long, Exception> taskCountUnsafeSupplier) {

		try {
			taskCount = taskCountUnsafeSupplier.get();
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
	protected Long taskCount;

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

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append(name);
		}

		sb.append(", ");

		sb.append("\"onTimeTaskCount\": ");

		if (onTimeTaskCount == null) {
			sb.append("null");
		}
		else {
			sb.append(onTimeTaskCount);
		}

		sb.append(", ");

		sb.append("\"overdueTaskCount\": ");

		if (overdueTaskCount == null) {
			sb.append("null");
		}
		else {
			sb.append(overdueTaskCount);
		}

		sb.append(", ");

		sb.append("\"taskCount\": ");

		if (taskCount == null) {
			sb.append("null");
		}
		else {
			sb.append(taskCount);
		}

		sb.append("}");

		return sb.toString();
	}

}