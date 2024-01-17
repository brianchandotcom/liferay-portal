/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useRef} from 'react';

import {useItems} from '../contexts/ItemsContext';
import {MenuItem} from './MenuItem';

export function Menu({sidebarPanelRef}) {
	const items = useItems();
	const menuRef = useRef();

	const onMenuItemRemoved = (itemIndex) => {
		const items = menuRef.current?.querySelectorAll('.focusable-menu-item');

		if (!items) {
			return;
		}

		const index = Math.min(itemIndex, items.length - 1);

		items[index]?.focus();
	};

	return (
		<div
			aria-orientation="vertical"
			className="container mb-3 ml-lg-auto ml-sm-0 pt-4 px-3"
			data-item-id="0"
			ref={menuRef}
			role="menubar"
		>
			{items.map((item, index) => (
				<MenuItem
					item={item}
					key={item.siteNavigationMenuItemId}
					onMenuItemRemoved={() => onMenuItemRemoved(index)}
					sidebarPanelRef={sidebarPanelRef}
				/>
			))}
		</div>
	);
}
