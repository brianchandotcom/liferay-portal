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

import './PasswordRegister.soy';

import React, {useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './PasswordAdapter.soy';

const Password = ({
	disabled,
	name,
	onBlur,
	onFocus,
	onInput,
	placeholder,
	value: initialValue,
}) => {
	const [value, setValue] = useState(initialValue);

	return (
		<input
			className="ddm-field-text form-control"
			disabled={disabled}
			id={name}
			name={name}
			onBlur={onBlur}
			onFocus={onFocus}
			onInput={event => {
				onInput(event);
				setValue(event.target.value);
			}}
			placeholder={placeholder}
			type="password"
			value={value}
		/>
	);
};

const PasswordProxy = connectStore(
	({
		emit,
		name,
		placeholder,
		predefinedValue,
		readOnly,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy {...otherProps} name={name} readOnly={readOnly}>
			<Password
				disabled={readOnly}
				name={name}
				onBlur={event =>
					emit('fieldBlurred', event, event.target.value)
				}
				onFocus={event =>
					emit('fieldFocused', event, event.target.value)
				}
				onInput={event =>
					emit('fieldEdited', event, event.target.value)
				}
				placeholder={placeholder}
				value={value ? value : predefinedValue}
			/>
		</FieldBaseProxy>
	)
);

const ReactPasswordAdapter = getConnectedReactComponentAdapter(
	PasswordProxy,
	templates
);

export {ReactPasswordAdapter};
export default ReactPasswordAdapter;
