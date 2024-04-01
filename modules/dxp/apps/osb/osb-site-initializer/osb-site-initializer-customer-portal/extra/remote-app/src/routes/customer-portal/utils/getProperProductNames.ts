/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getProperProductNames(name: string) {
	if (/dxp/i.test(name)) {
		return name.replace(/dxp/i, 'Liferay Self-Hosted');
	}

	if (/liferay experience cloud/i.test(name)) {
		return name.replace(/liferay experience cloud/i, 'Liferay SaaS');
	}

	if (/lxc - sm/i.test(name)) {
		return name.replace(/lxc - sm/i, 'Liferay PaaS');
	}

	return name;
}
