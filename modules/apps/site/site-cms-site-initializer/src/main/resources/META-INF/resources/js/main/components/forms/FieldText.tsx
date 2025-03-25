/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

import FieldWrapper from './FieldWrapper';

type ClayInputProps = {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	insetAfter?: boolean;
	insetBefore?: boolean;
	sizing?: 'lg' | 'regular' | 'sm';
} & React.InputHTMLAttributes<HTMLInputElement>;

const FieldText = ({
	disabled,
	errorMessage,
	helpMessage,
	id,
	label,
	name,
	required,
	type = 'input',
	value = '',
	...restProps
}: {
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label: string;
	name: string;
	required?: boolean;
	type?: 'textarea' | 'input';
	value?: string;
} & ClayInputProps) => {
	const fieldId = id ?? name;
	const feedbackId = `feedback-${fieldId}`;

	return (
		<FieldWrapper
			disabled={disabled}
			errorMessage={errorMessage}
			feedbackId={feedbackId}
			fieldId={fieldId}
			helpMessage={helpMessage}
			label={label}
			required={required}
		>
			<ClayInput
				{...restProps}
				aria-describedby={(errorMessage || helpMessage) ?? feedbackId}
				component={type}
				disabled={disabled}
				id={fieldId}
				name={name}
				type={type}
				value={value}
			/>
		</FieldWrapper>
	);
};

export default FieldText;
