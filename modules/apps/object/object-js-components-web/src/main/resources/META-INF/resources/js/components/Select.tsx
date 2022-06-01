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

import {ClaySelect} from '@clayui/form';
import React from 'react';

import {FieldBase} from './FieldBase';

interface ISelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	label: string;
	options?: string[];
	required?: boolean;
	tooltip?: string;
}

export function Select({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	options,
	required,
	tooltip,
	...otherProps
}: ISelectProps) {
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
			<ClaySelect
				{...otherProps}
				disabled={disabled}
				id={id}
				onChange={onChange}
			>
				<ClaySelect.Option
					label={Liferay.Language.get('choose-an-option')}
				/>

				{options?.map((label, index) => (
					<ClaySelect.Option
						key={index}
						label={label}
						value={index}
					/>
				))}
			</ClaySelect>
		</FieldBase>
	);
}
