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
import {closest} from 'metal-dom';
import React, {useRef, useEffect} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelector} from '../store/index';
import PageEditor from './PageEditor';
import UnsafeHTML from './UnsafeHTML';
import {Column, Container, Row} from './layout-data-items/index';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: Column,
	[LAYOUT_DATA_ITEM_TYPES.container]: Container,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneContainer,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Fragment,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: Row
};

export default function MasterPage() {
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const masterLayoutData = useSelector(state => state.masterLayoutData);
	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);

	const mainItem = masterLayoutData.items[masterLayoutData.rootItems.main];

	return (
		<div
			className={classNames('master-page', 'master-page--with-sidebar', {
				'master-page--with-sidebar-open': sidebarOpen
			})}
			id="master-layout"
		>
			<MasterLayoutDataItem
				fragmentEntryLinks={fragmentEntryLinks}
				item={mainItem}
				layoutData={masterLayoutData}
			/>
		</div>
	);
}

function MasterLayoutDataItem({fragmentEntryLinks, item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];

	if (!Component) {
		return null;
	}

	return (
		<Component
			fragmentEntryLinks={fragmentEntryLinks}
			item={item}
			layoutData={layoutData}
		>
			{item.children.map(childId => {
				return (
					<MasterLayoutDataItem
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

function DropZoneContainer() {
	return <PageEditor withinMasterPage />;
}

function Root({children}) {
	return <div className="pt-4">{children}</div>;
}

const FragmentContent = React.memo(function FragmentContent({content}) {
	const ref = useRef(null);

	useEffect(() => {
		const element = ref.current;

		if (!element) {
			return;
		}

		const handler = event => {
			const element = event.target;

			if (closest(element, '[href]')) {
				event.preventDefault();
			}
		};

		element.addEventListener('click', handler);

		return () => {
			element.removeEventListener('click', handler);
		};
	});

	return <UnsafeHTML markup={content} ref={ref} />;
});

function Fragment({fragmentEntryLinks, item}) {
	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	return (
		<FragmentContent content={fragmentEntryLink.content.value.content} />
	);
}
