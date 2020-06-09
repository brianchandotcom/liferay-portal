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
					this.addFormFieldRules_(form, [
						this.getEmailDomainFieldRule_(),
					]);
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
}

AccountUserEmailDomainValidator.STATE = {
	validDomains: Config.string,
};

export default AccountUserEmailDomainValidator;
