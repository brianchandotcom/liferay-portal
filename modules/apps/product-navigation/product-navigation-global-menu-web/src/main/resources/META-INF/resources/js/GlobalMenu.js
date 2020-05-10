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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const MOCK_ITEMS = [
	{
		items: [
			{label: 'Users and Organizations'},
			{label: 'User Groups'},
			{label: 'Roles'},
			{label: 'Password Policies'},
			{label: 'Monitoring'},
		],
		label: 'USERS',
		name: 'users',
	},
	{
		items: [{label: 'Accounts'}, {label: 'Account Users'}],
		label: 'ACCOUNTS',
		name: 'accounts',
	},
	{
		items: [{label: 'Sites'}, {label: 'Site Templates'}],
		label: 'SITES',
		name: 'sites',
	},
];

function GlobalMenu({itemsURL}) {
	const [items, setItems] = useState([]);
	const preloadPromise = useRef();

	function preloadItems() {
		if (!preloadPromise.current) {
			preloadPromise.current = fetch(itemsURL)
				.then((response) => response.json())
				.then((items) => setItems(items))
				.catch(() => setItems(MOCK_ITEMS));
		}
	}

	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'global-menu'}}
			trigger={
				<ClayIcon
					onFocus={preloadItems}
					onMouseOver={preloadItems}
					symbol="grid"
				/>
			}
		/>
	);
}

GlobalMenu.propTypes = {
	itemsURL: PropTypes.string,
};

export default GlobalMenu;
