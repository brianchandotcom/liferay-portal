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
	description = "Represents a reference to a fragment image class primary key.",
	value = "FragmentImageClassPKReference"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentImageClassPKReference")
public class FragmentImageClassPKReference implements Serializable {

	public static FragmentImageClassPKReference toDTO(String json) {
		return ObjectMapperUtil.readValue(
			FragmentImageClassPKReference.class, json);
	}

	public static FragmentImageClassPKReference unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			FragmentImageClassPKReference.class, json);
	}

	@Schema(description = "A map of class primary key references.")
	@Valid
	public Map<String, ClassPKReference> getClassPKReferences() {
		return classPKReferences;
	}

	public void setClassPKReferences(
		Map<String, ClassPKReference> classPKReferences) {

		this.classPKReferences = classPKReferences;
	}

	@JsonIgnore
	public void setClassPKReferences(
		UnsafeSupplier<Map<String, ClassPKReference>, Exception>
			classPKReferencesUnsafeSupplier) {

		try {
			classPKReferences = classPKReferencesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "A map of class primary key references.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, ClassPKReference> classPKReferences;

	@Schema(description = "The fragment image's configuration.")
	@Valid
	public FragmentImageConfiguration getFragmentImageConfiguration() {
		return fragmentImageConfiguration;
	}

	public void setFragmentImageConfiguration(
		FragmentImageConfiguration fragmentImageConfiguration) {

		this.fragmentImageConfiguration = fragmentImageConfiguration;
	}

	@JsonIgnore
	public void setFragmentImageConfiguration(
		UnsafeSupplier<FragmentImageConfiguration, Exception>
			fragmentImageConfigurationUnsafeSupplier) {

		try {
			fragmentImageConfiguration =
				fragmentImageConfigurationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment image's configuration.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentImageConfiguration fragmentImageConfiguration;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentImageClassPKReference)) {
			return false;
		}

		FragmentImageClassPKReference fragmentImageClassPKReference =
			(FragmentImageClassPKReference)object;

		return Objects.equals(
			toString(), fragmentImageClassPKReference.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (classPKReferences != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPKReferences\": ");

			sb.append(_toJSON(classPKReferences));
		}

		if (fragmentImageConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentImageConfiguration\": ");

			sb.append(String.valueOf(fragmentImageConfiguration));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentImageClassPKReference",
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