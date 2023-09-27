/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
