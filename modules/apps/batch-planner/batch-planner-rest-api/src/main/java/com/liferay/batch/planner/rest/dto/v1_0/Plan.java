/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.dto.v1_0;

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
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
@GraphQLName("Plan")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Plan")
public class Plan implements Serializable {

	public static Plan toDTO(String json) {
		return ObjectMapperUtil.readValue(Plan.class, json);
	}

	public static Plan unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Plan.class, json);
	}

	@Schema
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
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
	protected Boolean active;

	@Schema
	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean export) {
		this.export = export;
	}

	@JsonIgnore
	public void setExport(
		UnsafeSupplier<Boolean, Exception> exportUnsafeSupplier) {

		try {
			export = exportUnsafeSupplier.get();
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
	protected Boolean export;

	@Schema
	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}

	@JsonIgnore
	public void setExternalType(
		UnsafeSupplier<String, Exception> externalTypeUnsafeSupplier) {

		try {
			externalType = externalTypeUnsafeSupplier.get();
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
	protected String externalType;

	@Schema
	public String getExternalURL() {
		return externalURL;
	}

	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}

	@JsonIgnore
	public void setExternalURL(
		UnsafeSupplier<String, Exception> externalURLUnsafeSupplier) {

		try {
			externalURL = externalURLUnsafeSupplier.get();
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
	protected String externalURL;

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
	public String getInternalClassName() {
		return internalClassName;
	}

	public void setInternalClassName(String internalClassName) {
		this.internalClassName = internalClassName;
	}

	@JsonIgnore
	public void setInternalClassName(
		UnsafeSupplier<String, Exception> internalClassNameUnsafeSupplier) {

		try {
			internalClassName = internalClassNameUnsafeSupplier.get();
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
	protected String internalClassName;

	@Schema
	public String getInternalClassNameKey() {
		return internalClassNameKey;
	}

	public void setInternalClassNameKey(String internalClassNameKey) {
		this.internalClassNameKey = internalClassNameKey;
	}

	@JsonIgnore
	public void setInternalClassNameKey(
		UnsafeSupplier<String, Exception> internalClassNameKeyUnsafeSupplier) {

		try {
			internalClassNameKey = internalClassNameKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String internalClassNameKey;

	@Schema
	@Valid
	public Mapping[] getMappings() {
		return mappings;
	}

	public void setMappings(Mapping[] mappings) {
		this.mappings = mappings;
	}

	@JsonIgnore
	public void setMappings(
		UnsafeSupplier<Mapping[], Exception> mappingsUnsafeSupplier) {

		try {
			mappings = mappingsUnsafeSupplier.get();
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
	protected Mapping[] mappings;

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

	@Schema
	@Valid
	public Policy[] getPolicies() {
		return policies;
	}

	public void setPolicies(Policy[] policies) {
		this.policies = policies;
	}

	@JsonIgnore
	public void setPolicies(
		UnsafeSupplier<Policy[], Exception> policiesUnsafeSupplier) {

		try {
			policies = policiesUnsafeSupplier.get();
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
	protected Policy[] policies;

	@Schema
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonIgnore
	public void setSize(UnsafeSupplier<Integer, Exception> sizeUnsafeSupplier) {
		try {
			size = sizeUnsafeSupplier.get();
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
	protected Integer size;

	@Schema
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
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
	protected Integer status;

	@Schema
	public String getTaskItemDelegateName() {
		return taskItemDelegateName;
	}

	public void setTaskItemDelegateName(String taskItemDelegateName) {
		this.taskItemDelegateName = taskItemDelegateName;
	}

	@JsonIgnore
	public void setTaskItemDelegateName(
		UnsafeSupplier<String, Exception> taskItemDelegateNameUnsafeSupplier) {

		try {
			taskItemDelegateName = taskItemDelegateNameUnsafeSupplier.get();
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
	protected String taskItemDelegateName;

	@Schema
	public Boolean getTemplate() {
		return template;
	}

	public void setTemplate(Boolean template) {
		this.template = template;
	}

	@JsonIgnore
	public void setTemplate(
		UnsafeSupplier<Boolean, Exception> templateUnsafeSupplier) {

		try {
			template = templateUnsafeSupplier.get();
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
	protected Boolean template;

	@Schema
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@JsonIgnore
	public void setTotal(
		UnsafeSupplier<Integer, Exception> totalUnsafeSupplier) {

		try {
			total = totalUnsafeSupplier.get();
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
	protected Integer total;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Plan)) {
			return false;
		}

		Plan plan = (Plan)object;

		return Objects.equals(toString(), plan.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (export != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"export\": ");

			sb.append(export);
		}

		if (externalType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalType\": ");

			sb.append("\"");

			sb.append(_escape(externalType));

			sb.append("\"");
		}

		if (externalURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalURL\": ");

			sb.append("\"");

			sb.append(_escape(externalURL));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (internalClassName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalClassName\": ");

			sb.append("\"");

			sb.append(_escape(internalClassName));

			sb.append("\"");
		}

		if (internalClassNameKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalClassNameKey\": ");

			sb.append("\"");

			sb.append(_escape(internalClassNameKey));

			sb.append("\"");
		}

		if (mappings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mappings\": ");

			sb.append("[");

			for (int i = 0; i < mappings.length; i++) {
				sb.append(String.valueOf(mappings[i]));

				if ((i + 1) < mappings.length) {
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

		if (policies != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"policies\": ");

			sb.append("[");

			for (int i = 0; i < policies.length; i++) {
				sb.append(String.valueOf(policies[i]));

				if ((i + 1) < policies.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (size != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(size);
		}

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(status);
		}

		if (taskItemDelegateName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskItemDelegateName\": ");

			sb.append("\"");

			sb.append(_escape(taskItemDelegateName));

			sb.append("\"");
		}

		if (template != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"template\": ");

			sb.append(template);
		}

		if (total != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(total);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.batch.planner.rest.dto.v1_0.Plan",
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