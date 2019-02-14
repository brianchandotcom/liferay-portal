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

package com.liferay.headless.form.dto.v1_0;

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
@GraphQLName("SuccessPage")
@XmlRootElement(name = "SuccessPage")
public class SuccessPage {

	public String getDescription() {
		return description;
	}

	public String getHeadline() {
		return headline;
	}

	public Long getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setHeadline(
		UnsafeSupplier<String, Throwable> headlineUnsafeSupplier) {

		try {
			headline = headlineUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String description;

	@GraphQLField
	protected String headline;

	@GraphQLField
	protected Long id;

}