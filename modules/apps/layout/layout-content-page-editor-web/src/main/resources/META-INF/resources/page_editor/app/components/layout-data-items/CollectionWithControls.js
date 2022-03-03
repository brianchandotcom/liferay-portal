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
import React, {useEffect, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useHoveredItemId,
	useHoveredItemType,
} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import Topper from '../topper/Topper';
import Collection from './Collection';
import isHovered from './isHovered';

const CollectionWithControls = React.forwardRef(({children, item}, ref) => {
	const [hovered, setHovered] = useState(false);

	const [setRef, itemElement] = useSetRef(ref);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const {display} = responsiveConfig.styles;

	return (
		<>
			<HoverHandler
				hovered={hovered}
				item={item}
				setHovered={setHovered}
			/>
			<Topper
				className={classNames({
					'page-editor__topper--hovered': hovered,
				})}
				item={item}
				itemElement={itemElement}
				style={{display}}
			>
				<Collection item={item} ref={setRef}>
					{children}
				</Collection>
			</Topper>
		</>
	);
});

CollectionWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default CollectionWithControls;

const HoverHandler = ({hovered, item, setHovered}) => {
	const hoveredItemType = useHoveredItemType();
	const hoveredItemId = useHoveredItemId();

	useEffect(() => {
		const isMapped =
			item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
			'collection' in item.config;

		if (isMapped) {
			const nextHovered = isHovered({
				editableValue: item.config.collection,
				hoveredItemId,
				hoveredItemType,
			});

			if (hovered !== nextHovered) {
				setHovered(nextHovered);
			}
		}
	}, [item, hoveredItemId, hoveredItemType, setHovered, hovered]);

	return null;
};
