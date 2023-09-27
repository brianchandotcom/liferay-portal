/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithInfo} from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS} from '../constants';
import getDataAttributes from '../get_data_attributes';
import normalizeDropdownItems from '../normalize_dropdown_items';

export default function VerticalCard({
	actions,
	additionalProps: _additionalProps,
	ariaLabel,
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
	stickerTitle,
	symbol,
	title,
	...otherProps
}) {
	const normalizedActions = useMemo(() => normalizeDropdownItems(actions), [
		actions,
	]);

	const [selected, setSelected] = useState(initialSelected);

	const stickerProps = useMemo(() => {
		const stickerProps = {
			children: stickerLabel,
			className: stickerCssClass,
			displayType: stickerStyle,
			shape: stickerShape,
			title: stickerTitle,
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
		stickerTitle,
	]);

	const handleToggleAllItems = useCallback(
		({checked}) => {
			setSelected(checked);
		},
		[setSelected]
	);

	useEffect(() => {
		Liferay.on(
			EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS,
			handleToggleAllItems
		);

		return () => {
			Liferay.detach(
				EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS,
				handleToggleAllItems
			);
		};
	}, [handleToggleAllItems]);

	return (
		<ClayCardWithInfo
			actions={normalizedActions}
			aria-label={ariaLabel}
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
