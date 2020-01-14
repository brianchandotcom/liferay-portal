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

import FloatingToolbar from '../../components/FloatingToolbar';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import Topper from '../Topper';

const Row = React.forwardRef(({children, item, layoutData}, ref) => {
	const parent = layoutData.items[item.parentId];

	const rowContent = (
		<div className="page-editor__row-outline" ref={ref}>
			<div
				className={classNames('page-editor__row row', {
					empty: !item.children.some(
						childId => layoutData.items[childId].children.length
					),
					'no-gutters': !item.config.gutters
				})}
			>
				{children}
			</div>
		</div>
	);

	return (
		<Topper
			acceptDrop={[LAYOUT_DATA_ITEM_TYPES.column]}
			active
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('row')}
		>
			{() => (
				<>
					<FloatingToolbar
						buttons={[
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration
						]}
						item={item}
						itemRef={ref}
					/>

					{!parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
						<div className="container-fluid p-0">{rowContent}</div>
					) : (
						rowContent
					)}
				</>
			)}
		</Topper>
	);
});

export default Row;
