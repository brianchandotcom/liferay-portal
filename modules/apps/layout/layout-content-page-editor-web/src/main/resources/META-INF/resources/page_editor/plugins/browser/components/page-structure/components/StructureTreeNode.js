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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef} from 'react';

import {addMappingFields} from '../../../../../app/actions/index';
import {fromControlsId} from '../../../../../app/components/layout-data-items/Collection';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {useToControlsId} from '../../../../../app/contexts/CollectionItemContext';
import {
	useActivationOrigin,
	useActiveItemId,
	useHoverItem,
	useHoveredItemId,
	useSelectItem,
} from '../../../../../app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../app/contexts/StoreContext';
import selectCanUpdatePageStructure from '../../../../../app/selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../app/services/CollectionService';
import deleteItem from '../../../../../app/thunks/deleteItem';
import moveItem from '../../../../../app/thunks/moveItem';
import {deepEqual} from '../../../../../app/utils/checkDeepEqual';
import checkAllowedChild from '../../../../../app/utils/drag-and-drop/checkAllowedChild';
import {DRAG_DROP_TARGET_TYPE} from '../../../../../app/utils/drag-and-drop/constants/dragDropTargetType';
import {ORIENTATIONS} from '../../../../../app/utils/drag-and-drop/constants/orientations';
import {TARGET_POSITIONS} from '../../../../../app/utils/drag-and-drop/constants/targetPositions';
import getDropTargetPosition from '../../../../../app/utils/drag-and-drop/getDropTargetPosition';
import getTargetData from '../../../../../app/utils/drag-and-drop/getTargetData';
import getTargetPositions from '../../../../../app/utils/drag-and-drop/getTargetPositions';
import itemIsAncestor from '../../../../../app/utils/drag-and-drop/itemIsAncestor';
import toControlsId from '../../../../../app/utils/drag-and-drop/toControlsId';
import {
	initialDragDrop,
	useDragItem,
	useDropTarget,
} from '../../../../../app/utils/drag-and-drop/useDragAndDrop';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';

const HOVER_EXPAND_DELAY = 1000;

const loadCollectionFields = (
	dispatch,
	itemType,
	itemSubtype,
	mappingFieldsKey
) => {
	CollectionService.getCollectionMappingFields({
		itemSubtype: itemSubtype || '',
		itemType,
		onNetworkStatus: () => {},
	})
		.then((response) => {
			dispatch(
				addMappingFields({
					fields: response.mappingFields,
					key: mappingFieldsKey,
				})
			);
		})
		.catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
};

export default function StructureTreeNode({node}) {
	const activationOrigin = useActivationOrigin();
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
	const hoveredItemId = useHoveredItemId();
	const isSelected = node.id === fromControlsId(activeItemId);

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);
	const mappingFields = useSelector((state) => state.mappingFields);

	useEffect(() => {
		if (node.type === LAYOUT_DATA_ITEM_TYPES.collection) {
			const item =
				layoutData.items[node.id] || masterLayoutData.items[node.id];

			if (!item?.config?.collection) {
				return;
			}

			const {classNameId, itemSubtype, itemType} = item.config.collection;

			const key = getMappingFieldsKey(classNameId, itemSubtype);

			if (!mappingFields[key]) {
				loadCollectionFields(dispatch, itemType, itemSubtype, key);
			}
		}
	}, [
		layoutData,
		masterLayoutData,
		node,
		dispatch,
		mappingFields,
		fragmentEntryLinks,
	]);

	return (
		<MemoizedStructureTreeNodeContent
			activationOrigin={isSelected ? activationOrigin : null}
			isActive={node.activable && isSelected}
			isHovered={node.id === fromControlsId(hoveredItemId)}
			isMapped={node.mapped}
			isSelected={isSelected}
			node={node}
		/>
	);
}

StructureTreeNodeContent.propTypes = {
	node: PropTypes.shape({
		id: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		removable: PropTypes.bool,
	}).isRequired,
};

const MemoizedStructureTreeNodeContent = React.memo(
	StructureTreeNodeContent,
	(prevProps, nextProps) =>
		deepEqual(
			{...prevProps, node: {...prevProps.node, children: []}},
			{...nextProps, node: {...nextProps.node, children: []}}
		)
);

function StructureTreeNodeContent({
	activationOrigin,
	isActive,
	isHovered,
	isMapped,
	isSelected,
	node,
}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const dispatch = useDispatch();
	const hoverItem = useHoverItem();
	const isDisabled = !node.activable || node.disabled;
	const nodeRef = useRef();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectItem = useSelectItem();
	const toControlsId = useToControlsId();

	const item = {
		children: node.children,
		icon: node.icon,
		itemId: node.id,
		name: node.name,
		origin: ITEM_ACTIVATION_ORIGINS.sidebar,
		parentId: node.parentItemId,
		type: node.type || node.itemType,
	};

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(
		item,
		computeHover
	);

	const {handlerRef, isDraggingSource} = useDragItem(
		item,
		(parentItemId, position) =>
			dispatch(
				moveItem({
					itemId: node.id,
					parentItemId,
					position,
					segmentsExperienceId,
				})
			)
	);

	useEffect(() => {
		if (
			isActive &&
			activationOrigin === ITEM_ACTIVATION_ORIGINS.pageEditor &&
			nodeRef.current
		) {
			nodeRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [activationOrigin, isActive]);

	useEffect(() => {
		let timeoutId = null;

		if (isOverTarget) {
			timeoutId = setTimeout(() => {
				node.onHoverNode(node.id);
			}, HOVER_EXPAND_DELAY);
		}

		return () => {
			clearTimeout(timeoutId);
		};
	}, [isOverTarget, node]);

	return (
		<div
			aria-disabled={isDisabled}
			aria-selected={isActive}
			className={classNames('page-editor__page-structure__tree-node', {
				'drag-over-bottom':
					isOverTarget && targetPosition === TARGET_POSITIONS.BOTTOM,
				'drag-over-middle':
					isOverTarget && targetPosition === TARGET_POSITIONS.MIDDLE,
				'drag-over-top':
					isOverTarget && targetPosition === TARGET_POSITIONS.TOP,
				dragged: isDraggingSource,
				'page-editor__page-structure__tree-node--activable':
					node.activable && node.itemType !== ITEM_TYPES.editable,
				'page-editor__page-structure__tree-node--active': isActive,
				'page-editor__page-structure__tree-node--hovered': isHovered,
				'page-editor__page-structure__tree-node--mapped': isMapped,
			})}
			onMouseLeave={(event) => {
				if (!isDraggingSource && isHovered) {
					event.stopPropagation();
					hoverItem(null);
				}
			}}
			onMouseOver={(event) => {
				if (!isDraggingSource) {
					event.stopPropagation();
					hoverItem(node.id);
				}
			}}
			ref={targetRef}
		>
			<div
				aria-label={Liferay.Util.sub(Liferay.Language.get('select-x'), [
					node.name,
				])}
				className="page-editor__page-structure__tree-node__mask"
				onClick={(event) => {
					event.stopPropagation();
					event.target.focus();

					if (node.activable) {
						selectItem(toControlsId(node.id), {
							itemType: node.itemType,
							origin: ITEM_ACTIVATION_ORIGINS.sidebar,
						});
					}
				}}
				onDoubleClick={(event) => event.stopPropagation()}
				ref={handlerRef}
				role="button"
			/>

			<NameLabel
				disabled={node.disabled}
				icon={node.icon}
				isActive={isActive}
				isMapped={isMapped}
				name={node.name}
				ref={nodeRef}
			/>

			{node.removable && canUpdatePageStructure && (
				<RemoveButton node={node} visible={isHovered || isSelected} />
			)}
		</div>
	);
}

const NameLabel = React.forwardRef(
	({disabled, icon, isActive, isMapped, name}, ref) => (
		<div
			className={classNames(
				'page-editor__page-structure__tree-node__name',
				{
					'page-editor__page-structure__tree-node__name--active': isActive,
					'page-editor__page-structure__tree-node__name--disabled': disabled,
					'page-editor__page-structure__tree-node__name--mapped': isMapped,
				}
			)}
			ref={ref}
		>
			{icon && <ClayIcon symbol={icon || ''} />}

			{name || Liferay.Language.get('element')}
		</div>
	)
);

const RemoveButton = ({node, visible}) => {
	const dispatch = useDispatch();
	const selectItem = useSelectItem();
	const store = useSelector((state) => state);

	return (
		<ClayButton
			aria-label={Liferay.Util.sub(Liferay.Language.get('remove-x'), [
				node.name,
			])}
			className={classNames(
				'page-editor__page-structure__tree-node__remove-button',
				{
					'page-editor__page-structure__tree-node__remove-button--visible': visible,
				}
			)}
			displayType="unstyled"
			onClick={(event) => {
				event.stopPropagation();

				dispatch(deleteItem({itemId: node.id, selectItem, store}));
			}}
		>
			<ClayIcon symbol="times-circle" />
		</ClayButton>
	);
};

function computeHover({
	dispatch,
	layoutDataRef,
	monitor,
	siblingItem = null,
	sourceItem,
	targetItem,
	targetRefs,
}) {

	// Not dragging over direct child
	// We do not want to alter state here,
	// as dnd generate extra hover events when
	// items are being dragged over nested children

	if (!monitor.isOver({shallow: true})) {
		return;
	}

	// Dragging over itself or a descendant

	if (itemIsAncestor(sourceItem, targetItem, layoutDataRef)) {
		return dispatch({
			...initialDragDrop.state,
			type: DRAG_DROP_TARGET_TYPE.DRAGGING_TO_ITSELF,
		});
	}

	// Apparently valid drag, calculate vertical position and
	// nesting validation

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevation,
	] = getItemPosition(
		siblingItem || targetItem,
		monitor,
		layoutDataRef,
		targetRefs
	);

	// Drop inside target

	const validDropInsideTarget = (() => {
		const targetIsColumn =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.column;
		const targetIsFragment =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.fragment;
		const targetIsContainer =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.container;
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;
		const targetIsParent = sourceItem.parentId === targetItem.itemId;

		return (
			targetPositionWithMiddle === TARGET_POSITIONS.MIDDLE &&
			(targetIsEmpty || targetIsColumn || targetIsContainer) &&
			!targetIsFragment &&
			!targetIsParent
		);
	})();

	if (!siblingItem && validDropInsideTarget) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: targetItem,
			droppable: checkAllowedChild(sourceItem, targetItem, layoutDataRef),
			elevate: null,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.INSIDE,
		});
	}

	// Valid elevation:
	// - dropItem should be child of dropTargetItem
	// - dropItem should be sibling of siblingItem

	if (
		siblingItem &&
		checkAllowedChild(sourceItem, targetItem, layoutDataRef)
	) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: siblingItem,
			droppable: true,
			elevate: true,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.ELEVATE,
		});
	}

	// Try to elevate to a valid ancestor

	if (elevation) {
		const getElevatedTargetItem = (target) => {
			const parent = layoutDataRef.current.items[target.parentId]
				? {
						...layoutDataRef.current.items[target.parentId],
						collectionItemIndex: target.collectionItemIndex,
				  }
				: null;

			if (parent) {
				const [targetPosition] = getItemPosition(
					target,
					monitor,
					layoutDataRef,
					targetRefs
				);

				const [parentPosition] = getItemPosition(
					parent,
					monitor,
					layoutDataRef,
					targetRefs
				);

				if (
					(targetPosition === targetPositionWithMiddle ||
						parentPosition === targetPositionWithMiddle) &&
					checkAllowedChild(sourceItem, parent, layoutDataRef)
				) {
					return [parent, target];
				}
			}

			return [null, null];
		};

		const [elevatedTargetItem, siblingItem] = getElevatedTargetItem(
			targetItem
		);

		if (elevatedTargetItem && elevatedTargetItem !== targetItem) {
			return computeHover({
				dispatch,
				layoutDataRef,
				monitor,
				siblingItem,
				sourceItem,
				targetItem: elevatedTargetItem,
				targetRefs,
			});
		}
	}
}

const ELEVATION_BORDER_SIZE = 5;

function getItemPosition(item, monitor, layoutDataRef, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));

	if (!targetRef || !targetRef.current) {
		return [null, null];
	}

	const clientOffsetY = monitor.getClientOffset().y;
	const hoverBoundingRect = targetRef.current.getBoundingClientRect();

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
	] = getDropTargetPosition(
		clientOffsetY,
		ELEVATION_BORDER_SIZE,
		getTargetPositions(ORIENTATIONS.vertical),
		getTargetData(hoverBoundingRect, ORIENTATIONS.vertical)
	);

	const elevation = targetPositionWithMiddle !== TARGET_POSITIONS.MIDDLE;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle, elevation];
}
