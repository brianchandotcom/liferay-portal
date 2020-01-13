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

import FloatingToolbar from '../components/FloatingToolbar';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import duplicateFragment from '../thunks/duplicateFragment';
import {useIsActive} from './Controls';
import DragPreview from './DragPreview';
import FragmentContent from './FragmentContent';
import Topper from './Topper';

function Root({canDrop, children, isOver}, ref) {
	return (
		<div
			className={classNames('page-editor__root', {
				'page-editor__root--active': isOver && canDrop
			})}
			ref={ref}
		>
			{React.Children.count(children) ? (
				children
			) : (
				<div className="taglib-empty-result-message">
					<div className="taglib-empty-result-message-header"></div>
					<div className="text-center text-muted">
						{Liferay.Language.get('place-fragments-here')}
					</div>
				</div>
			)}
		</div>
	);
}

function Container({children, item}, ref) {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingBottom,
		paddingHorizontal,
		paddingTop,
		type
	} = item.config;

	return (
		<div
			className={classNames(
				`page-editor__container pb-${paddingBottom} pt-${paddingTop} px-${paddingHorizontal}`,
				{
					[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
					container: type === 'fixed',
					'container-fluid': type === 'fluid',
					empty: !item.children.length
				}
			)}
			ref={ref}
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
			<div className="page-editor__container-outline">{children}</div>
		</div>
	);
}

function Row({children, item, layoutData}, ref) {
	const parent = layoutData.items[item.parentId];

	const rowContent = (
		<div className="page-editor__row-outline" ref={ref}>
			<div
				className={classNames('page-editor__row row', {
					empty: !item.children.some(
						childId => layoutData.items[childId].children.length
					),
					'no-gutters': !item.config.gutters
				})}
			>
				{children}
			</div>
		</div>
	);

	return !parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
		<div className="container-fluid">{rowContent}</div>
	) : (
		rowContent
	);
}

function Column({children, className, item}, ref) {
	const {size} = item.config;

	return (
		<div
			className={classNames(className, 'page-editor__col', 'col', {
				[`col-${size}`]: size
			})}
			ref={ref}
		>
			{children}
		</div>
	);
}

function Fragment({item}, ref) {
	const {fragmentEntryLinks} = useContext(StoreContext);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	return <FragmentContent fragmentEntryLink={fragmentEntryLink} ref={ref} />;
}

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: React.forwardRef(Column),
	[LAYOUT_DATA_ITEM_TYPES.container]: React.forwardRef(Container),
	[LAYOUT_DATA_ITEM_TYPES.fragment]: React.forwardRef(Fragment),
	[LAYOUT_DATA_ITEM_TYPES.root]: React.forwardRef(Root),
	[LAYOUT_DATA_ITEM_TYPES.row]: React.forwardRef(Row)
};

const LAYOUT_DATA_ACCEPT_DROP_TYPES = {
	[LAYOUT_DATA_ITEM_TYPES.column]: [LAYOUT_DATA_ITEM_TYPES.fragment],
	[LAYOUT_DATA_ITEM_TYPES.container]: [
		LAYOUT_DATA_ITEM_TYPES.fragment,
		LAYOUT_DATA_ITEM_TYPES.row
	],
	[LAYOUT_DATA_ITEM_TYPES.fragment]: [
		LAYOUT_DATA_ITEM_TYPES.fragment,
		LAYOUT_DATA_ITEM_TYPES.row
	],
	[LAYOUT_DATA_ITEM_TYPES.root]: [
		LAYOUT_DATA_ITEM_TYPES.fragment,
		LAYOUT_DATA_ITEM_TYPES.container,
		LAYOUT_DATA_ITEM_TYPES.row
	],
	[LAYOUT_DATA_ITEM_TYPES.row]: [
		LAYOUT_DATA_ITEM_TYPES.fragment,
		LAYOUT_DATA_ITEM_TYPES.row
	]
};

const LAYOUT_DATA_TOPPER_ACTIVE = {
	[LAYOUT_DATA_ITEM_TYPES.column]: false,
	[LAYOUT_DATA_ITEM_TYPES.container]: true,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: true,
	[LAYOUT_DATA_ITEM_TYPES.root]: false,
	[LAYOUT_DATA_ITEM_TYPES.row]: true
};

const LAYOUT_DATA_FLOATING_TOOLBAR_TYPES = {
	[LAYOUT_DATA_ITEM_TYPES.column]: [],
	[LAYOUT_DATA_ITEM_TYPES.container]: [
		LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
	],
	[LAYOUT_DATA_ITEM_TYPES.fragment]: [
		LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment,
		LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
	],
	[LAYOUT_DATA_ITEM_TYPES.root]: [],
	[LAYOUT_DATA_ITEM_TYPES.row]: [
		LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration
	]
};

function LayoutDataItem({fragmentEntryLinks, item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];
	const floatingToolbarButtons =
		LAYOUT_DATA_FLOATING_TOOLBAR_TYPES[item.type];
	const isActive = useIsActive()(item.itemId);
	const isActiveTopper = LAYOUT_DATA_TOPPER_ACTIVE[item.type];
	const isMounted = useIsMounted();
	const componentRef = useRef(null);
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);

	const fragmentEntryLink = fragmentEntryLinks[
		item.config.fragmentEntryLinkId
	] || {name: item.type};

	useEffect(() => {
		if (isActive && componentRef.current && isMounted()) {
			componentRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'nearest',
				inline: 'nearest'
			});
		}
	}, [componentRef, isActive, isMounted]);

	return (
		<Topper
			acceptDrop={LAYOUT_DATA_ACCEPT_DROP_TYPES[item.type]}
			active={isActiveTopper}
			item={item}
			layoutData={layoutData}
			name={fragmentEntryLink.name}
		>
			{({canDrop, isOver}) => (
				<>
					{floatingToolbarButtons.length > 0 && (
						<FloatingToolbar
							buttons={floatingToolbarButtons}
							item={item}
							itemRef={componentRef}
							onButtonClick={id => {
								if (
									id ===
									LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS
										.duplicateFragment.id
								) {
									dispatch(
										duplicateFragment({
											config,
											fragmentEntryLinkId:
												item.config.fragmentEntryLinkId,
											itemId: item.itemId,
											store
										})
									);
								}
							}}
						/>
					)}

					<Component
						canDrop={canDrop}
						isOver={isOver}
						item={item}
						layoutData={layoutData}
						ref={componentRef}
					>
						{item.children.map(childId => {
							return (
								<LayoutDataItem
									fragmentEntryLinks={fragmentEntryLinks}
									item={layoutData.items[childId]}
									key={childId}
									layoutData={layoutData}
								/>
							);
						})}
					</Component>
				</>
			)}
		</Topper>
	);
}

export default function PageEditor() {
	const {
		fragmentEntryLinks,
		layoutData,
		sidebarOpen,
		sidebarPanelId
	} = useContext(StoreContext);

	const mainItem = layoutData.items[layoutData.rootItems.main];

	return (
		<div
			className={classNames('page-editor', 'page-editor--with-sidebar', {
				'page-editor--with-sidebar-open': sidebarPanelId && sidebarOpen
			})}
		>
			<DragPreview />
			<LayoutDataItem
				fragmentEntryLinks={fragmentEntryLinks}
				item={mainItem}
				layoutData={layoutData}
			/>
		</div>
	);
}
