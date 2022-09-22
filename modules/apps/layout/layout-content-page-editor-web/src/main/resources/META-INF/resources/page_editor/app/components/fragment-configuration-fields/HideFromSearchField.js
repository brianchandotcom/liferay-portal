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

import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import updateItemConfig from '../../thunks/updateItemConfig';
import {CheckboxField} from './CheckboxField';

function getHiddenAncestorId(layoutData, item) {
	const parent = layoutData.items[item.parentId];

	if (!parent) {
		return null;
	}

	return parent.config.indexed === false
		? parent.itemId
		: getHiddenAncestorId(layoutData, parent);
}

export function HideFromSearchField({item}) {
	const dispatch = useDispatch();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const layoutData = useSelector((state) => state.layoutData);
	const selectItem = useSelectItem();

	if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
		return null;
	}

	const hiddenAncestorId = getHiddenAncestorId(layoutData, item);

	return (
		<>
			<CheckboxField
				disabled={Boolean(hiddenAncestorId)}
				field={{
					label: Liferay.Language.get(
						'hide-from-site-search-results'
					),
					name: 'indexed',
				}}
				onValueSelect={(name, value) => {
					const itemConfig = {[name]: !value};

					dispatch(
						updateItemConfig({
							itemConfig,
							itemId: item.itemId,
						})
					);
				}}
				title={
					hiddenAncestorId
						? Liferay.Language.get(
								'configuration-inherited-from-parent-fragment'
						  )
						: null
				}
				value={hiddenAncestorId || item.config.indexed === false}
			/>

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

HideFromSearchField.propTypes = {
	item: PropTypes.shape({
		config: PropTypes.object.isRequired,
		itemId: PropTypes.string.isRequired,
		parentId: PropTypes.string.isRequired,
	}).isRequired,
};
