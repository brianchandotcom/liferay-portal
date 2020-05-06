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

import ClayAlert from '@clayui/alert';
import {Treeview} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {useActiveItemId} from '../../../app/components/Controls';
import hasDropZoneChild from '../../../app/components/layout-data-items/hasDropZoneChild';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../../../app/config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {PAGE_TYPES} from '../../../app/config/constants/pageTypes';
import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/store/index';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import StructureTreeNode from './StructureTreeNode';

export default function PageStructureSidebar() {
	const activeItemId = useActiveItemId();
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector((state) => state.masterLayoutData);
	const state = useSelector((state) => state);

	const isMasterPage = config.pageType === PAGE_TYPES.master;

	const data = masterLayoutData || layoutData;

	const nodes = useMemo(
		() =>
			visit(data.items[data.rootItems.main], data.items, {
				activeItemId,
				isMasterPage,
				state,
			}).children,
		[data, activeItemId, isMasterPage, state]
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-structure')}
			</SidebarPanelHeader>

			<div className="page-editor__page-structure px-4">
				{!nodes.length && (
					<ClayAlert
						displayType="info"
						title={Liferay.Language.get('info')}
					>
						{Liferay.Language.get(
							'there-is-no-content-on-this-page'
						)}
					</ClayAlert>
				)}
				<Treeview
					NodeComponent={StructureTreeNode}
					nodes={nodes}
					selectedNodeIds={[activeItemId]}
				/>
			</div>
		</>
	);
}

function isRemovable(item, layoutData) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column ||
		item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem
	) {
		return false;
	}

	return !hasDropZoneChild(item, layoutData);
}

function getName(item, fragmentEntryLinks) {
	let name;

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.collection;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.collectionItem;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.container;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.column;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.dropZone;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.row;
	}

	return name;
}

function visit(item, items, {activeItemId, isMasterPage, state}) {
	const children = [];
	const {fragmentEntryLinks, layoutData, masterLayoutData} = state;

	const itemInMasterLayout =
		masterLayoutData &&
		Object.keys(masterLayoutData.items).includes(item.itemId);

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		const fragmentEntryLink =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		const editables =
			fragmentEntryLink.editableValues[
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			] || {};

		Object.keys(editables).forEach((editableId) => {
			const childId = `${item.config.fragmentEntryLinkId}-${editableId}`;

			children.push({
				activable: true,
				children: [],
				disabled: !isMasterPage && itemInMasterLayout,
				expanded: childId === activeItemId,
				id: childId,
				name: editableId,
				removable: false,
				type: ITEM_TYPES.editable,
			});
		});

		children.push(
			...item.children.map((childItemId) => ({
				...visit(items[childItemId], items, {
					activeItemId,
					isMasterPage,
					state,
				}),

				name: Liferay.Language.get('drop-zone'),
				removable: false,
			}))
		);
	}
	else {
		item.children.forEach((childId) => {
			const childItem = items[childId];

			if (
				!isMasterPage &&
				childItem.type === LAYOUT_DATA_ITEM_TYPES.dropZone
			) {
				const dropZoneChildren = visit(
					layoutData.items[layoutData.rootItems.main],
					layoutData.items,
					{
						activeItemId,
						isMasterPage,
						state,
					}
				).children;

				children.push(...dropZoneChildren);
			}
			else {
				const child = visit(childItem, items, {
					activeItemId,
					isMasterPage,
					state,
				});

				children.push(child);
			}
		});
	}

	const node = {
		activable:
			layoutData.items[item.itemId] &&
			layoutData.items[item.itemId].type !==
				LAYOUT_DATA_ITEM_TYPES.column &&
			layoutData.items[item.itemId].type !==
				LAYOUT_DATA_ITEM_TYPES.collectionItem,
		children,
		disabled: !isMasterPage && itemInMasterLayout,
		expanded: item.itemId === activeItemId,
		id: item.itemId,
		name: getName(item, fragmentEntryLinks),
		removable: !itemInMasterLayout && isRemovable(item, layoutData),
		type: ITEM_TYPES.layoutDataItem,
	};

	return node;
}
