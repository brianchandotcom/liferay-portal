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
import {EDITABLE_TYPES} from '../../../app/config/constants/editableTypes';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import selectCanUpdateEditables from '../../../app/selectors/selectCanUpdateEditables';
import selectCanUpdateItemConfiguration from '../../../app/selectors/selectCanUpdateItemConfiguration';
import {useSelector} from '../../../app/store/index';
import getLayoutDataItemLabel from '../../../app/utils/getLayoutDataItemLabel';
import PageStructureSidebarSection from './PageStructureSidebarSection';
import StructureTreeNode from './StructureTreeNode';

const EDITABLE_TYPE_ICONS = {
	[EDITABLE_TYPES.backgroundImage]: 'picture',
	[EDITABLE_TYPES.html]: 'document-code',
	[EDITABLE_TYPES.image]: 'picture',
	[EDITABLE_TYPES.link]: 'link',
	[EDITABLE_TYPES['rich-text']]: 'text-editor',
	[EDITABLE_TYPES.text]: 'text',
};

const EDITABLE_TYPE_LABELS = {
	[EDITABLE_TYPES.backgroundImage]: Liferay.Language.get('image'),
	[EDITABLE_TYPES.html]: Liferay.Language.get('html'),
	[EDITABLE_TYPES.image]: Liferay.Language.get('image'),
	[EDITABLE_TYPES.link]: Liferay.Language.get('link'),
	[EDITABLE_TYPES['rich-text']]: Liferay.Language.get('rich-text'),
	[EDITABLE_TYPES.text]: Liferay.Language.get('text'),
};

const LAYOUT_DATA_ITEM_TYPE_ICONS = {
	[LAYOUT_DATA_ITEM_TYPES.collection]: 'list',
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: 'document',
	[LAYOUT_DATA_ITEM_TYPES.container]: 'container',
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.fragment]: 'code',
	[LAYOUT_DATA_ITEM_TYPES.root]: 'page',
	[LAYOUT_DATA_ITEM_TYPES.row]: 'grid',
};

export default function PageStructureSidebar() {
	const activeItemId = useActiveItemId();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const isMasterPage = config.layoutType === LAYOUT_TYPES.master;

	const data = masterLayoutData || layoutData;

	const nodes = useMemo(
		() =>
			visit(data.items[data.rootItems.main], data.items, {
				activeItemId,
				canUpdateEditables,
				canUpdateItemConfiguration,
				fragmentEntryLinks,
				isMasterPage,
				layoutData,
				masterLayoutData,
			}).children,
		[
			activeItemId,
			canUpdateEditables,
			canUpdateItemConfiguration,
			data.items,
			data.rootItems.main,
			fragmentEntryLinks,
			isMasterPage,
			layoutData,
			masterLayoutData,
		]
	);

	return (
		<PageStructureSidebarSection>
			<div className="page-editor__page-structure__structure-tree px-3">
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
		</PageStructureSidebarSection>
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

function visit(
	item,
	items,
	{
		activeItemId,
		canUpdateEditables,
		canUpdateItemConfiguration,
		fragmentEntryLinks,
		isMasterPage,
		layoutData,
		masterLayoutData,
	}
) {
	const children = [];

	const itemInMasterLayout =
		masterLayoutData &&
		Object.keys(masterLayoutData.items).includes(item.itemId);

	let icon = LAYOUT_DATA_ITEM_TYPE_ICONS[item.type];

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		const fragmentEntryLink =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		icon = fragmentEntryLink.icon || icon;

		const editables =
			fragmentEntryLink.editableValues[
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			] || {};

		const editableTypes = fragmentEntryLink.editableTypes;

		Object.keys(editables).forEach((editableId) => {
			const childId = `${item.config.fragmentEntryLinkId}-${editableId}`;
			const type = editableTypes[editableId] || EDITABLE_TYPES.text;

			children.push({
				activable: canUpdateEditables,
				children: [],
				disabled: !isMasterPage && itemInMasterLayout,
				expanded: childId === activeItemId,
				icon: EDITABLE_TYPE_ICONS[type],
				id: childId,
				name: EDITABLE_TYPE_LABELS[type],
				removable: false,
				type: ITEM_TYPES.editable,
			});
		});

		children.push(
			...item.children.map((childItemId) => ({
				...visit(items[childItemId], items, {
					activeItemId,
					canUpdateEditables,
					canUpdateItemConfiguration,
					fragmentEntryLinks,
					isMasterPage,
					layoutData,
					masterLayoutData,
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
						canUpdateEditables,
						canUpdateItemConfiguration,
						fragmentEntryLinks,
						isMasterPage,
						layoutData,
						masterLayoutData,
					}
				).children;

				children.push(...dropZoneChildren);
			}
			else {
				const child = visit(childItem, items, {
					activeItemId,
					canUpdateEditables,
					canUpdateItemConfiguration,
					fragmentEntryLinks,
					isMasterPage,
					layoutData,
					masterLayoutData,
				});

				children.push(child);
			}
		});
	}

	return {
		activable:
			item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			canUpdateItemConfiguration,
		children,
		disabled: !isMasterPage && itemInMasterLayout,
		expanded: item.itemId === activeItemId,
		icon,
		id: item.itemId,
		name: getLayoutDataItemLabel(item, fragmentEntryLinks),
		removable: !itemInMasterLayout && isRemovable(item, layoutData),
		type: ITEM_TYPES.layoutDataItem,
	};
}
