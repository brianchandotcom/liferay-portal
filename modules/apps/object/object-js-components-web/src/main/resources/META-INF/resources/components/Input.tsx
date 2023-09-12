/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

export const Input = React.forwardRef<HTMLInputElement, IProps>(
	(
		{
			className,
			component,
			disabled,
			error,
			feedbackMessage,
			id,
			label,
			name,
			onChange,
			onInput,
			readOnly,
			required,
			type,
			value,
			...otherProps
		},
		ref
	) => {
		return (
			<FieldBase
				className={className}
				disabled={disabled}
				errorMessage={error}
				helpMessage={feedbackMessage}
				id={id}
				label={label}
				required={required}
			>
				<ClayInput
					{...otherProps}
					component={component}
					disabled={disabled}
					id={id}
					name={name}
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
);

interface IProps
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea' | React.ForwardRefExoticComponent<any>;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	name?: string;
	placeholder?: string;
	required?: boolean;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string | number | string[];
}
