package com.liferay.commerce.headless.product.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ProductOptionForm {

	public static Form<ProductOptionForm> buildForm(
		Form.Builder<ProductOptionForm> formBuilder) {

		return formBuilder.title(
			__ -> "The product option creator form"
		).description(
			__ -> "This form can be used to create a product option"
		).constructor(
			ProductOptionForm::new
		).addRequiredLong(
			"optionId", ProductOptionForm::_setOptionId
		).build();
	}

	public Long getOptionId() {
		return _optionId;
	}

	private void _setOptionId(long optionId) {
		_optionId = optionId;
	}

	private long _optionId;

}