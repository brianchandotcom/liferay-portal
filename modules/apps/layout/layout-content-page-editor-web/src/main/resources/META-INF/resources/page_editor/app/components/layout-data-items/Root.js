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

import classNames from 'classnames';
import React from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes
} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import TopperEmpty from '../TopperEmpty';

const Root = React.forwardRef(({children, item, layoutData}, ref) => {
	return (
		<TopperEmpty
			acceptDrop={[
				LAYOUT_DATA_ITEM_TYPES.container,
				LAYOUT_DATA_ITEM_TYPES.dropZone,
				LAYOUT_DATA_ITEM_TYPES.fragment,
				LAYOUT_DATA_ITEM_TYPES.row
			]}
			item={item}
			layoutData={layoutData}
		>
			{({canDrop, isOver}) => (
				<div className={classNames('page-editor__root')} ref={ref}>
					{React.Children.count(children) ? (
						children
					) : (
						<div
							className={classNames(
								'page-editor__no-fragments-message',
								'taglib-empty-result-message',
								{
									'page-editor__no-fragments-message--active':
										isOver && canDrop
								}
							)}
						>
							<div className="taglib-empty-result-message-header"></div>
							<div className="text-center text-muted">
								{Liferay.Language.get('place-fragments-here')}
							</div>
						</div>
					)}
				</div>
			)}
		</TopperEmpty>
	);
});

Root.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired
};

export default Root;
