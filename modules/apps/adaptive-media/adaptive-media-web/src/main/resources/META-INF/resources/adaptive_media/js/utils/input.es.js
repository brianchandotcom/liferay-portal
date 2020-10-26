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

import ClayForm, {ClayInput} from '@clayui/form';
import React from 'react';

import {ErrorFeedback, RequiredMark} from './formComponents.es';

const Input = ({
	disabled,
	error,
	id,
	label,
	name,
	required,
	type = 'text',
	...restProps
}) => {
	const inputId = id || name;

	return (
		<ClayForm.Group className={error ? 'has-error' : ''}>
			<label className={disabled ? 'disabled' : ''} htmlFor={inputId}>
				{label}

				{required && <RequiredMark />}
			</label>

			<ClayInput
				{...restProps}
				className="form-control"
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={inputId}
				name={name}
				type={type}
			/>

			{error && <ErrorFeedback error={error} />}
		</ClayForm.Group>
	);
};

export default Input;
