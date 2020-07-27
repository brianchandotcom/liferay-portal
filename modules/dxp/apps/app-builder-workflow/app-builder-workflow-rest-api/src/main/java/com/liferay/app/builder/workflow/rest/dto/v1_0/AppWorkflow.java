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

package com.liferay.app.builder.workflow.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

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
@GraphQLName("AppWorkflow")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AppWorkflow")
public class AppWorkflow {

	public static AppWorkflow toDTO(String json) {
		return ObjectMapperUtil.readValue(AppWorkflow.class, json);
	}

	@Schema
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@JsonIgnore
	public void setAppId(UnsafeSupplier<Long, Exception> appIdUnsafeSupplier) {
		try {
			appId = appIdUnsafeSupplier.get();
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
	protected Long appId;

	@Schema
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@JsonIgnore
	public void setAppVersion(
		UnsafeSupplier<String, Exception> appVersionUnsafeSupplier) {

		try {
			appVersion = appVersionUnsafeSupplier.get();
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
	protected String appVersion;

	@Schema
	public Long getAppWorkflowDefinitionId() {
		return appWorkflowDefinitionId;
	}

	public void setAppWorkflowDefinitionId(Long appWorkflowDefinitionId) {
		this.appWorkflowDefinitionId = appWorkflowDefinitionId;
	}

	@JsonIgnore
	public void setAppWorkflowDefinitionId(
		UnsafeSupplier<Long, Exception> appWorkflowDefinitionIdUnsafeSupplier) {

		try {
			appWorkflowDefinitionId =
				appWorkflowDefinitionIdUnsafeSupplier.get();
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
	protected Long appWorkflowDefinitionId;

	@Schema
	@Valid
	public AppWorkflowState[] getAppWorkflowStates() {
		return appWorkflowStates;
	}

	public void setAppWorkflowStates(AppWorkflowState[] appWorkflowStates) {
		this.appWorkflowStates = appWorkflowStates;
	}

	@JsonIgnore
	public void setAppWorkflowStates(
		UnsafeSupplier<AppWorkflowState[], Exception>
			appWorkflowStatesUnsafeSupplier) {

		try {
			appWorkflowStates = appWorkflowStatesUnsafeSupplier.get();
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
	protected AppWorkflowState[] appWorkflowStates;

	@Schema
	@Valid
	public AppWorkflowTask[] getAppWorkflowTasks() {
		return appWorkflowTasks;
	}

	public void setAppWorkflowTasks(AppWorkflowTask[] appWorkflowTasks) {
		this.appWorkflowTasks = appWorkflowTasks;
	}

	@JsonIgnore
	public void setAppWorkflowTasks(
		UnsafeSupplier<AppWorkflowTask[], Exception>
			appWorkflowTasksUnsafeSupplier) {

		try {
			appWorkflowTasks = appWorkflowTasksUnsafeSupplier.get();
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
	protected AppWorkflowTask[] appWorkflowTasks;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflow)) {
			return false;
		}

		AppWorkflow appWorkflow = (AppWorkflow)object;

		return Objects.equals(toString(), appWorkflow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (appId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appId\": ");

			sb.append(appId);
		}

		if (appVersion != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appVersion\": ");

			sb.append("\"");

			sb.append(_escape(appVersion));

			sb.append("\"");
		}

		if (appWorkflowDefinitionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDefinitionId\": ");

			sb.append(appWorkflowDefinitionId);
		}

		if (appWorkflowStates != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowStates\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowStates.length; i++) {
				sb.append(String.valueOf(appWorkflowStates[i]));

				if ((i + 1) < appWorkflowStates.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTasks != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTasks\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowTasks.length; i++) {
				sb.append(String.valueOf(appWorkflowTasks[i]));

				if ((i + 1) < appWorkflowTasks.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow",
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