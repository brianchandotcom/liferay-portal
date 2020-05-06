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
import {closest} from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {
	ARROW_DOWN_KEYCODE,
	ARROW_UP_KEYCODE,
} from '../config/constants/keycodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {MOVE_ITEM_DIRECTIONS} from '../config/constants/moveItemDirections';
import {PAGE_TYPES} from '../config/constants/pageTypes';
import {config} from '../config/index';
import {useDispatch, useSelector} from '../store/index';
import moveItem from '../thunks/moveItem';
import {
	useActivationOrigin,
	useActiveItemId,
	useIsActive,
	useSelectItem,
} from './Controls';
import {EditableProcessorContextProvider} from './fragment-content/EditableProcessorContext';
import FragmentWithControls from './layout-data-items/FragmentWithControls';
import {
	CollectionItemWithControls,
	CollectionWithControls,
	ColumnWithControls,
	ContainerWithControls,
	DropZoneWithControls,
	Root,
	RowWithControls,
} from './layout-data-items/index';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.collection]: CollectionWithControls,
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: CollectionItemWithControls,
	[LAYOUT_DATA_ITEM_TYPES.column]: ColumnWithControls,
	[LAYOUT_DATA_ITEM_TYPES.container]: ContainerWithControls,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneWithControls,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: FragmentWithControls,
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: Root,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: RowWithControls,
};

export default function Layout({mainItemId}) {
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const mainItem = layoutData.items[mainItemId];
	const layoutRef = useRef(null);
	const selectItem = useSelectItem();
	const sidebarOpen = useSelector(
		(state) => state.sidebar.panelId && state.sidebar.open
	);
	const store = useSelector((state) => state);

	const onClick = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	const getDirection = (keycode) => {
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
		(event) => {
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
						itemId,
						parentItemId: parentId,
						position,
						store,
					})
				);
			}
		},
		[activeItemId, dispatch, layoutData.items, store]
	);

	useEffect(() => {
		const layout = layoutRef.current;

		const preventLinkClick = (event) => {
			const closestElement = closest(event.target, '[href]');

			if (
				closestElement &&
				!closestElement.dataset.lfrPageEditorHrefEnabled
			) {
				event.preventDefault();
			}
		};

		if (layout) {
			layout.addEventListener('click', preventLinkClick);
		}

		return () => {
			if (layout) {
				layout.removeEventListener('click', preventLinkClick);
			}
		};
	}, [layoutRef]);

	useEventListener('keyup', onKeyUp, false, document.body);

	const isPageConversion = config.pageType === PAGE_TYPES.conversion;
	const hasWarningMessages =
		isPageConversion &&
		config.layoutConversionWarningMessages &&
		config.layoutConversionWarningMessages.length > 0;

	return (
		<>
			{isPageConversion && (
				<div
					className={classNames('page-editor__conversion-messages', {
						'page-editor__conversion-messages--with-sidebar-open': sidebarOpen,
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
							{config.layoutConversionWarningMessages.map(
								(message) => (
									<>
										{message}
										<br />
									</>
								)
							)}
						</ClayAlert>
					)}
				</div>
			)}

			<div
				className={classNames('page-editor')}
				id="page-editor"
				onClick={onClick}
				ref={layoutRef}
			>
				<EditableProcessorContextProvider>
					<LayoutDataItem
						fragmentEntryLinks={fragmentEntryLinks}
						item={mainItem}
						layoutData={layoutData}
					/>
				</EditableProcessorContextProvider>
			</div>
		</>
	);
}

Layout.propTypes = {
	mainItemId: PropTypes.string.isRequired,
};

class LayoutDataItem extends React.PureComponent {
	static getDerivedStateFromError(error) {
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
			<LayoutDataItemContent {...this.props} />
		);
	}
}

LayoutDataItem.propTypes = {
	fragmentEntryLinks: PropTypes.object.isRequired,
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function LayoutDataItemContent({
	fragmentEntryLinks,
	item,
	layoutData,
	...otherProps
}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];
	const activationOrigin = useActivationOrigin();
	const isActive = useIsActive()(item.itemId);
	const isMounted = useIsMounted();
	const componentRef = useRef(null);

	useEffect(() => {
		if (
			isActive &&
			componentRef.current &&
			isMounted() &&
			activationOrigin === ITEM_ACTIVATION_ORIGINS.structureTree
		) {
			componentRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [activationOrigin, componentRef, isActive, isMounted]);

	return (
		<Component item={item} layoutData={layoutData} ref={componentRef}>
			{item.children.map((childId) => {
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

LayoutDataItemContent.propTypes = {
	fragmentEntryLinks: PropTypes.object.isRequired,
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};
