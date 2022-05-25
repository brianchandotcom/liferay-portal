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

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config/index';
import itemSelectorValueToCategoryTreeNode from '../../utils/item-selector-value/itemSelectorValueToCategoryTreeNode';

export function CategoryTreeNodeSelectorField({field, onValueSelect, value}) {
	const eventName = `${config.portletNamespace}selectAssetCategoryTreeNode`;

	return (
		<ItemSelector
			eventName={eventName}
			helpText={field.description}
			itemSelectorURL={config.assetCategoryTreeNodeItemSelectorURL}
			label={field.label}
			modalProps={{height: '60vh', size: 'lg'}}
			onItemSelect={(categoryTreeNode) => {
				onValueSelect(field.name, categoryTreeNode);
			}}
			selectedItem={value}
			showMappedItems={false}
			transformValueCallback={itemSelectorValueToCategoryTreeNode}
		/>
	);
}

CategoryTreeNodeSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
