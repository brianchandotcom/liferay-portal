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
import React, {useEffect, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import {switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';
import {
	useHoverItem,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from '../contexts/ControlsContext';
import {useEditableProcessorUniqueId} from '../contexts/EditableProcessorContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import moveItem from '../thunks/moveItem';
import {TARGET_POSITIONS} from '../utils/drag-and-drop/constants/targetPositions';
import {
	useDragItem,
	useDropTarget,
} from '../utils/drag-and-drop/useDragAndDrop';
import getLayoutDataItemLabel from '../utils/getLayoutDataItemLabel';
import ItemActions from './ItemActions';

const TOPPER_BAR_HEIGHT = 24;

const TopperListItem = React.forwardRef(
	({children, className, expand, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'page-editor__topper__item',
				'tbar-item',
				{'tbar-item-expand': expand},
				className
			)}
			ref={ref}
		>
			{children}
		</li>
	)
);

TopperListItem.displayName = 'TopperListItem';

TopperListItem.propTypes = {
	expand: PropTypes.bool,
};

const MemoizedTopperContent = React.memo(TopperContent);

export default function Topper({children, item, ...props}) {
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
	isHovered,
	isMapped,
	item,
	itemElement,
	style,
}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const hoverItem = useHoverItem();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const editableProcessorUniqueId = useEditableProcessorUniqueId();

	const canBeDragged = canUpdatePageStructure && !editableProcessorUniqueId;

	const selectItem = useSelectItem();

	const {
		canDropOverTarget,
		isOverTarget,
		sourceItem,
		targetPosition,
		targetRef,
	} = useDropTarget(item);

	const name =
		getLayoutDataItemLabel(item, fragmentEntryLinks) ||
		Liferay.Language.get('element');

	const {handlerRef, isDraggingSource} = useDragItem(
		{
			...item,
			name,
		},
		(parentItemId, position) =>
			dispatch(
				moveItem({
					itemId: item.itemId,
					parentItemId,
					position,
					segmentsExperienceId,
				})
			)
	);

	const commentsPanelId = config.sidebarPanels?.comments?.sidebarPanelId;

	const notDroppableMessage =
		isOverTarget && !canDropOverTarget
			? Liferay.Util.sub(
					Liferay.Language.get('a-x-cannot-be-dropped-inside-a-x'),
					[
						getLayoutDataItemLabel(sourceItem, fragmentEntryLinks),
						getLayoutDataItemLabel(item, fragmentEntryLinks),
					]
			  )
			: null;

	return (
		<div
			className={classNames(className, 'page-editor__topper', {
				active: isActive,
				'drag-over-bottom':
					isOverTarget && targetPosition === TARGET_POSITIONS.BOTTOM,
				'drag-over-left':
					isOverTarget && targetPosition === TARGET_POSITIONS.LEFT,
				'drag-over-middle':
					isOverTarget && targetPosition === TARGET_POSITIONS.MIDDLE,
				'drag-over-right':
					isOverTarget && targetPosition === TARGET_POSITIONS.RIGHT,
				'drag-over-top':
					isOverTarget && targetPosition === TARGET_POSITIONS.TOP,
				dragged: isDraggingSource,
				hovered: isHovered,
				'not-droppable': !!notDroppableMessage,
				'page-editor__topper--mapped': isMapped,
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
			ref={canBeDragged ? handlerRef : null}
			style={style}
		>
			<TopperLabel
				isActive={isActive}
				isMapped={isMapped}
				itemElement={itemElement}
			>
				<ul className="tbar-nav">
					{canBeDragged && (
						<TopperListItem className="page-editor__topper__drag-handler">
							<ClayIcon
								className="page-editor__topper__drag-icon page-editor__topper__icon"
								symbol="drag"
							/>
						</TopperListItem>
					)}

					<TopperListItem
						className="d-inline-block page-editor__topper__title"
						expand
						title={name}
					>
						{name}
					</TopperListItem>
					{item.type === LAYOUT_DATA_ITEM_TYPES.fragment && (
						<TopperListItem>
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
						</TopperListItem>
					)}
					{canUpdatePageStructure && (
						<TopperListItem>
							<ItemActions item={item} />
						</TopperListItem>
					)}
				</ul>
			</TopperLabel>

			<div className="page-editor__topper__content" ref={targetRef}>
				<TopperErrorBoundary>
					{React.cloneElement(children, {
						data: notDroppableMessage
							? {
									'data-not-droppable-message': notDroppableMessage,
							  }
							: null,
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

function TopperLabel({children, isActive, isMapped, itemElement}) {
	const [isInset, setIsInset] = useState(false);
	const [windowScrollPosition, setWindowScrollPosition] = useState(0);

	useEffect(() => {
		if (isActive) {
			const handleWindowScroll = () => {
				setWindowScrollPosition(window.scrollY);
			};

			window.addEventListener('scroll', handleWindowScroll);

			return () => {
				window.removeEventListener('scroll', handleWindowScroll);
			};
		}
	}, [isActive]);

	useEffect(() => {
		if (itemElement && isActive) {
			const itemTop =
				itemElement.getBoundingClientRect().top - TOPPER_BAR_HEIGHT;

			const controlMenuContainerHeight =
				document.querySelector('.control-menu-container')
					?.offsetHeight ?? 0;

			if (itemTop < controlMenuContainerHeight) {
				setIsInset(true);
			}
			else {
				setIsInset(false);
			}
		}
	}, [isActive, itemElement, windowScrollPosition]);

	return (
		<div
			className={classNames('page-editor__topper__bar', 'tbar', {
				'page-editor__topper__bar--inset': isInset,
				'page-editor__topper__bar--mapped': isMapped,
			})}
		>
			{children}
		</div>
	);
}

TopperLabel.propTypes = {
	isActive: PropTypes.bool,
	isMapped: PropTypes.bool,
	itemElement: PropTypes.object,
};
