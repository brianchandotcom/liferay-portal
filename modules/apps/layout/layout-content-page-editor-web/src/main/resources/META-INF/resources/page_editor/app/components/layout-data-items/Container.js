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
import selectLanguageId from '../../selectors/selectLanguageId';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import loadBackgroundImage from '../../utils/loadBackgroundImage';

const Container = React.forwardRef(
	({children, className, data, item, withinTopper = false}, ref) => {
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
			textAlign,
			textColor,
		} = item.config.styles;

		const {widthType} = item.config;

		const languageId = useSelector(selectLanguageId);
		const [backgroundImageValue, setBackgroundImageValue] = useState('');
		const [link, setLink] = useState(null);

		useEffect(() => {
			loadBackgroundImage(backgroundImage).then(setBackgroundImageValue);
		}, [backgroundImage]);

		useEffect(() => {
			if (!item.config.link) {
				return;
			}

			if (item.config.link.href) {
				setLink(item.config.link);
			}
			else if (item.config.link.fieldId) {
				InfoItemService.getInfoItemFieldValue({
					...item.config.link,
					languageId,
					onNetworkStatus: () => {},
				}).then(({fieldValue}) => {
					setLink({
						href: fieldValue,
						target: item.config.link.target,
					});
				});
			}
		}, [item.config.link, languageId]);

		const style = {
			boxSizing: 'border-box',
		};

		if (backgroundImageValue) {
			style.backgroundImage = `url(${backgroundImageValue})`;
			style.backgroundPosition = '50% 50%';
			style.backgroundRepeat = 'no-repeat';
			style.backgroundSize = 'cover';
		}

		if (fontSize) {
			style.fontSize = fontSize;
		}

		if (minHeight !== 'auto') {
			style.minHeight = minHeight;
		}

		if (minWidth !== 'auto') {
			style.minWidth = minWidth;
		}

		style.border = `solid ${borderWidth}px`;
		style.maxHeight = maxHeight;
		style.maxWidth = maxWidth;
		style.opacity = opacity;
		style.overflow = overflow;

		const content = (
			<div
				{...(link ? {} : data)}
				className={classNames(
					className,
					fontWeight,
					height,
					`mb-${marginBottom}`,
					`mt-${marginTop}`,
					`pb-${paddingBottom}`,
					`pl-${paddingLeft}`,
					`pr-${paddingRight}`,
					`pt-${paddingTop}`,
					{
						[`bg-${backgroundColor?.cssClass}`]: backgroundColor,
						[`border-${borderColor?.cssClass}`]: borderColor,
						[borderRadius]: !!borderRadius,
						container: widthType === 'fixed',
						empty: item.children.length === 0,
						[`text-${fontFamily}`]: fontFamily !== 'default',
						[`ml-${marginLeft}`]:
							widthType !== 'fixed' && !withinTopper,
						[`mr-${marginRight}`]:
							widthType !== 'fixed' && !withinTopper,
						[textAlign]: textAlign !== 'none',
						[`text-${textColor?.cssClass}`]: textColor,
					}
				)}
				ref={ref}
				style={style}
			>
				{children}
			</div>
		);

		return link ? (
			<a
				{...data}
				href={link.href}
				style={{color: 'inherit', textDecoration: 'none'}}
				target={link.target}
			>
				{content}
			</a>
		) : (
			content
		);
	}
);

Container.displayName = 'Container';

Container.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({}),
	}).isRequired,
};

export default Container;
