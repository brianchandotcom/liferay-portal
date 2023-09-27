/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PortletBase, openWindow, sub} from 'frontend-js-web';

class AccountUserEmailDomainValidator extends PortletBase {
	created(props) {
		this.accountEntryNames = props.accountEntryNames;
		this.blockedDomains = props.blockedDomains;
		this.validDomains = props.validDomains;
		this.viewValidDomainsURL = props.viewValidDomainsURL;
	}
	attached() {
		const form = this.getForm_(this.ns('fm'));

		if (form) {
			this.decorateEmailAddressField_(form);
		}
		else {
			Liferay.once(
				this.ns('formReady'),
				(event) => {
					const form = this.getForm_(event.formName);

					if (form) {
						this.decorateEmailAddressField_(form);
					}
				},
				this
			);
		}
	}

	addFieldMessage_(field) {
		const container = document.createElement('div');

		container.className = 'form-text';

		const button = document.createElement('button');

		button.className = 'btn btn-link btn-unstyled link-secondary';
		button.innerText = Liferay.Language.get('view-valid-domains');
		button.onclick = () => {
			this.openDialog_(this.viewValidDomainsURL);
		};
		button.type = 'button';

		container.appendChild(button);

		field.placeAfter(container);
	}

	addFormFieldRules_(form, fieldRules) {
		const oldFieldRules = form.get('fieldRules');

		form.set('fieldRules', oldFieldRules.concat(fieldRules));
	}

	decorateEmailAddressField_(form) {
		const field = form.formValidator.getField(this.ns('emailAddress'));

		if (this.viewValidDomainsURL) {
			this.addFieldMessage_(field);
		}

		const emailDomainFieldRule = this.getEmailDomainFieldRule_();

		this.addFormFieldRules_(form, [emailDomainFieldRule]);

		this.setWarningValidationStyle_(
			form,
			field,
			emailDomainFieldRule.validatorName
		);
	}

	getEmailDomainFieldRule_() {
		const accountEntryNames = this.accountEntryNames;
		const blockedDomains = this.blockedDomains
			? this.blockedDomains.split(',')
			: this.blockedDomains;
		const validDomains = this.validDomains
			? this.validDomains.split(',')
			: this.validDomains;
		const validatorName = 'emailDomain';

		return {
			body(val, field) {
				const emailDomain = val.substr(val.indexOf('@') + 1);
				let errorMessage;
				let hasError = false;

				if (!!blockedDomains && blockedDomains.includes(emailDomain)) {
					hasError = true;

					errorMessage = sub(
						Liferay.Language.get('x-is-a-blocked-domain'),
						emailDomain
					);
				}
				else if (
					!!validDomains &&
					!validDomains.includes(emailDomain)
				) {
					hasError = true;

					errorMessage = sub(
						Liferay.Language.get(
							'x-is-not-a-valid-domain-for-the-following-accounts-x'
						),
						emailDomain,
						accountEntryNames
					);
				}

				if (hasError) {
					const fieldName = field.get('name');

					const errorMessages = this.get('fieldStrings');

					if (!errorMessages[fieldName]) {
						errorMessages[fieldName] = {};
					}

					errorMessages[fieldName][validatorName] = errorMessage;

					return false;
				}

				return true;
			},
			custom: true,
			fieldName: this.ns('emailAddress'),
			validatorName,
		};
	}

	getForm_(formName) {
		return Liferay.Form?.get(formName);
	}

	onSubmitError_(event, form, field, validatorName) {
		event.preventDefault();

		const errors = event.validator.errors;

		const fieldErrors = errors[field.get('name')];

		if (fieldErrors.length === 1 && fieldErrors[0] === validatorName) {
			submitForm(form);

			event.halt();
		}
	}

	openDialog_(url) {
		openWindow({
			dialog: {
				destroyOnHide: true,
				height: 400,
				modal: true,
				resizable: false,
				width: 600,
			},
			title: Liferay.Language.get('valid-domains'),
			uri: url,
		});
	}

	setWarningValidationStyle_(form, formField, validatorName) {
		const formValidator = form.formValidator;

		formValidator.after('errorField', (event) => {
			if (
				event.validator.field === formField &&
				event.validator.errors[0] === validatorName
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

			if (field !== formField) {
				return;
			}

			const fieldContainer = formValidator.findFieldContainer(field);

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

export default AccountUserEmailDomainValidator;
