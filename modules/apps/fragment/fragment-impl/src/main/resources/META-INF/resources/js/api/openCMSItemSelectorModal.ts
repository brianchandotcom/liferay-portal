/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CMSFileUploaderComponent,
	openItemSelectorModal,
} from '@liferay/frontend-js-item-selector-web';

const CMS_FILE_ITEM_SELECTOR_CONFIG = {
	apiURL: `${location.origin}/o/search/v1.0/search?${[
		'emptySearch=true',
		'nestedFields=description,embedded,file.thumbnailURL',
		"filter=(cmsKind eq 'object') and (cmsSection eq 'files') and (status in (0, 2, 3))",
	].join('&')}`,
	items: [],
	locator: {
		id: 'embedded.id',
		label: 'embedded.title',
		value: 'embedded.id',
	},
	multiSelect: false,
};

const FDS_PROPS = {
	filters: [
		{
			apiURL: '/o/headless-asset-library/v1.0/asset-libraries',
			entityFieldType: 'collection',
			id: 'groupIds',
			itemKey: 'siteId',
			itemLabel: 'name',
			label: Liferay.Language.get('space'),
			multiple: true,
			type: 'selection',
		},
	],
	id: '',
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
				description: 'description',
				symbol: '',
				title: 'title',
			},

			setItemComponentProps: ({
				item,
				props,
			}: {
				item: {
					embedded:
						| {coverImage: {link: {href: string}}}
						| {file: {thumbnailURL: string}};
				};
				props: object;
			}) => {
				const stickerProps = {
					stickerProps: {
						className: 'file-icon-color-5',
						displayType: 'unstyled',
					},
				};

				if ('file' in item.embedded) {
					return {
						...props,
						imgProps: {src: item.embedded.file.thumbnailURL},
						...stickerProps,
					};
				}

				return {
					...props,
					...stickerProps,
				};
			},

			thumbnail: 'cards2',
		},
	],
};

function getRandomId(): string {
	return Math.random().toString(36).substring(2, 9);
}

export const openCMSItemSelectorModal = function ({
	groupId,
	onSelect,
}: {
	groupId: number;
	onSelect: (items: Array<Record<string, any>>) => void;
}) {
	openItemSelectorModal({
		...CMS_FILE_ITEM_SELECTOR_CONFIG,
		fdsProps: {
			...FDS_PROPS,
			id: `UploadFragmentItemSelectorFDS_${getRandomId()}`,
		},
		filesUploaderComponent: CMSFileUploaderComponent,
		groupId,
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
