/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

export async function getAssetsLibrariesByCompany() {
	await delay(1000);

	return [
		{
			id: 1,
			name: 'Space 1',
		},
		{
			id: 2,
			name: 'Space 2',
		},
	];
}
