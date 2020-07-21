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
			align,
			backgroundColorCssClass,
			backgroundImage,
			borderColor,
			borderRadius,
			borderWidth,
			contentDisplay,
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
			shadow,
			widthType,
		} = item.config;

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

		if (borderWidth) {
			style.borderStyle = 'solid';
			style.borderWidth = `${borderWidth}px`;
		}

		if (opacity) {
			style.opacity = Number(opacity / 100) || 1;
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
						[`bg-${backgroundColorCssClass}`]: !!backgroundColorCssClass,
						[`border-${borderColor}`]: !!borderColor,
						[borderRadius]: !!borderRadius,
						container: widthType === 'fixed',
						'd-block': contentDisplay === 'block',
						'd-flex': contentDisplay === 'flex',
						empty: item.children.length === 0,
						[justify]: !!justify,
						[`ml-${marginLeft || 0}`]:
							widthType !== 'fixed' && !withinTopper,
						[`mr-${marginRight || 0}`]:
							widthType !== 'fixed' && !withinTopper,
						[shadow]: !!shadow,
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
