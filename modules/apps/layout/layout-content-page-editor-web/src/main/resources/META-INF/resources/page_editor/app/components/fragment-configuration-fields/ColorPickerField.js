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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import ColorPicker from '../../../common/components/ColorPicker';
import useControlledState from '../../../core/hooks/useControlledState';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config/index';
import {ColorPaletteField} from './ColorPaletteField';

const COLOR_PICKER_TYPE = 'ColorPicker';

export const ColorPickerField = ({field, onValueSelect, value}) => {
	const {tokenValues} = useStyleBook();
	const [color, setColor] = useControlledState(tokenValues[value]?.value);
	let colors = {};

	if (config.tokenOptimizationEnabled) {
		Object.values(tokenValues)
			.filter((token) => token.editorType === COLOR_PICKER_TYPE)
			.forEach(
				({
					label,
					name,
					tokenCategoryLabel: category,
					tokenSetLabel: tokenSet,
					value,
				}) => {
					const color = {label, name, value};

					if (Object.keys(colors).includes(category)) {
						if (Object.keys(colors[category]).includes(tokenSet)) {
							colors[category][tokenSet].push(color);
						}
						else {
							colors[category][tokenSet] = [color];
						}
					}
					else {
						colors[category] = {[tokenSet]: [color]};
					}
				}
			);
	}
	else {
		colors = Object.values(tokenValues)
			.filter((token) => token.editorType === COLOR_PICKER_TYPE)
			.map((token) => ({
				label: token.label,
				name: token.name,
				value: token.value,
			}));
	}

	if (!Object.keys(colors).length) {
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
					<ColorPicker
						colors={colors}
						onValueChange={({name, value}) => {
							setColor(value);

							onValueSelect(field.name, name);
						}}
						showHex={false}
						value={color}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append>
					<ClayInput
						readOnly
						value={
							tokenValues[value]
								? tokenValues[value].label
								: Liferay.Language.get('default')
						}
					/>
				</ClayInput.GroupItem>
				{color && (
					<ClayButtonWithIcon
						className="ml-2"
						displayType="secondary"
						onClick={() => {
							setColor('');

							onValueSelect(field.name, '');
						}}
						small
						symbol="times-circle"
						title={Liferay.Language.get('clear-selection')}
					/>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
