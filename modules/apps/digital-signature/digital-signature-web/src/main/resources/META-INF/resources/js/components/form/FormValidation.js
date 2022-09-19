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

import {sub} from 'frontend-js-web';

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const required = (value) => {
	if (!value) {
		return Liferay.Language.get('this-field-is-required');
	}
};

const withInvalidExtensions = (fileEntries, availableExtensions) => {
	const fileEntriesError = fileEntries.filter(({extension}) =>
		availableExtensions.every(
			(availableExtension) => extension !== availableExtension
		)
	);

	if (fileEntriesError.length) {
		return fileEntriesError;
	}
};

const maxLength = (value, max) => {
	if (value.length > max) {
		return sub(
			Liferay.Language.get('this-field-exceeded-x-characters'),
			max
		);
	}
};

const isEmail = (value) =>
	EMAIL_REGEX.test(value)
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

export {isEmail, maxLength, required, validate, withInvalidExtensions};
