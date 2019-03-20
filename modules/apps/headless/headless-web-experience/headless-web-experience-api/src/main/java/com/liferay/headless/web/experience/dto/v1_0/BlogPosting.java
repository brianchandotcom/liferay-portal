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
@Generated("")
@GraphQLName("BlogPosting")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "BlogPosting")
public class BlogPosting extends ContentListElement {

	public BlogPosting getBlogPosting() {
		return blogPosting;
	}

	public void setBlogPosting(BlogPosting blogPosting) {
		this.blogPosting = blogPosting;
	}

	@JsonIgnore
	public void setBlogPosting(
		UnsafeSupplier<BlogPosting, Exception> blogPostingUnsafeSupplier) {

		try {
			blogPosting = blogPostingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BlogPosting blogPosting;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"blogPosting\": ");

		sb.append(blogPosting);

		sb.append("}");

		return sb.toString();
	}

}