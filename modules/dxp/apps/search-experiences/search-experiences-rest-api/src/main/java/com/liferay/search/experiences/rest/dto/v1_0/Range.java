/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("Range")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Range")
public class Range implements Serializable {

	public static Range toDTO(String json) {
		return ObjectMapperUtil.readValue(Range.class, json);
	}

	public static Range unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Range.class, json);
	}

	@Schema
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@JsonIgnore
	public void setFormat(
		UnsafeSupplier<String, Exception> formatUnsafeSupplier) {

		try {
			format = formatUnsafeSupplier.get();
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
	protected String format;

	@Schema
	@Valid
	public Object getGt() {
		return gt;
	}

	public void setGt(Object gt) {
		this.gt = gt;
	}

	@JsonIgnore
	public void setGt(UnsafeSupplier<Object, Exception> gtUnsafeSupplier) {
		try {
			gt = gtUnsafeSupplier.get();
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
	protected Object gt;

	@Schema
	@Valid
	public Object getGte() {
		return gte;
	}

	public void setGte(Object gte) {
		this.gte = gte;
	}

	@JsonIgnore
	public void setGte(UnsafeSupplier<Object, Exception> gteUnsafeSupplier) {
		try {
			gte = gteUnsafeSupplier.get();
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
	protected Object gte;

	@Schema
	@Valid
	public Object getLt() {
		return lt;
	}

	public void setLt(Object lt) {
		this.lt = lt;
	}

	@JsonIgnore
	public void setLt(UnsafeSupplier<Object, Exception> ltUnsafeSupplier) {
		try {
			lt = ltUnsafeSupplier.get();
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
	protected Object lt;

	@Schema
	@Valid
	public Object getLte() {
		return lte;
	}

	public void setLte(Object lte) {
		this.lte = lte;
	}

	@JsonIgnore
	public void setLte(UnsafeSupplier<Object, Exception> lteUnsafeSupplier) {
		try {
			lte = lteUnsafeSupplier.get();
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
	protected Object lte;

	@Schema
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@JsonIgnore
	public void setParameterName(
		UnsafeSupplier<String, Exception> parameterNameUnsafeSupplier) {

		try {
			parameterName = parameterNameUnsafeSupplier.get();
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
	protected String parameterName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Range)) {
			return false;
		}

		Range range = (Range)object;

		return Objects.equals(toString(), range.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (format != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"format\": ");

			sb.append("\"");

			sb.append(_escape(format));

			sb.append("\"");
		}

		if (gt != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gt\": ");

			if (gt instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)gt));
			}
			else if (gt instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)gt));
				sb.append("\"");
			}
			else {
				sb.append(gt);
			}
		}

		if (gte != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gte\": ");

			if (gte instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)gte));
			}
			else if (gte instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)gte));
				sb.append("\"");
			}
			else {
				sb.append(gte);
			}
		}

		if (lt != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lt\": ");

			if (lt instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)lt));
			}
			else if (lt instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)lt));
				sb.append("\"");
			}
			else {
				sb.append(lt);
			}
		}

		if (lte != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lte\": ");

			if (lte instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)lte));
			}
			else if (lte instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)lte));
				sb.append("\"");
			}
			else {
				sb.append(lte);
			}
		}

		if (parameterName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterName\": ");

			sb.append("\"");

			sb.append(_escape(parameterName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.Range",
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