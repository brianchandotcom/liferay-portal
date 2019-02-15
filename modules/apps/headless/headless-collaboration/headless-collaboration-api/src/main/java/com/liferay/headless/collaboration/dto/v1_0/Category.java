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

package com.liferay.headless.collaboration.dto.v1_0;

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
@GraphQLName("Category")
@XmlRootElement(name = "Category")
public class Category {

	public Long getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryId(
		UnsafeSupplier<Long, Throwable> categoryIdUnsafeSupplier) {

		try {
			categoryId = categoryIdUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCategoryName(
		UnsafeSupplier<String, Throwable> categoryNameUnsafeSupplier) {

		try {
			categoryName = categoryNameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected Long categoryId;

	@GraphQLField
	protected String categoryName;

}