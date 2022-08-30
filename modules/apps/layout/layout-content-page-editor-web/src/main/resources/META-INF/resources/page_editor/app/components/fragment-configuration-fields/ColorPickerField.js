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
import React, {useEffect, useState} from 'react';

import {
	ColorPicker,
	DEFAULT_TOKEN_LABEL,
} from '../../../common/components/ColorPicker/ColorPicker';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useActiveItemId} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import {convertRGBtoHex} from '../../utils/convertRGBtoHex';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {ColorPaletteField} from './ColorPaletteField';

export function ColorPickerField({field, onValueSelect, value}) {
	const activeItemId = useActiveItemId();
	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const [computedValue, setComputedValue] = useState(null);
	const globalContext = useGlobalContext();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const {tokenValues} = useStyleBook();

	useEffect(() => {
		if (!field.cssProperty) {
			setComputedValue(null);

			return;
		}

		if (value) {
			setComputedValue(null);

			return;
		}

		const element = globalContext.document.querySelector(
			`.${getLayoutDataItemUniqueClassName(activeItemId)}`
		);

		if (!element) {
			setComputedValue(null);

			return;
		}

		const propertyValue = globalContext.window
			.getComputedStyle(element)
			.getPropertyValue(field.cssProperty);

		setComputedValue(propertyValue ? convertRGBtoHex(propertyValue) : null);
	}, [activeItemId, field.cssProperty, globalContext, value]);

	return Object.keys(tokenValues).length ? (
		<ColorPicker
			canDetachTokenValues={canDetachTokenValues}
			defaultTokenLabel={
				computedValue
					? `${DEFAULT_TOKEN_LABEL} · ${computedValue}`
					: DEFAULT_TOKEN_LABEL
			}
			defaultTokenValue={computedValue}
			field={field}
			onValueSelect={onValueSelect}
			selectedViewportSize={selectedViewportSize}
			showLabel={false}
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
