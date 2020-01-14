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

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: React.forwardRef(Column),
	[LAYOUT_DATA_ITEM_TYPES.container]: React.forwardRef(Container),
	[LAYOUT_DATA_ITEM_TYPES.fragment]: React.forwardRef(Fragment),
	[LAYOUT_DATA_ITEM_TYPES.root]: React.forwardRef(Root),
	[LAYOUT_DATA_ITEM_TYPES.row]: React.forwardRef(Row)
};

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

function LayoutDataItem({fragmentEntryLinks, item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];
	const isActive = useIsActive()(item.itemId);
	const isMounted = useIsMounted();
	const componentRef = useRef(null);

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
		<Component item={item} layoutData={layoutData} ref={componentRef}>
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
	);
}

function Root({children, item, layoutData}, ref) {
	return (
		<Topper
			acceptDrop={[
				LAYOUT_DATA_ITEM_TYPES.fragment,
				LAYOUT_DATA_ITEM_TYPES.container,
				LAYOUT_DATA_ITEM_TYPES.row
			]}
			active={false}
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('root')}
		>
			{({canDrop, isOver}) => (
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
			)}
		</Topper>
	);
}

function Container({children, item, layoutData}, ref) {
	const {
		backgroundColorCssClass,
		backgroundImage,
		paddingBottom,
		paddingHorizontal,
		paddingTop,
		type
	} = item.config;

	return (
		<Topper
			acceptDrop={[
				LAYOUT_DATA_ITEM_TYPES.fragment,
				LAYOUT_DATA_ITEM_TYPES.row
			]}
			active
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('container')}
		>
			{() => (
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
					<FloatingToolbar
						buttons={[
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
						]}
						item={item}
						itemRef={ref}
					/>

					<div className="page-editor__container-outline">
						{children}
					</div>
				</div>
			)}
		</Topper>
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

	return (
		<Topper
			acceptDrop={[LAYOUT_DATA_ITEM_TYPES.column]}
			active
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('row')}
		>
			{() => (
				<>
					<FloatingToolbar
						buttons={[
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration
						]}
						item={item}
						itemRef={ref}
					/>

					{!parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
						<div className="container-fluid p-0">{rowContent}</div>
					) : (
						rowContent
					)}
				</>
			)}
		</Topper>
	);
}

function Column({children, className, item, layoutData}, ref) {
	const {size} = item.config;

	return (
		<Topper
			acceptDrop={[
				LAYOUT_DATA_ITEM_TYPES.fragment,
				LAYOUT_DATA_ITEM_TYPES.row
			]}
			active={false}
			item={item}
			layoutData={layoutData}
			name={Liferay.Language.get('column')}
		>
			{() => (
				<div
					className={classNames(className, 'col page-editor__col', {
						[`col-${size}`]: size
					})}
					ref={ref}
				>
					{children}
				</div>
			)}
		</Topper>
	);
}

function Fragment({item, layoutData}, ref) {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);

	const {fragmentEntryLinks} = store;

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const handleButtonClick = id => {
		if (id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment.id) {
			dispatch(
				duplicateFragment({
					config,
					fragmentEntryLinkId: item.config.fragmentEntryLinkId,
					itemId: item.itemId,
					store
				})
			);
		}
	};

	return (
		<Topper
			acceptDrop={[LAYOUT_DATA_ITEM_TYPES.fragment]}
			active
			item={item}
			layoutData={layoutData}
			name={fragmentEntryLink.name}
		>
			{() => (
				<>
					<FloatingToolbar
						buttons={[
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment,
							LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
						]}
						item={item}
						itemRef={ref}
						onButtonClick={handleButtonClick}
					/>

					<FragmentContent
						fragmentEntryLink={fragmentEntryLink}
						ref={ref}
					/>
				</>
			)}
		</Topper>
	);
}
