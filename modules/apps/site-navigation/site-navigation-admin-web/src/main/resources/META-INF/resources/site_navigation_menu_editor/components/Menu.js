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

import PropTypes from 'prop-types';
import React from 'react';

import {MenuItem} from './MenuItem';

export const Menu = ({items, onSelectMenuItem, selectedMenuItemId}) => (
	<div className="container p-3">
		<MenuContent
			items={items}
			onSelectMenuItem={onSelectMenuItem}
			selectedMenuItemId={selectedMenuItemId}
		/>
	</div>
);

Menu.propTypes = {
	items: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array.isRequired,
			siteNavigationMenuItemId: PropTypes.string.isRequired,
		})
	),
	onSelectMenuItem: PropTypes.func.isRequired,
	selectedMenuItemId: PropTypes.string,
};

const MenuContent = ({items, onSelectMenuItem, selectedMenuItemId}) =>
	items.map((item) => (
		<div key={item.siteNavigationMenuItemId}>
			<MenuItem
				item={item}
				onSelect={() => onSelectMenuItem(item)}
				selected={selectedMenuItemId === item.siteNavigationMenuItemId}
			/>

			<div className="pl-4">
				{!!item.children.length && (
					<MenuContent
						items={item.children}
						onSelectMenuItem={onSelectMenuItem}
						selectedMenuItemId={selectedMenuItemId}
					/>
				)}
			</div>
		</div>
	));
