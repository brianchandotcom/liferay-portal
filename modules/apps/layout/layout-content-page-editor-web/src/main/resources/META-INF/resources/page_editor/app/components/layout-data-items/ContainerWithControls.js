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
import {CONTAINER_WIDTH_TYPES} from '../../config/constants/containerWidthTypes';
import {config} from '../../config/index';
import {
	useHoveredItemId,
	useHoveredItemType,
} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {isValidSpacingOption} from '../../utils/isValidSpacingOption';
import Topper from '../topper/Topper';
import Container from './Container';
import isHovered from './isHovered';

const ContainerWithControls = React.forwardRef(
	({children, isDropTarget = true, item}, ref) => {
		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const [hovered, setHovered] = useState(false);
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const [setRef, itemElement] = useSetRef(ref);

		const itemConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);

		const {widthType} = itemConfig;

		const {
			display,
			height,
			marginLeft,
			marginRight,
			maxWidth,
			minWidth,
			shadow,
			width,
		} = itemConfig.styles;

		const style = {};

		style.boxShadow = getFrontendTokenValue(shadow);
		style.display = display;
		style.maxWidth = maxWidth;
		style.minWidth = minWidth;
		style.width = width;

		return (
			<>
				<HoverHandler
					hovered={hovered}
					item={item}
					setHovered={setHovered}
				/>
				<Topper
					className={classNames({
						[getLayoutDataItemTopperUniqueClassName(
							item.itemId
						)]: config.featureFlagLps132571,
						[`container-fluid`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						[`container-fluid-max-xl`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						[`ml-${marginLeft}`]:
							isValidSpacingOption(marginLeft) &&
							widthType !== CONTAINER_WIDTH_TYPES.fixed,
						[`mr-${marginRight}`]:
							isValidSpacingOption(marginRight) &&
							widthType !== CONTAINER_WIDTH_TYPES.fixed,
						'p-0': widthType === CONTAINER_WIDTH_TYPES.fixed,
						'page-editor__topper--hovered': hovered,
					})}
					isDropTarget={isDropTarget}
					item={item}
					itemElement={itemElement}
					style={style}
				>
					<Container
						className={classNames({
							'empty': !item.children.length && !height,
							'page-editor__container': canUpdateItemConfiguration,
						})}
						item={item}
						ref={setRef}
					>
						{children}
					</Container>
				</Topper>
			</>
		);
	}
);

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default ContainerWithControls;

const HoverHandler = ({hovered, item, setHovered}) => {
	const hoveredItemType = useHoveredItemType();
	const hoveredItemId = useHoveredItemId();

	useEffect(() => {
		const backgroundImage = item.config?.styles?.backgroundImage;

		if (backgroundImage?.classNameId && backgroundImage?.classPK) {
			const nextHovered = isHovered({
				editableValue: backgroundImage,
				hoveredItemId,
				hoveredItemType,
			});

			if (hovered !== nextHovered) {
				setHovered(nextHovered);
			}
		}
	}, [hovered, hoveredItemId, hoveredItemType, item, setHovered]);

	return null;
};
