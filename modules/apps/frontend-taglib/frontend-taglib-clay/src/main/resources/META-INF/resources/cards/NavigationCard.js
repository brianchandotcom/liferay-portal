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

import {ClayCardWithNavigation} from '@clayui/card';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function NavigationCard({
	actions: _actions,
	componentId: _componentId,
	cssClass,
	description,
	disabled: _disabled,
	horizontal,
	href,
	imageAlt,
	imageSrc,
	inputName: _inputName,
	inputValue: _inputValue,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	selectable: _selectable,
	selected: _selected,
	symbol,
	title,
	...otherProps
}) {
	return (
		<ClayCardWithNavigation
			className={cssClass}
			description={description}
			horizontal={horizontal}
			horizontalSymbol={symbol}
			href={href}
			title={title}
			{...otherProps}
		>
			{imageSrc ? (
				<img alt={imageAlt} src={imageSrc} />
			) : (
				symbol && <ClayIcon symbol={symbol} />
			)}
		</ClayCardWithNavigation>
	);
}
