/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	REQUIRED_MSG,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';
import {ChangeEventHandler} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface IUseObjectValidationForm {
	initialValues: Partial<ObjectValidation>;
	onSubmit: (validation: ObjectValidation) => void;
}

export type ObjectValidationErrors = FormError<ObjectValidation>;

export interface TabProps {
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export function useObjectValidationForm({
	initialValues,
	onSubmit,
}: IUseObjectValidationForm) {
	const validate = (validation: Partial<ObjectValidation>) => {
		const errors: ObjectValidationErrors = {};
		const label = validation.name?.[defaultLanguageId];
		const errorMessage = validation.errorLabel?.[defaultLanguageId];
		const script = validation.script;

		if (invalidateRequired(label)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(errorMessage)) {
			errors.errorLabel = REQUIRED_MSG;
		}

		if (
			!validation.engine?.startsWith('function#') &&
			invalidateRequired(script)
		) {
			errors.script = REQUIRED_MSG;
		}

		if (
			validation.engine === 'groovy' &&
			!!validation.lineCount &&
			validation.lineCount > 2987
		) {
			errors.script = Liferay.Language.get(
				'the-maximum-number-of-lines-available-is-2987'
			);
		}

		if (
			Liferay.FeatureFlags['LPS-187846'] &&
			validation.outputType === 'partialValidation' &&
			!validation.objectValidationRuleSettings?.length
		) {
			errors.outputType = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ObjectValidation
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}
