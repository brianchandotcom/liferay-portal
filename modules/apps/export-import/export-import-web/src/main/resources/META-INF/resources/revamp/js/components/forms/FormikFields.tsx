/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field, FieldProps, FieldValidator} from 'formik';
import React from 'react';

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
					errorMessage: meta.error,
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
