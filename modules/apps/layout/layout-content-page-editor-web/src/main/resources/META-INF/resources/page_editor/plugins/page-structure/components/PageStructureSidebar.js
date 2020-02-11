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

import {Treeview} from 'frontend-js-components-web';
import React from 'react';

import {useActiveItemId} from '../../../app/components/Controls';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../../../app/config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/store/index';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import StructureTreeNode from './StructureTreeNode';

export default function PageStructureSidebar() {
	const activeItemId = useActiveItemId();

	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const layoutData = useSelector(state => state.layoutData);

	const getName = (item, fragmentEntryLinks) => {
		let name;

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
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
	};

	const visit = (item, items) => {
		const children = [];

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			const fragmentChildren =
				fragmentEntryLinks[item.config.fragmentEntryLinkId]
					.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

			Object.keys(fragmentChildren).forEach(editableId => {
				const childId = `${item.config.fragmentEntryLinkId}-${editableId}`;

				children.push({
					children: [],
					expanded: childId === activeItemId,
					id: childId,
					name: editableId,
					removable: false
				});
			});
		}
		else {
			item.children.forEach(childId => {
				const childItem = items[childId];

				const child = visit(childItem, items);

				children.push(child);
			});
		}

		const node = {
			children,
			expanded: item.itemId === activeItemId,
			id: item.itemId,
			name: getName(item, fragmentEntryLinks),
			removable: isRemovable(item, layoutData)
		};

		return node;
	};

	const nodes = visit(
		layoutData.items[layoutData.rootItems.main],
		layoutData.items
	).children;

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-structure')}
			</SidebarPanelHeader>

			<div className="page-editor__page-structure px-4">
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
	function hasDropZoneChildren(item, layoutData) {
		return item.children.some(childId => {
			const child = layoutData.items[childId];

			return child.type === LAYOUT_DATA_ITEM_TYPES.dropZone
				? true
				: hasDropZoneChildren(child, layoutData);
		});
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return false;
	}

	return !hasDropZoneChildren(item, layoutData);
}
