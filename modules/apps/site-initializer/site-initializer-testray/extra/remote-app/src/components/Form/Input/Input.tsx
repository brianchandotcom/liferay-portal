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
import {InputHTMLAttributes} from 'react';

import {BaseWrapper} from '../Base';

type InputProps = {
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({
	errors = {},
	label,
	name,
	register = () => {},
	id = name,
	type,
	value,
	required = false,
	onBlur,
	...otherProps
}) => (
	<BaseWrapper
		error={errors[name]?.message}
		label={label}
		required={required}
	>
		<ClayInput
			className="rounded-xs"
			component={type === 'textarea' ? 'textarea' : 'input'}
			id={id}
			name={name}
			type={type}
			value={value}
			{...otherProps}
			{...register(name, {onBlur, required})}
		/>
	</BaseWrapper>
);

export default Input;
