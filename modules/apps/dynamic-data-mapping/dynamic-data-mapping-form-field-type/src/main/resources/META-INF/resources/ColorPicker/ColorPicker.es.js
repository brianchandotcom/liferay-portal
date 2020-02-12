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

import './ColorPickerRegister.soy';

import ClayColorPicker from '@clayui/color-picker';
import React, {useEffect, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './ColorPickerAdapter.soy';

const DEFAULT_COLORS = [
	'000000',
	'5F5F5F',
	'9A9A9A',
	'CBCBCB',
	'E1E1E1',
	'FFFFFF',
	'FF0D0D',
	'FF8A1C',
	'2BA676',
	'006EF8',
	'7F26FF',
	'FF21A0',
];

const ClayColorPickerWithState = ({
	inputValue,
	name,
	onBlur,
	onFocus,
	onValueChange,
	readOnly,
	spritemap,
}) => {
	const [customColors, setCustoms] = useState(DEFAULT_COLORS);

	const [color, setColor] = useState(
		inputValue ? inputValue : customColors[0]
	);

	useEffect(() => {
		if (inputValue) {
			setColor(inputValue);
		}
	}, [inputValue]);

	return (
		<ClayColorPicker
			colors={customColors}
			disabled={readOnly}
			label={Liferay.Language.get('color-field-type-label')}
			name={name}
			onBlur={onBlur}
			onColorsChange={setCustoms}
			onFocus={onFocus}
			onValueChange={value => {
				if (value !== color) {
					setColor(value);
					onValueChange(value);
				}
			}}
			spritemap={spritemap}
			value={color}
		/>
	);
};

/**
 * The Proxy is on the front line of `PageRenderer.RegisterFieldType`, communicates
 * directly with the store and issues events from the Metal instance. This should
 * be overridden when we have a Store/Provider React implementation.
 */
const ColorPickerProxy = connectStore(
	({
		dispatch,
		emit,
		name,
		predefinedValue = '000000',
		readOnly,
		spritemap,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy
			dispatch={dispatch}
			name={name}
			readOnly={readOnly}
			spritemap={spritemap}
			{...otherProps}
		>
			<ClayColorPickerWithState
				inputValue={value ? value : predefinedValue}
				name={name}
				onBlur={event =>
					emit('fieldBlurred', event, event.target.value)
				}
				onFocus={event =>
					emit('fieldFocused', event, event.target.value)
				}
				onValueChange={value => emit('fieldEdited', {}, value)}
				readOnly={readOnly}
				spritemap={spritemap}
			/>
		</FieldBaseProxy>
	)
);

const ReactColorPickerAdapter = getConnectedReactComponentAdapter(
	ColorPickerProxy,
	templates
);

export {ClayColorPickerWithState, ReactColorPickerAdapter};
export default ReactColorPickerAdapter;
