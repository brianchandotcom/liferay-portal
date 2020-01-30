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
@GraphQLName("FragmentDefinition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentDefinition")
public class FragmentDefinition {

	@Schema
	public String getFragmentCollectionName() {
		return fragmentCollectionName;
	}

	public void setFragmentCollectionName(String fragmentCollectionName) {
		this.fragmentCollectionName = fragmentCollectionName;
	}

	@JsonIgnore
	public void setFragmentCollectionName(
		UnsafeSupplier<String, Exception>
			fragmentCollectionNameUnsafeSupplier) {

		try {
			fragmentCollectionName = fragmentCollectionNameUnsafeSupplier.get();
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
	protected String fragmentCollectionName;

	@Schema
	@Valid
	public Map<String, Object> getFragmentConfig() {
		return fragmentConfig;
	}

	public void setFragmentConfig(Map<String, Object> fragmentConfig) {
		this.fragmentConfig = fragmentConfig;
	}

	@JsonIgnore
	public void setFragmentConfig(
		UnsafeSupplier<Map<String, Object>, Exception>
			fragmentConfigUnsafeSupplier) {

		try {
			fragmentConfig = fragmentConfigUnsafeSupplier.get();
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
	protected Map<String, Object> fragmentConfig;

	@Schema
	@Valid
	public FragmentContentField[] getFragmentContentFields() {
		return fragmentContentFields;
	}

	public void setFragmentContentFields(
		FragmentContentField[] fragmentContentFields) {

		this.fragmentContentFields = fragmentContentFields;
	}

	@JsonIgnore
	public void setFragmentContentFields(
		UnsafeSupplier<FragmentContentField[], Exception>
			fragmentContentFieldsUnsafeSupplier) {

		try {
			fragmentContentFields = fragmentContentFieldsUnsafeSupplier.get();
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
	protected FragmentContentField[] fragmentContentFields;

	@Schema
	public String getFragmentName() {
		return fragmentName;
	}

	public void setFragmentName(String fragmentName) {
		this.fragmentName = fragmentName;
	}

	@JsonIgnore
	public void setFragmentName(
		UnsafeSupplier<String, Exception> fragmentNameUnsafeSupplier) {

		try {
			fragmentName = fragmentNameUnsafeSupplier.get();
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
	protected String fragmentName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentDefinition)) {
			return false;
		}

		FragmentDefinition fragmentDefinition = (FragmentDefinition)object;

		return Objects.equals(toString(), fragmentDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (fragmentCollectionName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentCollectionName\": ");

			sb.append("\"");

			sb.append(_escape(fragmentCollectionName));

			sb.append("\"");
		}

		if (fragmentConfig != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentConfig\": ");

			sb.append(_toJSON(fragmentConfig));
		}

		if (fragmentContentFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentContentFields\": ");

			sb.append("[");

			for (int i = 0; i < fragmentContentFields.length; i++) {
				sb.append(String.valueOf(fragmentContentFields[i]));

				if ((i + 1) < fragmentContentFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (fragmentName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentName\": ");

			sb.append("\"");

			sb.append(_escape(fragmentName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentDefinition",
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