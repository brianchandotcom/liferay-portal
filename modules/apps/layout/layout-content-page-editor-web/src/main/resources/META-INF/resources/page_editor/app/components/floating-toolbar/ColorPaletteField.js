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

import ClayForm from '@clayui/form';
import React from 'react';

import ColorPalette from '../../../common/components/ColorPalette';

export const ColorPaletteField = ({field, onValueSelect, value}) => (
	<ClayForm.Group>
		<ColorPalette
			label={field.label}
			onColorSelect={(color, event) => {
				onValueSelect(field.name, {
					cssClass: color,
					rgbValue: getComputedStyle(event.target).backgroundColor
				});
			}}
			selectedColor={value && value.cssClass}
		/>
	</ClayForm.Group>
);
