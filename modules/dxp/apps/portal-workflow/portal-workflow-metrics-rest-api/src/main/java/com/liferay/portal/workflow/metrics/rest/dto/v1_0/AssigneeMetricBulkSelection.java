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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("AssigneeMetricBulkSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssigneeMetricBulkSelection")
public class AssigneeMetricBulkSelection {

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
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@JsonIgnore
	public void setDateEnd(
		UnsafeSupplier<Date, Exception> dateEndUnsafeSupplier) {

		try {
			dateEnd = dateEndUnsafeSupplier.get();
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
	protected Date dateEnd;

	@Schema
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@JsonIgnore
	public void setDateStart(
		UnsafeSupplier<Date, Exception> dateStartUnsafeSupplier) {

		try {
			dateStart = dateStartUnsafeSupplier.get();
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
	protected Date dateStart;

	@Schema
	public Long[] getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(Long[] instanceIds) {
		this.instanceIds = instanceIds;
	}

	@JsonIgnore
	public void setInstanceIds(
		UnsafeSupplier<Long[], Exception> instanceIdsUnsafeSupplier) {

		try {
			instanceIds = instanceIdsUnsafeSupplier.get();
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
	protected Long[] instanceIds;

	@Schema
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@JsonIgnore
	public void setKeywords(
		UnsafeSupplier<String, Exception> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
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
	protected String keywords;

	@Schema
	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	@JsonIgnore
	public void setRoleIds(
		UnsafeSupplier<Long[], Exception> roleIdsUnsafeSupplier) {

		try {
			roleIds = roleIdsUnsafeSupplier.get();
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
	protected Long[] roleIds;

	@Schema
	public String[] getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String[] taskNames) {
		this.taskNames = taskNames;
	}

	@JsonIgnore
	public void setTaskNames(
		UnsafeSupplier<String[], Exception> taskNamesUnsafeSupplier) {

		try {
			taskNames = taskNamesUnsafeSupplier.get();
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
	protected String[] taskNames;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssigneeMetricBulkSelection)) {
			return false;
		}

		AssigneeMetricBulkSelection assigneeMetricBulkSelection =
			(AssigneeMetricBulkSelection)object;

		return Objects.equals(
			toString(), assigneeMetricBulkSelection.toString());
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

		if (completed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(completed);
		}

		if (dateEnd != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateEnd\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateEnd));

			sb.append("\"");
		}

		if (dateStart != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateStart\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateStart));

			sb.append("\"");
		}

		if (instanceIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceIds\": ");

			sb.append("[");

			for (int i = 0; i < instanceIds.length; i++) {
				sb.append(instanceIds[i]);

				if ((i + 1) < instanceIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (keywords != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("\"");

			sb.append(_escape(keywords));

			sb.append("\"");
		}

		if (roleIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleIds\": ");

			sb.append("[");

			for (int i = 0; i < roleIds.length; i++) {
				sb.append(roleIds[i]);

				if ((i + 1) < roleIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taskNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskNames\": ");

			sb.append("[");

			for (int i = 0; i < taskNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(taskNames[i]));

				sb.append("\"");

				if ((i + 1) < taskNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetricBulkSelection",
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