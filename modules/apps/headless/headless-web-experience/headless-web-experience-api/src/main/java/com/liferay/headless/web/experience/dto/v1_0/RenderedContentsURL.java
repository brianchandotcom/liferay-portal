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
@GraphQLName("RenderedContentsURL")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "RenderedContentsURL")
public class RenderedContentsURL {

	public String getRenderedContentURL() {
		return renderedContentURL;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setRenderedContentURL(String renderedContentURL) {
		this.renderedContentURL = renderedContentURL;
	}

	@JsonIgnore
	public void setRenderedContentURL(
		UnsafeSupplier<String, Exception> renderedContentURLUnsafeSupplier) {

		try {
			renderedContentURL = renderedContentURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@JsonIgnore
	public void setTemplateName(
		UnsafeSupplier<String, Exception> templateNameUnsafeSupplier) {

		try {
			templateName = templateNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{");

		sb.append("\"renderedContentURL\": ");

		sb.append("\"");
		sb.append(renderedContentURL);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"templateName\": ");

		sb.append("\"");
		sb.append(templateName);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String renderedContentURL;

	@GraphQLField
	@JsonProperty
	protected String templateName;

}