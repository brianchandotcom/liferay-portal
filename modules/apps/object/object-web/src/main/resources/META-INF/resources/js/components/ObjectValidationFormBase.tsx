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

import {
	FormError,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';

import {defaultLanguageId} from '../utils/locale';

const REQUIRED_MSG = Liferay.Language.get('required');

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

		if (invalidateRequired(script)) {
			errors.script = REQUIRED_MSG;
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
interface IUseObjectValidationForm {
	initialValues: Partial<ObjectValidation>;
	onSubmit: (validation: ObjectValidation) => void;
}

export type ObjectValidationErrors = FormError<ObjectValidation>;
