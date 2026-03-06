/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import CMSFileUploaderComponent from '../item_selector_file_uploader/CMSFileUploaderComponent';
import {IItemSelectorModalProps} from './ItemSelectorModal';

interface CMSFile {
	description: string;
	embedded: {
		file: {
			thumbnailURL: string;
		};
		id: number;
		title: string;
	};
	title: string;
}

type CMSFileItemSelectorModalProps = IItemSelectorModalProps<CMSFile>;

type CMSFileItemSelectorModalConfig = {
	apiURL: CMSFileItemSelectorModalProps['apiURL'];
	items: CMSFileItemSelectorModalProps['items'];
	locator: CMSFileItemSelectorModalProps['locator'];
	multiSelect: CMSFileItemSelectorModalProps['multiSelect'];
};

function urlBuilder({
	base = location.origin,
	filters = [],
	resource = '/o/search/v1.0/search',
}: {
	base?: string;
	filters?: string[];
	resource?: string;
}) {
	const finalURL = new URL(resource, base);

	const filter = [
		"(cmsKind eq 'object')",
		"(cmsSection eq 'files')",
		'(status in (0, 2, 3))',
	]
		.concat(filters.filter(Boolean))
		.join(' and ');

	finalURL.search = new URLSearchParams({
		emptySearch: 'true',
		filter,
		nestedFields: 'description,embedded,file.thumbnailURL',
	}).toString();

	return finalURL.toString();
}

const CMS_FILE_ITEM_SELECTOR_CONFIG: CMSFileItemSelectorModalConfig = {
	apiURL: urlBuilder({}),
	items: [],
	locator: {
		id: 'embedded.id',
		label: 'embedded.title',
		value: 'embedded.id',
	},
	multiSelect: false,
};

const FDS_PROPS: Omit<
	CMSFileItemSelectorModalProps['fdsProps'],
	'id' | 'items'
> = {
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
				item: {embedded: {file: {thumbnailURL: string}}};
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

function normalizeExtensions(allowedExtensions: string) {
	const cleanExtensions = allowedExtensions
		.split(',')
		.map((item) => item.trim().replace(/^\./, ''))
		.filter(Boolean);

	if (cleanExtensions.length < 1) {
		return '';
	}

	const extensions = cleanExtensions.map((item) => `'${item}'`).join(',');

	return `(extension in (${extensions}))`;
}

export default function openCMSFileSelectorModal({
	allowDragAndDrop = false,
	allowedExtensions,
	config,
	fdsProps,
	groupId,
	maxFileSize,
	onSelect,
}: {
	allowDragAndDrop: boolean;
	allowedExtensions?: string;
	config?: Partial<CMSFileItemSelectorModalConfig>;
	fdsProps?: Partial<CMSFileItemSelectorModalProps['fdsProps']>;
	groupId: number;
	maxFileSize?: number;
	onSelect: (items: Array<CMSFile>) => void;
}) {
	const finalConfig = {
		...CMS_FILE_ITEM_SELECTOR_CONFIG,
		...config,
	};

	if (allowedExtensions && allowedExtensions.length) {
		const extensions = normalizeExtensions(allowedExtensions);

		finalConfig.apiURL = urlBuilder({
			filters: [extensions],
		});
	}

	return render(

		// @ts-ignore

		DetachedCMSFilesItemSelectorModal,
		{
			...finalConfig,
			allowedExtensions,
			fdsProps: {
				...FDS_PROPS,
				...fdsProps,
				id: `CMSItemSelectorFDS_${getRandomId()}`,
			},
			filesUploaderComponent: allowDragAndDrop
				? CMSFileUploaderComponent
				: undefined,
			groupId,
			itemTypeLabel: Liferay.Language.get('files'),
			maxFileSize,
			onItemsChange: onSelect,
		},
		document.createElement('div')
	);
}
