/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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
	component = 'input',
	disabled,
	errorMessage,
	id,
	label,
	name,
	required,
	type = 'text',
	value = '',
	...restProps
}: {
	component?: 'textarea' | 'input';
	errorMessage?: string;
	label: string;
	name: string;
	required?: boolean;
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
			label={label}
			required={required}
		>
			<ClayInput
				{...restProps}
				aria-describedby={errorMessage ? feedbackId : undefined}
				component={component}
				disabled={disabled}
				id={fieldId}
				name={name}
				required={required}
				type={type}
				value={value}
			/>
		</FieldWrapper>
	);
};

export default FieldText;
