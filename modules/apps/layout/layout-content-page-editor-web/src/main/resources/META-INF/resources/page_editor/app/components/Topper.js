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
import React, {useEffect, useMemo, useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import {switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';
import selectCanUpdateItemConfiguration from '../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import {useDispatch, useSelector} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import moveItem from '../thunks/moveItem';
import getLayoutDataItemLabel from '../utils/getLayoutDataItemLabel';
import {
	TARGET_POSITION,
	useDragItem,
	useDropTarget,
} from '../utils/useDragAndDrop';
import {
	useActiveItemId,
	useHoverItem,
	useHoveredItemId,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from './Controls';
import hasDropZoneChild from './layout-data-items/hasDropZoneChild';

const TOPPER_BAR_HEIGHT = 24;

const itemIsMappedCollection = (item) =>
	item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
	'collection' in item.config;

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

export default function ({children, item, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);

	if (canUpdatePageStructure || canUpdateItemConfiguration) {
		return (
			<Topper item={item} {...props}>
				{children}
			</Topper>
		);
	}

	return children;
}

function Topper({children, item, itemElement, layoutData}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const activeItemId = useActiveItemId();
	const hoveredItemId = useHoveredItemId();
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isActive = useIsActive();
	const selectItem = useSelectItem();

	const {
		canDropOverTarget,
		isOverTarget,
		sourceItem,
		targetPosition,
		targetRef,
	} = useDropTarget(item, layoutData);

	const {handlerRef, isDraggingSource} = useDragItem(
		item,
		(parentItemId, position) =>
			dispatch(
				moveItem({
					itemId: item.itemId,
					parentItemId,
					position,
					store,
				})
			)
	);

	const itemIsRemovable = useMemo(
		() => canUpdatePageStructure && isRemovable(item, layoutData),
		[canUpdatePageStructure, item, layoutData]
	);

	const commentsPanelId = config.sidebarPanels?.comments?.sidebarPanelId;

	const fragmentEntryLinks = store.fragmentEntryLinks;

	const [isInset, setIsInset] = useState(false);
	const [windowScrollPosition, setWindowScrollPosition] = useState(0);

	const fragmentShouldBeHovered = () => {
		const [activeItemfragmentEntryLinkId] = activeItemId
			? activeItemId.split('-')
			: '';
		const [hoveredItemfragmentEntryLinkId] = hoveredItemId
			? hoveredItemId.split('-')
			: '';

		const childIsActive =
			Number(activeItemfragmentEntryLinkId) ===
			item.config.fragmentEntryLinkId;
		const childIsHovered =
			Number(hoveredItemfragmentEntryLinkId) ===
			item.config.fragmentEntryLinkId;

		return (
			item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
			(isHovered(item.itemId) || (childIsActive && childIsHovered))
		);
	};

	useEffect(() => {
		const handleWindowScroll = () => {
			setWindowScrollPosition(window.scrollY);
		};

		window.addEventListener('scroll', handleWindowScroll);

		return () => {
			window.removeEventListener('scroll', handleWindowScroll);
		};
	}, []);

	useEffect(() => {
		if (itemElement) {
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
	}, [itemElement, layoutData, windowScrollPosition]);

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
			className={classNames('page-editor__topper', {
				active: isActive(item.itemId),
				'drag-over-bottom':
					isOverTarget && targetPosition === TARGET_POSITION.BOTTOM,
				'drag-over-middle':
					isOverTarget && targetPosition === TARGET_POSITION.MIDDLE,
				'drag-over-top':
					isOverTarget && targetPosition === TARGET_POSITION.TOP,
				dragged: isDraggingSource,
				hovered: isHovered(item.itemId) || fragmentShouldBeHovered(),
				'not-droppable': !!notDroppableMessage,
				'page-editor__topper--mapped': itemIsMappedCollection(item),
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

				if (isHovered(item.itemId)) {
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
			ref={canUpdatePageStructure ? handlerRef : null}
		>
			<div
				className={classNames('page-editor__topper__bar', 'tbar', {
					'page-editor__topper__bar--inset': isInset,
					'page-editor__topper__bar--mapped': itemIsMappedCollection(
						item
					),
				})}
			>
				<ul className="tbar-nav">
					<TopperListItem className="page-editor__topper__drag-handler">
						{canUpdatePageStructure && (
							<ClayIcon
								className="page-editor__topper__drag-icon page-editor__topper__icon"
								symbol="drag"
							/>
						)}
					</TopperListItem>
					<TopperListItem
						className="page-editor__topper__title"
						expand
					>
						{getLayoutDataItemLabel(item, fragmentEntryLinks) ||
							Liferay.Language.get('element')}
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
					{itemIsRemovable && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								onClick={(event) => {
									event.stopPropagation();

									dispatch(
										deleteItem({
											itemId: item.itemId,
											store,
										})
									);
								}}
								small
								title={Liferay.Language.get('remove')}
							>
								<ClayIcon
									className="page-editor__topper__icon"
									symbol="times-circle"
								/>
							</ClayButton>
						</TopperListItem>
					)}
				</ul>
			</div>
			<div className="page-editor__topper__content" ref={targetRef}>
				{notDroppableMessage
					? React.cloneElement(children, {
							data: {
								'data-not-droppable-message': notDroppableMessage,
							},
					  })
					: children}
			</div>
		</div>
	);
}

Topper.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	itemElement: PropTypes.object,
	layoutData: LayoutDataPropTypes.isRequired,
};

function isRemovable(item, layoutData) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return false;
	}

	return !hasDropZoneChild(item, layoutData);
}
