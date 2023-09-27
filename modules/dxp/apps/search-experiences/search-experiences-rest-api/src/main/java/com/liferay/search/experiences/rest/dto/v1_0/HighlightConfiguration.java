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
@GraphQLName("HighlightConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "HighlightConfiguration")
public class HighlightConfiguration implements Serializable {

	public static HighlightConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(HighlightConfiguration.class, json);
	}

	public static HighlightConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			HighlightConfiguration.class, json);
	}

	@Schema
	@Valid
	public Map<String, HighlightField> getFields() {
		return fields;
	}

	public void setFields(Map<String, HighlightField> fields) {
		this.fields = fields;
	}

	@JsonIgnore
	public void setFields(
		UnsafeSupplier<Map<String, HighlightField>, Exception>
			fieldsUnsafeSupplier) {

		try {
			fields = fieldsUnsafeSupplier.get();
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
	protected Map<String, HighlightField> fields;

	@Schema
	public Integer getFragment_size() {
		return fragment_size;
	}

	public void setFragment_size(Integer fragment_size) {
		this.fragment_size = fragment_size;
	}

	@JsonIgnore
	public void setFragment_size(
		UnsafeSupplier<Integer, Exception> fragment_sizeUnsafeSupplier) {

		try {
			fragment_size = fragment_sizeUnsafeSupplier.get();
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
	protected Integer fragment_size;

	@Schema
	public Integer getNumber_of_fragments() {
		return number_of_fragments;
	}

	public void setNumber_of_fragments(Integer number_of_fragments) {
		this.number_of_fragments = number_of_fragments;
	}

	@JsonIgnore
	public void setNumber_of_fragments(
		UnsafeSupplier<Integer, Exception> number_of_fragmentsUnsafeSupplier) {

		try {
			number_of_fragments = number_of_fragmentsUnsafeSupplier.get();
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
	protected Integer number_of_fragments;

	@Schema
	public String[] getPost_tags() {
		return post_tags;
	}

	public void setPost_tags(String[] post_tags) {
		this.post_tags = post_tags;
	}

	@JsonIgnore
	public void setPost_tags(
		UnsafeSupplier<String[], Exception> post_tagsUnsafeSupplier) {

		try {
			post_tags = post_tagsUnsafeSupplier.get();
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
	protected String[] post_tags;

	@Schema
	public String[] getPre_tags() {
		return pre_tags;
	}

	public void setPre_tags(String[] pre_tags) {
		this.pre_tags = pre_tags;
	}

	@JsonIgnore
	public void setPre_tags(
		UnsafeSupplier<String[], Exception> pre_tagsUnsafeSupplier) {

		try {
			pre_tags = pre_tagsUnsafeSupplier.get();
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
	protected String[] pre_tags;

	@Schema
	public Boolean getRequire_field_match() {
		return require_field_match;
	}

	public void setRequire_field_match(Boolean require_field_match) {
		this.require_field_match = require_field_match;
	}

	@JsonIgnore
	public void setRequire_field_match(
		UnsafeSupplier<Boolean, Exception> require_field_matchUnsafeSupplier) {

		try {
			require_field_match = require_field_matchUnsafeSupplier.get();
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
	protected Boolean require_field_match;

	@Schema
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String type;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HighlightConfiguration)) {
			return false;
		}

		HighlightConfiguration highlightConfiguration =
			(HighlightConfiguration)object;

		return Objects.equals(toString(), highlightConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (fields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fields\": ");

			sb.append(_toJSON(fields));
		}

		if (fragment_size != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragment_size\": ");

			sb.append(fragment_size);
		}

		if (number_of_fragments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"number_of_fragments\": ");

			sb.append(number_of_fragments);
		}

		if (post_tags != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"post_tags\": ");

			sb.append("[");

			for (int i = 0; i < post_tags.length; i++) {
				sb.append("\"");

				sb.append(_escape(post_tags[i]));

				sb.append("\"");

				if ((i + 1) < post_tags.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pre_tags != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pre_tags\": ");

			sb.append("[");

			for (int i = 0; i < pre_tags.length; i++) {
				sb.append("\"");

				sb.append(_escape(pre_tags[i]));

				sb.append("\"");

				if ((i + 1) < pre_tags.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (require_field_match != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"require_field_match\": ");

			sb.append(require_field_match);
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
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.HighlightConfiguration",
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