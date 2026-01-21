/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

type ClayInputProps = {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	insetAfter?: boolean;
	insetBefore?: boolean;
	sizing?: 'lg' | 'regular' | 'sm';
} & React.InputHTMLAttributes<HTMLInputElement>;

const FieldText = ({
	component = 'input',
	disabled,
	errorMessage,
	formGroupProps,
	helpMessage,
	id,
	label,
	name,
	required,
	type = 'text',
	value = '',
	...restProps
}: {
	component?: 'textarea' | 'input';
	disabled?: boolean;
	errorMessage?: string;
	formGroupProps?: {className: string};
	helpMessage?: string;
	id?: string;
	label: string;
	name: string;
	required?: boolean;
	type?: 'text' | 'number';
	value?: string;
} & ClayInputProps) => {
	const fieldId = id ?? name;

	return (
		<FieldBase
			className={formGroupProps?.className}
			disabled={disabled}
			errorMessage={errorMessage}
			helpMessage={helpMessage}
			id={fieldId}
			label={label}
			required={required}
		>
			<ClayInput
				{...restProps}
				aria-describedby={
					errorMessage || helpMessage
						? `${fieldId}fieldFeedback`
						: undefined
				}
				aria-invalid={errorMessage ? true : undefined}
				component={component}
				disabled={disabled}
				id={fieldId}
				name={name}
				required={required}
				type={type}
				value={value}
			/>
		</FieldBase>
	);
};

export default FieldText;
