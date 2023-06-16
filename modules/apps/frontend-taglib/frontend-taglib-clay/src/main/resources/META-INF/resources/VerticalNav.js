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

import {VerticalNav as ClayVerticalNav} from '@clayui/core';
import React from 'react';

export default function VerticalNav({
	activation,
	active,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	decorated,
	items,
	large,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	triggerLabel,
	...otherProps
}) {
	return (
		<ClayVerticalNav
			activation={activation}
			active={active}
			className={cssClass}
			decorated={decorated}
			items={items}
			large={large}
			triggerLabel={triggerLabel}
			{...otherProps}
		>
			{(item) => (
				<ClayVerticalNav.Item
					href={item.href}
					items={item.items}
					key={item.id}
				>
					{item.label}
				</ClayVerticalNav.Item>
			)}
		</ClayVerticalNav>
	);
}
