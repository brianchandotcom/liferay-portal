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
import {useIsMounted} from 'frontend-js-react-web';
import React, {useContext, useState, useEffect, useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import {Permission} from '../../common/index';
import {moveItem} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {getConfig} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import updateLayoutData from '../thunks/updateLayoutData';
import Topper, {
	TopperProvider,
	TopperContext,
	TOPPER_ACTIVE,
	TOPPER_HOVER
} from './Topper';
import UnsafeHTML from './UnsafeHTML';

function Root({children, item}) {
	const dropItem = item;

	const [{canDrop, isOver}, drop] = useDrop({
		accept: [
			LAYOUT_DATA_ITEM_TYPES.column,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.root,
			LAYOUT_DATA_ITEM_TYPES.row
		],
		collect(monitor) {
			return {
				canDrop: monitor.canDrop(),
				isOver: monitor.isOver({shallow: true})
			};
		},
		drop(_, monitor) {
			if (!monitor.didDrop()) {
				return {
					itemType: monitor.getItemType(),
					parentId: dropItem.itemId,
					position: dropItem.children.length + 1
				};
			}
		}
	});

	const active = isOver && canDrop;
	const background = active ? 'honeydew' : 'aliceblue';

	return (
		<TopperProvider>
			<div ref={drop} style={{background, height: '100%'}}>
				{children}
			</div>
		</TopperProvider>
	);
}

const TopperBar = ({children, item, name}) => {
	const containerRef = useRef(null);
	const [dragHover, setDragHover] = useState(null);
	const [{active, hover}, dispatch] = useContext(TopperContext);
	const {layoutData} = useContext(StoreContext);
	const dispatchStore = useContext(DispatchContext);

	const [{isDragging}, drag] = useDrag({
		collect: monitor => ({
			isDragging: monitor.isDragging()
		}),
		end(_item, _monitor) {
			const {itemId, position, siblingId} = _monitor.getDropResult();

			dispatchStore(moveItem({itemId, position, siblingId}));
		},
		item: {
			...item,
			type: LAYOUT_DATA_ITEM_TYPES[item.type]
		}
	});

	const [{isOver}, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.fragment],
		collect(_monitor) {
			return {
				isOver: _monitor.isOver({shallow: true})
			};
		},
		drop(_item, _monitor) {
			if (!_monitor.didDrop()) {
				return {
					itemId: _item.itemId,
					itemType: _monitor.getItemType(),
					position: dragHover,
					siblingId: item.itemId
				};
			}
		},
		hover(_item, _monitor) {
			const dragId = _item.itemId;
			const dragParentId = _item.parentId;
			const hoverId = item.itemId;

			// Don't replace items with themselves
			if (dragId === hoverId) {
				setDragHover(null);

				return;
			}

			// Determine rectangle on screen
			const hoverBoundingRect = containerRef.current.getBoundingClientRect();

			// Get vertical middle
			const hoverMiddleY =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			// Determine mouse position
			const clientOffset = _monitor.getClientOffset();

			// Get pixels to the top
			const hoverClientY = clientOffset.y - hoverBoundingRect.top;

			const parentChildren = layoutData.items[dragParentId].children;

			const dragIndex = parentChildren.findIndex(
				child => child === dragId
			);

			// When dragging downwards, only move when the cursor is below 50%
			// When dragging upwards, only move when the cursor is above 50%
			// Dragging downwards
			if (
				parentChildren[dragIndex + 1] !== hoverId &&
				hoverClientY < hoverMiddleY
			) {
				setDragHover(0);
				return;
			}

			// Dragging upwards
			if (
				parentChildren[dragIndex - 1] !== hoverId &&
				hoverClientY > hoverMiddleY
			) {
				setDragHover(1);
				return;
			}

			setDragHover(null);
		}
	});

	return (
		<div
			className={classNames(
				'fragments-editor__drag-source fragments-editor__drag-source--fragment fragments-editor__drop-target fragments-editor__topper-wrapper fragment-entry-link-wrapper',
				{
					'fragments-editor__topper-wrapper--active':
						active === item.itemId,
					'fragments-editor__topper-wrapper--hovered fragment-entry-link-wrapper--hovered':
						hover === item.itemId,
					'fragments-editor-border-bottom': dragHover === 1 && isOver,
					'fragments-editor-border-top': dragHover === 0 && isOver
				}
			)}
			onClick={event => {
				event.stopPropagation();

				dispatch({payload: item.itemId, type: TOPPER_ACTIVE});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				dispatch({payload: null, type: TOPPER_HOVER});
			}}
			onMouseOver={event => {
				if (!isDragging) {
					event.stopPropagation();

					dispatch({payload: item.itemId, type: TOPPER_HOVER});
				}
			}}
			ref={containerRef}
		>
			<Permission>
				<Topper>
					<Topper.Item className="pr-0" isDragHandler ref={drag}>
						<ClayIcon
							className="fragments-editor__topper__drag-icon fragments-editor__topper__icon"
							symbol="drag"
						/>
					</Topper.Item>
					<Topper.Item expand isDragHandler isTitle ref={drag}>
						{name}
					</Topper.Item>
					<Topper.Item>
						<ClayButton displayType="unstyled" small>
							<ClayIcon
								className="fragments-editor__topper__icon"
								symbol="comments"
							/>
						</ClayButton>
					</Topper.Item>
					<Topper.Item>
						<ClayButton displayType="unstyled" small>
							<ClayIcon
								className="fragments-editor__topper__icon"
								symbol="times-circle"
							/>
						</ClayButton>
					</Topper.Item>
				</Topper>
			</Permission>
			<div
				className={classNames('fragment-entry-link-content', {
					dragged: isDragging
				})}
				ref={drop}
			>
				{children}
			</div>
		</div>
	);
};

function Container({children, item}) {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingHorizontal,
		paddingVertical,
		type
	} = item.config;

	const [, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.fragment, LAYOUT_DATA_ITEM_TYPES.row]
	});

	return (
		<TopperBar item={item} name="Container">
			<div
				className={classNames(`container py-${paddingVertical}`, {
					[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
					container: type === 'fixed',
					'container-fluid': type === 'fluid',
					[`px-${paddingHorizontal}`]: paddingHorizontal !== 3
				})}
				ref={drop}
				style={
					backgroundImage
						? {
								backgroundImage: `url(${backgroundImage})`,
								backgroundPosition: '50% 50%',
								backgroundRepeat: 'no-repeat',
								backgroundSize: 'cover'
						  }
						: {}
				}
			>
				{children}
			</div>
		</TopperBar>
	);
}

function Row({children, item}) {
	const {layoutData} = useContext(StoreContext);
	const parent = layoutData.items[item.parentId];

	const [, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.column]
	});

	const rowContent = (
		<div
			className={classNames('row', {
				empty: !item.children.some(
					childId => layoutData.items[childId].children.length
				),
				'no-gutters': !item.config.gutters
			})}
			ref={drop}
		>
			{children}
		</div>
	);

	return !parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
		<div className="container-fluid p-0">{rowContent}</div>
	) : (
		rowContent
	);
}

function Column({children, item}) {
	const containerRef = useRef(null);

	const [, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.fragment],
		collect(_monitor) {
			return {
				canDrop: _monitor.canDrop(),
				isOver: _monitor.isOver({shallow: true})
			};
		},
		drop(_item, _monitor) {
			if (!_monitor.didDrop()) {
				return {
					itemId: _item.itemId,
					itemType: _monitor.getItemType(),
					position: item.children.length + 1,
					siblingId: item.itemId
				};
			}
		}
	});

	const {size} = item.config;

	return (
		<div
			className={classNames('col', {[`col-${size}`]: size})}
			ref={node => {
				containerRef.current = node;
				drop(node);
			}}
		>
			{children}
		</div>
	);
}

function Fragment({item}) {
	const {fragmentEntryLinks} = useContext(StoreContext);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	let markup = '';

	if (typeof fragmentEntryLink.content === 'string') {
		markup = fragmentEntryLink.content;
	} else if (
		fragmentEntryLink.content.value &&
		fragmentEntryLink.content.value.content
	) {
		markup = fragmentEntryLink.content.value.content;
	} else {
		markup = `<div>No markup from ${item.config.fragmentEntryLinkId}</div>`;
	}

	return (
		<TopperBar item={item} name={fragmentEntryLink.name}>
			<UnsafeHTML className="fragment" markup={markup} />
		</TopperBar>
	);
}

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: Column,
	[LAYOUT_DATA_ITEM_TYPES.container]: Container,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Fragment,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: Row
};

const LayoutDataItem = React.forwardRef(({item, layoutData}, ref) => {
	const Component = LAYOUT_DATA_ITEMS[item.type];

	return (
		<Component item={item}>
			{item.children.map(childId => {
				return (
					<LayoutDataItem
						drag={ref}
						item={layoutData.items[childId]}
						key={childId}
						layoutData={layoutData}
					/>
				);
			})}
		</Component>
	);
});

export default function PageEditor() {
	const {portletNamespace} = getConfig();

	const dispatch = useContext(DispatchContext);

	const {
		classNameId,
		classPK,
		layoutData,
		segmentsExperienceId,
		updateLayoutPageTemplateDataURL
	} = useContext(StoreContext);

	const mainItem = layoutData.items[layoutData.rootItems.main];

	const isMounted = useIsMounted();
	const shouldUpdateLayoutData = useRef();

	useEffect(() => {
		if (isMounted()) {
			// Only run this after first render
			if (!shouldUpdateLayoutData.current) {
				shouldUpdateLayoutData.current = true;
			} else {
				dispatch(
					updateLayoutData({
						classNameId,
						classPK,
						layoutData,
						portletNamespace,
						segmentsExperienceId,
						updateLayoutPageTemplateDataURL
					})
				);
			}
		}
	}, [
		classNameId,
		classPK,
		dispatch,
		isMounted,
		layoutData,
		portletNamespace,
		segmentsExperienceId,
		updateLayoutPageTemplateDataURL
	]);

	return <LayoutDataItem item={mainItem} layoutData={layoutData} />;
}
