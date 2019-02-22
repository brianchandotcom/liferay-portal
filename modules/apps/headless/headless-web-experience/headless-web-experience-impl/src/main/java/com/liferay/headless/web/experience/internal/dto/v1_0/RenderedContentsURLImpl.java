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

import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsURL;
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
@GraphQLName("RenderedContentsURL")
@XmlRootElement(name = "RenderedContentsURL")
public class RenderedContentsURLImpl implements RenderedContentsURL {

	public String getRenderedContentURL() {
			return renderedContentURL;
	}

	public void setRenderedContentURL(
			String renderedContentURL) {

			this.renderedContentURL = (String)renderedContentURL;
	}

	@JsonIgnore
	public void setRenderedContentURL(
			UnsafeSupplier<String, Throwable>
				renderedContentURLUnsafeSupplier) {

			try {
				renderedContentURL =
					(String)renderedContentURLUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String renderedContentURL;
	public String getTemplateName() {
			return templateName;
	}

	public void setTemplateName(
			String templateName) {

			this.templateName = (String)templateName;
	}

	@JsonIgnore
	public void setTemplateName(
			UnsafeSupplier<String, Throwable>
				templateNameUnsafeSupplier) {

			try {
				templateName =
					(String)templateNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String templateName;

}