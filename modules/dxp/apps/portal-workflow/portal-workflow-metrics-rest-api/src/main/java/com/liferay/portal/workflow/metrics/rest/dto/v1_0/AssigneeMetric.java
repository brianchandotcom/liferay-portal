/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
@GraphQLName(
	description = "https://schema.org/AssigneeMetric", value = "AssigneeMetric"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssigneeMetric")
public class AssigneeMetric implements Serializable {

	public static AssigneeMetric toDTO(String json) {
		return ObjectMapperUtil.readValue(AssigneeMetric.class, json);
	}

	public static AssigneeMetric unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AssigneeMetric.class, json);
	}

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
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetric",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}