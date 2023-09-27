/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const alphanumeric = (value) =>
	/^[\w-]+$/.test(value)
		? undefined
		: Liferay.Language.get(
				'please-enter-only-alphanumeric-characters-dashes-or-underscores'
		  );

const required = (value) => {
	if (!value) {
		return Liferay.Language.get('this-field-is-required');
	}
};

const validate = (fields, values) => {
	const errors = {};

	Object.entries(fields).forEach(([inputName, validations]) => {
		validations.some((validation) => {
			const error = validation(values[inputName]);

			if (error) {
				errors[inputName] = error;
			}

			return error;
		});
	});

	return errors;
};

export {alphanumeric, required, validate};
