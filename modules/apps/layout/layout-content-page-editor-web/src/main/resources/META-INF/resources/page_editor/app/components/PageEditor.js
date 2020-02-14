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
import classNames from 'classnames';
import {useEventListener, useIsMounted} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useRef} from 'react';

import {
	ARROW_DOWN_KEYCODE,
	ARROW_UP_KEYCODE
} from '../config/constants/keycodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {MOVE_ITEM_DIRECTIONS} from '../config/constants/moveItemDirections';
import {PAGE_TYPES} from '../config/constants/pageTypes';
import {ConfigContext} from '../config/index';
import {useDispatch, useSelector} from '../store/index';
import moveItem from '../thunks/moveItem';
import {useActiveItemId, useIsActive, useSelectItem} from './Controls';
import DragPreview from './DragPreview';
import {EditableDecorationProvider} from './fragment-content/EditableDecorationContext';
import {EditableProcessorContextProvider} from './fragment-content/EditableProcessorContext';
import {
	ColumnWithControls,
	ContainerWithControls,
	DropZoneWithControls,
	FragmentWithControls,
	Root,
	RowWithControls
} from './layout-data-items/index';
import {DragDropManager} from './useDragAndDrop';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: ColumnWithControls,
	[LAYOUT_DATA_ITEM_TYPES.container]: ContainerWithControls,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneWithControls,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: FragmentWithControls,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: RowWithControls
};

export default function PageEditor({withinMasterPage = false}) {
	const activeItemId = useActiveItemId();
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const layoutData = useSelector(state => state.layoutData);
	const selectItem = useSelectItem();
	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);
	const store = useSelector(state => state);

	const {layoutConversionWarningMessages, pageType} = config;

	const mainItem = layoutData.items[layoutData.rootItems.main];

	const onClick = event => {
		if (event.target === event.currentTarget) {
			selectItem(null, {multiSelect: event.shiftKey});
		}
	};

	const getDirection = keycode => {
		let direction = null;

		if (keycode === ARROW_UP_KEYCODE) {
			direction = MOVE_ITEM_DIRECTIONS.UP;
		}
		else if (keycode === ARROW_DOWN_KEYCODE) {
			direction = MOVE_ITEM_DIRECTIONS.DOWN;
		}

		return direction;
	};

	const onKeyUp = useCallback(
		event => {
			event.preventDefault();

			if (!activeItemId) {
				return;
			}

			const item = layoutData.items[activeItemId];

			if (!item) {
				return;
			}

			const {itemId, parentId} = item;

			const direction = getDirection(event.keyCode);
			const parentItem = layoutData.items[parentId];

			if (direction) {
				const numChildren = parentItem.children.length;
				const currentPosition = parentItem.children.indexOf(itemId);

				if (
					(direction === MOVE_ITEM_DIRECTIONS.UP &&
						currentPosition === 0) ||
					(direction === MOVE_ITEM_DIRECTIONS.DOWN &&
						currentPosition === numChildren - 1)
				) {
					return;
				}

				let position;
				if (direction === MOVE_ITEM_DIRECTIONS.UP) {
					position = currentPosition - 1;
				}
				else if (direction === MOVE_ITEM_DIRECTIONS.DOWN) {
					position = currentPosition + 1;
				}

				dispatch(
					moveItem({
						config,
						itemId,
						parentItemId: parentId,
						position,
						store
					})
				);
			}
		},
		[activeItemId, config, dispatch, layoutData.items, store]
	);

	useEventListener('keyup', onKeyUp, false, document.body);

	const isPageConversion = pageType === PAGE_TYPES.conversion;
	const hasWarningMessages =
		isPageConversion &&
		layoutConversionWarningMessages &&
		layoutConversionWarningMessages.length > 0;

	return (
		<>
			{isPageConversion && (
				<div
					className={classNames('page-editor__conversion-messages', {
						'page-editor__conversion-messages--with-sidebar-open': sidebarOpen
					})}
				>
					<ClayAlert
						displayType="info"
						title={Liferay.Language.get(
							'page-conversion-description'
						)}
						variant="stripe"
					/>

					{hasWarningMessages && (
						<ClayAlert displayType="warning" variant="stripe">
							{layoutConversionWarningMessages.map(message => (
								<>
									{message}
									<br />
								</>
							))}
						</ClayAlert>
					)}
				</div>
			)}

			<div
				className={classNames('page-editor', {
					'page-editor--with-sidebar': !withinMasterPage,
					'page-editor--with-sidebar-open':
						sidebarOpen && !withinMasterPage,
					'pt-4': !withinMasterPage
				})}
				id="page-editor"
				onClick={onClick}
			>
				<DragPreview />

				<EditableProcessorContextProvider>
					<EditableDecorationProvider>
						<DragDropManager>
							<LayoutDataItem
								fragmentEntryLinks={fragmentEntryLinks}
								item={mainItem}
								layoutData={layoutData}
							/>
						</DragDropManager>
					</EditableDecorationProvider>
				</EditableProcessorContextProvider>
			</div>
		</>
	);
}

function LayoutDataItem({fragmentEntryLinks, item, layoutData, ...otherProps}) {
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
						{...otherProps}
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
