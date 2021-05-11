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
import React, {useMemo} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useGetFieldValue} from '../../contexts/CollectionItemContext';
import {useSelector} from '../../contexts/StoreContext';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {isValidSpacingOption} from '../../utils/isValidSpacingOption';
import useBackgroundImageValue from '../../utils/useBackgroundImageValue';
import {useId} from '../../utils/useId';

const Row = React.forwardRef(
	({children, className, item, withinTopper = false}, ref) => {
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

		const elementId = useId();
		const getFieldValue = useGetFieldValue();
		const backgroundImageValue = useBackgroundImageValue(
			elementId,
			backgroundImage,
			getFieldValue
		);

		const style = {};

		style.backgroundColor = getFrontendTokenValue(backgroundColor);
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

		if (borderWidth) {
			style.borderWidth = `${borderWidth}px`;
			style.borderStyle = 'solid';
		}

		if (!withinTopper) {
			style.maxWidth = maxWidth;
			style.minWidth = minWidth;
			style.width = width;
		}

		if (backgroundImageValue.url) {
			style.backgroundImage = `url(${backgroundImageValue.url})`;
			style.backgroundPosition = '50% 50%';
			style.backgroundRepeat = 'no-repeat';
			style.backgroundSize = 'cover';

			if (backgroundImage?.fileEntryId) {
				style['--background-image-file-entry-id'] =
					backgroundImage.fileEntryId;
			}
		}

		const rowContent = (
			<ClayLayout.Row
				className={classNames(className, {
					'flex-column-reverse':
						item.config.numberOfColumns === 2 &&
						modulesPerRow === 1 &&
						reverseOrder,
					[`mb-${marginBottom}`]: isValidSpacingOption(marginBottom),
					[`mt-${marginTop}`]: isValidSpacingOption(marginTop),
					[`pb-${paddingBottom}`]: isValidSpacingOption(
						paddingBottom
					),
					[`pl-${paddingLeft}`]: isValidSpacingOption(paddingLeft),
					[`pr-${paddingRight}`]: isValidSpacingOption(paddingRight),
					[`pt-${paddingTop}`]: isValidSpacingOption(paddingTop),
					[`ml-${marginLeft}`]: isValidSpacingOption(marginLeft),
					[`mr-${marginRight}`]: isValidSpacingOption(marginRight),
					'no-gutters': !item.config.gutters,
					[textAlign
						? textAlign.startsWith('text-')
							? textAlign
							: `text-${textAlign}`
						: '']: textAlign,
				})}
				id={elementId}
				ref={ref}
				style={style}
			>
				{backgroundImageValue.mediaQueries ? (
					<style>{backgroundImageValue.mediaQueries}</style>
				) : null}

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
