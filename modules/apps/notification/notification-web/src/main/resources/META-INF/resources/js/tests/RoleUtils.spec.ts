/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

import {getCheckedChildren} from '../components/SettingsContainer/rolesUtils';

it('Assert role names checked items', () => {
	const children = [
		{
			checked: false,
			label: 'Account Administrator',
			value: 'Account Administrator',
		},
		{
			checked: false,
			label: 'Account Member',
			value: 'Account Member',
		},
		{
			checked: false,
			label: 'Account Supplier',
			value: 'Account Supplier',
		},
		{
			checked: false,
			label: 'Buyer',
			value: 'Buyer',
		},
		{
			checked: false,
			label: 'Order Manager',
			value: 'Order Manager',
		},
	];

	const rolesNamesList = [
		{roleName: 'Account Administrator'},
		{roleName: 'Account Manager'},
		{roleName: 'Account Member'},
		{roleName: 'Administrator'},
		{roleName: 'Analytics Administrator'},
		{roleName: 'Order Manager'},
		{roleName: 'Organization Administrator'},
		{roleName: 'Owner'},
	];

	const checkedChildren = getCheckedChildren(rolesNamesList, children);

	expect(checkedChildren).toStrictEqual([
		{
			checked: true,
			label: 'Account Administrator',
			value: 'Account Administrator',
		},
		{
			checked: true,
			label: 'Account Member',
			value: 'Account Member',
		},
		{
			checked: false,
			label: 'Account Supplier',
			value: 'Account Supplier',
		},
		{
			checked: false,
			label: 'Buyer',
			value: 'Buyer',
		},
		{
			checked: true,
			label: 'Order Manager',
			value: 'Order Manager',
		},
	]);
});
