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
import React, {useCallback, useMemo, useState} from 'react';

import getAllEditables from '../../../../../app/components/fragment-content/getAllEditables';
import getAllPortals from '../../../../../app/components/layout-data-items/getAllPortals';
import hasDropZoneChild from '../../../../../app/components/layout-data-items/hasDropZoneChild';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../../../app/config/constants/layoutTypes';
import {config} from '../../../../../app/config/index';
import {useActiveItemId} from '../../../../../app/contexts/ControlsContext';
import {useSelector} from '../../../../../app/contexts/StoreContext';
import selectCanUpdateEditables from '../../../../../app/selectors/selectCanUpdateEditables';
import selectCanUpdateItemConfiguration from '../../../../../app/selectors/selectCanUpdateItemConfiguration';
import canActivateEditable from '../../../../../app/utils/canActivateEditable';
import {DragAndDropContextProvider} from '../../../../../app/utils/drag-and-drop/useDragAndDrop';
import isMapped from '../../../../../app/utils/editable-value/isMapped';
import isMappedToCollection from '../../../../../app/utils/editable-value/isMappedToCollection';
import getLayoutDataItemLabel from '../../../../../app/utils/getLayoutDataItemLabel';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';
import PageStructureSidebarSection from './PageStructureSidebarSection';
import StructureTreeNode from './StructureTreeNode';

const EDITABLE_TYPE_ICONS = {
	[EDITABLE_TYPES.backgroundImage]: 'picture',
	[EDITABLE_TYPES.html]: 'code',
	[EDITABLE_TYPES.image]: 'picture',
	[EDITABLE_TYPES.link]: 'link',
	[EDITABLE_TYPES['rich-text']]: 'text-editor',
	[EDITABLE_TYPES.text]: 'text',
};

const LAYOUT_DATA_ITEM_TYPE_ICONS = {
	[LAYOUT_DATA_ITEM_TYPES.collection]: 'list',
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: 'document',
	[LAYOUT_DATA_ITEM_TYPES.container]: 'container',
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.fragment]: 'code',
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.root]: 'page',
	[LAYOUT_DATA_ITEM_TYPES.row]: 'table',
};

function getCollectionAncestor(layoutData, itemId) {
	const item = layoutData.items[itemId];

	const parent = layoutData.items[item.parentId];

	if (!parent) {
		return null;
	}

	return parent.type === LAYOUT_DATA_ITEM_TYPES.collection
		? parent
		: getCollectionAncestor(layoutData, item.parentId);
}

export default function PageStructureSidebar() {
	const activeItemId = useActiveItemId();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const mappedInfoItems = useSelector((state) => state.mappedInfoItems);
	const mappingFields = useSelector((state) => state.mappingFields);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const [dragAndDropHoveredItemId, setDragAndDropHoveredItemId] = useState(
		null
	);

	const isMasterPage = config.layoutType === LAYOUT_TYPES.master;

	const data = masterLayoutData || layoutData;

	const onHoverNode = useCallback((itemId) => {
		setDragAndDropHoveredItemId(itemId);
	}, []);

	const nodes = useMemo(
		() =>
			visit(data.items[data.rootItems.main], data.items, {
				activeItemId,
				canUpdateEditables,
				canUpdateItemConfiguration,
				dragAndDropHoveredItemId,
				fragmentEntryLinks,
				isMasterPage,
				layoutData,
				mappedInfoItems,
				mappingFields,
				masterLayoutData,
				onHoverNode,
				selectedViewportSize,
			}).children,
		[
			activeItemId,
			canUpdateEditables,
			canUpdateItemConfiguration,
			data.items,
			data.rootItems.main,
			dragAndDropHoveredItemId,
			fragmentEntryLinks,
			isMasterPage,
			layoutData,
			mappedInfoItems,
			mappingFields,
			masterLayoutData,
			onHoverNode,
			selectedViewportSize,
		]
	);

	return (
		<PageStructureSidebarSection>
			<div className="page-editor__page-structure__structure-tree">
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
				<DragAndDropContextProvider>
					<Treeview
						NodeComponent={StructureTreeNode}
						nodes={nodes}
						selectedNodeIds={[activeItemId]}
					/>
				</DragAndDropContextProvider>
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
		dragAndDropHoveredItemId,
		fragmentEntryLinks,
		isMasterPage,
		layoutData,
		mappedInfoItems,
		mappingFields,
		masterLayoutData,
		onHoverNode,
		selectedViewportSize,
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

		const documentFragment = getDocumentFragment(fragmentEntryLink.content);

		const sortedElements = [
			...getAllEditables(documentFragment),
			...getAllPortals(documentFragment),
		].sort((a, b) => a.priority - b.priority);

		const editableTypes = fragmentEntryLink.editableTypes;

		let collectionAncestor = null;

		sortedElements.forEach((element) => {
			if (element.editableId) {
				const {editableId} = element;

				const editable =
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					][editableId];

				const childId = `${item.config.fragmentEntryLinkId}-${editableId}`;
				const type =
					editableTypes[editableId] || EDITABLE_TYPES.backgroundImage;

				if (!collectionAncestor) {
					collectionAncestor = isMappedToCollection(editable)
						? getCollectionAncestor(
								fragmentEntryLink.masterLayout
									? masterLayoutData
									: layoutData,
								item.itemId
						  )
						: null;
				}

				const collectionConfig = collectionAncestor?.config?.collection;

				const mappedFieldLabel = isMapped(editable)
					? getMappedFieldLabel(
							editable,
							collectionConfig,
							mappedInfoItems,
							mappingFields
					  )
					: null;

				children.push({
					activable:
						canUpdateEditables &&
						canActivateEditable(selectedViewportSize, type),
					children: [],
					disabled: !isMasterPage && itemInMasterLayout,
					dragAndDropHoveredItemId,
					draggable: false,
					expanded: childId === activeItemId,
					icon: EDITABLE_TYPE_ICONS[type],
					id: childId,
					itemType: ITEM_TYPES.editable,
					mapped: isMapped(editable),
					name: mappedFieldLabel || editableId,
					onHoverNode,
					parentId: item.parentId,
					removable: false,
				});
			}
			else {
				const {dropZoneId, mainItemId} = element;

				children.push({
					...visit(items[mainItemId], items, {
						activeItemId,
						canUpdateEditables,
						canUpdateItemConfiguration,
						dragAndDropHoveredItemId,
						fragmentEntryLinks,
						isMasterPage,
						layoutData,
						mappedInfoItems,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						selectedViewportSize,
					}),

					name: `${Liferay.Language.get('drop-zone')} ${dropZoneId}`,
					removable: false,
				});
			}
		});
	}
	else {
		item.children.forEach((childId) => {
			const childItem = items[childId];

			if (
				item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
				!item.config.collection
			) {
				return;
			}

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
						dragAndDropHoveredItemId,
						fragmentEntryLinks,
						isMasterPage,
						layoutData,
						mappedInfoItems,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						selectedViewportSize,
					}
				).children;

				children.push(...dropZoneChildren);
			}
			else {
				const child = visit(childItem, items, {
					activeItemId,
					canUpdateEditables,
					canUpdateItemConfiguration,
					dragAndDropHoveredItemId,
					fragmentEntryLinks,
					isMasterPage,
					layoutData,
					mappedInfoItems,
					mappingFields,
					masterLayoutData,
					onHoverNode,
					selectedViewportSize,
				});

				children.push(child);
			}
		});
	}

	return {
		activable:
			item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
			canUpdateItemConfiguration,
		children,
		disabled: !isMasterPage && itemInMasterLayout,
		draggable: true,
		expanded:
			item.itemId === activeItemId ||
			dragAndDropHoveredItemId === item.itemId,
		icon,
		id: item.itemId,
		itemType: ITEM_TYPES.layoutDataItem,
		name: getLayoutDataItemLabel(item, fragmentEntryLinks),
		onHoverNode,
		parentItemId: item.parentId,
		removable: !itemInMasterLayout && isRemovable(item, layoutData),
		type: item.type,
	};
}

function getDocumentFragment(content) {
	const fragment = document.createDocumentFragment();
	const div = document.createElement('div');

	div.innerHTML = content;

	return fragment.appendChild(div);
}

function getMappedFieldLabel(
	editable,
	collectionConfig,
	mappedInfoItems,
	mappingFields
) {
	const infoItem = mappedInfoItems.find(
		({classNameId, classPK}) =>
			editable.classNameId === classNameId && editable.classPK === classPK
	);

	const {selectedMappingTypes} = config;

	if (!infoItem && !selectedMappingTypes && !collectionConfig) {
		return null;
	}

	const key = collectionConfig
		? getMappingFieldsKey(
				collectionConfig.classNameId,
				collectionConfig.itemSubtype
		  )
		: editable.mappedField
		? getMappingFieldsKey(
				selectedMappingTypes.type.id,
				selectedMappingTypes.subtype.id || 0
		  )
		: getMappingFieldsKey(infoItem.classNameId, infoItem.classTypeId);

	const fields = mappingFields[key];

	if (fields) {
		const field = fields
			.flatMap((fieldSet) => fieldSet.fields)
			.find(
				(field) =>
					field.key ===
					(editable.mappedField ||
						editable.fieldId ||
						editable.collectionFieldId)
			);

		return field.label;
	}

	return null;
}
