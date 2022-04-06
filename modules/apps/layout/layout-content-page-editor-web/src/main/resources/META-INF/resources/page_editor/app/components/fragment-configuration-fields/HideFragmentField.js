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
import PropTypes from 'prop-types';
import React from 'react';

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {CheckboxField} from './CheckboxField';

function getHiddenAncestorId(layoutData, item, selectedViewportSize) {
	const parent = layoutData.items[item.parentId];

	if (!parent) {
		return null;
	}

	const responsiveConfig = getResponsiveConfig(
		parent.config,
		selectedViewportSize
	);

	return responsiveConfig.styles.display === 'none'
		? parent.itemId
		: getHiddenAncestorId(layoutData, parent);
}

export function HideFragmentField({
	disabled,
	field,
	item,
	onValueSelect,
	title,
	value,
}) {
	const layoutData = useSelector((state) => state.layoutData);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const selectItem = useSelectItem();

	const hiddenAncestorId = getHiddenAncestorId(
		layoutData,
		item,
		selectedViewportSize
	);

	return (
		<>
			<CheckboxField
				disabled={Boolean(hiddenAncestorId) || disabled}
				field={field}
				onValueSelect={onValueSelect}
				title={title}
				value={value}
			/>

			{value === 'none' &&
				!hiddenAncestorId &&
				config.fragmentAdvancedOptionsEnabled && (
					<p className="small text-secondary">
						{Liferay.Language.get(
							'this-fragment-is-still-visible-on-search-.you-can-hide-it-from-search-in-advanced-tab'
						)}
					</p>
				)}

			{hiddenAncestorId && (
				<>
					<p className="m-0 small text-secondary">
						{Liferay.Language.get(
							'this-configuration-is-inherited'
						)}
					</p>

					<ClayButton
						className="p-0 page-editor__hide-feedback-button text-left"
						displayType="link"
						onClick={() => selectItem(hiddenAncestorId)}
					>
						{Liferay.Language.get('go-to-parent-fragment-to-edit')}
					</ClayButton>
				</>
			)}
		</>
	);
}

HideFragmentField.propTypes = {
	disabled: PropTypes.bool,
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
