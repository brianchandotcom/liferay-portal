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

import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import selectShowLayoutItemTopper from '../../selectors/selectShowLayoutItemTopper';
import {useSelector} from '../../store/index';
import TopperEmpty from '../TopperEmpty';
import Column from './Column';

const ColumnWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const showLayoutItemTopper = useSelector(selectShowLayoutItemTopper);

		const content = (
			<Column className="page-editor__col" item={item} ref={ref}>
				{children}
			</Column>
		);

		return showLayoutItemTopper ? (
			<TopperEmpty
				acceptDrop={[
					LAYOUT_DATA_ITEM_TYPES.dropZone,
					LAYOUT_DATA_ITEM_TYPES.fragment,
					LAYOUT_DATA_ITEM_TYPES.row
				]}
				item={item}
				layoutData={layoutData}
			>
				{() => content}
			</TopperEmpty>
		) : (
			content
		);
	}
);

export default ColumnWithControls;
