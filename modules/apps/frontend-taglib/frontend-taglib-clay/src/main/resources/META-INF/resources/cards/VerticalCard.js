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

import {ClayCardWithInfo} from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import React, {useMemo, useState} from 'react';

import getDataAttributes from '../get_data_attributes';

export default function VerticalCard({
	actions,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	description,
	disabled,
	displayType,
	flushHorizontal,
	flushVertical,
	href,
	imageAlt,
	imageSrc,
	inputName = '',
	inputValue = '',
	labels = [],
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	selectable,
	selected: initialSelected,
	showSticker,
	stickerCssClass,
	stickerIcon,
	stickerImageAlt,
	stickerImageSrc,
	stickerLabel,
	stickerShape,
	stickerStyle,
	symbol,
	title,
	...otherProps
}) {
	const [selected, setSelected] = useState(initialSelected);

	const stickerProps = useMemo(() => {
		const stickerProps = {
			children: stickerLabel,
			className: stickerCssClass,
			displayType: stickerStyle,
			shape: stickerShape,
		};

		if (stickerImageSrc) {
			stickerProps.children = (
				<ClaySticker.Image
					alt={stickerImageAlt}
					src={stickerImageSrc}
				/>
			);
		}
		else if (stickerIcon) {
			stickerProps.children = <ClayIcon symbol={stickerIcon} />;
		}

		return stickerProps;
	}, [
		stickerCssClass,
		stickerIcon,
		stickerImageAlt,
		stickerImageSrc,
		stickerLabel,
		stickerShape,
		stickerStyle,
	]);

	return (
		<ClayCardWithInfo
			actions={actions?.map(({data, ...rest}) => {
				const dataAttributes = getDataAttributes(data);

				return {
					...dataAttributes,
					...rest,
				};
			})}
			checkboxProps={{
				name: inputName ?? '',
				value: inputValue ?? '',
			}}
			className={cssClass}
			description={description}
			disabled={disabled}
			displayType={displayType}
			flushHorizontal={flushHorizontal}
			flushVertical={flushVertical}
			href={href}
			imgProps={imageSrc && {alt: imageAlt, src: imageSrc}}
			labels={labels?.map(
				({
					closeable: _closeable,
					data,
					label,
					style: _style,
					...rest
				}) => {
					const dataAttributes = getDataAttributes(data);

					return {
						value: label,
						...dataAttributes,
						...rest,
					};
				}
			)}
			onSelectChange={
				selectable
					? (selected) => {
							setSelected(selected);
					  }
					: null
			}
			selectable={selectable}
			selected={selected}
			stickerProps={showSticker ? stickerProps : null}
			symbol={symbol}
			title={title}
			{...otherProps}
		/>
	);
}
