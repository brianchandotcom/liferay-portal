/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayColorPicker from '@clayui/color-picker';
import React, {useEffect, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

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
		<>
			<input name={name} type="hidden" value={color} />
			<ClayColorPicker
				colors={customColors}
				disabled={readOnly}
				label={Liferay.Language.get('color-field-type-label')}
				onBlur={onBlur}
				onColorsChange={setCustoms}
				onFocus={onFocus}
				onValueChange={(value) => {
					if (value !== color) {
						setColor(value);
						onValueChange(value);
					}
				}}
				spritemap={spritemap}
				value={color}
			/>
		</>
	);
};

const ColorPicker = ({
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue = '000000',
	readOnly,
	spritemap,
	value,
	...otherProps
}) => {
	let colorDropDownClickEvent;
	let previousShow = false;

	const observer = new MutationObserver((mutationsList, observer) => {
		for (const mutation of mutationsList) {
			if (
				mutation.type === 'attributes' &&
				mutation.attributeName === 'class'
			) {
				const show = mutation.target.classList.contains('show');

				if (show === previousShow) {
					return;
				}

				if (show) {
					onFocus(colorDropDownClickEvent);
				}
				else {
					onBlur(colorDropDownClickEvent);

					observer.disconnect();
				}

				previousShow = show;
			}
		}
	});

	// watch dropdown click for sending to Analytics

	const handleColorDropDownClicked = (event) => {
		if (!event.target.classList.contains('dropdown-toggle')) {
			return;
		}

		const colorDropdownNode = document.querySelector(
			'.clay-color-dropdown-menu'
		);

		if (!colorDropdownNode) {
			return;
		}

		colorDropDownClickEvent = event;

		observer.observe(colorDropdownNode, {attributes: true});
	};

	return (
		<FieldBase
			name={name}
			onClick={handleColorDropDownClicked}
			readOnly={readOnly}
			spritemap={spritemap}
			{...otherProps}
		>
			<ClayColorPickerWithState
				inputValue={value ? value : predefinedValue}
				name={name}
				onBlur={onBlur}
				onFocus={onFocus}
				onValueChange={(value) => onChange({}, value)}
				readOnly={readOnly}
				spritemap={spritemap}
			/>
		</FieldBase>
	);
};

export default ColorPicker;
