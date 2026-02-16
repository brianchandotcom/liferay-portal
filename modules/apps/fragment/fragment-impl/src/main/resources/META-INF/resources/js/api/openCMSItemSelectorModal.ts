/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';

export const openCMSItemSelectorModal = function ({
	onSelect,
}: {
	onSelect: () => void;
}) {
	const ROOT_URL = `${window.location.origin}${Liferay.ThemeDisplay.getPathContext()}/o/search/v1.0/search`;

	const BASE_SEARCH_PARAMS = {
		currentURL: '/web/cms/files',
		emptySearch: 'true',
		nestedFields: 'description,embedded,file.thumbnailURL',
	};

	const CMS_ROOT_FILES_URL = `${ROOT_URL}?${new URLSearchParams({
		...BASE_SEARCH_PARAMS,
		filter: "cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, 3)",
	}).toString()}`;

	openItemSelectorModal({
		apiURL: CMS_ROOT_FILES_URL,
		fdsProps: {
			id: `itemSelectorModal-${Date.now()}`,
			pagination: {
				deltas: [{label: 20}, {label: 40}, {label: 60}],
				initialDelta: 20,
			},
			views: [
				{
					contentRenderer: 'cards',
					label: Liferay.Language.get('cards'),
					name: 'cards',
					schema: {
						description: 'embedded.description',
						image: 'embedded.file.thumbnailURL',
						title: 'embedded.title',
					},
					thumbnail: 'cards2',
				},
				{
					contentRenderer: 'table',
					label: Liferay.Language.get('table'),
					name: 'table',
					schema: {
						fields: [
							{
								fieldName: 'embedded.title',
								label: Liferay.Language.get('title'),
								sortable: false,
							},
							{
								fieldName: 'embedded.description',
								label: Liferay.Language.get('description'),
								sortable: false,
							},
							{
								fieldName: 'embedded.file.name',
								label: Liferay.Language.get('file-name'),
								sortable: false,
							},
							{
								fieldName: 'embedded.file.mimeType',
								label: Liferay.Language.get('type'),
								sortable: false,
							},
						],
					},
					thumbnail: 'table',
				},
			],
		},
		itemTypeLabel: Liferay.Language.get('files'),
		items: [],
		locator: {
			id: 'embedded.id',
			label: 'embedded.title',
			value: 'embedded.id',
		},
		multiSelect: false,
		onItemsChange: onSelect,
	});
};
