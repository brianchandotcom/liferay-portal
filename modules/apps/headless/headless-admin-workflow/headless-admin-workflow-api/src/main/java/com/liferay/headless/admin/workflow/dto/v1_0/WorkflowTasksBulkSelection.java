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

import io.swagger.v3.oas.annotations.media.Schema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("WorkflowTasksBulkSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WorkflowTasksBulkSelection")
public class WorkflowTasksBulkSelection {

	@Schema
	public Boolean getAndOperator() {
		return andOperator;
	}

	public void setAndOperator(Boolean andOperator) {
		this.andOperator = andOperator;
	}

	@JsonIgnore
	public void setAndOperator(
		UnsafeSupplier<Boolean, Exception> andOperatorUnsafeSupplier) {

		try {
			andOperator = andOperatorUnsafeSupplier.get();
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
	protected Boolean andOperator;

	@Schema
	public Long[] getAssetPrimaryKeys() {
		return assetPrimaryKeys;
	}

	public void setAssetPrimaryKeys(Long[] assetPrimaryKeys) {
		this.assetPrimaryKeys = assetPrimaryKeys;
	}

	@JsonIgnore
	public void setAssetPrimaryKeys(
		UnsafeSupplier<Long[], Exception> assetPrimaryKeysUnsafeSupplier) {

		try {
			assetPrimaryKeys = assetPrimaryKeysUnsafeSupplier.get();
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
	protected Long[] assetPrimaryKeys;

	@Schema
	public String getAssetTitle() {
		return assetTitle;
	}

	public void setAssetTitle(String assetTitle) {
		this.assetTitle = assetTitle;
	}

	@JsonIgnore
	public void setAssetTitle(
		UnsafeSupplier<String, Exception> assetTitleUnsafeSupplier) {

		try {
			assetTitle = assetTitleUnsafeSupplier.get();
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
	protected String assetTitle;

	@Schema
	public String[] getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(String[] assetTypes) {
		this.assetTypes = assetTypes;
	}

	@JsonIgnore
	public void setAssetTypes(
		UnsafeSupplier<String[], Exception> assetTypesUnsafeSupplier) {

		try {
			assetTypes = assetTypesUnsafeSupplier.get();
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
	protected String[] assetTypes;

	@Schema
	public Long[] getAssigneeIds() {
		return assigneeIds;
	}

	public void setAssigneeIds(Long[] assigneeIds) {
		this.assigneeIds = assigneeIds;
	}

	@JsonIgnore
	public void setAssigneeIds(
		UnsafeSupplier<Long[], Exception> assigneeIdsUnsafeSupplier) {

		try {
			assigneeIds = assigneeIdsUnsafeSupplier.get();
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
	protected Long[] assigneeIds;

	@Schema
	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	@JsonIgnore
	public void setCompleted(
		UnsafeSupplier<Boolean, Exception> completedUnsafeSupplier) {

		try {
			completed = completedUnsafeSupplier.get();
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
	protected Boolean completed;

	@Schema
	public Date getDateDueEnd() {
		return dateDueEnd;
	}

	public void setDateDueEnd(Date dateDueEnd) {
		this.dateDueEnd = dateDueEnd;
	}

	@JsonIgnore
	public void setDateDueEnd(
		UnsafeSupplier<Date, Exception> dateDueEndUnsafeSupplier) {

		try {
			dateDueEnd = dateDueEndUnsafeSupplier.get();
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
	protected Date dateDueEnd;

	@Schema
	public Date getDateDueStart() {
		return dateDueStart;
	}

	public void setDateDueStart(Date dateDueStart) {
		this.dateDueStart = dateDueStart;
	}

	@JsonIgnore
	public void setDateDueStart(
		UnsafeSupplier<Date, Exception> dateDueStartUnsafeSupplier) {

		try {
			dateDueStart = dateDueStartUnsafeSupplier.get();
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
	protected Date dateDueStart;

	@Schema
	public Boolean getSearchByRoles() {
		return searchByRoles;
	}

	public void setSearchByRoles(Boolean searchByRoles) {
		this.searchByRoles = searchByRoles;
	}

	@JsonIgnore
	public void setSearchByRoles(
		UnsafeSupplier<Boolean, Exception> searchByRolesUnsafeSupplier) {

		try {
			searchByRoles = searchByRolesUnsafeSupplier.get();
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
	protected Boolean searchByRoles;

	@Schema
	public Boolean getSearchByUserRoles() {
		return searchByUserRoles;
	}

	public void setSearchByUserRoles(Boolean searchByUserRoles) {
		this.searchByUserRoles = searchByUserRoles;
	}

	@JsonIgnore
	public void setSearchByUserRoles(
		UnsafeSupplier<Boolean, Exception> searchByUserRolesUnsafeSupplier) {

		try {
			searchByUserRoles = searchByUserRolesUnsafeSupplier.get();
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
	protected Boolean searchByUserRoles;

	@Schema
	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}

	@JsonIgnore
	public void setWorkflowDefinitionId(
		UnsafeSupplier<Long, Exception> workflowDefinitionIdUnsafeSupplier) {

		try {
			workflowDefinitionId = workflowDefinitionIdUnsafeSupplier.get();
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
	protected Long workflowDefinitionId;

	@Schema
	public Long[] getWorkflowInstanceIds() {
		return workflowInstanceIds;
	}

	public void setWorkflowInstanceIds(Long[] workflowInstanceIds) {
		this.workflowInstanceIds = workflowInstanceIds;
	}

	@JsonIgnore
	public void setWorkflowInstanceIds(
		UnsafeSupplier<Long[], Exception> workflowInstanceIdsUnsafeSupplier) {

		try {
			workflowInstanceIds = workflowInstanceIdsUnsafeSupplier.get();
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
	protected Long[] workflowInstanceIds;

	@Schema
	public String[] getWorkflowTaskNames() {
		return workflowTaskNames;
	}

	public void setWorkflowTaskNames(String[] workflowTaskNames) {
		this.workflowTaskNames = workflowTaskNames;
	}

	@JsonIgnore
	public void setWorkflowTaskNames(
		UnsafeSupplier<String[], Exception> workflowTaskNamesUnsafeSupplier) {

		try {
			workflowTaskNames = workflowTaskNamesUnsafeSupplier.get();
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
	protected String[] workflowTaskNames;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTasksBulkSelection)) {
			return false;
		}

		WorkflowTasksBulkSelection workflowTasksBulkSelection =
			(WorkflowTasksBulkSelection)object;

		return Objects.equals(
			toString(), workflowTasksBulkSelection.toString());
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

		if (andOperator != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"andOperator\": ");

			sb.append(andOperator);
		}

		if (assetPrimaryKeys != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetPrimaryKeys\": ");

			sb.append("[");

			for (int i = 0; i < assetPrimaryKeys.length; i++) {
				sb.append(assetPrimaryKeys[i]);

				if ((i + 1) < assetPrimaryKeys.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assetTitle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetTitle\": ");

			sb.append("\"");

			sb.append(_escape(assetTitle));

			sb.append("\"");
		}

		if (assetTypes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetTypes\": ");

			sb.append("[");

			for (int i = 0; i < assetTypes.length; i++) {
				sb.append("\"");

				sb.append(_escape(assetTypes[i]));

				sb.append("\"");

				if ((i + 1) < assetTypes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assigneeIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assigneeIds\": ");

			sb.append("[");

			for (int i = 0; i < assigneeIds.length; i++) {
				sb.append(assigneeIds[i]);

				if ((i + 1) < assigneeIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (completed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(completed);
		}

		if (dateDueEnd != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateDueEnd\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateDueEnd));

			sb.append("\"");
		}

		if (dateDueStart != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateDueStart\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateDueStart));

			sb.append("\"");
		}

		if (searchByRoles != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchByRoles\": ");

			sb.append(searchByRoles);
		}

		if (searchByUserRoles != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchByUserRoles\": ");

			sb.append(searchByUserRoles);
		}

		if (workflowDefinitionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowDefinitionId\": ");

			sb.append(workflowDefinitionId);
		}

		if (workflowInstanceIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowInstanceIds\": ");

			sb.append("[");

			for (int i = 0; i < workflowInstanceIds.length; i++) {
				sb.append(workflowInstanceIds[i]);

				if ((i + 1) < workflowInstanceIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (workflowTaskNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskNames\": ");

			sb.append("[");

			for (int i = 0; i < workflowTaskNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(workflowTaskNames[i]));

				sb.append("\"");

				if ((i + 1) < workflowTaskNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTasksBulkSelection",
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