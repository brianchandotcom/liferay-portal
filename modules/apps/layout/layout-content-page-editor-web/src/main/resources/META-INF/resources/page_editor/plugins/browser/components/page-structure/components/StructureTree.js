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
import {FRAGMENT_ENTRY_TYPES} from '../../../../../app/config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../../../app/config/constants/layoutTypes';
import {config} from '../../../../../app/config/index';
import {
	useActiveItemId,
	useHoverItem,
} from '../../../../../app/contexts/ControlsContext';
import {useMovementTarget} from '../../../../../app/contexts/KeyboardMovementContext';
import {useSelector} from '../../../../../app/contexts/StoreContext';
import selectCanUpdateEditables from '../../../../../app/selectors/selectCanUpdateEditables';
import selectCanUpdateItemConfiguration from '../../../../../app/selectors/selectCanUpdateItemConfiguration';
import selectLayoutDataItemLabel from '../../../../../app/selectors/selectLayoutDataItemLabel';
import {selectPageContents} from '../../../../../app/selectors/selectPageContents';
import canActivateEditable from '../../../../../app/utils/canActivateEditable';
import {DragAndDropContextProvider} from '../../../../../app/utils/drag-and-drop/useDragAndDrop';
import isMapped from '../../../../../app/utils/editable-value/isMapped';
import isMappedToCollection from '../../../../../app/utils/editable-value/isMappedToCollection';
import {formIsMapped} from '../../../../../app/utils/formIsMapped';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';
import {getResponsiveConfig} from '../../../../../app/utils/getResponsiveConfig';
import getSelectedField from '../../../../../app/utils/getSelectedField';
import StructureTreeNode from './StructureTreeNode';

const EDITABLE_LABEL = {
	[EDITABLE_TYPES.backgroundImage]: Liferay.Language.get('background-image'),
	[EDITABLE_TYPES.html]: Liferay.Language.get('html'),
	[EDITABLE_TYPES.image]: Liferay.Language.get('image'),
	[EDITABLE_TYPES.link]: Liferay.Language.get('link'),
	[EDITABLE_TYPES['rich-text']]: Liferay.Language.get('rich-text'),
	[EDITABLE_TYPES.text]: Liferay.Language.get('text'),
};

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
	[LAYOUT_DATA_ITEM_TYPES.form]: 'container',
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.fragment]: 'code',
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.root]: 'page',
	[LAYOUT_DATA_ITEM_TYPES.row]: 'table',
};

export default function PageStructureSidebar() {
	const activeItemId = useActiveItemId();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const pageContents = useSelector(selectPageContents);
	const hoverItem = useHoverItem();

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

	const {itemId: keyboardMovementTargetId} = useMovementTarget();

	const nodes = useMemo(
		() =>
			visit(data.items[data.rootItems.main], data.items, {
				activeItemId,
				canUpdateEditables,
				canUpdateItemConfiguration,
				dragAndDropHoveredItemId,
				fragmentEntryLinks,
				isMasterPage,
				keyboardMovementTargetId,
				layoutData,
				mappingFields,
				masterLayoutData,
				onHoverNode,
				pageContents,
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
			keyboardMovementTargetId,
			layoutData,
			mappingFields,
			masterLayoutData,
			pageContents,
			onHoverNode,
			selectedViewportSize,
		]
	);

	const handleNodeFocus = () => {
		const focusedItem = document.activeElement?.querySelector(
			'[data-item-id]'
		);

		if (focusedItem) {
			hoverItem(focusedItem.dataset.itemId);
		}
	};

	return (
		<div
			className="overflow-auto page-editor__page-structure__structure-tree pt-4"
			onFocus={handleNodeFocus}
		>
			{!nodes.length && (
				<ClayAlert
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get('there-is-no-content-on-this-page')}
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
	);
}

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

function getDocumentFragment(content) {
	const fragment = document.createDocumentFragment();
	const div = document.createElement('div');

	div.innerHTML = content;

	return fragment.appendChild(div);
}

function getKey({collectionConfig, editable, infoItem, selectedMappingTypes}) {
	if (collectionConfig) {
		if (collectionConfig.classNameId) {
			return getMappingFieldsKey(
				collectionConfig.classNameId,
				collectionConfig.classPK
			);
		}
		else {
			return collectionConfig.key;
		}
	}
	else if (editable.mappedField) {
		return getMappingFieldsKey(
			selectedMappingTypes.type.id,
			selectedMappingTypes.subtype.id || 0
		);
	}
	else if (!infoItem) {
		return null;
	}

	return getMappingFieldsKey(infoItem.classNameId, infoItem.classTypeId);
}

function getMappedFieldLabel(
	editable,
	collectionConfig,
	pageContents,
	mappingFields
) {
	const infoItem = pageContents.find(
		({classNameId, classPK}) =>
			editable.classNameId === classNameId && editable.classPK === classPK
	);

	const {selectedMappingTypes} = config;

	if (!infoItem && !selectedMappingTypes && !collectionConfig) {
		for (const [mappingFieldsKey, fields] of Object.entries(
			mappingFields
		)) {
			if (mappingFieldsKey.startsWith(editable.classNameId)) {
				const field = getSelectedField({
					fields,
					mappingFieldsKey,
					value:
						editable.mappedField ||
						editable.fieldId ||
						editable.collectionFieldId,
				});

				return field?.label;
			}
		}

		return null;
	}

	const key = getKey({
		collectionConfig,
		editable,
		infoItem,
		selectedMappingTypes,
	});
	const fields = mappingFields[key];

	if (fields) {
		const field = getSelectedField({
			fields,
			mappingFieldsKey: key,
			value:
				editable.mappedField ||
				editable.fieldId ||
				editable.collectionFieldId,
		});

		return field?.label;
	}

	return null;
}

function getNameInfo(item) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.container &&
		item.config.htmlTag !== 'div'
	) {
		return item.config.htmlTag;
	}

	return null;
}

function fragmentIsMapped(item, fragmentEntryLinks) {
	if (item.type === LAYOUT_DATA_ITEM_TYPES.form) {
		return formIsMapped(item);
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		const {editableValues, fragmentEntryType} = fragmentEntryLinks[
			item.config.fragmentEntryLinkId
		];

		return fragmentEntryType === FRAGMENT_ENTRY_TYPES.input
			? Boolean(
					editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]
						?.inputFieldId
			  )
			: false;
	}

	return false;
}

function isItemHidden(item, selectedViewportSize) {
	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	return responsiveConfig.styles.display === 'none';
}

function isHidable(item, fragmentEntryLinks, layoutData) {
	if (!isRemovable(item, layoutData)) {
		return false;
	}

	if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
		return true;
	}

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	return fragmentEntryLink.fragmentEntryType !== FRAGMENT_ENTRY_TYPES.input;
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
		hasHiddenAncestor,
		isMasterPage,
		keyboardMovementTargetId,
		layoutData,
		mappingFields,
		masterLayoutData,
		onHoverNode,
		pageContents,
		selectedViewportSize,
	}
) {
	const children = [];

	const itemInMasterLayout =
		masterLayoutData &&
		Object.keys(masterLayoutData.items).includes(item.itemId);

	const hidden = isItemHidden(item, selectedViewportSize);

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
					]?.[editableId];

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
							pageContents,
							mappingFields
					  )
					: null;

				children.push({
					activable:
						canUpdateEditables &&
						canActivateEditable(selectedViewportSize, type),
					children: [],
					dragAndDropHoveredItemId,
					draggable: false,
					expanded: childId === activeItemId,
					hidable: false,
					hidden: false,
					hiddenAncestor: hasHiddenAncestor || hidden,
					icon: EDITABLE_TYPE_ICONS[type],
					id: childId,
					isMasterItem: !isMasterPage && itemInMasterLayout,
					itemType: ITEM_TYPES.editable,
					mapped: isMapped(editable),
					name: mappedFieldLabel || editableId,
					onHoverNode,
					parentId: item.parentId,
					removable: false,
					tooltipTitle: EDITABLE_LABEL[type],
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
						hasHiddenAncestor: hasHiddenAncestor || hidden,
						isMasterPage,
						layoutData,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						pageContents,
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
			if (
				(item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
					!item.config.collection) ||
				(item.type === LAYOUT_DATA_ITEM_TYPES.form &&
					!formIsMapped(item))
			) {
				return;
			}

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
						dragAndDropHoveredItemId,
						fragmentEntryLinks,
						hasHiddenAncestor: hasHiddenAncestor || hidden,
						isMasterPage,
						keyboardMovementTargetId,
						layoutData,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						pageContents,
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
					hasHiddenAncestor: hasHiddenAncestor || hidden,
					isMasterPage,
					keyboardMovementTargetId,
					layoutData,
					mappingFields,
					masterLayoutData,
					onHoverNode,
					pageContents,
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
		draggable: true,
		expanded:
			item.itemId === activeItemId ||
			item.itemId === dragAndDropHoveredItemId ||
			item.itemId === keyboardMovementTargetId,
		hidable:
			!itemInMasterLayout &&
			isHidable(item, fragmentEntryLinks, layoutData),
		hidden,
		hiddenAncestor: hasHiddenAncestor,
		icon,
		id: item.itemId,
		isMasterItem: !isMasterPage && itemInMasterLayout,
		itemType: ITEM_TYPES.layoutDataItem,
		mapped: fragmentIsMapped(item, fragmentEntryLinks),
		name: selectLayoutDataItemLabel({fragmentEntryLinks}, item),
		nameInfo: getNameInfo(item),
		onHoverNode,
		parentItemId: item.parentId,
		removable: !itemInMasterLayout && isRemovable(item, layoutData),
		tooltipTitle: selectLayoutDataItemLabel({fragmentEntryLinks}, item, {
			useCustomName: false,
		}),
		type: item.type,
	};
}
