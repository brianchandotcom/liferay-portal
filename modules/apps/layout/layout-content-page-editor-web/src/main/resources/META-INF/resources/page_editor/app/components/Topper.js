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
import React, {useContext, useRef, useMemo} from 'react';

import useOnClickOutside from '../../core/hooks/useOnClickOutside';
import {switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import moveItem from '../thunks/moveItem';
import {
	useCurrentFloatingToolbar,
	useIsSelected,
	useIsHovered,
	useSelectItem,
	useHoverItem
} from './Controls';
import useDragAndDrop, {EDGE} from './useDragAndDrop';

const TopperListItem = React.forwardRef(
	({children, className, expand, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'page-editor-topper__item',
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

export default function Topper({acceptDrop, children, item, layoutData}) {
	const containerRef = useRef(null);
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const floatingToolbarRef = useCurrentFloatingToolbar();

	const {canDrop, drag, drop, edge, isDragging, isOver} = useDragAndDrop({
		accept: acceptDrop,
		containerRef,
		item,
		layoutData,
		onDragEnd: data =>
			dispatch(
				moveItem({
					...data,
					config,
					store
				})
			)
	});

	useOnClickOutside(
		[containerRef.current, floatingToolbarRef.current],
		event => {
			if (!event.shiftKey) {
				selectItem(null);
			}
		}
	);

	const showDeleteButton = useMemo(() => isRemovable(item, layoutData), [
		item,
		layoutData
	]);

	const childrenElement = children({canDrop, isOver});

	const {sidebarPanels} = config;

	const commentsPanelId = sidebarPanels.comments.sidebarPanelId;

	const fragmentEntryLinks = store.fragmentEntryLinks;

	const getName = (item, fragmentEntryLinks) => {
		let name;

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
			name = Liferay.Language.get('container');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
			name = Liferay.Language.get('column');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			name = Liferay.Language.get('drop-zone');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
			name = Liferay.Language.get('row');
		}

		return name;
	};

	return (
		<div
			className={classNames({
				active: isSelected(item.itemId),
				'drag-over-bottom': edge === EDGE.BOTTOM && isOver,
				'drag-over-top': edge === EDGE.TOP && isOver,
				dragged: isDragging,
				hovered: isHovered(item.itemId),
				'page-editor-topper': true
			})}
			onClick={event => {
				event.stopPropagation();

				if (
					!acceptDrop.length ||
					isDragging ||
					floatingToolbarRef.current
				) {
					return;
				}

				const multiSelect = event.shiftKey;

				selectItem(item.itemId, {multiSelect});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				if (!acceptDrop.length || isDragging) {
					return;
				}

				if (isHovered(item.itemId)) {
					hoverItem(null);
				}
			}}
			onMouseOver={event => {
				event.stopPropagation();

				if (!acceptDrop.length || isDragging) {
					return;
				}

				hoverItem(item.itemId);
			}}
			ref={containerRef}
		>
			<div className="page-editor-topper__bar tbar">
				<ul className="tbar-nav">
					<TopperListItem
						className="page-editor-topper__drag-handler"
						ref={drag}
					>
						<ClayIcon
							className="page-editor-topper__drag-icon page-editor-topper__icon"
							symbol="drag"
						/>
					</TopperListItem>
					<TopperListItem
						className="page-editor-topper__title"
						expand
					>
						{getName(item, fragmentEntryLinks) ||
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
									className="page-editor-topper__icon"
									onClick={() => {
										dispatch(
											switchSidebarPanel({
												sidebarOpen: true,
												sidebarPanelId: commentsPanelId
											})
										);
									}}
									symbol="comments"
								/>
							</ClayButton>
						</TopperListItem>
					)}
					{showDeleteButton && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								onClick={event => {
									event.stopPropagation();
									dispatch(
										deleteItem({
											config,
											itemId: item.itemId,
											store
										})
									);
								}}
								small
								title={Liferay.Language.get('remove')}
							>
								<ClayIcon
									className="page-editor-topper__icon"
									symbol="times-circle"
								/>
							</ClayButton>
						</TopperListItem>
					)}
				</ul>
			</div>

			<div className="page-editor-topper__content" ref={drop}>
				{childrenElement}
			</div>
		</div>
	);
}

function isRemovable(item, layoutData) {
	function hasDropZoneChildren(item, layoutData) {
		return item.children.some(childrenId => {
			const children = layoutData.items[childrenId];

			return children.type === LAYOUT_DATA_ITEM_TYPES.dropZone
				? true
				: hasDropZoneChildren(children, layoutData);
		});
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return false;
	}

	return !hasDropZoneChildren(item, layoutData);
}
