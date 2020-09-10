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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useSelector} from '../../store/index';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import loadBackgroundImage from '../../utils/loadBackgroundImage';
import {useCustomRowContext} from '../ResizeContext';

const Row = React.forwardRef(
	({children, className, item, layoutData, withinTopper = false}, ref) => {
		const customRow = useCustomRowContext();
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const itemConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);
		const {modulesPerRow, reverseOrder} = itemConfig;

		const {
			backgroundColor,
			backgroundImage,
			borderColor,
			borderRadius,
			borderWidth,
			fontFamily,
			fontSize,
			fontWeight,
			height,
			marginBottom,
			marginLeft,
			marginRight,
			marginTop,
			maxHeight,
			maxWidth,
			minHeight,
			minWidth,
			opacity,
			overflow,
			paddingBottom,
			paddingLeft,
			paddingRight,
			paddingTop,
			shadow,
			textAlign,
			textColor,
			width,
		} = itemConfig.styles;

		const [backgroundImageValue, setBackgroundImageValue] = useState('');

		useEffect(() => {
			loadBackgroundImage(backgroundImage).then(setBackgroundImageValue);
		}, [backgroundImage]);

		const style = {};

		style.backgroundColor = getFrontendTokenValue(backgroundColor);
		style.border = `solid ${borderWidth}px`;
		style.borderColor = getFrontendTokenValue(borderColor);
		style.borderRadius = getFrontendTokenValue(borderRadius);
		style.boxShadow = getFrontendTokenValue(shadow);
		style.color = getFrontendTokenValue(textColor);
		style.fontFamily = getFrontendTokenValue(fontFamily);
		style.fontSize = getFrontendTokenValue(fontSize);
		style.fontWeight = getFrontendTokenValue(fontWeight);
		style.height = height;
		style.maxHeight = maxHeight;
		style.minHeight = minHeight;
		style.opacity = opacity ? opacity / 100 : null;
		style.overflow = overflow;

		if (!withinTopper) {
			style.maxWidth = maxWidth;
			style.minWidth = minWidth;
			style.width = width;
		}

		if (backgroundImageValue) {
			style.backgroundImage = `url(${backgroundImageValue})`;
			style.backgroundPosition = '50% 50%';
			style.backgroundRepeat = 'no-repeat';
			style.backgroundSize = 'cover';
		}

		const rowContent = (
			<ClayLayout.Row
				className={classNames(
					className,
					`mb-${marginBottom || 0}`,
					`mt-${marginTop || 0}`,
					`pb-${paddingBottom || 0}`,
					`pl-${paddingLeft || 0}`,
					`pr-${paddingRight || 0}`,
					`pt-${paddingTop || 0}`,
					{
						empty:
							!height &&
							(!item.children.some(
								(childId) =>
									layoutData.items[childId]?.children.length
							) ||
								item.config.numberOfColumns !== modulesPerRow),
						'flex-column': customRow && modulesPerRow === 1,
						'flex-column-reverse':
							item.config.numberOfColumns === 2 &&
							modulesPerRow === 1 &&
							reverseOrder,
						[`ml-${marginLeft}`]: marginLeft && marginLeft !== '0',
						[`mr-${marginRight}`]:
							marginRight && marginRight !== '0',
						'no-gutters': !item.config.gutters,
						[textAlign
							? textAlign.startsWith('text-')
								? textAlign
								: `text-${textAlign}`
							: '']: textAlign,
					}
				)}
				ref={ref}
				style={style}
			>
				{children}
			</ClayLayout.Row>
		);

		const masterLayoutData = useSelector(
			(state) => state.masterLayout?.masterLayoutData
		);

		const masterParent = useMemo(() => {
			const dropZone =
				masterLayoutData &&
				masterLayoutData.items[masterLayoutData.rootItems.dropZone];

			return dropZone
				? getItemParent(dropZone, masterLayoutData)
				: undefined;
		}, [masterLayoutData]);

		const shouldAddContainer = useSelector(
			(state) => !getItemParent(item, state.layoutData) && !masterParent
		);

		return shouldAddContainer ? (
			<ClayLayout.ContainerFluid className="p-0" size={false}>
				{rowContent}
			</ClayLayout.ContainerFluid>
		) : (
			rowContent
		);
	}
);

Row.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function getItemParent(item, itemLayoutData) {
	const parent = itemLayoutData.items[item.parentId];

	return parent &&
		(parent.type === LAYOUT_DATA_ITEM_TYPES.root ||
			parent.type === LAYOUT_DATA_ITEM_TYPES.fragmentDropZone)
		? getItemParent(parent, itemLayoutData)
		: parent;
}

export default Row;
