/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getAPIApplicationsFDSFilters() {
	return [
		{
			autocompleteEnabled: false,
			id: 'applicationStatus',
			items: [
				{
					label: 'Unpublished',
					value: 'unpublished',
				},
				{
					label: 'Published',
					value: 'published',
				},
			],
			label: Liferay.Language.get('status'),
			multiple: false,
			type: 'selection',
		},
		{
			id: 'dateModified',
			label: Liferay.Language.get('last-updated'),
			type: 'dateRange',
		},
	];
}
