/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React, {InputHTMLAttributes, Ref} from 'react';

interface InputProps
	extends InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	placeholder?: string;
	ref?: Ref<HTMLInputElement>;
	required?: boolean;
	tooltip?: string;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string | number | string[];
}

export function Input({
	className,
	component,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	min,
	name,
	onBlur,
	onChange,
	onInput,
	readOnly,
	ref,
	required,
	tooltip,
	type,
	value,
	...otherProps
}: InputProps) {
	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
			tooltip={tooltip}
		>
			<ClayInput
				{...otherProps}
				component={component}
				disabled={disabled}
				id={id}
				min={min}
				name={name}
				onBlur={onBlur}
				onChange={onChange}
				onInput={onInput}
				readOnly={readOnly}
				ref={ref}
				type={type}
				value={value}
			/>
		</FieldBase>
	);
}
