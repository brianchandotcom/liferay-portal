/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field, FieldProps} from 'formik';
import React from 'react';

import FieldText, {FieldTextProps} from './FieldText';

function FormikWrapper({
	children,
	name,
}: {
	children: (props: {
		errorMessage?: string;
		field: FieldProps['field'];
	}) => React.ReactNode;
	name: string;
}) {
	return (
		<Field name={name}>
			{({field, meta}: FieldProps) =>
				children({
					errorMessage: meta.touched ? meta.error : undefined,
					field,
				})
			}
		</Field>
	);
}

export function FormikFieldText(props: FieldTextProps) {
	return (
		<FormikWrapper name={props.name}>
			{({errorMessage, field}) => (
				<FieldText {...props} {...field} errorMessage={errorMessage} />
			)}
		</FormikWrapper>
	);
}
