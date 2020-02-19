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
import React from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes
} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

const Row = React.forwardRef(({children, className, item, layoutData}, ref) => {
	const {gutters} = {
		...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[LAYOUT_DATA_ITEM_TYPES.row],
		...item.config
	};
	const parent = layoutData.items[item.parentId];

	const rowContent = (
		<div
			className={classNames(className, 'row', {
				empty: !item.children.some(
					childId => layoutData.items[childId].children.length
				),
				'no-gutters': !gutters
			})}
			ref={ref}
		>
			{children}
		</div>
	);

	return (
		<>
			{!parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
				<div className="container-fluid p-0">{rowContent}</div>
			) : (
				rowContent
			)}
		</>
	);
});

Row.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool})
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired
};

export default Row;
