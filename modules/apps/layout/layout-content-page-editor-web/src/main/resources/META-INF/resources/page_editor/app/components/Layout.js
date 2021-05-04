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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';
import {useSetCollectionActiveItemContext} from '../contexts/CollectionActiveItemContext';
import {
	useActivationOrigin,
	useIsActive,
	useSelectItem,
} from '../contexts/ControlsContext';
import {useSelector} from '../contexts/StoreContext';
import {deepEqual} from '../utils/checkDeepEqual';
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
	const layoutData = useSelector((state) => state.layoutData);
	const mainItem = layoutData.items[mainItemId];
	const layoutRef = useRef(null);

	const selectItem = useSelectItem();
	const sidebarOpen = useSelector(
		(state) => state.sidebar.panelId && state.sidebar.open
	);

	const onClick = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	useEffect(() => {
		const layout = layoutRef.current;

		const preventLinkClick = (event) => {
			const closestElement = event.target.closest('[href]');

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

	const isPageConversion = config.layoutType === LAYOUT_TYPES.conversion;
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

			{mainItem && (
				<div
					className="page-editor"
					id="page-editor"
					onClick={onClick}
					ref={layoutRef}
				>
					<LayoutDataItem item={mainItem} layoutData={layoutData} />
				</div>
			)}
		</>
	);
}

Layout.propTypes = {
	mainItemId: PropTypes.string.isRequired,
};

class LayoutDataItem extends React.Component {
	static getDerivedStateFromError(error) {
		return {error};
	}

	static destructureItem(item, layoutData) {
		return {
			...item,
			children: item.children.map((child) =>
				LayoutDataItem.destructureItem(
					layoutData.items[child],
					layoutData
				)
			),
		};
	}

	constructor(props) {
		super(props);

		this.state = {
			error: null,
		};
	}

	shouldComponentUpdate(nextProps, nextState) {
		return (
			nextState.error ||
			!deepEqual(this.props.item, nextProps.item) ||
			!deepEqual(
				LayoutDataItem.destructureItem(
					this.props.item,
					this.props.layoutData
				),
				LayoutDataItem.destructureItem(
					nextProps.item,
					nextProps.layoutData
				)
			)
		);
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
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function LayoutDataItemContent({item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];
	const componentRef = useRef(null);

	return (
		<>
			<LayoutDataItemInteractionFilter
				componentRef={componentRef}
				item={item}
			/>

			<Component item={item} layoutData={layoutData} ref={componentRef}>
				{item.children.map((childId) => {
					return (
						<LayoutDataItem
							item={layoutData.items[childId]}
							key={childId}
							layoutData={layoutData}
						/>
					);
				})}
			</Component>
		</>
	);
}

LayoutDataItemContent.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

const LayoutDataItemInteractionFilter = ({componentRef, item}) => {
	useSetCollectionActiveItemContext(item.itemId);

	const activationOrigin = useActivationOrigin();
	const isActive = useIsActive()(item.itemId);
	const isMounted = useIsMounted();

	useEffect(() => {
		if (
			isActive &&
			componentRef.current &&
			isMounted() &&
			activationOrigin === ITEM_ACTIVATION_ORIGINS.sidebar
		) {
			componentRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [activationOrigin, componentRef, isActive, isMounted]);

	return null;
};

LayoutDataItemInteractionFilter.propTypes = {
	componentRef: PropTypes.object.isRequired,
	item: getLayoutDataItemPropTypes().isRequired,
};
