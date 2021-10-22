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
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
@GraphQLName(
	description = "The fragment's viewport style.",
	value = "FragmentViewportStyle"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentViewportStyle")
public class FragmentViewportStyle implements Serializable {

	public static FragmentViewportStyle toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentViewportStyle.class, json);
	}

	public static FragmentViewportStyle unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			FragmentViewportStyle.class, json);
	}

	@Schema(
		description = "Specifies if the fragment's viewport is hidden to the user."
	)
	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@JsonIgnore
	public void setHidden(
		UnsafeSupplier<Boolean, Exception> hiddenUnsafeSupplier) {

		try {
			hidden = hiddenUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Specifies if the fragment's viewport is hidden to the user."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean hidden;

	@Schema(description = "The fragment viewport's margin bottom.")
	public String getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	@JsonIgnore
	public void setMarginBottom(
		UnsafeSupplier<String, Exception> marginBottomUnsafeSupplier) {

		try {
			marginBottom = marginBottomUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's margin bottom.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginBottom;

	@Schema(description = "The fragment viewport's margin left.")
	public String getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	@JsonIgnore
	public void setMarginLeft(
		UnsafeSupplier<String, Exception> marginLeftUnsafeSupplier) {

		try {
			marginLeft = marginLeftUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's margin left.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginLeft;

	@Schema(description = "The fragment viewport's margin right.")
	public String getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	@JsonIgnore
	public void setMarginRight(
		UnsafeSupplier<String, Exception> marginRightUnsafeSupplier) {

		try {
			marginRight = marginRightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's margin right.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginRight;

	@Schema(description = "The fragment viewport's margin top.")
	public String getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	@JsonIgnore
	public void setMarginTop(
		UnsafeSupplier<String, Exception> marginTopUnsafeSupplier) {

		try {
			marginTop = marginTopUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's margin top.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginTop;

	@Schema(description = "The fragment viewport's padding bottom.")
	public String getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	@JsonIgnore
	public void setPaddingBottom(
		UnsafeSupplier<String, Exception> paddingBottomUnsafeSupplier) {

		try {
			paddingBottom = paddingBottomUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's padding bottom.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingBottom;

	@Schema(description = "The fragment viewport's padding left.")
	public String getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	@JsonIgnore
	public void setPaddingLeft(
		UnsafeSupplier<String, Exception> paddingLeftUnsafeSupplier) {

		try {
			paddingLeft = paddingLeftUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's padding left.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingLeft;

	@Schema(description = "The fragment viewport's padding right.")
	public String getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;
	}

	@JsonIgnore
	public void setPaddingRight(
		UnsafeSupplier<String, Exception> paddingRightUnsafeSupplier) {

		try {
			paddingRight = paddingRightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's padding right.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingRight;

	@Schema(description = "The fragment viewport's padding top.")
	public String getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;
	}

	@JsonIgnore
	public void setPaddingTop(
		UnsafeSupplier<String, Exception> paddingTopUnsafeSupplier) {

		try {
			paddingTop = paddingTopUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment viewport's padding top.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingTop;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentViewportStyle)) {
			return false;
		}

		FragmentViewportStyle fragmentViewportStyle =
			(FragmentViewportStyle)object;

		return Objects.equals(toString(), fragmentViewportStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (hidden != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hidden\": ");

			sb.append(hidden);
		}

		if (marginBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginBottom\": ");

			sb.append("\"");

			sb.append(_escape(marginBottom));

			sb.append("\"");
		}

		if (marginLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginLeft\": ");

			sb.append("\"");

			sb.append(_escape(marginLeft));

			sb.append("\"");
		}

		if (marginRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginRight\": ");

			sb.append("\"");

			sb.append(_escape(marginRight));

			sb.append("\"");
		}

		if (marginTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginTop\": ");

			sb.append("\"");

			sb.append(_escape(marginTop));

			sb.append("\"");
		}

		if (paddingBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append("\"");

			sb.append(_escape(paddingBottom));

			sb.append("\"");
		}

		if (paddingLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingLeft\": ");

			sb.append("\"");

			sb.append(_escape(paddingLeft));

			sb.append("\"");
		}

		if (paddingRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingRight\": ");

			sb.append("\"");

			sb.append(_escape(paddingRight));

			sb.append("\"");
		}

		if (paddingTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append("\"");

			sb.append(_escape(paddingTop));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentViewportStyle",
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
				sb.append(value);
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

}