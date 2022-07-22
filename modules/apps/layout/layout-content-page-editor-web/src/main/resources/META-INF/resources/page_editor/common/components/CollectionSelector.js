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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
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
	onPreventCollectionSelect,
	optionsMenuItems = DEFAULT_OPTION_MENU_ITEMS,
}) {
	const eventName = `${config.portletNamespace}selectInfoList`;

	const customCollectionSelectorURL = useCustomCollectionSelectorURL();

	const filterConfig = collectionItem?.config ?? {};

	const isPrefiltered = !!Object.keys(filterConfig).length;

	return (
		<>
			<ItemSelector
				className={classNames({'mb-0': isPrefiltered})}
				eventName={eventName}
				itemSelectorURL={
					customCollectionSelectorURL ||
					itemSelectorURL ||
					config.infoListSelectorURL
				}
				label={label}
				onItemSelect={onCollectionSelect}
				onPreventCollectionSelect={onPreventCollectionSelect}
				optionsMenuItems={optionsMenuItems}
				quickMappedInfoItems={
					config.selectedMappingTypes?.linkedCollection
				}
				selectedItem={collectionItem}
				showMappedItems={
					!!config.selectedMappingTypes?.linkedCollection
				}
				transformValueCallback={itemSelectorValueToCollection}
			/>

			{isPrefiltered && (
				<p className="text-info">
					<ClayIcon className="mr-2 mt-0" symbol="info-panel-open" />

					<span className="text-2">
						{Liferay.Language.get('collection-prefiltered')}
					</span>
				</p>
			)}
		</>
	);
}

CollectionSelector.propTypes = {
	collectionItem: PropTypes.shape({title: PropTypes.string}),
	label: PropTypes.string,
	onCollectionSelect: PropTypes.func.isRequired,
	onPreventCollectionSelect: PropTypes.func,
};
