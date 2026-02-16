/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {
	ArrayHelpers,
	Field,
	FieldArray,
	FieldProps,
	FieldValidator,
	FormikValues,
	useFormikContext,
} from 'formik';
import React from 'react';

import {FieldCheckbox} from './FieldCheckbox';
import FieldText, {FieldTextProps} from './FieldText';
import {required as requiredValidation} from './validations';

function FormikWrapper({
	children,
	name,
	required,
	validate,
}: {
	children: (props: {
		errorMessage?: string;
		field: FieldProps['field'];
	}) => React.ReactNode;
	name: string;
	required?: boolean;
	validate?: FieldValidator;
}) {
	return (
		<Field
			name={name}
			validate={
				validate ? validate : required ? requiredValidation : undefined
			}
		>
			{({field, meta}: FieldProps) =>
				children({
					errorMessage:
						meta.touched && meta.error ? meta.error : undefined,
					field,
				})
			}
		</Field>
	);
}

export function FormikFieldText(props: FieldTextProps) {
	return (
		<FormikWrapper name={props.name} required={props.required}>
			{({errorMessage, field}) => (
				<FieldText {...props} {...field} errorMessage={errorMessage} />
			)}
		</FormikWrapper>
	);
}

interface FormikFieldMultiCheckboxProps {
	name: string;
	options: Array<{
		description?: string;
		label: string;
		value: string;
	}>;
}

export function FormikFieldMultiCheckbox({
	name,
	options,
}: FormikFieldMultiCheckboxProps) {
	const {errors, setFieldTouched, touched, values} =
		useFormikContext<FormikValues>();

	const fieldErrors = errors[name] as string | undefined;
	const fieldTouched = touched[name] as boolean | undefined;

	return (
		<>
			<FieldArray name={name}>
				{(arrayHelper: ArrayHelpers) => {
					const selectedValues: string[] =
						values[name] && Array.isArray(values[name])
							? values[name]
							: [];

					return options.map(({description, label, value}, index) => (
						<FieldCheckbox
							checked={selectedValues.includes(value)}
							description={description}
							key={value}
							label={label}
							name={`${name}[${index}]`}
							onChange={(checked) => {
								checked
									? arrayHelper.push(value)
									: arrayHelper.remove(
											currentSelectedValues.indexOf(value)
										);

								setFieldTouched(name, true);
							}}
						/>
					));
				}}
			</FieldArray>

			{fieldTouched && fieldErrors && (
				<ClayAlert
					displayType="danger"
					title={Liferay.Language.get('error-colon')}
				>
					{fieldErrors}
				</ClayAlert>
			)}
		</>
	);
}
