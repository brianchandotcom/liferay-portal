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
import ManageAllowedFragmentButton from '../ManageAllowedFragmentButton';
import Topper from '../Topper';

const DropZoneWithControls = React.forwardRef(({item, layoutData}, ref) => {
	const showLayoutItemTopper = useSelector(selectShowLayoutItemTopper);

	const content = (
		<div className="page-editor__drop-zone" ref={ref}>
			<p>{Liferay.Language.get('drop-zone')}</p>

			<p>
				{Liferay.Language.get(
					'fragments-and-widgets-for-pages-based-on-this-master-will-be-placed-here'
				)}
			</p>

			<ManageAllowedFragmentButton item={item} />
		</div>
	);

	return showLayoutItemTopper ? (
		<Topper
			acceptDrop={[LAYOUT_DATA_ITEM_TYPES.fragment]}
			active
			item={item}
			layoutData={layoutData}
		>
			{() => content}
		</Topper>
	) : (
		content
	);
});

export default DropZoneWithControls;
