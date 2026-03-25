/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

import FieldWrapper from './FieldWrapper';

interface Props
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	errorMessage?: string;
	insetAfter?: boolean;
	insetBefore?: boolean;
	label: string;
	name: string;
	sizing?: 'lg' | 'regular' | 'sm';
}

export default function FieldText({
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
}: Props) {
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
}
