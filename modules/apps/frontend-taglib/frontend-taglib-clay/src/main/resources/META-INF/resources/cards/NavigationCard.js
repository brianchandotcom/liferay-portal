/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
