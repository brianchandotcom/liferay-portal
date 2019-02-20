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

package com.liferay.headless.web.experience.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsByTemplate;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("RenderedContentsByTemplate")
@XmlRootElement(name = "RenderedContentsByTemplate")
public class RenderedContentsByTemplateImpl implements RenderedContentsByTemplate {

	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getRenderedContent() {
			return renderedContent;
	}

	public void setRenderedContent(
			String renderedContent) {

			this.renderedContent = renderedContent;
	}

	@JsonIgnore
	public void setRenderedContent(
			UnsafeSupplier<String, Throwable>
				renderedContentUnsafeSupplier) {

			try {
				renderedContent =
					renderedContentUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String renderedContent;
	public String getTemplate() {
			return template;
	}

	public void setTemplate(
			String template) {

			this.template = template;
	}

	@JsonIgnore
	public void setTemplate(
			UnsafeSupplier<String, Throwable>
				templateUnsafeSupplier) {

			try {
				template =
					templateUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String template;

}