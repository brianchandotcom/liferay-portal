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

import FloatingToolbar from '../components/FloatingToolbar';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import updateLayoutData from '../thunks/updateLayoutData';
import Topper from './Topper';
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
		<div ref={drop} style={{background, height: '100vh'}}>
			{children}
		</div>
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

	const containerRef = useRef(null);

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
		<Topper item={item} name="Container">
			<FloatingToolbar
				buttons={[
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.backgroundColor,
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.layoutBackgroundImage,
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.spacing
				]}
				item={item}
				itemRef={containerRef}
			/>

			<div
				className={classNames(`container py-${paddingVertical}`, {
					[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
					container: type === 'fixed',
					'container-fluid': type === 'fluid',
					[`px-${paddingHorizontal}`]: paddingHorizontal !== 3
				})}
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
		</Topper>
	);
}

function Row({children, item}) {
	const {layoutData} = useContext(StoreContext);
	const parent = layoutData.items[item.parentId];
	const rowRef = useRef(null);

	const [, drop] = useDrop({
		accept: [LAYOUT_DATA_ITEM_TYPES.column]
	});

	const rowContent = (
		<>
			<FloatingToolbar
				buttons={[LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.spacing]}
				item={item}
				itemRef={rowRef}
			/>

			<div
				className={classNames('row', {
					empty: !item.children.some(
						childId => layoutData.items[childId].children.length
					),
					'no-gutters': !item.config.gutters
				})}
				ref={node => {
					rowRef.current = node;
					drop(node);
				}}
			>
				{children}
			</div>
		</>
	);

	return !parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
		<Topper item={item} name="Row">
			<div className="container-fluid p-0">{rowContent}</div>
		</Topper>
	) : (
		<Topper item={item} name="Row">
			{rowContent}
		</Topper>
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

	const fragmentRef = useRef(null);

	return (
		<Topper item={item} name={fragmentEntryLink.name}>
			<FloatingToolbar
				buttons={[
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
				]}
				item={item}
				itemRef={fragmentRef}
			/>

			<UnsafeHTML
				className="fragment"
				markup={markup}
				ref={fragmentRef}
			/>
		</Topper>
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
