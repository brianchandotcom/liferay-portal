/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {config} from '../../config/index';
import {useSetCollectionActiveItemContext} from '../../contexts/CollectionActiveItemContext';
import {
	useActivationOrigin,
	useActiveItemIds,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useMultiSelectType,
	useSelectItem,
} from '../../contexts/ControlsContext';
import {useEditableProcessorUniqueId} from '../../contexts/EditableProcessorContext';
import {
	useMovementSources,
	useMovementTarget,
	useMovementTargetPosition,
} from '../../contexts/KeyboardMovementContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import {useLayoutKeyboardNavigation} from '../../hooks/app_hooks/useLayoutKeyboardNavigation';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import moveItems from '../../thunks/moveItems';
import moveStepper from '../../thunks/moveStepper';
import switchSidebarPanel from '../../thunks/switchSidebarPanel';
import {deepEqual} from '../../utils/checkDeepEqual';
import {TARGET_POSITIONS} from '../../utils/drag_and_drop/constants/targetPositions';
import {
	useDragItem,
	useDropTarget,
} from '../../utils/drag_and_drop/useDragAndDrop';
import isStepper from '../../utils/isStepper';
import {isUnmappedCollection} from '../../utils/isUnmappedCollection';
import {isUnmappedForm} from '../../utils/isUnmappedForm';
import toMovementItem from '../../utils/toMovementItem';
import useDropContainerId from '../../utils/useDropContainerId';
import TopperItemActions from './TopperItemActions';
import {TopperLabel} from './TopperLabel';

const MemoizedTopperContent = React.memo(TopperContent);

export default function Topper({children, item, itemElement, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const isHovered = useIsHovered();
	const isActive = useIsActive();
	const multiSelectType = useMultiSelectType();

	if (canUpdatePageStructure || canUpdateItemConfiguration) {
		return (
			<>
				<TopperInteractionFilter
					itemElement={itemElement}
					itemId={item.itemId}
				/>

				<MemoizedTopperContent
					isActive={isActive(item.itemId)}
					isHovered={isHovered(item.itemId)}
					item={item}
					itemElement={itemElement}
					multiSelectType={multiSelectType}
					{...props}
				>
					{children}
				</MemoizedTopperContent>
			</>
		);
	}

	return children;
}

function TopperContent({
	children,
	className,
	isActive,
	isHovered,
	item,
	itemElement,
	multiSelectType,
}) {
	const activeItemIds = useActiveItemIds();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const commentsPanelId = config.sidebarPanelsMap?.comments?.sidebarPanelId;
	const dispatch = useDispatch();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const hoverItem = useHoverItem();
	const {isOverTarget, targetPosition, targetRef} = useDropTarget(item);
	const isMultiSelect = activeItemIds.length > 1;
	const {itemId: keyboardMovementTargetId} = useMovementTarget();
	const keyboardMovementPosition = useMovementTargetPosition();
	const selectItem = useSelectItem();
	const topperLabelId = useId();

	const dropContainerId = useDropContainerId();
	const dropTargetPosition = targetPosition || keyboardMovementPosition;

	const isHighlighted = isItemHighlighted(item, dropContainerId);

	const selectable =
		!multiSelectType ||
		!activeItemIds.some((activeItemId) => item.itemId === activeItemId) ||
		isActive;

	const canBeDragged =
		selectable &&
		canUpdatePageStructure &&
		!editableProcessorUniqueId &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.formStepContainer;

	const name = useSelectorCallback(
		(state) => selectLayoutDataItemLabel(state, item),
		[item]
	);

	const dragItem = useSelectorCallback(
		(state) =>
			toMovementItem(
				item.itemId,
				state.layoutData,
				state.fragmentEntryLinks
			),
		[item],
		deepEqual
	);

	const onDragBegin = () => {
		if (!isActive) {
			selectItem(item.itemId, {
				origin: ITEM_ACTIVATION_ORIGINS.layout,
			});
		}
	};

	const onDragEnd = (parentItemId, position) => {
		const thunk = isStepper(dragItem)
			? moveStepper({
					itemId: item.itemId,
					parentItemId,
					position,
				})
			: moveItems({
					itemIds: activeItemIds,
					parentItemIds: [parentItemId],
					positions: [position],
				});

		dispatch(thunk);
	};

	const {handlerRef: itemRef, isDraggingSource: draggingItem} = useDragItem(
		dragItem,
		onDragEnd,
		onDragBegin
	);

	const {handlerRef: topperRef, isDraggingSource: draggingTopper} =
		useDragItem(dragItem, onDragEnd, onDragBegin);

	const keyboardMovementSources = useMovementSources();
	const lastSource =
		keyboardMovementSources[keyboardMovementSources.length - 1];

	const isDraggingSource =
		draggingItem || draggingTopper || lastSource?.itemId === item.itemId;

	const isTarget =
		(isOverTarget || keyboardMovementTargetId === item.itemId) &&
		!isUnmappedCollection(item) &&
		!isUnmappedForm(item);

	const {elementRef, isFocusable} = useLayoutKeyboardNavigation(item);

	return (
		<div
			className={classNames(className, 'page-editor__topper', {
				'active': isActive,
				'drag-over-bottom':
					isTarget && dropTargetPosition === TARGET_POSITIONS.BOTTOM,
				'drag-over-left':
					isTarget && dropTargetPosition === TARGET_POSITIONS.LEFT,
				'drag-over-middle':
					isTarget && dropTargetPosition === TARGET_POSITIONS.MIDDLE,
				'drag-over-right':
					isTarget && dropTargetPosition === TARGET_POSITIONS.RIGHT,
				'drag-over-top':
					isTarget && dropTargetPosition === TARGET_POSITIONS.TOP,
				'dragged': isDraggingSource,
				'drop-container': dropContainerId === item.itemId,
				'highlighted': isHighlighted,
				'hovered': isHovered,
				'not-allowed': !selectable,
			})}
			data-name={name}
			onClick={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				if (!selectable) {
					return;
				}

				if (!isSelectionAllowed(event.target)) {
					return;
				}

				selectItem(item.itemId, {
					origin: ITEM_ACTIVATION_ORIGINS.layout,
				});
			}}
			onMouseLeave={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				if (isHovered) {
					hoverItem(null, {
						origin: ITEM_ACTIVATION_ORIGINS.layout,
					});
				}
			}}
			onMouseOver={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				hoverItem(item.itemId, {
					origin: ITEM_ACTIVATION_ORIGINS.layout,
				});
			}}
			ref={(element) => {
				if (canBeDragged) {
					itemRef(element);
				}

				elementRef.current = element;
			}}
			tabIndex={isFocusable ? 0 : -1}
		>
			{isActive ||
			isHighlighted ||
			(isHovered && Liferay.FeatureFlags['LPD-32075']) ? (
				<TopperLabel
					isDragging={isDraggingSource}
					isHovered={isHovered && !isActive}
					itemElement={itemElement}
				>
					<ul className="tbar-nav">
						{canBeDragged && isActive && (
							<li
								className="p-0 page-editor__topper__drag-handler page-editor__topper__item tbar-item"
								ref={topperRef}
							>
								<ClayIcon
									className="page-editor__topper__drag-icon page-editor__topper__icon"
									symbol="drag"
								/>
							</li>
						)}

						<li
							className="d-inline-block mx-2 page-editor__topper__item page-editor__topper__title tbar-item tbar-item-expand"
							id={topperLabelId}
						>
							{name}
						</li>

						{item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
							isActive && (
								<li className="page-editor__topper__item tbar-item">
									<ClayButton
										aria-label={Liferay.Language.get(
											'comments'
										)}
										disabled={isMultiSelect}
										displayType="unstyled"
										onClick={(event) =>
											event.stopPropagation()
										}
										size="sm"
										title={Liferay.Language.get('comments')}
									>
										<ClayIcon
											className="page-editor__topper__icon"
											onClick={() => {
												dispatch(
													switchSidebarPanel({
														sidebarOpen: true,
														sidebarPanelId:
															commentsPanelId,
													})
												);
											}}
											symbol="comments"
										/>
									</ClayButton>
								</li>
							)}

						{canUpdatePageStructure && isActive && (
							<li className="page-editor__topper__item tbar-item">
								<TopperItemActions
									disabled={isMultiSelect}
									item={item}
								/>
							</li>
						)}
					</ul>
				</TopperLabel>
			) : null}

			<div className="page-editor__topper__content" ref={targetRef}>
				<TopperErrorBoundary>{children}</TopperErrorBoundary>
			</div>
		</div>
	);
}

TopperContent.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	itemElement: PropTypes.object,
};

function TopperInteractionFilter({itemElement, itemId}) {
	useSetCollectionActiveItemContext(itemId);

	const {itemId: keyboardTargetId} = useMovementTarget();
	const activationOrigin = useActivationOrigin();
	const isActive = useIsActive()(itemId);
	const isMounted = useIsMounted();

	useEffect(() => {
		if (
			itemElement &&
			(keyboardTargetId === itemId ||
				(activationOrigin === ITEM_ACTIVATION_ORIGINS.sidebar &&
					isMounted() &&
					isActive))
		) {
			itemElement.scrollIntoView({
				behavior: 'instant',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [
		activationOrigin,
		isActive,
		isMounted,
		itemElement,
		itemId,
		keyboardTargetId,
	]);

	return null;
}

TopperInteractionFilter.propTypes = {
	itemElement: PropTypes.object,
	itemId: PropTypes.string.isRequired,
};

class TopperErrorBoundary extends React.Component {
	static getDerivedStateFromError(error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}

		return {error};
	}

	constructor(props) {
		super(props);

		this.state = {
			error: null,
		};
	}

	render() {
		return this.state.error ? (
			<ClayAlert
				displayType="danger"
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'an-unexpected-error-occurred-while-rendering-this-item'
				)}
			</ClayAlert>
		) : (
			this.props.children
		);
	}
}

function isItemHighlighted(item, targetId) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.row ||
		item.type === LAYOUT_DATA_ITEM_TYPES.collection
	) {
		return item.children.includes(targetId);
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.container ||
		item.type === LAYOUT_DATA_ITEM_TYPES.form
	) {
		return targetId === item.itemId;
	}

	return false;
}

function isSelectionAllowed(element) {
	if (element.closest('.portlet-options')) {
		return false;
	}

	return true;
}
