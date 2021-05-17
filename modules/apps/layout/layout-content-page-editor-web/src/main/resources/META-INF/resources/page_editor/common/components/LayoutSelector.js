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
import React, {useMemo} from 'react';

import {config} from '../../app/config/index';
import itemSelectorValueToLayout from '../../app/utils/item-selector-value/itemSelectorValueToLayout';
import ItemSelector from './ItemSelector';

export const LayoutSelector = ({mappedLayout, onLayoutSelect}) => {
	const itemSelectorURL = useMemo(() => {
		if (mappedLayout?.layoutUuid) {
			const url = new URL(config.layoutItemSelectorURL);

			url.searchParams.set(
				`${Liferay.Util.getPortletNamespace(
					Liferay.PortletKeys.ITEM_SELECTOR
				)}layoutUuid`,
				mappedLayout.layoutUuid
			);

			return url.toString();
		}

		return config.layoutItemSelectorURL;
	}, [mappedLayout]);

	return (
		<div className="mb-3">
			<ItemSelector
				eventName={`${config.portletNamespace}selectLayout`}
				itemSelectorURL={itemSelectorURL}
				label={Liferay.Language.get('page')}
				onItemSelect={(layout) => onLayoutSelect(layout)}
				selectedItem={mappedLayout}
				showMappedItems={false}
				transformValueCallback={itemSelectorValueToLayout}
			/>
		</div>
	);
};

LayoutSelector.propTypes = {
	mappedLayout: PropTypes.shape({
		layoutUuid: PropTypes.string,
		title: PropTypes.string.isRequired,
	}),
	onLayoutSelect: PropTypes.func.isRequired,
};
