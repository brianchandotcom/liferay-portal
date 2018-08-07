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

package com.liferay.category.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;

/**
 * Instances of this class represent the values extracted from a linked category
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class LinkedCategoryForm {

	/**
	 * Builds a {@code Form} that generates {@code LinkedCategoryForm} depending
	 * on the HTTP body.
	 *
	 * @param  builder the {@code Form} builder
	 * @return a nested category form
	 * @review
	 */
	public static Form<LinkedCategoryForm> buildForm(
		Builder<LinkedCategoryForm> builder) {

		return builder.title(
			__ -> "Linked category form"
		).description(
			__ -> "This form can be used to link a category to another resource"
		).constructor(
			LinkedCategoryForm::new
		).addRequiredLinkedModel(
			"category", CategoryIdentifier.class,
			LinkedCategoryForm::setCategoryId
		).build();
	}

	/**
	 * Returns the asset category's ID.
	 *
	 * @return the asset category's ID
	 * @review
	 */
	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	private long _categoryId;

}