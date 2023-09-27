/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.dto.v2_0;

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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataLayoutRenderingContext")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataLayoutRenderingContext")
public class DataLayoutRenderingContext implements Serializable {

	public static DataLayoutRenderingContext toDTO(String json) {
		return ObjectMapperUtil.readValue(
			DataLayoutRenderingContext.class, json);
	}

	public static DataLayoutRenderingContext unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			DataLayoutRenderingContext.class, json);
	}

	@Schema
	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	@JsonIgnore
	public void setContainerId(
		UnsafeSupplier<String, Exception> containerIdUnsafeSupplier) {

		try {
			containerId = containerIdUnsafeSupplier.get();
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
	protected String containerId;

	@Schema
	@Valid
	public Map<String, Object> getDataRecordValues() {
		return dataRecordValues;
	}

	public void setDataRecordValues(Map<String, Object> dataRecordValues) {
		this.dataRecordValues = dataRecordValues;
	}

	@JsonIgnore
	public void setDataRecordValues(
		UnsafeSupplier<Map<String, Object>, Exception>
			dataRecordValuesUnsafeSupplier) {

		try {
			dataRecordValues = dataRecordValuesUnsafeSupplier.get();
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
	protected Map<String, Object> dataRecordValues;

	@Schema
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@JsonIgnore
	public void setNamespace(
		UnsafeSupplier<String, Exception> namespaceUnsafeSupplier) {

		try {
			namespace = namespaceUnsafeSupplier.get();
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
	protected String namespace;

	@Schema
	public String getPathThemeImages() {
		return pathThemeImages;
	}

	public void setPathThemeImages(String pathThemeImages) {
		this.pathThemeImages = pathThemeImages;
	}

	@JsonIgnore
	public void setPathThemeImages(
		UnsafeSupplier<String, Exception> pathThemeImagesUnsafeSupplier) {

		try {
			pathThemeImages = pathThemeImagesUnsafeSupplier.get();
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
	protected String pathThemeImages;

	@Schema
	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	@JsonIgnore
	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
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
	protected Boolean readOnly;

	@Schema
	public Long getScopeGroupId() {
		return scopeGroupId;
	}

	public void setScopeGroupId(Long scopeGroupId) {
		this.scopeGroupId = scopeGroupId;
	}

	@JsonIgnore
	public void setScopeGroupId(
		UnsafeSupplier<Long, Exception> scopeGroupIdUnsafeSupplier) {

		try {
			scopeGroupId = scopeGroupIdUnsafeSupplier.get();
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
	protected Long scopeGroupId;

	@Schema
	public Long getSiteGroupId() {
		return siteGroupId;
	}

	public void setSiteGroupId(Long siteGroupId) {
		this.siteGroupId = siteGroupId;
	}

	@JsonIgnore
	public void setSiteGroupId(
		UnsafeSupplier<Long, Exception> siteGroupIdUnsafeSupplier) {

		try {
			siteGroupId = siteGroupIdUnsafeSupplier.get();
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
	protected Long siteGroupId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataLayoutRenderingContext)) {
			return false;
		}

		DataLayoutRenderingContext dataLayoutRenderingContext =
			(DataLayoutRenderingContext)object;

		return Objects.equals(
			toString(), dataLayoutRenderingContext.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (containerId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"containerId\": ");

			sb.append("\"");

			sb.append(_escape(containerId));

			sb.append("\"");
		}

		if (dataRecordValues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordValues\": ");

			sb.append(_toJSON(dataRecordValues));
		}

		if (namespace != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"namespace\": ");

			sb.append("\"");

			sb.append(_escape(namespace));

			sb.append("\"");
		}

		if (pathThemeImages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pathThemeImages\": ");

			sb.append("\"");

			sb.append(_escape(pathThemeImages));

			sb.append("\"");
		}

		if (readOnly != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(readOnly);
		}

		if (scopeGroupId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scopeGroupId\": ");

			sb.append(scopeGroupId);
		}

		if (siteGroupId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteGroupId\": ");

			sb.append(siteGroupId);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.data.engine.rest.dto.v2_0.DataLayoutRenderingContext",
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