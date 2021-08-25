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

import {config} from '../../app/config/index';
import {useCustomCollectionSelectorURL} from '../../app/contexts/CollectionItemContext';
import itemSelectorValueToCollection from '../../app/utils/item-selector-value/itemSelectorValueToCollection';
import ItemSelector from './ItemSelector';

const DEFAULT_OPTION_MENU_ITEMS = [];

export default function CollectionSelector({
	collectionItem,
	itemSelectorURL,
	label,
	onCollectionSelect,
	optionsMenuItems = DEFAULT_OPTION_MENU_ITEMS,
	shouldPreventCollectionSelect,
}) {
	const eventName = `${config.portletNamespace}selectInfoList`;

	const customCollectionSelectorURL = useCustomCollectionSelectorURL();

	return (
		<ItemSelector
			eventName={eventName}
			itemSelectorURL={
				customCollectionSelectorURL ||
				itemSelectorURL ||
				config.infoListSelectorURL
			}
			label={label}
			onItemSelect={onCollectionSelect}
			optionsMenuItems={optionsMenuItems}
			quickMappedInfoItems={config.selectedMappingTypes?.linkedCollection}
			selectedItem={collectionItem}
			shouldPreventItemSelect={shouldPreventCollectionSelect}
			showMappedItems={!!config.selectedMappingTypes?.linkedCollection}
			transformValueCallback={itemSelectorValueToCollection}
		/>
	);
}

CollectionSelector.propTypes = {
	collectionItem: PropTypes.shape({title: PropTypes.string}),
	label: PropTypes.string,
	onCollectionSelect: PropTypes.func.isRequired,
	shouldPreventCollectionSelect: PropTypes.func,
};
