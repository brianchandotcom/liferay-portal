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

package com.liferay.headless.foundation.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("AssetType")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AssetType")
public class AssetType {

	public static enum Type {

		ALL_ASSET_TYPES("AllAssetTypes"), BLOG_POSTING("BlogPosting"),
		DOCUMENT("Document"), KNOWLEDGE_BASE_ARTICLE("KnowledgeBaseArticle"),
		ORGANIZATION("Organization"), STRUCTURED_CONTENT("StructuredContent"),
		USER_ACCOUNT("UserAccount"), WEB_PAGE("WebPage"), WEB_SITE("WebSite"),
		WIKI_PAGE("WikiPage");

		@JsonCreator
		public static Type create(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
				}
			}

			return null;
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
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
	protected Boolean required;

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	@JsonIgnore
	public void setSubtype(
		UnsafeSupplier<String, Exception> subtypeUnsafeSupplier) {

		try {
			subtype = subtypeUnsafeSupplier.get();
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
	protected String subtype;

	public Type getType() {
		return type;
	}

	@JsonIgnore
	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
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
	protected Type type;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetType)) {
			return false;
		}

		AssetType assetType = (AssetType)object;

		return Objects.equals(toString(), assetType.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"required\": ");

		sb.append(required);
		sb.append(", ");

		sb.append("\"subtype\": ");

		sb.append("\"");
		sb.append(subtype);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"type\": ");

		sb.append("\"");
		sb.append(type);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

}