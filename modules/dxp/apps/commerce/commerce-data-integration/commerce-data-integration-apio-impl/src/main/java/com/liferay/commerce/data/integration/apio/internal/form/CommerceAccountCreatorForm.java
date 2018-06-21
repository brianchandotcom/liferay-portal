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
import com.liferay.apio.architect.form.Form.Builder;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CommerceAccountCreatorForm {

	public static Form<CommerceAccountCreatorForm> buildForm(
		Builder<CommerceAccountCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The account creator form"
		).description(
			__ -> "This form can be used to create an account"
		).constructor(
			CommerceAccountCreatorForm::new
		).addRequiredString(
			"name", CommerceAccountCreatorForm::_setName
		).addOptionalLongList(
			"commerceUserIds", CommerceAccountCreatorForm::_setCommerceUserIds
		).build();
	}

	public String getName() {
		return _name;
	}

	public List<Long> getCommerceUserIds() {
		return _commerceUserIds;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setCommerceUserIds(List<Long> commerceUserIds) {
		_commerceUserIds = commerceUserIds;
	}

	private String _name;
	private List<Long> _commerceUserIds;

}