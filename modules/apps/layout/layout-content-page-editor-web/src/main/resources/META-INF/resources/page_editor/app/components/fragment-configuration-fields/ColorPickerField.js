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
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {ColorPaletteField} from './ColorPaletteField';

export function ColorPickerField({field, onValueSelect, value}) {
	const activeItemId = useActiveItemId();
	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const globalContext = useGlobalContext();
	const {tokenValues} = useStyleBook();

	const [defaultTokenLabel, setDefaultTokenLabel] = useState(
		DEFAULT_TOKEN_LABEL
	);

	useEffect(() => {
		if (!field.cssProperty) {
			setDefaultTokenLabel(DEFAULT_TOKEN_LABEL);

			return;
		}

		if (value) {
			setDefaultTokenLabel(DEFAULT_TOKEN_LABEL);

			return;
		}

		const element = globalContext.document.querySelector(
			`.${getLayoutDataItemUniqueClassName(activeItemId)}`
		);

		if (!element) {
			setDefaultTokenLabel(DEFAULT_TOKEN_LABEL);

			return;
		}

		const computedValue = globalContext.window
			.getComputedStyle(element)
			.getPropertyValue(field.cssProperty);

		if (!computedValue) {
			setDefaultTokenLabel(DEFAULT_TOKEN_LABEL);

			return;
		}

		setDefaultTokenLabel(`${DEFAULT_TOKEN_LABEL} · ${computedValue}`);
	}, [activeItemId, field.cssProperty, globalContext, value]);

	return Object.keys(tokenValues).length ? (
		<ColorPicker
			canDetachTokenValues={canDetachTokenValues}
			defaultTokenLabel={defaultTokenLabel}
			field={field}
			onValueSelect={onValueSelect}
			showLabel={!Liferay.FeatureFlags['LPS-143206']}
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
