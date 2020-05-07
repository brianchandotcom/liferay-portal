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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

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
@GraphQLName("Mapping")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Mapping")
public class Mapping {

	@Schema
	public String getCollectionItemFieldKey() {
		return collectionItemFieldKey;
	}

	public void setCollectionItemFieldKey(String collectionItemFieldKey) {
		this.collectionItemFieldKey = collectionItemFieldKey;
	}

	@JsonIgnore
	public void setCollectionItemFieldKey(
		UnsafeSupplier<String, Exception>
			collectionItemFieldKeyUnsafeSupplier) {

		try {
			collectionItemFieldKey = collectionItemFieldKeyUnsafeSupplier.get();
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
	protected String collectionItemFieldKey;

	@Schema
	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	@JsonIgnore
	public void setFieldKey(
		UnsafeSupplier<String, Exception> fieldKeyUnsafeSupplier) {

		try {
			fieldKey = fieldKeyUnsafeSupplier.get();
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
	protected String fieldKey;

	@Schema
	public String getItemClassName() {
		return itemClassName;
	}

	public void setItemClassName(String itemClassName) {
		this.itemClassName = itemClassName;
	}

	@JsonIgnore
	public void setItemClassName(
		UnsafeSupplier<String, Exception> itemClassNameUnsafeSupplier) {

		try {
			itemClassName = itemClassNameUnsafeSupplier.get();
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
	protected String itemClassName;

	@Schema
	public Long getItemClassPK() {
		return itemClassPK;
	}

	public void setItemClassPK(Long itemClassPK) {
		this.itemClassPK = itemClassPK;
	}

	@JsonIgnore
	public void setItemClassPK(
		UnsafeSupplier<Long, Exception> itemClassPKUnsafeSupplier) {

		try {
			itemClassPK = itemClassPKUnsafeSupplier.get();
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
	protected Long itemClassPK;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Mapping)) {
			return false;
		}

		Mapping mapping = (Mapping)object;

		return Objects.equals(toString(), mapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (collectionItemFieldKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionItemFieldKey\": ");

			sb.append("\"");

			sb.append(_escape(collectionItemFieldKey));

			sb.append("\"");
		}

		if (fieldKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldKey\": ");

			sb.append("\"");

			sb.append(_escape(fieldKey));

			sb.append("\"");
		}

		if (itemClassName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemClassName\": ");

			sb.append("\"");

			sb.append(_escape(itemClassName));

			sb.append("\"");
		}

		if (itemClassPK != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemClassPK\": ");

			sb.append(itemClassPK);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.Mapping",
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