/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function DesignLibraryResourcesFDSPropsTransformer(props: {}) {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-style-book'),
			},
		],
	};

	return {
		...props,
		creationMenu,
		hideManagementBarInEmptyState: true,
		itemsPerPage: 20,
		multiSelect: true,
		selectedItemsKey: 'embedded.id',
		selectionType: 'multiple',
		showSelectAll: true,
		views: [
			{
				contentRenderer: 'list',
				default: true,
				label: 'List',
				name: 'list',
				schema: {
					title: 'title',
				},
				thumbnail: 'list',
			},
			{
				active: true,
				contentRenderer: 'cards',
				default: false,
				id: 'cards',
				name: 'cards',
				schema: {
					title: 'title',
				},
				template: 'cards',
				thumbnail: 'cards2',
			},
		],
	};
}
