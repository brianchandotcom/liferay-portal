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
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {switchSidebarPanel} from '../../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {config} from '../../config/index';
import {
	useHoverItem,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from '../../contexts/ControlsContext';
import {useEditableProcessorUniqueId} from '../../contexts/EditableProcessorContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import moveItem from '../../thunks/moveItem';
import {TARGET_POSITIONS} from '../../utils/drag-and-drop/constants/targetPositions';
import {
	useDragItem,
	useDropTarget,
} from '../../utils/drag-and-drop/useDragAndDrop';
import {useId} from '../../utils/useId';
import {fromControlsId} from '../layout-data-items/Collection';
import TopperItemActions from './TopperItemActions';
import {TopperLabel} from './TopperLabel';

function isItemHighlighted(item, layoutData, targetItemId, targetPosition) {
	if (
		(item.type === LAYOUT_DATA_ITEM_TYPES.container ||
			item.type === LAYOUT_DATA_ITEM_TYPES.form) &&
		item.itemId === targetItemId &&
		targetPosition === TARGET_POSITIONS.MIDDLE
	) {
		return true;
	}
	else if (item.children.includes(fromControlsId(targetItemId))) {
		return true;
	}
	else if (
		item.type === LAYOUT_DATA_ITEM_TYPES.row ||
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment ||
		item.type === LAYOUT_DATA_ITEM_TYPES.collection
	) {
		return item.children.some((childId) => {
			const child = layoutData.items[childId];

			return child.children.includes(fromControlsId(targetItemId));
		});
	}

	return false;
}

const MemoizedTopperContent = React.memo(TopperContent);

export default function Topper({
	children,
	isDropTarget = true,
	item,
	...props
}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const isHovered = useIsHovered();
	const isActive = useIsActive();

	if (canUpdatePageStructure || canUpdateItemConfiguration) {
		return (
			<MemoizedTopperContent
				isActive={isActive(item.itemId)}
				isDropTarget={isDropTarget}
				isHovered={isHovered(item.itemId)}
				item={item}
				{...props}
			>
				{children}
			</MemoizedTopperContent>
		);
	}

	return children;
}

function TopperContent({
	children,
	className,
	isActive,
	isDropTarget,
	isHovered,
	item,
	itemElement,
	style,
}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const commentsPanelId = config.sidebarPanels?.comments?.sidebarPanelId;
	const dispatch = useDispatch();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const layoutData = useSelector((state) => state.layoutData);
	const hoverItem = useHoverItem();
	const {
		isOverTarget,
		targetItemId,
		targetPosition,
		targetRef,
	} = useDropTarget(item);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectItem = useSelectItem();
	const topperLabelId = useId();

	const isHighlighted = useMemo(
		() => isItemHighlighted(item, layoutData, targetItemId, targetPosition),
		[item, layoutData, targetItemId, targetPosition]
	);

	const canBeDragged = canUpdatePageStructure && !editableProcessorUniqueId;

	const name = useSelectorCallback(
		(state) => selectLayoutDataItemLabel(state, item),
		[item]
	);

	const onDragEnd = (parentItemId, position) =>
		dispatch(
			moveItem({
				itemId: item.itemId,
				parentItemId,
				position,
				segmentsExperienceId,
			})
		);

	const {
		handlerRef: itemHandlerRef,
		isDraggingSource: itemIsDraggingSource,
	} = useDragItem({...item, name}, onDragEnd);

	const {
		handlerRef: topperHandlerRef,
		isDraggingSource: topperIsDraggingSource,
	} = useDragItem({...item, name}, onDragEnd);

	const isDraggingSource = itemIsDraggingSource || topperIsDraggingSource;

	return (
		<div
			aria-label={name}
			aria-labelledby={isActive ? topperLabelId : null}
			className={classNames(className, 'page-editor__topper', {
				'active': isActive,
				'drag-over-bottom':
					isOverTarget && targetPosition === TARGET_POSITIONS.BOTTOM,
				'drag-over-left':
					isOverTarget && targetPosition === TARGET_POSITIONS.LEFT,
				'drag-over-middle':
					isDropTarget &&
					isOverTarget &&
					targetPosition === TARGET_POSITIONS.MIDDLE,
				'drag-over-right':
					isOverTarget && targetPosition === TARGET_POSITIONS.RIGHT,
				'drag-over-top':
					isOverTarget && targetPosition === TARGET_POSITIONS.TOP,
				'dragged': isDraggingSource,
				'highlighted': isHighlighted,
				'hovered': isHovered,
			})}
			onClick={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				selectItem(item.itemId);
			}}
			onMouseLeave={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				if (isHovered) {
					hoverItem(null);
				}
			}}
			onMouseOver={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				hoverItem(item.itemId);
			}}
			ref={canBeDragged ? itemHandlerRef : null}
			style={config.featureFlagLps132571 ? {} : style}
		>
			{(isActive || isHighlighted) && style?.display !== 'none' ? (
				<TopperLabel
					itemElement={itemElement}
					style={isDraggingSource ? {opacity: 0} : {}}
				>
					<ul className="tbar-nav">
						{canBeDragged && (
							<li
								className="page-editor__topper__drag-handler page-editor__topper__item tbar-item"
								ref={topperHandlerRef}
							>
								<ClayIcon
									className="page-editor__topper__drag-icon page-editor__topper__icon"
									symbol="drag"
								/>
							</li>
						)}

						<li
							className="d-inline-block page-editor__topper__item page-editor__topper__title tbar-item tbar-item-expand"
							id={topperLabelId}
						>
							{name}
						</li>

						{item.type === LAYOUT_DATA_ITEM_TYPES.fragment && (
							<li className="page-editor__topper__item tbar-item">
								<ClayButton
									displayType="unstyled"
									small
									title={Liferay.Language.get('comments')}
								>
									<ClayIcon
										className="page-editor__topper__icon"
										onClick={() => {
											dispatch(
												switchSidebarPanel({
													sidebarOpen: true,
													sidebarPanelId: commentsPanelId,
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
								<TopperItemActions item={item} />
							</li>
						)}
					</ul>
				</TopperLabel>
			) : null}

			<div
				className="page-editor__topper__content"
				ref={isDropTarget ? targetRef : null}
			>
				<TopperErrorBoundary>
					{React.cloneElement(children, {
						withinTopper: true,
					})}
				</TopperErrorBoundary>
			</div>
		</div>
	);
}

TopperContent.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	itemElement: PropTypes.object,
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
