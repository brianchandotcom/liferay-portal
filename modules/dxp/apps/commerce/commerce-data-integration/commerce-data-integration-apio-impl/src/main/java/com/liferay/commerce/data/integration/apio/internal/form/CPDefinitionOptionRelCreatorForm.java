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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CPDefinitionOptionRelCreatorForm {

	public static Form<CPDefinitionOptionRelCreatorForm> buildForm(
		Form.Builder<CPDefinitionOptionRelCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The product option creator form"
		).description(
			__ -> "This form can be used to create a product option"
		).constructor(
			CPDefinitionOptionRelCreatorForm::new
		).addRequiredLong(
			"commerceProductOptionId",
			CPDefinitionOptionRelCreatorForm::_setCommerceProductOptionId
		).build();
	}

	public Long getCommerceProductOptionId() {
		return _commerceProductOptionId;
	}

	private void _setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOptionId = commerceProductOptionId;
	}

	private long _commerceProductOptionId;

}