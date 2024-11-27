/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {TreeView as ClayTreeView} from '@clayui/core';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import getAllEditables from '../../../../../app/components/fragment_content/getAllEditables';
import {fromControlsId} from '../../../../../app/components/layout_data_items/Collection';
import getAllPortals from '../../../../../app/components/layout_data_items/getAllPortals';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPE_LABELS} from '../../../../../app/config/constants/editableTypeLabels';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import {FRAGMENT_ENTRY_TYPES} from '../../../../../app/config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
	ENTER_KEY_CODE,
	SPACE_KEY_CODE,
} from '../../../../../app/config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../../../app/config/constants/layoutTypes';
import {MULTI_SELECT_TYPES} from '../../../../../app/config/constants/multiSelectTypes';
import {config} from '../../../../../app/config/index';
import {
	useActiveItemIds,
	useHoverItem,
	useHoveredItemId,
	useMultiSelectType,
	useSelectItem,
} from '../../../../../app/contexts/ControlsContext';
import {useEditedNodeId} from '../../../../../app/contexts/ShortcutContext';
import {
	useDispatch,
	useSelector,
	useSelectorRef,
} from '../../../../../app/contexts/StoreContext';
import selectCanUpdateEditables from '../../../../../app/selectors/selectCanUpdateEditables';
import selectCanUpdateItemConfiguration from '../../../../../app/selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../../../../app/selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../../../../app/selectors/selectLayoutDataItemLabel';
import canActivateEditable from '../../../../../app/utils/canActivateEditable';
import {DragAndDropContextProvider} from '../../../../../app/utils/drag_and_drop/useDragAndDrop';
import isMapped from '../../../../../app/utils/editable_value/isMapped';
import isMappedToCollection from '../../../../../app/utils/editable_value/isMappedToCollection';
import findPageContent from '../../../../../app/utils/findPageContent';
import {formIsMapped} from '../../../../../app/utils/formIsMapped';
import {formIsRestricted} from '../../../../../app/utils/formIsRestricted';
import getFirstControlsId from '../../../../../app/utils/getFirstControlsId';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';
import getSelectedField from '../../../../../app/utils/getSelectedField';
import isHideable from '../../../../../app/utils/isHideable';
import {isItemHidden} from '../../../../../app/utils/isItemHidden';
import isRemovable from '../../../../../app/utils/isRemovable';
import usePageContents from '../../../../../app/utils/usePageContents';
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
	[LAYOUT_DATA_ITEM_TYPES.formStep]: 'arrow-end',
	[LAYOUT_DATA_ITEM_TYPES.formStepContainer]: 'table',
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.fragment]: 'code',
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: 'box-container',
	[LAYOUT_DATA_ITEM_TYPES.root]: 'page',
	[LAYOUT_DATA_ITEM_TYPES.row]: 'table',
};

export default function StructureTreeContent({expandedKeys, setExpandedKeys}) {
	const activeItemIds = useActiveItemIds();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const multiSelectType = useMultiSelectType();
	const pageContents = usePageContents();
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const selectItem = useSelectItem();
	const treeRef = useRef(null);

	const mappingFields = useSelector((state) => state.mappingFields);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const restrictedItemIds = useSelector((state) => state.restrictedItemIds);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const layoutDataRef = useSelectorRef((store) => store.layoutData);

	const [dragAndDropHoveredItemId, setDragAndDropHoveredItemId] =
		useState(null);

	const isMasterPage = config.layoutType === LAYOUT_TYPES.master;

	const data = masterLayoutData || layoutData;

	const onHoverNode = useCallback((itemId) => {
		setDragAndDropHoveredItemId(itemId);
	}, []);

	const nodes = useMemo(
		() =>
			visit(data.items[data.rootItems.main], data.items, {
				activeItemIds,
				canUpdateEditables,
				canUpdateItemConfiguration,
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
			activeItemIds,
			canUpdateEditables,
			canUpdateItemConfiguration,
			data.items,
			data.rootItems.main,
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
		const activeItemIds = useActiveItemIds();
		const editedNodeId = useEditedNodeId();
		const dispatch = useDispatch();
		const hoveredItemId = useHoveredItemId();
		const isMultiSelect =
			Liferay.FeatureFlags['LPD-18221'] && activeItemIds.length > 1;
		const isSelected = fromControlsId(activeItemIds).includes(item.id);
		const isHovered = item.id === fromControlsId(hoveredItemId);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);
		const showOptions =
			canUpdatePageStructure &&
			item.itemType !== ITEM_TYPES.editable &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.formStepContainer &&
			item.activable &&
			!item.isMasterItem;

		if (editedNodeId) {
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
				{(item.hideable || item.hidden) && (
					<VisibilityButton
						className="ml-0"
						disabled={
							item.isMasterItem ||
							item.hiddenAncestor ||
							isMultiSelect
						}
						dispatch={dispatch}
						node={item}
						visible={item.hidden || isHovered || isSelected}
					/>
				)}

				{showOptions && (
					<StructureTreeNodeActions
						disabled={isMultiSelect}
						item={item}
						visible={item.hidden || isHovered || isSelected}
					/>
				)}
			</div>
		);
	};

	useEffect(() => {
		if (dragAndDropHoveredItemId) {
			setExpandedKeys((previousExpanedKeys) => [
				...new Set([
					...previousExpanedKeys,
					...[dragAndDropHoveredItemId],
				]),
			]);
		}
	}, [dragAndDropHoveredItemId, setExpandedKeys]);

	const onKeyDown = (event, item) => {
		const {code} = event.nativeEvent;

		if (![ENTER_KEY_CODE, SPACE_KEY_CODE].includes(code)) {
			return;
		}

		const itemId = getFirstControlsId({
			item,
			layoutData: layoutDataRef.current,
		});

		if (item.activable) {
			selectItem(itemId, {
				itemType: item.itemType,
				origin: ITEM_ACTIVATION_ORIGINS.sidebar,
			});

			hoverItem(null, {
				origin: ITEM_ACTIVATION_ORIGINS.sidebar,
			});
		}
	};

	// Each time an item is focused on, if range multiselect is enabled,
	// the item is selected.

	useEventListener(
		'focusin',
		(event) => {
			if (multiSelectType === MULTI_SELECT_TYPES.range) {
				const itemId = event.target.querySelector(
					'.page-editor__page-structure__tree-node__mask'
				).dataset.itemId;

				const item = layoutData.items[itemId];

				if (item) {
					selectItem(itemId, {
						origin: ITEM_ACTIVATION_ORIGINS.sidebar,
					});
				}
			}
		},
		false,
		treeRef.current
	);

	return (
		<div
			className="overflow-auto page-editor__page-structure__structure-tree pt-4"
			ref={treeRef}
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
					items={nodes}
					onExpandedChange={(expandedNodes) => {
						setExpandedKeys(Array.from(expandedNodes));
					}}
					onItemsChange={() => {}}
					showExpanderOnHover={false}
				>
					{(item) => (
						<ClayTreeView.Item
							actions={<ItemActions item={item} />}
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
								onKeyDown={(event) => onKeyDown(event, item)}
								onMouseLeave={(event) => {
									if (item.hovered) {
										event.stopPropagation();
										hoverItem(null, {
											origin: ITEM_ACTIVATION_ORIGINS.sidebar,
										});
									}
								}}
								onMouseOver={(event) => {
									event.stopPropagation();
									hoverItem(item.id, {
										origin: ITEM_ACTIVATION_ORIGINS.sidebar,
									});
								}}
							>
								<span className="sr-only">{item.name}</span>

								<StructureTreeNode node={item} />
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item
										actions={<ItemActions item={item} />}
									>
										<span className="sr-only">
											{item.name}
										</span>

										<StructureTreeNode node={item} />
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
		const {editableValues, fragmentEntryType} =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		return fragmentEntryType === FRAGMENT_ENTRY_TYPES.input
			? Boolean(
					editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]
						?.inputFieldId
				)
			: false;
	}

	return false;
}

function visit(
	item,
	items,
	{
		activeItemIds,
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

	const hidden = isItemHidden(layoutData, item.itemId, selectedViewportSize);

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
					active: activeItemIds.includes(childId),
					children: [],
					draggable: false,
					hidden: false,
					hiddenAncestor: hasHiddenAncestor || hidden,
					hideable: false,
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
						activeItemIds,
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
						restrictedItemIds.has(item.itemId))) ||
				(item.type === LAYOUT_DATA_ITEM_TYPES.form &&
					(!formIsMapped(item) || formIsRestricted(item)))
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
						activeItemIds,
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
					activeItemIds,
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
			(Liferay.FeatureFlags['LPD-18221'] ||
				(item.type !== LAYOUT_DATA_ITEM_TYPES.formStep &&
					item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
					item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone)) &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			canUpdateItemConfiguration,
		active: activeItemIds.includes(item.itemId),
		children,
		config: layoutDataRef?.current?.items[item.itemId]?.config,
		draggable: true,
		hidden,
		hiddenAncestor: hasHiddenAncestor,
		hideable:
			!itemInMasterLayout &&
			isHideable(item, fragmentEntryLinks, layoutData),
		hovered: item.itemId === hoveredItemId,
		icon,
		id: item.itemId,
		isMasterItem: !isMasterPage && itemInMasterLayout,
		itemType: ITEM_TYPES.layoutDataItem,
		mapped: fragmentIsMapped(item, fragmentEntryLinks),
		name: selectLayoutDataItemLabel({fragmentEntryLinks, layoutData}, item),
		nameInfo: getNameInfo(item),
		onHoverNode,
		parentItemId: item.parentId,
		removable: !itemInMasterLayout && isRemovable(item, layoutData),
		tooltipTitle: selectLayoutDataItemLabel(
			{fragmentEntryLinks, layoutData},
			item,
			{
				useCustomName: false,
			}
		),
		type: item.type,
	};
}
