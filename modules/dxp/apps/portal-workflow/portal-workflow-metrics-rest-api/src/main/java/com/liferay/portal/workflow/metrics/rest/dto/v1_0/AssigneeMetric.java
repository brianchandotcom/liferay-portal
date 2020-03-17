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
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("AssigneeMetric")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssigneeMetric")
public class AssigneeMetric {

	@Schema
	@Valid
	public Assignee getAssignee() {
		return assignee;
	}

	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}

	@JsonIgnore
	public void setAssignee(
		UnsafeSupplier<Assignee, Exception> assigneeUnsafeSupplier) {

		try {
			assignee = assigneeUnsafeSupplier.get();
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
	protected Assignee assignee;

	@Schema
	public Long getDurationTaskAvg() {
		return durationTaskAvg;
	}

	public void setDurationTaskAvg(Long durationTaskAvg) {
		this.durationTaskAvg = durationTaskAvg;
	}

	@JsonIgnore
	public void setDurationTaskAvg(
		UnsafeSupplier<Long, Exception> durationTaskAvgUnsafeSupplier) {

		try {
			durationTaskAvg = durationTaskAvgUnsafeSupplier.get();
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
	protected Long durationTaskAvg;

	@Schema
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

	@Schema
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

	@Schema
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

		if (!(object instanceof AssigneeMetric)) {
			return false;
		}

		AssigneeMetric assigneeMetric = (AssigneeMetric)object;

		return Objects.equals(toString(), assigneeMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (assignee != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assignee\": ");

			sb.append(String.valueOf(assignee));
		}

		if (durationTaskAvg != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"durationTaskAvg\": ");

			sb.append(durationTaskAvg);
		}

		if (onTimeTaskCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onTimeTaskCount\": ");

			sb.append(onTimeTaskCount);
		}

		if (overdueTaskCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"overdueTaskCount\": ");

			sb.append(overdueTaskCount);
		}

		if (taskCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskCount\": ");

			sb.append(taskCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetric",
		name = "x-class-name"
	)
	public String xClassName;

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