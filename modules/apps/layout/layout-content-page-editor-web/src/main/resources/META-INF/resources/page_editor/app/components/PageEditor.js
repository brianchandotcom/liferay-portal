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
import {useIsMounted} from 'frontend-js-react-web';
import React, {useEffect, useRef} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelector} from '../store/index';
import {useIsActive, useSelectItem} from './Controls';
import DragPreview from './DragPreview';
import {
	ColumnWithControls,
	ContainerWithControls,
	DropZoneWithControls,
	FragmentWithControls,
	Root,
	RowWithControls
} from './layout-data-items/index';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: ColumnWithControls,
	[LAYOUT_DATA_ITEM_TYPES.container]: ContainerWithControls,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneWithControls,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: FragmentWithControls,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: RowWithControls
};

export default function PageEditor({withinMasterPage = false}) {
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const layoutData = useSelector(state => state.layoutData);
	const selectItem = useSelectItem();
	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);

	const mainItem = layoutData.items[layoutData.rootItems.main];

	const onClick = event => {
		if (event.target === event.currentTarget) {
			selectItem(null, {multiSelect: event.shiftKey});
		}
	};

	return (
		<div
			className={classNames('page-editor', {
				'page-editor--with-sidebar': !withinMasterPage,
				'page-editor--with-sidebar-open':
					sidebarOpen && !withinMasterPage,
				'pt-4': !withinMasterPage
			})}
			id="page-editor"
			onClick={onClick}
		>
			<DragPreview />

			<LayoutDataItem
				fragmentEntryLinks={fragmentEntryLinks}
				item={mainItem}
				layoutData={layoutData}
			/>
		</div>
	);
}

function LayoutDataItem({fragmentEntryLinks, item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];
	const isActive = useIsActive()(item.itemId);
	const isMounted = useIsMounted();
	const componentRef = useRef(null);

	useEffect(() => {
		if (isActive && componentRef.current && isMounted()) {
			componentRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'nearest',
				inline: 'nearest'
			});
		}
	}, [componentRef, isActive, isMounted]);

	return (
		<Component item={item} layoutData={layoutData} ref={componentRef}>
			{item.children.map(childId => {
				return (
					<LayoutDataItem
						fragmentEntryLinks={fragmentEntryLinks}
						item={layoutData.items[childId]}
						key={childId}
						layoutData={layoutData}
					/>
				);
			})}
		</Component>
	);
}
