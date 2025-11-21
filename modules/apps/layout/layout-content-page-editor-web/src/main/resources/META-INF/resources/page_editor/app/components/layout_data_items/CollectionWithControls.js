/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useHoveredItemId,
	useHoveredItemType,
} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
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
				className={classNames(
					getLayoutDataItemTopperUniqueClassName(item.itemId),
					{
						'page-editor__topper--hovered': hovered,
					}
				)}
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
