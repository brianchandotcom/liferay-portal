/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents a fragment image.", value = "FragmentImage"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentImage")
public class FragmentImage implements Serializable {

	public static FragmentImage toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentImage.class, json);
	}

	public static FragmentImage unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(FragmentImage.class, json);
	}

	@Schema(description = "The fragment image's description.")
	@Valid
	public Object getDescription() {
		return description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<Object, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment image's description.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object description;

	@Schema(description = "A reference to a fragment image class primary key.")
	@Valid
	public FragmentImageClassPKReference getFragmentImageClassPKReference() {
		return fragmentImageClassPKReference;
	}

	public void setFragmentImageClassPKReference(
		FragmentImageClassPKReference fragmentImageClassPKReference) {

		this.fragmentImageClassPKReference = fragmentImageClassPKReference;
	}

	@JsonIgnore
	public void setFragmentImageClassPKReference(
		UnsafeSupplier<FragmentImageClassPKReference, Exception>
			fragmentImageClassPKReferenceUnsafeSupplier) {

		try {
			fragmentImageClassPKReference =
				fragmentImageClassPKReferenceUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A reference to a fragment image class primary key."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentImageClassPKReference fragmentImageClassPKReference;

	@Schema(description = "The fragment image's title.")
	@Valid
	public Object getTitle() {
		return title;
	}

	public void setTitle(Object title) {
		this.title = title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<Object, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment image's title.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object title;

	@Schema(
		description = "The fragment image's url. Can be inline or mapped to an external value."
	)
	@Valid
	public Object getUrl() {
		return url;
	}

	public void setUrl(Object url) {
		this.url = url;
	}

	@JsonIgnore
	public void setUrl(UnsafeSupplier<Object, Exception> urlUnsafeSupplier) {
		try {
			url = urlUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The fragment image's url. Can be inline or mapped to an external value."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object url;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentImage)) {
			return false;
		}

		FragmentImage fragmentImage = (FragmentImage)object;

		return Objects.equals(toString(), fragmentImage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			if (description instanceof Map) {
				sb.append(
					JSONFactoryUtil.createJSONObject((Map<?, ?>)description));
			}
			else if (description instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)description));
				sb.append("\"");
			}
			else {
				sb.append(description);
			}
		}

		if (fragmentImageClassPKReference != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentImageClassPKReference\": ");

			sb.append(String.valueOf(fragmentImageClassPKReference));
		}

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			if (title instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)title));
			}
			else if (title instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)title));
				sb.append("\"");
			}
			else {
				sb.append(title);
			}
		}

		if (url != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			if (url instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)url));
			}
			else if (url instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)url));
				sb.append("\"");
			}
			else {
				sb.append(url);
			}
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentImage",
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