/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {config} from '../../config/index';
import itemSelectorValueToSiteNavigationMenuItem from '../../utils/item_selector_value/itemSelectorValueToSiteNavigationMenuItem';

export function NavigationMenuSelectorField({field, onValueSelect, value}) {
	const eventName = `${config.portletNamespace}selectSiteNavigationMenu`;

	const selectedValue = value
		? {
				...value,
				title:
					value.parentSiteNavigationMenuItemId &&
					value.parentSiteNavigationMenuItemId !== '0'
						? `... / ${value.title}`
						: value.title,
		  }
		: {
				title: config.isPrivateLayoutsEnabled
					? Liferay.Language.get('public-pages-hierarchy')
					: Liferay.Language.get('pages-hierarchy'),
		  };

	return (
		<ItemSelector
			eventName={eventName}
			helpText={field.description}
			itemSelectorURL={config.siteNavigationMenuItemSelectorURL}
			label={field.label}
			modalProps={{height: '60vh', size: 'lg'}}
			onItemSelect={(navigationMenu) => {
				onValueSelect(field.name, navigationMenu);
			}}
			selectedItem={selectedValue}
			showMappedItems={false}
			transformValueCallback={itemSelectorValueToSiteNavigationMenuItem}
		/>
	);
}

NavigationMenuSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
