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

const required = (value) => {
	if (!value) {
		return Liferay.Language.get('this-field-is-required');
	}
};

const maxLength = (value, max) => {
	if (value.length > max) {
		return Liferay.Util.sub(
			Liferay.Language.get('this-field-exceeded-x-characters'),
			max
		);
	}
};

const isEmail = (value) =>
	/^[^\s@]+@[^\s@]+$/.test(value)
		? undefined
		: Liferay.Language.get('please-enter-a-valid-email-address');

const validate = (fields, values) => {
	const errors = {};

	Object.entries(fields).forEach(([inputName, validations]) => {
		validations.forEach((validation) => {
			const error = validation(values[inputName]);

			if (error) {
				errors[inputName] = error;
			}

			return error;
		});
	});

	return errors;
};

export {isEmail, maxLength, required, validate};
