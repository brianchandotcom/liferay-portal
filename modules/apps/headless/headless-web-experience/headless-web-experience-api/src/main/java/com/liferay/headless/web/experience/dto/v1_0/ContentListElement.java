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

package com.liferay.headless.web.experience.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@JsonSubTypes(
	{
		@JsonSubTypes.Type(name = "blogPosting", value = BlogPosting.class),
		@JsonSubTypes.Type(name = "document", value = Document.class),
		@JsonSubTypes.Type(name = "folder", value = Folder.class),
		@JsonSubTypes.Type(
			name = "genericContentListElement",
			value = GenericContentListElement.class
		),
		@JsonSubTypes.Type(
			name = "knowledgeBaseArticle", value = KnowledgeBaseArticle.class
		),
		@JsonSubTypes.Type(
			name = "structuredContent", value = StructuredContent.class
		)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.PROPERTY, property = "childType",
	use = JsonTypeInfo.Id.NAME
)
@Generated("")
@GraphQLName("ContentListElement")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ContentListElement")
public class ContentListElement {

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@JsonIgnore
	public void setContentType(
		UnsafeSupplier<String, Exception> contentTypeUnsafeSupplier) {

		try {
			contentType = contentTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String contentType;

	public Number getOrder() {
		return order;
	}

	public void setOrder(Number order) {
		this.order = order;
	}

	@JsonIgnore
	public void setOrder(
		UnsafeSupplier<Number, Exception> orderUnsafeSupplier) {

		try {
			order = orderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Number order;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"contentType\": ");

		sb.append("\"");
		sb.append(contentType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"order\": ");

		sb.append(order);

		sb.append("}");

		return sb.toString();
	}

}