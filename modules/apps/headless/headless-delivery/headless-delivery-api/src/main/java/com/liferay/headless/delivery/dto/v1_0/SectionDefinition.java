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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("SectionDefinition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SectionDefinition")
public class SectionDefinition {

	@Schema
	public String getBackgroundColorCssClass() {
		return backgroundColorCssClass;
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		this.backgroundColorCssClass = backgroundColorCssClass;
	}

	@JsonIgnore
	public void setBackgroundColorCssClass(
		UnsafeSupplier<String, Exception>
			backgroundColorCssClassUnsafeSupplier) {

		try {
			backgroundColorCssClass =
				backgroundColorCssClassUnsafeSupplier.get();
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
	protected String backgroundColorCssClass;

	@Schema
	@Valid
	public FragmentImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(FragmentImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@JsonIgnore
	public void setBackgroundImage(
		UnsafeSupplier<FragmentImage, Exception>
			backgroundImageUnsafeSupplier) {

		try {
			backgroundImage = backgroundImageUnsafeSupplier.get();
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
	protected FragmentImage backgroundImage;

	@Schema
	@Valid
	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	@JsonIgnore
	public void setLayout(
		UnsafeSupplier<Layout, Exception> layoutUnsafeSupplier) {

		try {
			layout = layoutUnsafeSupplier.get();
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
	protected Layout layout;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SectionDefinition)) {
			return false;
		}

		SectionDefinition sectionDefinition = (SectionDefinition)object;

		return Objects.equals(toString(), sectionDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (backgroundColorCssClass != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundColorCssClass\": ");

			sb.append("\"");

			sb.append(_escape(backgroundColorCssClass));

			sb.append("\"");
		}

		if (backgroundImage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundImage\": ");

			sb.append(String.valueOf(backgroundImage));
		}

		if (layout != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layout\": ");

			sb.append(String.valueOf(layout));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.SectionDefinition",
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}