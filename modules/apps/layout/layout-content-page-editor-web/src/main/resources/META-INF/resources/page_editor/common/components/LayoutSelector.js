/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getPortletNamespace} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {config} from '../../app/config/index';
import itemSelectorValueToLayout from '../../app/utils/item_selector_value/itemSelectorValueToLayout';
import ItemSelector from './ItemSelector';

export function LayoutSelector({
	label = Liferay.Language.get('page'),
	mappedLayout,
	onLayoutSelect,
}) {
	const itemSelectorURL = useMemo(() => {
		if (mappedLayout?.layoutUuid) {
			const url = new URL(config.layoutItemSelectorURL);

			url.searchParams.set(
				`${getPortletNamespace(
					Liferay.PortletKeys.ITEM_SELECTOR
				)}layoutUuid`,
				mappedLayout.layoutUuid
			);

			return url.toString();
		}

		return config.layoutItemSelectorURL;
	}, [mappedLayout]);

	return (
		<ItemSelector
			eventName={`${config.portletNamespace}selectLayout`}
			itemSelectorURL={itemSelectorURL}
			label={label}
			onItemSelect={(layout) => onLayoutSelect(layout)}
			selectedItem={mappedLayout}
			showMappedItems={false}
			transformValueCallback={itemSelectorValueToLayout}
		/>
	);
}

LayoutSelector.propTypes = {
	mappedLayout: PropTypes.shape({
		layoutUuid: PropTypes.string,
		title: PropTypes.string.isRequired,
	}),
	onLayoutSelect: PropTypes.func.isRequired,
};
