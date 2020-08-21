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
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config/index';

const COLOR_PICKER_TYPE = 'ColorPicker';

export const ColorPickerField = ({field, onValueSelect, value}) => {
	const frontendTokens = config.frontendTokens;

	const [color, setColor] = useControlledState(frontendTokens[value]?.value);

	const colorsToNames = Object.values(frontendTokens)
		.filter((token) => token.editorType === COLOR_PICKER_TYPE)
		.reduce((acc, token) => {
			const tokenValue = token.value.replace('#', '');
			acc[tokenValue] = token.name;

			return acc;
		}, {});

	return (
		<ClayForm.Group small>
			<label>{field.label}</label>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ClayColorPicker
						colors={Object.keys(colorsToNames)}
						onValueChange={(nextColor) => {
							setColor(nextColor);

							onValueSelect(field.name, colorsToNames[nextColor]);
						}}
						showHex={false}
						value={color}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append>
					<ClayInput readOnly value={frontendTokens[value]?.label} />
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
