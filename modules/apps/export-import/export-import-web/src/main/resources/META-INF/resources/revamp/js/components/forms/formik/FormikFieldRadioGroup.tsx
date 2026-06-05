/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikValues, useFormikContext} from 'formik';
import React from 'react';

import {FieldRadio} from '../FieldRadio';

interface FormikFieldRadioGroupProps {
	'aria-describedby'?: string;
	'aria-labelledby'?: string;
	'name': string;
	'options': Array<{
		description?: string;
		label: string;
		value: string;
	}>;
}

export function FormikFieldRadioGroup({
	'aria-describedby': ariaDescribedby,
	'aria-labelledby': ariaLabelledby,
	name,
	options,
}: FormikFieldRadioGroupProps) {
	const {setFieldValue, values} = useFormikContext<FormikValues>();

	const selectedValue = values[name];

	return (
		<div
			aria-describedby={ariaDescribedby}
			aria-labelledby={ariaLabelledby}
			className="option-group"
			role="radiogroup"
		>
			{options.map(({description, label, value}) => (
				<FieldRadio
					checked={selectedValue === value}
					description={description}
					key={value}
					label={label}
					name={name}
					onChange={() => setFieldValue(name, value)}
					value={value}
				/>
			))}
		</div>
	);
}
