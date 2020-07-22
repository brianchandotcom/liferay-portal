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
import ClayForm from '@clayui/form';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const debouncedOnValueSelect = debounce(
	(onValueSelect, value) => onValueSelect(value),
	300
);
export default function ColorFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label} = frontendToken;

	const [customColors, setCustomColors] = useState([]);
	const [color, setColor] = useState(() => value?.replace('#', '') ?? '');

	return (
		<ClayForm.Group small>
			<div className="style-book-editor__color-frontend-token">
				<ClayColorPicker
					colors={customColors}
					label={label}
					onColorsChange={setCustomColors}
					onValueChange={(color) => {
						setColor(color);
						debouncedOnValueSelect(onValueSelect, `#${color}`);
					}}
					showHex={true}
					title={label}
					value={color}
				/>
			</div>
		</ClayForm.Group>
	);
}

ColorFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({label: PropTypes.string.isRequired})
		.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
