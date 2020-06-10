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

import {PortletBase} from 'frontend-js-web';
import {Config} from 'metal-state';

class AccountUserEmailDomainValidator extends PortletBase {
	attached() {
		Liferay.once(
			this.ns('formReady'),
			(event) => {
				const form = Liferay.Form.get(event.formName);

				const field = form.formValidator.getField(
					this.ns('emailAddress')
				);

				if (field) {
					const emailDomainFieldRule = this.getEmailDomainFieldRule_();

					this.addFormFieldRules_(form, [emailDomainFieldRule]);

					this.setWarningValidationStyle_(
						form,
						field,
						emailDomainFieldRule.validatorName
					);
				}
			},
			this
		);
	}

	addFormFieldRules_(form, fieldRules) {
		const oldFieldRules = form.get('fieldRules');

		form.set('fieldRules', oldFieldRules.concat(fieldRules));
	}

	getEmailDomainFieldRule_() {
		const validDomains = this.validDomains.split(',');

		return {
			body(val) {
				const emailDomain = val.substr(val.indexOf('@') + 1);

				if (!validDomains.includes(emailDomain)) {
					return false;
				}

				return true;
			},
			custom: true,
			errorMessage: Liferay.Language.get('this-email-has-invalid-domain'),
			fieldName: this.ns('emailAddress'),
			validatorName: 'emailDomain',
		};
	}

	onSubmitError_(event, form, field, validatorName) {
		event.preventDefault();

		const errors = event.validator.errors;

		const fieldErrors = errors[field.get('name')];

		if (fieldErrors.length == 1 && fieldErrors[0] == validatorName) {
			submitForm(form);

			event.halt();
		}
	}

	setWarningValidationStyle_(form, formField, validatorName) {
		const formValidator = form.formValidator;

		formValidator.after('errorField', (event) => {
			if (
				event.validator.field == formField &&
				event.validator.errors[0] == validatorName
			) {
				const fieldContainer = formValidator.findFieldContainer(
					formField
				);

				if (fieldContainer) {
					fieldContainer.replaceClass('has-error', 'has-warning');
				}
			}
		});

		const resetFieldCss = formValidator.resetFieldCss;

		formValidator.resetFieldCss = function (field) {
			resetFieldCss.apply(formValidator, [field]);

			if (field != formField) {
				return;
			}

			var fieldContainer = formValidator.findFieldContainer(field);

			if (fieldContainer) {
				fieldContainer.removeClass('has-warning');
			}
		};

		formValidator.on(
			'submitError',
			(event) => {
				this.onSubmitError_(event, form, formField, validatorName);
			},
			this
		);
	}
}

AccountUserEmailDomainValidator.STATE = {
	validDomains: Config.string,
};

export default AccountUserEmailDomainValidator;
