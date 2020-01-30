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

import ClayColorPicker from '@clayui/color-picker';
import React, {useState, useEffect} from 'react';

import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
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
	'FF21A0'
];

const ClayColorPickerWithState = ({
	dispatch,
	inputValue,
	name,
	readOnly,
	spritemap
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
			onBlur={event => dispatch({payload: event, type: 'blur'})}
			onColorsChange={setCustoms}
			onFocus={event => dispatch({payload: event, type: 'focus'})}
			onValueChange={value => {
				if (value !== color) {
					setColor(value);
					dispatch({payload: value, type: 'value'});
				}
			}}
			spritemap={spritemap}
			value={color}
		/>
	);
};

const ReactColorPickerAdapter = getConnectedReactComponentAdapter(
	ClayColorPickerWithState,
	templates
);

export {ReactColorPickerAdapter};
export default ReactColorPickerAdapter;
