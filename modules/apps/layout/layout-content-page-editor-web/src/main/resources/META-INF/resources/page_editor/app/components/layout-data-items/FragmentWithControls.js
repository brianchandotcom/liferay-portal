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
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {useSelector} from '../../store/index';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import loadBackgroundImage from '../../utils/loadBackgroundImage';
import Topper from '../Topper';
import FragmentContent from '../fragment-content/FragmentContent';

const FragmentWithControls = React.forwardRef(({item, layoutData}, ref) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const [setRef, itemElement] = useSetRef(ref);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

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

	style.border = `solid ${borderWidth}px`;
	style.borderRadius = getFrontendTokenValue(borderRadius);
	style.boxShadow = getFrontendTokenValue(shadow);
	style.fontFamily = getFrontendTokenValue(fontFamily);
	style.fontSize = getFrontendTokenValue(fontSize);
	style.fontWeight = getFrontendTokenValue(fontWeight);
	style.height = height;
	style.maxHeight = maxHeight;
	style.maxWidth = maxWidth;
	style.minHeight = minHeight;
	style.minWidth = minWidth;
	style.opacity = opacity;
	style.overflow = overflow;
	style.width = width;

	if (backgroundImageValue) {
		style.backgroundImage = `url(${backgroundImageValue})`;
		style.backgroundPosition = '50% 50%';
		style.backgroundRepeat = 'no-repeat';
		style.backgroundSize = 'cover';
	}

	return (
		<Topper
			className={classNames(
				`mb-${marginBottom}`,
				`ml-${marginLeft}`,
				`mr-${marginRight}`,
				`mt-${marginTop}`,
				`pb-${paddingBottom}`,
				`pl-${paddingLeft}`,
				`pr-${paddingRight}`,
				`pt-${paddingTop}`,
				{
					[`bg-${backgroundColor?.cssClass}`]: backgroundColor,
					[`border-${borderColor?.cssClass}`]: borderColor,
					[textAlign]: textAlign !== 'none',
					[`text-${textColor?.cssClass || textColor}`]: textColor,
				}
			)}
			item={item}
			itemElement={itemElement}
			layoutData={layoutData}
			style={style}
		>
			<FragmentContent
				elementRef={setRef}
				fragmentEntryLinkId={item.config.fragmentEntryLinkId}
				itemId={item.itemId}
			/>
		</Topper>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default FragmentWithControls;
