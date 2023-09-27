/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {InputHTMLAttributes} from 'react';

import {BaseWrapper} from '../Base';

type InputProps = {
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({
	disabled = false,
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
		disabled={disabled}
		error={errors[name]?.message}
		id={id}
		label={label}
		required={required}
	>
		<ClayInput
			className="rounded-xs"
			component={type === 'textarea' ? 'textarea' : 'input'}
			disabled={disabled}
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
