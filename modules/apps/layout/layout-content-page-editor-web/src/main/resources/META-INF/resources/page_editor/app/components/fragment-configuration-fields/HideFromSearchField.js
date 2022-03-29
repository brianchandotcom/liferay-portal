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

import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import updateItemConfig from '../../thunks/updateItemConfig';
import {CheckboxField} from './CheckboxField';

function hasHiddenAncestor(layoutData, item) {
	const parent = layoutData.items[item.parentId];

	if (!parent) {
		return false;
	}

	return (
		parent.config.indexed === false || hasHiddenAncestor(layoutData, parent)
	);
}

export function HideFromSearchField({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const layoutData = useSelector((state) => state.layoutData);

	const hiddenAncestor = hasHiddenAncestor(layoutData, item);

	return (
		<CheckboxField
			disabled={hiddenAncestor}
			field={{
				label: Liferay.Language.get('hide-from-site-search-results'),
				name: 'indexed',
			}}
			onValueSelect={(name, value) => {
				const itemConfig = {[name]: !value};

				dispatch(
					updateItemConfig({
						itemConfig,
						itemId: item.itemId,
						segmentsExperienceId,
					})
				);
			}}
			title={
				hiddenAncestor
					? Liferay.Language.get(
							'configuration-inherited-from-parent-fragment'
					  )
					: null
			}
			value={hiddenAncestor || item.config.indexed === false}
		/>
	);
}

HideFromSearchField.propTypes = {
	item: PropTypes.shape({
		config: PropTypes.object.isRequired,
		itemId: PropTypes.string.isRequired,
		parentId: PropTypes.string.isRequired,
	}).isRequired,
};
