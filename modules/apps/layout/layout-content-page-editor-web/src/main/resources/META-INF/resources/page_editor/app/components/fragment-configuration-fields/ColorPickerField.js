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

import PropTypes from 'prop-types';
import React from 'react';

import {ColorPicker} from '../../../common/components/ColorPicker/ColorPicker';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {ColorPaletteField} from './ColorPaletteField';

export function ColorPickerField({field, onValueSelect, value}) {
	const {tokenValues} = useStyleBook();

	return Object.keys(tokenValues).length ? (
		<ColorPicker
			field={field}
			onValueSelect={onValueSelect}
			tokenValues={tokenValues}
			value={value}
		/>
	) : (
		<ColorPaletteField
			field={field}
			onValueSelect={(name, value) =>
				onValueSelect(name, value?.rgbValue ?? '')
			}
			value={value}
		/>
	);
}

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
