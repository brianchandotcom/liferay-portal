/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {ButtonView, Command, Plugin} from 'ckeditor5';
import {openItemSelectorModal} from 'frontend-js-item-selector-web';

import getIcon from '../utils/getIcon';

const FDS_DEFAULT_PROPS: Partial<IFrontendDataSetProps> = {
	pagination: {
		deltas: [{label: 20}, {label: 40}, {label: 60}],
		initialDelta: 20,
	},
};

const CMS_FILE_VIEWS = [
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
];

const CMS_FILE_ITEM_SELECTOR_CONFIG = {
	apiURL: `${location.origin}/o/search/v1.0/search?${[
		'emptySearch=true',
		'nestedFields=embedded,file.thumbnailURL',
		"filter=(cmsKind eq 'object') and (cmsSection eq 'files') and (status in (0, 2, 3))",
	].join('&')}`,
	locator: {
		id: 'embedded.id',
		label: 'embedded.title',
		value: 'embedded.id',
	},
	multiSelect: false,
	views: CMS_FILE_VIEWS,
};

function getRandomId(): string {
	return Math.random().toString(36).substring(2, 9);
}

interface IImageSelectedItem {
	embedded?: {
		file?: {
			link?: {
				href?: string;
			};
		};
	};
}

interface IVideoSelectedItem {
	embedded?: {
		videoURL?: string;
	};
}

class HeadlessItemSelector extends Plugin {
	init() {
		const editor = this.editor;

		const commandName = 'headlessItemSelectorCommand';

		editor.commands.add(commandName, new Command(editor));

		const command = editor.commands.get(commandName)!;

		editor.ui.componentFactory.add('headlessImageSelector', () => {
			const buttonView = new ButtonView();

			buttonView.set({
				icon: getIcon({symbol: 'picture'}),
				label: Liferay.Language.get('image'),
				tooltip: true,
			});

			buttonView.bind('isEnabled').to(command, 'isEnabled');

			buttonView.on('execute', () => {
				openItemSelectorModal({
					...CMS_FILE_ITEM_SELECTOR_CONFIG,
					fdsProps: {
						...FDS_DEFAULT_PROPS,
						id: `ImageHeadlessItemSelectorFDS_${getRandomId()}`,
						views: CMS_FILE_VIEWS,
					},
					itemTypeLabel: Liferay.Language.get('image'),
					items: [],
					onItemsChange: (items: Array<IImageSelectedItem>) => {
						const item = items[0];

						if (!item?.embedded?.file?.link?.href) {
							return;
						}

						const viewFragment = editor.data.processor.toView(
							`<img src="${item.embedded.file.link.href}">`
						);

						const modelFragment = editor.data.toModel(viewFragment);

						editor.model.insertContent(modelFragment);
					},
				});
			});

			return buttonView;
		});

		editor.ui.componentFactory.add('headlessVideoSelector', () => {
			const buttonView = new ButtonView();

			buttonView.set({
				icon: getIcon({symbol: 'video'}),
				label: Liferay.Language.get('video'),
				tooltip: true,
			});

			buttonView.bind('isEnabled').to(command, 'isEnabled');

			buttonView.on('execute', () => {
				openItemSelectorModal({
					...CMS_FILE_ITEM_SELECTOR_CONFIG,
					fdsProps: {
						...FDS_DEFAULT_PROPS,
						id: `VideoHeadlessItemSelectorFDS_${getRandomId()}`,
						views: CMS_FILE_VIEWS,
					},
					itemTypeLabel: Liferay.Language.get('video'),
					items: [],
					onItemsChange: (items: Array<IVideoSelectedItem>) => {
						const item = items[0];

						if (!item?.embedded?.videoURL) {
							return;
						}

						const viewFragment = editor.data.processor.toView(
							`<oembed url="${item.embedded.videoURL}"></oembed>`
						);

						const modelFragment = editor.data.toModel(viewFragment);

						editor.model.insertContent(modelFragment);
					},
				});
			});

			return buttonView;
		});
	}
}

export default HeadlessItemSelector;
