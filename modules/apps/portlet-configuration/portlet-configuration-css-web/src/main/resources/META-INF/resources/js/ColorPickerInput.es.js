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

function normalizeColor(color) {
	return color.startsWith('#') ? color.substring(1) : color;
}

const ColorPicker = ({color, label, name}) => {
	const [colorValue, setColorValue] = useState(normalizeColor(color));

	return (
		<div className="form-group">
			<input name={name} type="hidden" value={`#${colorValue}`} />

			<ClayColorPicker
				label={label}
				name={`${name}ColorPicker`}
				onValueChange={setColorValue}
				showHex={true}
				title={label}
				value={colorValue}
			/>
		</div>
	);
};

export default function(props) {
	return <ColorPicker {...props} />;
}
