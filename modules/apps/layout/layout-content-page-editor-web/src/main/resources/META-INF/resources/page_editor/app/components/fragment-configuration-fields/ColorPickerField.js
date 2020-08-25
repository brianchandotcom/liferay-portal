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

import ClayButton from '@clayui/button';
import ClayColorPicker from '@clayui/color-picker';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config/index';
import {ColorPaletteField} from './ColorPaletteField';

const COLOR_PICKER_TYPE = 'ColorPicker';

export const ColorPickerField = ({field, onValueSelect, value}) => {
	const frontendTokens = config.frontendTokens;

	const [color, setColor] = useControlledState(frontendTokens[value]?.value);

	const colorTokens = Object.values(frontendTokens).filter(
		(token) => token.editorType === COLOR_PICKER_TYPE
	);

	const colorsToNames = colorTokens.reduce((acc, token) => {
		const tokenValue = token.value.replace('#', '');
		acc[tokenValue] = token.name;

		return acc;
	}, {});

	const colors = colorTokens.map((token) => token.value.replace('#', ''));

	if (!colors.length) {
		return (
			<ColorPaletteField
				field={field}
				onValueSelect={(name, value) =>
					onValueSelect(name, value?.rgbValue ?? '')
				}
				value={value}
			/>
		);
	}

	return (
		<ClayForm.Group small>
			<label>{field.label}</label>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ClayColorPicker
						colors={colors}
						onValueChange={(nextColor) => {
							setColor(nextColor);

							onValueSelect(field.name, colorsToNames[nextColor]);
						}}
						showHex={false}
						value={color}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append>
					<ClayInput
						readOnly
						value={
							frontendTokens[value]
								? frontendTokens[value].label
								: Liferay.Language.get('default')
						}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<ClayButton
				className="mt-2"
				displayType="secondary"
				onClick={() => {
					setColor('');

					onValueSelect(field.name, '');
				}}
			>
				{Liferay.Language.get('clear')}
			</ClayButton>
		</ClayForm.Group>
	);
};

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
