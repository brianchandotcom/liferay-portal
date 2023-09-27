/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ColorPicker,
	DEFAULT_TOKEN_LABEL,
	convertRGBtoHex,
} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {useStyleBook} from '../../../plugins/page_design_options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {useActiveItemId} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {getResetLabelByViewport} from '../../utils/getResetLabelByViewport';
import {ColorPaletteField} from './ColorPaletteField';

export function ColorPickerField({field, isCustomStyle, onValueSelect, value}) {
	const activeItemId = useActiveItemId();
	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const [computedValue, setComputedValue] = useState(null);
	const globalContext = useGlobalContext();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const {tokenValues} = useStyleBook();

	const restoreButtonLabel = useMemo(
		() => getResetLabelByViewport(selectedViewportSize),
		[selectedViewportSize]
	);

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
			activeItemId={activeItemId}
			canDetachTokenValues={canDetachTokenValues}
			defaultTokenLabel={
				computedValue ? computedValue : DEFAULT_TOKEN_LABEL
			}
			defaultTokenValue={computedValue}
			field={field}
			onValueSelect={onValueSelect}
			restoreButtonLabel={restoreButtonLabel}
			selectedViewportSize={selectedViewportSize}
			showLabel={isCustomStyle}
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
	isCustomStyle: PropTypes.bool.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
