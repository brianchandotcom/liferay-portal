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

package com.liferay.headless.admin.workflow.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("WorkflowLog")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WorkflowLog")
public class WorkflowLog {

	public static WorkflowLog toDTO(String json) {
		return ObjectMapperUtil.readValue(WorkflowLog.class, json);
	}

	@Schema(
		description = "The user account of the person auditing the workflow."
	)
	@Valid
	public Creator getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(Creator auditPerson) {
		this.auditPerson = auditPerson;
	}

	@JsonIgnore
	public void setAuditPerson(
		UnsafeSupplier<Creator, Exception> auditPersonUnsafeSupplier) {

		try {
			auditPerson = auditPersonUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The user account of the person auditing the workflow."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator auditPerson;

	@Schema(description = "The log's comments.")
	public String getCommentLog() {
		return commentLog;
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;
	}

	@JsonIgnore
	public void setCommentLog(
		UnsafeSupplier<String, Exception> commentLogUnsafeSupplier) {

		try {
			commentLog = commentLogUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The log's comments.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String commentLog;

	@Schema(description = "The log's creation date.")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The log's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@Schema(description = "The log's ID.")
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

	@GraphQLField(description = "The log's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema(description = "The person assigned to the workflow.")
	@Valid
	public Creator getPerson() {
		return person;
	}

	public void setPerson(Creator person) {
		this.person = person;
	}

	@JsonIgnore
	public void setPerson(
		UnsafeSupplier<Creator, Exception> personUnsafeSupplier) {

		try {
			person = personUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The person assigned to the workflow.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator person;

	@Schema(description = "The previous person assigned to the workflow.")
	@Valid
	public Creator getPreviousPerson() {
		return previousPerson;
	}

	public void setPreviousPerson(Creator previousPerson) {
		this.previousPerson = previousPerson;
	}

	@JsonIgnore
	public void setPreviousPerson(
		UnsafeSupplier<Creator, Exception> previousPersonUnsafeSupplier) {

		try {
			previousPerson = previousPersonUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The previous person assigned to the workflow.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator previousPerson;

	@Schema(description = "The workflow's previous state.")
	public String getPreviousState() {
		return previousState;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	@JsonIgnore
	public void setPreviousState(
		UnsafeSupplier<String, Exception> previousStateUnsafeSupplier) {

		try {
			previousState = previousStateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The workflow's previous state.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String previousState;

	@Schema(description = "The workflow's current state.")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<String, Exception> stateUnsafeSupplier) {

		try {
			state = stateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The workflow's current state.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String state;

	@Schema(description = "The task asociated with this workflow log.")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@JsonIgnore
	public void setTaskId(
		UnsafeSupplier<Long, Exception> taskIdUnsafeSupplier) {

		try {
			taskId = taskIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The task asociated with this workflow log.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long taskId;

	@Schema(description = "The workflow log's type.")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The workflow log's type.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String type;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowLog)) {
			return false;
		}

		WorkflowLog workflowLog = (WorkflowLog)object;

		return Objects.equals(toString(), workflowLog.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (auditPerson != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"auditPerson\": ");

			sb.append(String.valueOf(auditPerson));
		}

		if (commentLog != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"commentLog\": ");

			sb.append("\"");

			sb.append(_escape(commentLog));

			sb.append("\"");
		}

		if (dateCreated != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCreated));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (person != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"person\": ");

			sb.append(String.valueOf(person));
		}

		if (previousPerson != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousPerson\": ");

			sb.append(String.valueOf(previousPerson));
		}

		if (previousState != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousState\": ");

			sb.append("\"");

			sb.append(_escape(previousState));

			sb.append("\"");
		}

		if (state != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append("\"");

			sb.append(_escape(state));

			sb.append("\"");
		}

		if (taskId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskId\": ");

			sb.append(taskId);
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(type));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
			sb.append("\":");

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
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}