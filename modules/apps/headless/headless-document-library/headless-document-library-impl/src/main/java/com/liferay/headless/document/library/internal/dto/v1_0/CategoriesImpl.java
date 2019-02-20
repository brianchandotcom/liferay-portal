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

package com.liferay.headless.document.library.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.document.library.dto.v1_0.Categories;
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
@GraphQLName("Categories")
@XmlRootElement(name = "Categories")
public class CategoriesImpl implements Categories {

	public Long getCategoryId() {
			return categoryId;
	}

	public void setCategoryId(
			Long categoryId) {

			this.categoryId = categoryId;
	}

	@JsonIgnore
	public void setCategoryId(
			UnsafeSupplier<Long, Throwable>
				categoryIdUnsafeSupplier) {

			try {
				categoryId =
					categoryIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long categoryId;
	public String getCategoryName() {
			return categoryName;
	}

	public void setCategoryName(
			String categoryName) {

			this.categoryName = categoryName;
	}

	@JsonIgnore
	public void setCategoryName(
			UnsafeSupplier<String, Throwable>
				categoryNameUnsafeSupplier) {

			try {
				categoryName =
					categoryNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String categoryName;

}