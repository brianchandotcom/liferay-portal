/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayInput} from '@clayui/form';
import {InputHTMLAttributes} from 'react';

import BaseWrapper from '../Base/BaseWrapper';

type InputTypes = {
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputTypes> = ({
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
}) => {
	return (
		<>
			<BaseWrapper
				error={errors[name]?.message}
				id={id}
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
		</>
	);
};

export default Input;
