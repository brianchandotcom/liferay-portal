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
import Topper from '../Topper';
import Collection from './Collection';
import isHovered from './isHovered';

const CollectionWithControls = React.forwardRef(({children, item}, ref) => {
	const [hovered, setHovered] = useState(false);
	const hoveredItemType = useHoveredItemType();
	const hoveredItemId = useHoveredItemId();
	const [setRef, itemElement] = useSetRef(ref);

	const isMapped =
		item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
		'collection' in item.config;

	useEffect(() => {
		if (isMapped) {
			setHovered(
				isHovered({
					editableValue: item.config.collection,
					hoveredItemId,
					hoveredItemType,
				})
			);
		}
	}, [isMapped, item, hoveredItemId, hoveredItemType]);

	return (
		<Topper
			className={classNames({
				'page-editor__topper--hovered': hovered,
			})}
			isMapped={isMapped}
			item={item}
			itemElement={itemElement}
		>
			<Collection item={item} ref={setRef}>
				{children}
			</Collection>
		</Topper>
	);
});

CollectionWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default CollectionWithControls;
