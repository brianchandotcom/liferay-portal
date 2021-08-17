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
import React, {useState} from 'react';

const HEX_COLOR_REGEX = /^#?[0-9A-F]{3}(?:[0-9A-F]{3})?$/i;

const ColorPicker = ({color, label, name}) => {
	const [colorValue, setColorValue] = useState(color);
	const [customColors, setCustomColors] = useState([]);

	const noHashColorValue = colorValue.replace('#', '');

	return (
		<div className="form-group">
			<input
				name={name}
				type="hidden"
				value={
					colorValue
						? `${
								HEX_COLOR_REGEX.test(noHashColorValue)
									? '#'
									: ''
						  }${noHashColorValue}`
						: ''
				}
			/>

			<ClayColorPicker
				colors={customColors}
				label={label}
				name={`${name}ColorPicker`}
				onColorsChange={setCustomColors}
				onValueChange={setColorValue}
				showHex={true}
				showPredefinedColorsWithCustom
				title={label}
				value={noHashColorValue}
			/>
		</div>
	);
};

export default ColorPicker;
