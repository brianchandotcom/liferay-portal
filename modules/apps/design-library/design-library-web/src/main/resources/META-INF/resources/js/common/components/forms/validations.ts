/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikErrors} from 'formik';
import {sub} from 'frontend-js-web';

export type Errors = FormikErrors<Record<string, any>>;
type ValidationFunction = (value: any) => string | undefined;
type ValidationSchema = Record<string, ValidationFunction[]>;

const maxLength =
	(max: number): ValidationFunction =>
	(value) => {
		if (value && value.length > max) {
			return sub(
				Liferay.Language.get('please-enter-no-more-than-x-characters'),
				max
			);
		}
	};

const required: ValidationFunction = (value) => {
	if (!value) {
		return Liferay.Language.get('this-field-is-required');
	}
};

const validate = (
	fields: ValidationSchema,
	values: Record<string, any>,
	errors?: Errors
) => {
	const nextErrors = {...errors};

	Object.entries(fields).forEach(([inputName, validations]) => {
		if (!validations.length) {
			delete nextErrors[inputName];

			return;
		}

		validations.some((validation) => {
			const error = validation(values[inputName]);

			if (error) {
				nextErrors[inputName] = error;
			}
			else {
				delete nextErrors[inputName];
			}

			return Boolean(error);
		});
	});

	return nextErrors;
};

export {maxLength, required, validate};
