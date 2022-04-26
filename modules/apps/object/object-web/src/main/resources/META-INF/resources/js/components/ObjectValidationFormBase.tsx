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

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import React, {ChangeEventHandler, ReactNode} from 'react';

import useForm, {FormError, invalidateRequired} from '../hooks/useForm';
import CustomSelect from './Form/CustomSelect/CustomSelect';

const REQUIRED_MSG = Liferay.Language.get('required');

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Liferay.Language.Locale;

export default function ObjectValidationFormBase({
	children,
	disabled,
	handleChange,
	objectValidationTypes,
	setValues,
	values,
}: IProps) {
	return (
		<>
			<CustomSelect<ObjectValidationType>
				disabled={true}
				label={Liferay.Language.get('type')}
				options={objectValidationTypes}
				value="Groovy"
			/>

			<ClayForm.Group>
				<label htmlFor="description">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					component="textarea"
					disabled={disabled}
					id="description"
					onChange={handleChange}
					type="text"
				/>
			</ClayForm.Group>

			{children}
			<ClayToggle
				disabled={disabled}
				label={Liferay.Language.get('active-validation')}
				name="required"
				onToggle={(active) => setValues({active})}
				toggled={values.active}
			/>
		</>
	);
}

export function useObjectValidationForm({
	initialValues,
	onSubmit,
}: IUseObjectValidationForm) {
	const validate = (validation: Partial<ObjectValidation>) => {
		const errors: ObjectValidationErrors = {};

		const label = validation.name?.[defaultLanguageId];
		const errorMessage = validation.errorLabel?.[defaultLanguageId];

		if (invalidateRequired(label)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(errorMessage)) {
			errors.errorLabel = REQUIRED_MSG;
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
interface IProps {
	children?: ReactNode;
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectValidationTypes: ObjectValidationType[];
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export type ObjectValidationErrors = FormError<ObjectValidation>;
