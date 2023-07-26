/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {CONTAINER_WIDTH_TYPES} from '../../config/constants/containerWidthTypes';
import {CONTENT_DISPLAY_OPTIONS} from '../../config/constants/contentDisplayOptions';
import {useGetFieldValue} from '../../contexts/CollectionItemContext';
import {useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import resolveEditableValue from '../../utils/editable_value/resolveEditableValue';
import {getEditableLinkValue} from '../../utils/getEditableLinkValue';
import getLayoutDataItemClassName from '../../utils/getLayoutDataItemClassName';
import getLayoutDataItemCssClasses from '../../utils/getLayoutDataItemCssClasses';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import useBackgroundImageValue from '../../utils/useBackgroundImageValue';

const Container = React.memo(
	React.forwardRef(({children, className, data, item}, ref) => {
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

		const {backgroundImage, height} = itemConfig.styles;

		const {
			align,
			contentDisplay,
			contentVisibility,
			flexWrap,
			justify,
			widthType,
		} = itemConfig;

		const backgroundImageValue = useBackgroundImageValue(
			elementId,
			backgroundImage,
			getFieldValue
		);

		useEffect(() => {
			if (!itemConfig.link) {
				return;
			}

			const linkConfig = getEditableLinkValue(
				itemConfig.link,
				languageId
			);

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

		const style = {};

		if (backgroundImageValue.url) {
			style[
				`--lfr-background-image-${item.itemId}`
			] = `url(${backgroundImageValue.url})`;

			if (backgroundImage?.fileEntryId) {
				style['--background-image-file-entry-id'] =
					backgroundImage.fileEntryId;
			}
		}

		if (contentVisibility) {
			style.contentVisibility = contentVisibility;
		}

		const HTMLTag = itemConfig.htmlTag || 'div';

		const content = (
			<HTMLTag
				{...(link ? {} : data)}
				className={classNames(
					className,
					getLayoutDataItemClassName(item.type),
					getLayoutDataItemCssClasses(item),
					getLayoutDataItemUniqueClassName(item.itemId),
					{
						[align]: !!align,
						[`container-fluid`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						[`container-fluid-max-xl`]:
							widthType === CONTAINER_WIDTH_TYPES.fixed,
						'd-flex flex-column':
							contentDisplay ===
							CONTENT_DISPLAY_OPTIONS.flexColumn,
						'd-flex flex-row':
							contentDisplay === CONTENT_DISPLAY_OPTIONS.flexRow,
						'empty': !item.children.length && !height,
						[flexWrap]: Boolean(flexWrap),
						[justify]: Boolean(justify),
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
			</HTMLTag>
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
	})
);

Container.displayName = 'Container';

Container.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({}),
	}).isRequired,
};

export default Container;
