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
@GraphQLName("AppWorkflowTask")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AppWorkflowTask")
public class AppWorkflowTask {

	public static AppWorkflowTask toDTO(String json) {
		return ObjectMapperUtil.readValue(AppWorkflowTask.class, json);
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
	@Valid
	public AppWorkflowAction[] getAppWorkflowActions() {
		return appWorkflowActions;
	}

	public void setAppWorkflowActions(AppWorkflowAction[] appWorkflowActions) {
		this.appWorkflowActions = appWorkflowActions;
	}

	@JsonIgnore
	public void setAppWorkflowActions(
		UnsafeSupplier<AppWorkflowAction[], Exception>
			appWorkflowActionsUnsafeSupplier) {

		try {
			appWorkflowActions = appWorkflowActionsUnsafeSupplier.get();
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
	protected AppWorkflowAction[] appWorkflowActions;

	@Schema
	public Long[] getDataLayoutIds() {
		return dataLayoutIds;
	}

	public void setDataLayoutIds(Long[] dataLayoutIds) {
		this.dataLayoutIds = dataLayoutIds;
	}

	@JsonIgnore
	public void setDataLayoutIds(
		UnsafeSupplier<Long[], Exception> dataLayoutIdsUnsafeSupplier) {

		try {
			dataLayoutIds = dataLayoutIdsUnsafeSupplier.get();
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
	protected Long[] dataLayoutIds;

	@Schema
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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTask)) {
			return false;
		}

		AppWorkflowTask appWorkflowTask = (AppWorkflowTask)object;

		return Objects.equals(toString(), appWorkflowTask.toString());
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

		if (appWorkflowActions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowActions\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowActions.length; i++) {
				sb.append(String.valueOf(appWorkflowActions[i]));

				if ((i + 1) < appWorkflowActions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataLayoutIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutIds\": ");

			sb.append("[");

			for (int i = 0; i < dataLayoutIds.length; i++) {
				sb.append(dataLayoutIds[i]);

				if ((i + 1) < dataLayoutIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask",
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

			Object value = entry.getValue();

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
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