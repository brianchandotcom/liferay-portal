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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useSelector} from '../../store/index';

const Row = React.forwardRef(({children, className, item, layoutData}, ref) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = item.config[selectedViewportSize]
		? item.config[selectedViewportSize]
		: item.config;

	const rowContent = (
		<div
			className={classNames(className, 'row', {
				'align-items-center': itemConfig.verticalAlignment === 'middle',
				'align-items-end': itemConfig.verticalAlignment === 'bottom',
				'align-items-start': itemConfig.verticalAlignment === 'top',
				empty: !item.children.some(
					(childId) => layoutData.items[childId].children.length
				),
				'flex-row-reverse': !(typeof itemConfig.reverseOrder ===
				'boolean'
					? !itemConfig.reverseOrder
					: LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[item.type]),
				'no-gutters': !(typeof itemConfig.gutters === 'boolean'
					? itemConfig.gutters
					: LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[item.type]),
			})}
			ref={ref}
		>
			{children}
		</div>
	);

	const masterLayoutData = useSelector((state) => state.masterLayoutData);

	const masterParent = useMemo(() => {
		const dropZone =
			masterLayoutData &&
			masterLayoutData.items[masterLayoutData.rootItems.dropZone];

		return dropZone ? getItemParent(dropZone, masterLayoutData) : undefined;
	}, [masterLayoutData]);

	const shouldAddContainer = useSelector(
		(state) => !getItemParent(item, state.layoutData) && !masterParent
	);

	return shouldAddContainer ? (
		<div className="container-fluid p-0">{rowContent}</div>
	) : (
		rowContent
	);
});

Row.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function getItemParent(item, itemLayoutData) {
	const parent = itemLayoutData.items[item.parentId];

	return parent &&
		(parent.type === LAYOUT_DATA_ITEM_TYPES.root ||
			parent.type === LAYOUT_DATA_ITEM_TYPES.fragmentDropZone)
		? getItemParent(parent, itemLayoutData)
		: parent;
}

export default Row;
