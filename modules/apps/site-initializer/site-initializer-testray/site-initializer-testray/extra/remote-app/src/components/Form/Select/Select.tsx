/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {InputHTMLAttributes} from 'react';

import {BaseWrapper} from '../Base';

type InputSelectProps = {
	className?: string;
	defaultOption?: boolean;
	disabled?: boolean;
	errors?: any;
	forceSelectOption?: boolean;
	id?: string;
	label?: string;
	name: string;
	options: {label: string; value: string | number}[];
	register?: any;
	registerOptions?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const InputSelect: React.FC<InputSelectProps> = ({
	className,
	disabled = false,
	registerOptions,
	defaultOption = true,
	errors = {},
	defaultValue,
	label,
	name,
	register = () => {},
	id = name,
	options,
	forceSelectOption = false,
	required = false,
	...otherProps
}) => {
	return (
		<BaseWrapper
			disabled={disabled}
			error={errors[name]?.message}
			label={label}
			required={required}
		>
			<select
				className={classNames('form-control rounded-xs', className)}
				defaultValue={defaultValue}
				disabled={disabled}
				id={id}
				name={name}
				{...otherProps}
				{...register(name, {required, ...registerOptions})}
			>
				{defaultOption && <option value=""></option>}

				{options.map(({label, value}, index) => (
					<option
						key={index}
						selected={
							forceSelectOption
								? value === defaultValue
								: undefined
						}
						value={value}
					>
						{label}
					</option>
				))}
			</select>
		</BaseWrapper>
	);
};

export default InputSelect;
