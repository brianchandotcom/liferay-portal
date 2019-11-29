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

import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useContext, useEffect, useRef} from 'react';
import {useDrop} from 'react-dnd';

import useOnClickOutside from '../../core/hooks/useOnClickOutside';
import hideFloatingToolbar from '../actions/hideFloatingToolbar';
import showFloatingToolbar from '../actions/showFloatingToolbar';
import {FloatingToolbarContext} from '../components/FloatingToolbarProvider';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import updateLayoutData from '../thunks/updateLayoutData';
import {TopperProvider} from './Topper';
import TopperBar from './TopperBar';
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
			<div ref={drop} style={{background, height: '100vh'}}>
				{children}
			</div>
		</TopperProvider>
	);
}

function Container({children, item}) {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingHorizontal,
		paddingVertical,
		type
	} = item.config;

	const {dispatch} = useContext(FloatingToolbarContext);

	const containerRef = useRef(null);

	useOnClickOutside(containerRef, () =>
		dispatch(
			hideFloatingToolbar({
				targetContainerRef: containerRef
			})
		)
	);

	const [, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.fragment, LAYOUT_DATA_ITEM_TYPES.row],
		collect(monitor) {
			return {
				canDrop: monitor.canDrop(),
				isOver: monitor.isOver()
			};
		},
		drop(_, monitor) {
			if (!monitor.didDrop()) {
				return {
					itemType: monitor.getItemType(),
					parentId: item.itemId,
					position: item.children.length + 1
				};
			}
		}
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
				onClick={event => {
					event.preventDefault();

					/**
					 * Mocked buttons to render the FloatingToolbar on Container. However,
					 * the mechanism just needs dispatch an action with previously-defined
					 * constants to render FloatingToolbar buttons.
					 */
					dispatch(
						showFloatingToolbar({
							buttons: [
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.backgroundColor,
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.edit,
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.spacing
							],
							targetContainerRef: containerRef
						})
					);
				}}
				ref={node => {
					containerRef.current = node;
					drop(node);
				}}
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

	const {dispatch} = useContext(FloatingToolbarContext);

	const fragmentRef = useRef(null);

	useOnClickOutside(fragmentRef, () =>
		dispatch(
			hideFloatingToolbar({
				targetContainerRef: fragmentRef
			})
		)
	);

	return (
		<TopperBar item={item} name={fragmentEntryLink.name}>
			<UnsafeHTML
				className="fragment"
				markup={markup}
				onClick={event => {
					event.preventDefault();

					/**
					 * Mocked buttons to render the FloatingToolbar on Fragment. However,
					 * the mechanism just needs dispatch an action with previously-defined
					 * constants to render FloatingToolbar buttons.
					 */
					dispatch(
						showFloatingToolbar({
							buttons: [
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.backgroundColor,
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment
							],
							targetContainerRef: fragmentRef
						})
					);
				}}
				ref={fragmentRef}
			/>
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
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const {layoutData, segmentsExperienceId} = useContext(StoreContext);

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
						config,
						layoutData,
						segmentsExperienceId
					})
				);
			}
		}
	}, [config, dispatch, isMounted, layoutData, segmentsExperienceId]);

	return <LayoutDataItem item={mainItem} layoutData={layoutData} />;
}
