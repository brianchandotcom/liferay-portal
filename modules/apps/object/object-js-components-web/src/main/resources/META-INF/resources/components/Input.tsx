/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

import {FieldBase} from './FieldBase';

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
	required?: boolean;
	type?: 'number' | 'textarea' | 'text';
	value?: string | number | string[];
}
