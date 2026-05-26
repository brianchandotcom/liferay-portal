/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikErrors} from 'formik';

type ValidationFunction = (value: any) => string | undefined;
type ValidationSchema = Record<string, ValidationFunction[]>;

const required: ValidationFunction = (value) => {
	if (!value) {
		return Liferay.Language.get('required');
	}
};

const requiredLocalized: ValidationFunction = (value) => {
	if (!value || !Object.values(value).some(Boolean)) {
		return Liferay.Language.get('required');
	}
};

function validate<T>(schema: ValidationSchema, values: T): FormikErrors<T> {
	const errors: Record<string, string> = {};

	Object.entries(schema).forEach(([field, validations]) => {
		validations.some((validation) => {
			const error = validation((values as Record<string, any>)[field]);

			if (error) {
				errors[field] = error;
			}

			return Boolean(error);
		});
	});

	return errors as FormikErrors<T>;
}

export {required, requiredLocalized, validate};
