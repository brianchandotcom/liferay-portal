/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import getAllEditables from '../../../../../app/components/fragment_content/getAllEditables';
import {fromControlsId} from '../../../../../app/components/layout_data_items/Collection';
import getAllPortals from '../../../../../app/components/layout_data_items/getAllPortals';
import hasDropZoneChild from '../../../../../app/components/layout_data_items/hasDropZoneChild';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPE_LABELS} from '../../../../../app/config/constants/editableTypeLabels';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import {FRAGMENT_ENTRY_TYPES} from '../../../../../app/config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
} from '../../../../../app/config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../../../app/config/constants/layoutTypes';
import {config} from '../../../../../app/config/index';
import {
	useActiveItemId,
	useHoverItem,
	useHoveredItemId,
} from '../../../../../app/contexts/ControlsContext';
import {useMovementTarget} from '../../../../../app/contexts/KeyboardMovementContext';
import {
	useDispatch,
	useSelector,
	useSelectorRef,
} from '../../../../../app/contexts/StoreContext';
import selectCanUpdateEditables from '../../../../../app/selectors/selectCanUpdateEditables';
import selectCanUpdateItemConfiguration from '../../../../../app/selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../../../../app/selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../../../../app/selectors/selectLayoutDataItemLabel';
import {selectPageContents} from '../../../../../app/selectors/selectPageContents';
import canActivateEditable from '../../../../../app/utils/canActivateEditable';
import {DragAndDropContextProvider} from '../../../../../app/utils/drag_and_drop/useDragAndDrop';
import isMapped from '../../../../../app/utils/editable_value/isMapped';
import isMappedToCollection from '../../../../../app/utils/editable_value/isMappedToCollection';
import findPageContent from '../../../../../app/utils/findPageContent';
import {formIsMapped} from '../../../../../app/utils/formIsMapped';
import {formIsRestricted} from '../../../../../app/utils/formIsRestricted';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';
import {getResponsiveConfig} from '../../../../../app/utils/getResponsiveConfig';
import getSelectedField from '../../../../../app/utils/getSelectedField';
import StructureTreeNode from './StructureTreeNode';
import StructureTreeNodeActions from './StructureTreeNodeActions';
import VisibilityButton from './VisibilityButton';

const EDITABLE_TYPE_ICONS = {
	[EDITABLE_TYPES.action]: 'cursor',
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
	const hoveredItemId = useHoveredItemId();

	const mappingFields = useSelector((state) => state.mappingFields);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const restrictedItemIds = useSelector((state) => state.restrictedItemIds);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const layoutDataRef = useSelectorRef((store) => store.layoutData);

	const [dragAndDropHoveredItemId, setDragAndDropHoveredItemId] = useState(
		null
	);

	const [editingNodeId, setEditingNodeId] = useState(null);
	const [expandedKeys, setExpandedKeys] = useState([]);

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
				editingNodeId,
				fragmentEntryLinks,
				hoveredItemId,
				isMasterPage,
				layoutData,
				layoutDataRef,
				mappingFields,
				masterLayoutData,
				onHoverNode,
				pageContents,
				restrictedItemIds,
				selectedViewportSize,
			}).children,

		[
			activeItemId,
			canUpdateEditables,
			canUpdateItemConfiguration,
			data.items,
			data.rootItems.main,
			editingNodeId,
			fragmentEntryLinks,
			hoveredItemId,
			isMasterPage,
			layoutData,
			layoutDataRef,
			mappingFields,
			masterLayoutData,
			pageContents,
			restrictedItemIds,
			onHoverNode,
			selectedViewportSize,
		]
	);

	const setExpandedNodes = (expandedNodes) => {
		setExpandedKeys(Array.from(expandedNodes));
	};

	const handleNodeFocus = () => {
		const focusedItem = document.activeElement?.querySelector(
			'[data-item-id]'
		);

		if (focusedItem) {
			hoverItem(focusedItem.dataset.itemId);
		}
	};

	const handleButtonsKeyDown = (event) => {
		if (
			[
				ARROW_DOWN_KEY_CODE,
				ARROW_LEFT_KEY_CODE,
				ARROW_RIGHT_KEY_CODE,
				ARROW_UP_KEY_CODE,
			].includes(event.nativeEvent.code)
		) {
			document.activeElement
				.closest('.page-editor__page-structure__clay-tree-node')
				?.focus();
		}
		else {
			event.stopPropagation();
		}
	};

	const ItemActions = ({item}) => {
		const activeItemId = useActiveItemId();
		const dispatch = useDispatch();
		const hoveredItemId = useHoveredItemId();
		const isSelected = item.id === fromControlsId(activeItemId);
		const isHovered = item.id === fromControlsId(hoveredItemId);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);
		const showOptions =
			canUpdatePageStructure &&
			item.itemType !== ITEM_TYPES.editable &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			item.activable &&
			!item.isMasterItem;

		if (item.editingName) {
			return null;
		}

		return (
			<div
				className={classNames('autofit-row w-auto', {
					'page-editor__page-structure__tree-node__buttons--hidden':
						item.hidden || item.hiddenAncestor,
				})}
				onFocus={(event) => event.stopPropagation()}
				onKeyDown={handleButtonsKeyDown}
			>
				{(item.hidable || item.hidden) && (
					<VisibilityButton
						className="ml-0"
						dispatch={dispatch}
						node={item}
						selectedViewportSize={selectedViewportSize}
						visible={item.hidden || isHovered || isSelected}
					/>
				)}

				{showOptions && (
					<StructureTreeNodeActions
						item={item}
						setEditingNodeId={setEditingNodeId}
						visible={item.hidden || isHovered || isSelected}
					/>
				)}
			</div>
		);
	};

	const getAncestorsIds = useCallback(
		(layoutDataItem, data) => {
			if (!layoutDataItem.parentId) {
				const itemInMasterLayout =
					masterLayoutData?.items[layoutDataItem.itemId];
				if (
					!itemInMasterLayout &&
					masterLayoutData?.rootItems?.dropZone
				) {
					const dropZoneItem =
						masterLayoutData.items[
							masterLayoutData.rootItems.dropZone
						];

					return [
						...[layoutDataItem.itemId],
						...getAncestorsIds(
							masterLayoutData.items[dropZoneItem.parentId],
							masterLayoutData
						),
					];
				}
				else {
					return [layoutDataItem.itemId];
				}
			}

			return [
				...[layoutDataItem.itemId],
				...getAncestorsIds(data.items[layoutDataItem.parentId], data),
			];
		},
		[masterLayoutData]
	);

	useEffect(() => {
		if (activeItemId) {
			const layoutDataActiveItem = layoutData.items[activeItemId];

			if (!layoutDataActiveItem) {
				return;
			}

			setExpandedKeys((previousExpanedKeys) => [
				...new Set([
					...previousExpanedKeys,
					...getAncestorsIds(layoutDataActiveItem, layoutData),
				]),
			]);
		}
	}, [activeItemId, getAncestorsIds, layoutData, masterLayoutData]);

	useEffect(() => {
		if (dragAndDropHoveredItemId) {
			setExpandedKeys((previousExpanedKeys) => [
				...new Set([
					...previousExpanedKeys,
					...[dragAndDropHoveredItemId],
				]),
			]);
		}
	}, [dragAndDropHoveredItemId]);

	useEffect(() => {
		if (keyboardMovementTargetId) {
			const layoutDataTargetItem =
				layoutData.items[keyboardMovementTargetId];

			if (!layoutDataTargetItem) {
				return;
			}

			setExpandedKeys((previousExpanedKeys) => [
				...new Set([
					...previousExpanedKeys,
					...getAncestorsIds(layoutDataTargetItem, layoutData),
				]),
			]);
		}
	}, [getAncestorsIds, keyboardMovementTargetId, layoutData]);

	return (
		<div
			className="overflow-auto page-editor__page-structure__structure-tree pt-4"
			onFocus={handleNodeFocus}
		>
			{!nodes.length && (
				<ClayAlert
					aria-live="polite"
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get('there-is-no-content-on-this-page')}
				</ClayAlert>
			)}

			<DragAndDropContextProvider>
				<ClayTreeView
					displayType="light"
					expandDoubleClick={false}
					expandedKeys={new Set(expandedKeys)}
					expanderIcons={{
						close: <ClayIcon symbol="hr" />,
						open: <ClayIcon symbol="plus" />,
					}}
					items={nodes}
					onExpandedChange={setExpandedNodes}
					onItemsChange={() => {}}
					showExpanderOnHover={false}
				>
					{(item) => (
						<ClayTreeView.Item
							actions={<ItemActions item={item} />}
							active={item.active && item.activable}
						>
							<ClayTreeView.ItemStack
								className={classNames(
									'page-editor__page-structure__clay-tree-node',
									{
										'page-editor__page-structure__clay-tree-node--active':
											item.active && item.activable,
										'page-editor__page-structure__clay-tree-node--hovered':
											item.hovered,
										'page-editor__page-structure__clay-tree-node--mapped':
											item.mapped,
										'page-editor__page-structure__clay-tree-node--master-item':
											item.isMasterItem,
									}
								)}
								data-qa-id={item.tooltipTitle}
								data-title={
									item.isMasterItem || !item.activable
										? ''
										: item.tooltipTitle
								}
								data-tooltip-align={
									item.isMasterItem || !item.activable
										? ''
										: 'right'
								}
								onMouseLeave={(event) => {
									if (item.hovered) {
										event.stopPropagation();
										hoverItem(null);
									}
								}}
								onMouseOver={(event) => {
									event.stopPropagation();
									hoverItem(item.id);
								}}
							>
								<span className="sr-only">{item.name}</span>

								<StructureTreeNode
									node={item}
									setEditingNodeId={setEditingNodeId}
								/>
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item
										actions={<ItemActions item={item} />}
									>
										<span className="sr-only">
											{item.name}
										</span>

										<StructureTreeNode
											node={item}
											setEditingNodeId={setEditingNodeId}
										/>
									</ClayTreeView.Item>
								)}
							</ClayTreeView.Group>
						</ClayTreeView.Item>
					)}
				</ClayTreeView>
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
			return getMappingFieldsKey(collectionConfig);
		}
		else {
			return collectionConfig.key;
		}
	}
	else if (editable.mappedField) {
		return getMappingFieldsKey(selectedMappingTypes);
	}
	else if (!infoItem) {
		return null;
	}

	return getMappingFieldsKey(infoItem);
}

function getMappedFieldLabel(
	editable,
	collectionConfig,
	pageContents,
	mappingFields
) {
	const infoItem = findPageContent(pageContents, editable);

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
		editingNodeId,
		fragmentEntryLinks,
		hasHiddenAncestor,
		hoveredItemId,
		isMasterPage,
		layoutData,
		layoutDataRef,
		mappingFields,
		masterLayoutData,
		onHoverNode,
		pageContents,
		restrictedItemIds,
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
					active: childId === activeItemId,
					children: [],
					draggable: false,
					hidable: false,
					hidden: false,
					hiddenAncestor: hasHiddenAncestor || hidden,
					hovered: childId === hoveredItemId,
					icon: EDITABLE_TYPE_ICONS[type],
					id: childId,
					isMasterItem: !isMasterPage && itemInMasterLayout,
					itemType: ITEM_TYPES.editable,
					mapped: isMapped(editable),
					name: mappedFieldLabel || editableId,
					onHoverNode,
					parentId: item.parentId,
					removable: false,
					tooltipTitle: EDITABLE_TYPE_LABELS[type],
				});
			}
			else {
				const {dropZoneId, mainItemId} = element;

				children.push({
					...visit(items[mainItemId], items, {
						activeItemId,
						canUpdateEditables,
						canUpdateItemConfiguration,
						editingNodeId,
						fragmentEntryLinks,
						hasHiddenAncestor: hasHiddenAncestor || hidden,
						hoveredItemId,
						isMasterPage,
						layoutData,
						layoutDataRef,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						pageContents,
						restrictedItemIds,
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
					(!item.config.collection ||
						(Liferay.FeatureFlags['LPS-169923'] &&
							restrictedItemIds.has(item.itemId)))) ||
				(item.type === LAYOUT_DATA_ITEM_TYPES.form &&
					(!formIsMapped(item) ||
						(Liferay.FeatureFlags['LPS-169923'] &&
							formIsRestricted(item))))
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
						editingNodeId,
						fragmentEntryLinks,
						hasHiddenAncestor: hasHiddenAncestor || hidden,
						hoveredItemId,
						isMasterPage,
						layoutData,
						layoutDataRef,
						mappingFields,
						masterLayoutData,
						onHoverNode,
						pageContents,
						restrictedItemIds,
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
					editingNodeId,
					fragmentEntryLinks,
					hasHiddenAncestor: hasHiddenAncestor || hidden,
					hoveredItemId,
					isMasterPage,
					layoutData,
					layoutDataRef,
					mappingFields,
					masterLayoutData,
					onHoverNode,
					pageContents,
					restrictedItemIds,
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
		active: item.itemId === activeItemId,
		children,
		config: layoutDataRef?.current?.items[item.itemId]?.config,
		draggable: true,
		editingName: editingNodeId === item.itemId,
		hidable:
			!itemInMasterLayout &&
			isHidable(item, fragmentEntryLinks, layoutData),
		hidden,
		hiddenAncestor: hasHiddenAncestor,
		hovered: item.itemId === hoveredItemId,
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
