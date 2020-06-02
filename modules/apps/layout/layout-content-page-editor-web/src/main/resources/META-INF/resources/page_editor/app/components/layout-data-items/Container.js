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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import InfoItemService from '../../services/InfoItemService';

const Container = React.forwardRef(({children, className, data, item}, ref) => {
	const {
		align,
		backgroundColor,
		backgroundImage,
		borderColor,
		borderRadius,
		borderWidth,
		containerWidth,
		contentDisplay,
		dropShadow,
		justify,
		marginBottom,
		marginLeft,
		marginRight,
		marginTop,
		opacity,
		paddingBottom,
		paddingLeft,
		paddingRight,
		paddingTop,
	} = item.config;

	const backgroundColorCssClass = backgroundColor && backgroundColor.cssClass;
	const [backgroundImageValue, setBackgroundImageValue] = useState('');
	const borderColorCssClass = borderColor && borderColor.cssClass;

	useEffect(() => {
		loadBackgroundImage(backgroundImage).then(setBackgroundImageValue);
	}, [backgroundImage]);

	const style = {
		boxSizing: 'border-box',
	};

	if (backgroundImageValue) {
		style.backgroundImage = `url(${backgroundImageValue})`;
		style.backgroundPosition = '50% 50%';
		style.backgroundRepeat = 'no-repeat';
		style.backgroundSize = 'cover';
	}

	if (borderWidth) {
		style.borderStyle = 'solid';
		style.borderWidth = `${borderWidth}px`;
	}

	if (opacity) {
		style.opacity = Number(opacity / 100) || 1;
	}

	return (
		<div
			{...data}
			className={classNames(
				className,
				`mb-${marginBottom || 0}`,
				`ml-${marginLeft || 0}`,
				`mr-${marginRight || 0}`,
				`mt-${marginTop || 0}`,
				`pb-${paddingBottom || 0}`,
				`pl-${paddingLeft || 0}`,
				`pr-${paddingRight || 0}`,
				`pt-${paddingTop || 0}`,
				{
					[align]: !!align,
					[borderRadius]: !!borderRadius,
					container: containerWidth === 'fixed',
					'd-block': contentDisplay === 'block',
					'd-flex': contentDisplay === 'flex',
					[dropShadow]: !!dropShadow,
					empty: item.children.length === 0,
					[justify]: !!justify,
					[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
					[`border-${borderColorCssClass}`]: !!borderColorCssClass,
				}
			)}
			ref={ref}
			style={style}
		>
			{children}
		</div>
	);
});

Container.displayName = 'Container';

Container.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({}),
	}).isRequired,
};

const loadBackgroundImage = (backgroundImage) => {
	if (!backgroundImage) {
		return Promise.resolve('');
	}
	else if (typeof backgroundImage.url === 'string') {
		return Promise.resolve(backgroundImage.url);
	}
	else if (backgroundImage.fieldId) {
		return InfoItemService.getAssetFieldValue({
			classNameId: backgroundImage.classNameId,
			classPK: backgroundImage.classPK,
			fieldId: backgroundImage.fieldId,
			onNetworkStatus: () => {},
		}).then((response) => {
			if (response.fieldValue && response.fieldValue.url) {
				return response.fieldValue.url;
			}

			return '';
		});
	}

	return Promise.resolve('');
};

export default Container;
