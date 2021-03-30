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
import {CONTAINER_DISPLAY_OPTIONS} from '../../config/constants/containerDisplayOptions';
import {CONTAINER_WIDTH_TYPES} from '../../config/constants/containerWidthTypes';
import {config} from '../../config/index';
import selectLanguageId from '../../selectors/selectLanguageId';
import {useSelector} from '../../store/index';
import resolveEditableValue from '../../utils/editable-value/resolveEditableValue';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import useBackgroundImageValue from '../../utils/useBackgroundImageValue';
import {useId} from '../../utils/useId';
import {useGetFieldValue} from '../CollectionItemContext';

const Container = React.forwardRef(
	({children, className, data, item, withinTopper = false}, ref) => {
		const elementId = useId();
		const getFieldValue = useGetFieldValue();
		const languageId = useSelector(selectLanguageId);
		const [link, setLink] = useState(null);
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const itemConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);

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

		const {align, contentDisplay, justify, widthType} = itemConfig;

		const backgroundImageValue = useBackgroundImageValue(
			elementId,
			backgroundImage,
			getFieldValue
		);

		useEffect(() => {
			if (!itemConfig.link) {
				return;
			}

			const linkConfig =
				itemConfig.link[languageId] ||
				itemConfig.link[config.defaultLanguageId] ||
				itemConfig.link;

			if (!linkConfig) {
				return;
			}

			resolveEditableValue(linkConfig, languageId, getFieldValue).then(
				(linkHref) => {
					if (typeof linkHref === 'string') {
						setLink({...linkConfig, href: linkHref});
					}
					else if (linkHref) {
						setLink({...linkConfig, ...linkHref});
					}
				}
			);
		}, [itemConfig.link, languageId, getFieldValue]);

		const style = {
			boxSizing: 'border-box',
		};

		style.backgroundColor = getFrontendTokenValue(backgroundColor);
		style.border = `solid ${borderWidth}px`;
		style.borderColor = getFrontendTokenValue(borderColor);
		style.borderRadius = getFrontendTokenValue(borderRadius);
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
			style.boxShadow = getFrontendTokenValue(shadow);
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

		const content = (
			<div
				{...(link ? {} : data)}
				className={classNames(
					className,
					`mb-${marginBottom || 0}`,
					`mt-${marginTop || 0}`,
					`pb-${paddingBottom || 0}`,
					`pl-${paddingLeft || 0}`,
					`pr-${paddingRight || 0}`,
					`pt-${paddingTop || 0}`,
					{
						[align]: !!align,
						[`container-fluid`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						[`container-fluid-max-xl`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						'd-flex flex-column':
							contentDisplay ===
							CONTAINER_DISPLAY_OPTIONS.flexColumn,
						'd-flex flex-row':
							contentDisplay ===
							CONTAINER_DISPLAY_OPTIONS.flexRow,
						empty: !item.children.length && !height,
						[`bg-${backgroundColor}`]:
							backgroundColor && !backgroundColor.startsWith('#'),
						[justify]: !!justify,
						[`ml-${marginLeft || 0}`]:
							widthType !== CONTAINER_WIDTH_TYPES.fixed &&
							!withinTopper,
						[`mr-${marginRight || 0}`]:
							widthType !== CONTAINER_WIDTH_TYPES.fixed &&
							!withinTopper,
						[textAlign
							? textAlign.startsWith('text-')
								? textAlign
								: `text-${textAlign}`
							: '']: textAlign,
					}
				)}
				id={elementId}
				ref={ref}
				style={style}
			>
				{backgroundImageValue.mediaQueries ? (
					<style>{backgroundImageValue.mediaQueries}</style>
				) : null}

				{children}
			</div>
		);

		return link?.href ? (
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
