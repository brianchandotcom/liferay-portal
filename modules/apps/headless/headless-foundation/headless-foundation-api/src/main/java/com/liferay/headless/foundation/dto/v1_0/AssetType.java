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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonValue;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("AssetType")
@XmlRootElement(name = "AssetType")
public class AssetType {

	public Boolean getRequired() {
		return required;
	}

	public String getSubtype() {
		return subtype;
	}

	public Type getType() {
		return type;
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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("required=");

		sb.append(required);
		sb.append(", type=");

		sb.append(type);
		sb.append(", subtype=");

		sb.append(subtype);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Boolean required;

	@GraphQLField
	@JsonProperty
	protected String subtype;

	@GraphQLField
	@JsonProperty
	protected Type type;


	public static enum Type {
		ALL_ASSET_TYPES("AllAssetTypes"),
		BLOG_POSTING("BlogPosting"),
		DOCUMENT("Document"),
		KNOWLEDGE_BASE_ARTICLE("KnowledgeBaseArticle"),
		ORGANIZATION("Organization"),
		STRUCTURED_CONTENT("StructuredContent"),
		USER_ACCOUNT("UserAccount"),
		WEB_PAGE("WebPage"),
		WEB_SITE("WebSite"),
		WIKI_PAGE("WikiPage");

		private String _value;

		private Type(String value) {
			_value = value;
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@JsonCreator
		public static Type create(String value) {
			Type[] types = Type.values();

			for (Type type : types) {
				String typeValue = type.getValue();

				if (typeValue.equalsIgnoreCase(value)) {
					return type;
				}
			}

			return ALL_ASSET_TYPES;
		}
	}

}