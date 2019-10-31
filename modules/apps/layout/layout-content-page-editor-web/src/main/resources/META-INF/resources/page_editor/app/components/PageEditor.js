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
import React, {useContext} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {StoreContext} from '../store/index';
import UnsafeHTML from './UnsafeHTML';

export default function PageEditor() {
	const {layoutData} = useContext(StoreContext);
	const mainItem = layoutData.items[layoutData.rootItems.main];

	return <LayoutDataItem item={mainItem} layoutData={layoutData} />;
}

function LayoutDataItem({item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];

	return (
		<Component item={item}>
			{item.children.map(childId => {
				return (
					<LayoutDataItem
						item={layoutData.items[childId]}
						key={childId}
						layoutData={layoutData}
					/>
				);
			})}
		</Component>
	);
}

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: Column,
	[LAYOUT_DATA_ITEM_TYPES.container]: Container,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Fragment,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: Row
};

function Column({children, item}) {
	const {size} = item.config;

	return (
		<div className={classNames('col', {[`col-${size}`]: size})}>
			{children}
		</div>
	);
}

function Container({children, item}) {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingHorizontal,
		paddingVertical,
		type
	} = item.config;

	return (
		<div
			className={classNames(`py-${paddingVertical}`, {
				[`bg-${backgroundColorCssClass}`]: Boolean(
					backgroundColorCssClass
				),
				container: type === 'fixed',
				'container-fluid': type === 'fluid',
				[`px-${paddingHorizontal}`]: paddingHorizontal !== 3
			})}
			style={
				backgroundImage
					? {
							backgroundImage: `url(${backgroundImage})`,
							backgroundPosition: '50% 50%',
							backgroundRepeat: 'no-repeat',
							backgroundSize: 'cover'
					  }
					: {}
			}
		>
			{children}
		</div>
	);
}

function Fragment({item}) {
	const {fragmentEntryLinks} = useContext(StoreContext);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	return (
		<UnsafeHTML className="fragment" markup={fragmentEntryLink.content} />
	);
}

function Root({children}) {
	return <>{children}</>;
}

function Row({children, item}) {
	const {layoutData} = useContext(StoreContext);

	const parent = Object.values(layoutData.items).find(parent =>
		parent.children.includes(item.itemId)
	);

	const rowContent = (
		<div
			className={classNames('row', {
				empty: !item.children.some(
					childId => layoutData.items[childId].children.length
				),
				'no-gutters': !item.config.gutters
			})}
		>
			{children}
		</div>
	);

	return parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
		<div className="container-fluid p-0">{rowContent}</div>
	) : (
		rowContent
	);
}
