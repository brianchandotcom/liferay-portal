/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useEventListener} from '@liferay/frontend-js-react-web';
import {useControlledState} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef} from 'react';

import {addMappingFields} from '../../../../../app/actions/index';
import {fromControlsId} from '../../../../../app/components/layout_data_items/Collection';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
} from '../../../../../app/config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../app/config/constants/viewportSizes';
import {
	useActivationOrigin,
	useActiveItemId,
	useSelectItem,
} from '../../../../../app/contexts/ControlsContext';
import {
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSetMovementSource,
	useSetMovementText,
} from '../../../../../app/contexts/KeyboardMovementContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
	useSelectorRef,
} from '../../../../../app/contexts/StoreContext';
import selectCanUpdatePageStructure from '../../../../../app/selectors/selectCanUpdatePageStructure';
import CollectionService from '../../../../../app/services/CollectionService';
import moveItem from '../../../../../app/thunks/moveItem';
import updateItemConfig from '../../../../../app/thunks/updateItemConfig';
import canBeRenamed from '../../../../../app/utils/canBeRenamed';
import {deepEqual} from '../../../../../app/utils/checkDeepEqual';
import {collectionIsMapped} from '../../../../../app/utils/collectionIsMapped';
import checkAllowedChild from '../../../../../app/utils/drag_and_drop/checkAllowedChild';
import {DRAG_DROP_TARGET_TYPE} from '../../../../../app/utils/drag_and_drop/constants/dragDropTargetType';
import {ORIENTATIONS} from '../../../../../app/utils/drag_and_drop/constants/orientations';
import {TARGET_POSITIONS} from '../../../../../app/utils/drag_and_drop/constants/targetPositions';
import getDropTargetPosition from '../../../../../app/utils/drag_and_drop/getDropTargetPosition';
import getTargetData from '../../../../../app/utils/drag_and_drop/getTargetData';
import getTargetPositions from '../../../../../app/utils/drag_and_drop/getTargetPositions';
import itemIsAncestor from '../../../../../app/utils/drag_and_drop/itemIsAncestor';
import {
	initialDragDrop,
	useDragItem,
	useDropTarget,
	useIsDroppable,
} from '../../../../../app/utils/drag_and_drop/useDragAndDrop';
import {formIsMapped} from '../../../../../app/utils/formIsMapped';
import {formIsRestricted} from '../../../../../app/utils/formIsRestricted';
import {formIsUnavailable} from '../../../../../app/utils/formIsUnavailable';
import getFirstControlsId from '../../../../../app/utils/getFirstControlsId';
import getMappingFieldsKey from '../../../../../app/utils/getMappingFieldsKey';
import isItemWidget from '../../../../../app/utils/isItemWidget';

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

export default function StructureTreeNode({node, setEditingNodeId}) {
	const activationOrigin = useActivationOrigin();
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
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
				layoutData.items[node.id] || masterLayoutData?.items[node.id];

			if (!item?.config?.collection) {
				return;
			}

			const {
				classNameId,
				itemSubtype,
				itemType,
				key: collectionKey,
			} = item.config.collection;

			const key = classNameId
				? getMappingFieldsKey(item.config.collection)
				: collectionKey;

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
			isMapped={node.mapped}
			node={node}
			setEditingNodeId={setEditingNodeId}
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
	(prevProps, nextProps) => deepEqual(prevProps, nextProps)
);

function StructureTreeNodeContent({
	activationOrigin,
	isActive,
	isMapped,
	node,
	setEditingNodeId,
}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const dispatch = useDispatch();
	const nodeRef = useRef();
	const restrictedItemIds = useSelector((state) => state.restrictedItemIds);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const selectItem = useSelectItem();
	const setText = useSetMovementText();

	const layoutDataRef = useSelectorRef((store) => store.layoutData);

	const item = useMemo(
		() => ({
			children:
				node.itemType === ITEM_TYPES.editable ? [] : node.children,
			config: layoutDataRef.current.items[node.id]?.config,
			icon: node.icon,
			itemId: node.id,
			name: node.name,
			origin: ITEM_ACTIVATION_ORIGINS.sidebar,
			parentId: node.parentItemId,
			type: node.type || node.itemType,
		}),
		[layoutDataRef, node]
	);

	const fragmentEntryType = useSelectorCallback(
		(state) => {
			if (!node.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
				return null;
			}

			const fragmentEntryLink =
				state.fragmentEntryLinks[item.config?.fragmentEntryLinkId];

			return fragmentEntryLink?.fragmentEntryType ?? null;
		},
		[item]
	);

	const isWidget = useSelectorCallback(
		(state) => isItemWidget(item, state.fragmentEntryLinks),
		[item]
	);

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(
		item,
		computeHover
	);

	const {handlerRef, isDraggingSource: itemIsDraggingSource} = useDragItem(
		{...item, fragmentEntryType, isWidget},
		(parentItemId, position) =>
			dispatch(
				moveItem({
					itemId: node.id,
					parentItemId,
					position,
				})
			)
	);

	const {
		itemId: keyboardMovementTargetId,
		position: keyboardMovementPosition,
	} = useMovementTarget();

	const dropTargetPosition = targetPosition || keyboardMovementPosition;

	const keyboardMovementSource = useMovementSource();

	const isDraggingSource =
		itemIsDraggingSource || keyboardMovementSource?.itemId === item.itemId;

	const isDroppable = useIsDroppable();

	const isValidDrop =
		(isDroppable && isOverTarget) ||
		keyboardMovementTargetId === item.itemId;

	const onEditName = (nextName) => {
		const trimmedName = nextName?.trim();

		if (trimmedName && node.name !== trimmedName) {
			dispatch(
				updateItemConfig({
					itemConfig: {name: trimmedName},
					itemId: node.id,
				})
			);
		}

		setEditingNodeId(null);
		setText(Liferay.Language.get('name-saved'));
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

	useEffect(() => {
		if (
			item.itemId === keyboardMovementTargetId ||
			(activationOrigin === ITEM_ACTIVATION_ORIGINS.pageEditor &&
				nodeRef.current &&
				isActive)
		) {
			nodeRef.current.scrollIntoView({
				behavior: 'instant',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [activationOrigin, isActive, item.itemId, keyboardMovementTargetId]);

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

	useEffect(() => {
		if (
			isActive &&
			activationOrigin === ITEM_ACTIVATION_ORIGINS.itemActions
		) {
			document.querySelector(`[data-id*="${node.id}"]`).focus();
		}
	}, [activationOrigin, isActive, node.id, node.hidden]);

	return (
		<div
			aria-disabled={node.isMasterItem || !node.activable}
			aria-selected={isActive}
			className={classNames('page-editor__page-structure__tree-node', {
				'drag-over-bottom':
					isValidDrop &&
					dropTargetPosition === TARGET_POSITIONS.BOTTOM,
				'drag-over-middle':
					isValidDrop &&
					dropTargetPosition === TARGET_POSITIONS.MIDDLE,
				'drag-over-top':
					isValidDrop && dropTargetPosition === TARGET_POSITIONS.TOP,
				'dragged': isDraggingSource,
				'font-weight-semi-bold':
					node.activable && node.itemType !== ITEM_TYPES.editable,
			})}
			ref={targetRef}
		>
			<div
				aria-label={sub(Liferay.Language.get('select-x'), [node.name])}
				className="lfr-portal-tooltip page-editor__page-structure__tree-node__mask"
				data-item-id={node.id}
				onClick={(event) => {
					event.stopPropagation();
					const itemId = getFirstControlsId({
						item: node,
						layoutData: layoutDataRef.current,
					});

					if (node.activable) {
						selectItem(itemId, {
							itemType: node.itemType,
							origin: ITEM_ACTIVATION_ORIGINS.sidebar,
						});
					}
				}}
				onDoubleClick={(event) => {
					event.stopPropagation();
					if (canBeRenamed(item)) {
						setEditingNodeId(item.itemId);
					}
				}}
				ref={
					selectedViewportSize === VIEWPORT_SIZES.desktop
						? handlerRef
						: null
				}
				role="button"
			/>

			<MoveButton
				canUpdate={canUpdatePageStructure}
				fragmentEntryType={fragmentEntryType}
				isWidget={isWidget}
				node={node}
				nodeRef={nodeRef}
				onKeyDown={handleButtonsKeyDown}
				selectedViewportSize={selectedViewportSize}
			/>

			<NameLabel
				editingName={node.editingName}
				hidden={node.hidden || node.hiddenAncestor}
				icon={node.icon}
				isMapped={isMapped}
				isMasterItem={node.isMasterItem}
				name={node.name}
				nameInfo={node.nameInfo}
				onEditName={onEditName}
				ref={nodeRef}
				showPermissionRestriction={isRestricted(
					item,
					node,
					restrictedItemIds
				)}
				showUnavailableWarning={
					Liferay.FeatureFlags['LPS-169923'] &&
					node.type === LAYOUT_DATA_ITEM_TYPES.form &&
					formIsUnavailable(item)
				}
			/>

			{node.hidden ? (
				<span className="sr-only">
					{Liferay.Language.get('hidden-item')}
				</span>
			) : null}
		</div>
	);
}

const NameLabel = React.forwardRef(
	(
		{
			editingName,
			hidden,
			icon,
			isMapped,
			isMasterItem,
			name: defaultName,
			nameInfo,
			onEditName,
			showPermissionRestriction,
			showUnavailableWarning,
		},
		ref
	) => {
		const inputRef = useRef();

		const [name, setName] = useControlledState(defaultName);

		useEffect(() => {
			if (editingName && inputRef.current) {
				inputRef.current.focus();
			}
		}, [editingName]);

		return (
			<div
				className={classNames(
					'page-editor__page-structure__tree-node__name d-flex flex-grow-1 align-items-center',
					{
						'page-editor__page-structure__tree-node__name--hidden': hidden,
						'page-editor__page-structure__tree-node__name--mapped': isMapped,
						'page-editor__page-structure__tree-node__name--master-item': isMasterItem,
					}
				)}
				ref={ref}
			>
				{icon && (
					<ClayIcon
						className="flex-shrink-0 mt-0"
						symbol={icon || ''}
					/>
				)}

				{editingName ? (
					<input
						className="flex-grow-1"
						onBlur={() => {
							onEditName(name);
						}}
						onChange={(event) => {
							setName(event.target.value);
						}}
						onFocus={(event) => {
							inputRef.current.setSelectionRange(0, name.length);
							event.stopPropagation();
						}}
						onKeyDown={(event) => {
							if (
								event.key === 'Enter' ||
								event.key === 'Escape' ||
								event.key === 'Tab'
							) {
								inputRef.current
									.closest('.treeview-link')
									.focus();
							}

							if (!event.key.match(/[a-z0-9-_ ]/gi)) {
								event.preventDefault();
							}

							event.stopPropagation();
						}}
						ref={inputRef}
						type="text"
						value={name}
					/>
				) : (
					name || defaultName || Liferay.Language.get('element')
				)}

				{!editingName && nameInfo && (
					<span className="ml-3 page-editor__page-structure__tree-node__name-info position-relative">
						{nameInfo}
					</span>
				)}

				{showUnavailableWarning ? (
					<>
						<ClayIcon
							className="ml-2 mt-0 text-secondary"
							symbol="warning-full"
						/>
						<span className="sr-only">
							{Liferay.Language.get(
								'this-content-is-currently-unavailable-or-has-been-deleted.-users-cannot-see-this-fragment'
							)}
						</span>
					</>
				) : showPermissionRestriction ? (
					<>
						<ClayIcon
							className="ml-2 mt-0 text-secondary"
							symbol="password-policies"
						/>
						<span className="sr-only">
							{Liferay.Language.get(
								'this-content-cannot-be-displayed-due-to-permission-restrictions'
							)}
						</span>
					</>
				) : null}
			</div>
		);
	}
);

const MoveButton = ({
	canUpdate,
	fragmentEntryType,
	isWidget,
	node,
	nodeRef,
	onKeyDown,
	selectedViewportSize,
}) => {
	const setMovementSource = useSetMovementSource();
	const disableMovement = useDisableKeyboardMovement();

	const buttonRef = useRef(null);

	useEventListener('blur', () => disableMovement(), false, buttonRef.current);
	useEventListener(
		'focus',
		() =>
			nodeRef.current.scrollIntoView({
				behavior: 'instant',
				block: 'center',
				inline: 'nearest',
			}),
		false,
		buttonRef.current
	);

	if (
		selectedViewportSize !== VIEWPORT_SIZES.desktop ||
		node.itemType === ITEM_TYPES.editable ||
		node.itemType === ITEM_TYPES.dropZone ||
		node.isMasterItem ||
		!node.activable ||
		!canUpdate
	) {
		return null;
	}

	return (
		<ClayButton
			aria-label={sub(Liferay.Language.get('move-x'), [node.name])}
			className="mr-2 sr-only sr-only-focusable"
			disabled={node.isMasterItem || node.hiddenAncestor}
			displayType="unstyled"
			onBlur={(event) => event.stopPropagation()}
			onClick={() =>
				setMovementSource({
					fragmentEntryType,
					icon: node.icon,
					isWidget,
					itemId: node.id,
					name: node.name,
					type: node.type,
				})
			}
			onFocus={(event) => {
				buttonRef.current
					?.closest('.treeview-link')
					?.classList.remove('focus');
				event.stopPropagation();
			}}
			onKeyDown={onKeyDown}
			ref={buttonRef}
			tabIndex={
				document.activeElement.dataset.id?.includes(node.id)
					? '0'
					: '-1'
			}
			title={sub(Liferay.Language.get('move-x'), [node.name])}
		>
			<ClayIcon symbol="drag" />
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
	] = getItemPosition(siblingItem || targetItem, monitor, targetRefs);

	// Drop inside target

	const validDropInsideTarget = (() => {
		const targetIsCollectionNotMapped =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.collection &&
			!collectionIsMapped(targetItem);
		const targetIsColumn =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.column;
		const targetIsFragment =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.fragment;
		const targetIsContainer =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.container ||
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.form;
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;
		const targetIsFormNotMapped =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.form &&
			!formIsMapped(targetItem);
		const targetIsParent = sourceItem.parentId === targetItem.itemId;

		return (
			targetPositionWithMiddle === TARGET_POSITIONS.MIDDLE &&
			(targetIsEmpty ||
				targetIsCollectionNotMapped ||
				targetIsColumn ||
				targetIsContainer ||
				targetIsFormNotMapped) &&
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

	if (siblingItem) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: siblingItem,
			droppable: checkAllowedChild(sourceItem, targetItem, layoutDataRef),
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
					targetRefs
				);

				const [parentPosition] = getItemPosition(
					parent,
					monitor,
					targetRefs
				);

				if (
					targetPosition === targetPositionWithMiddle ||
					parentPosition === targetPositionWithMiddle
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

function getItemPosition(item, monitor, targetRefs) {
	const targetRef = targetRefs.get(item.itemId);

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

function isRestricted(item, node, restrictedItemIds) {
	if (!Liferay.FeatureFlags['LPS-169923']) {
		return false;
	}

	if (node.type === LAYOUT_DATA_ITEM_TYPES.form) {
		return formIsRestricted(item);
	}

	if (
		node.type === LAYOUT_DATA_ITEM_TYPES.collection ||
		node.type === LAYOUT_DATA_ITEM_TYPES.fragment
	) {
		return restrictedItemIds.has(item.itemId);
	}

	return false;
}
