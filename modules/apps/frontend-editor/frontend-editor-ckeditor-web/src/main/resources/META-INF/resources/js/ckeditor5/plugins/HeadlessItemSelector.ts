/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Command, Plugin} from '@ckeditor/ckeditor5-core/dist/index.js';
import {ButtonView} from '@ckeditor/ckeditor5-ui/dist/index.js';
import {openCMSFileSelectorModal} from '@liferay/frontend-js-item-selector-web';

import getIcon from '../utils/getIcon';

const ALLOWED_IMAGE_FILE_EXTENSIONS = [
	'apng',
	'avif',
	'gif',
	'jpg',
	'jpeg',
	'png',
	'svg',
	'tiff',
	'webp',
];

const ALLOWED_VIDEO_FILE_EXTENSIONS = [
	'avi',
	'm4v',
	'mkv',
	'mov',
	'mp4',
	'ogg',
	'ogv',
	'webm',
	'wmv',
];

const CMS_CREATE_ITEM_URL = `${location.origin}/web/cms/files?com.liferay.site.cms.site.initializer-filesSection_fdsConfig=(view:gallery)`;

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
				openCMSFileSelectorModal({
					allowDragAndDrop: false,
					allowedExtensions: ALLOWED_IMAGE_FILE_EXTENSIONS.join(','),
					createItemURL: CMS_CREATE_ITEM_URL,
					groupId: Liferay.ThemeDisplay.getSiteGroupId(),
					itemTypeLabel: Liferay.Language.get('image'),
					onSelect: (items) => {
						const item = items[0] as IImageSelectedItem;

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
				openCMSFileSelectorModal({
					allowDragAndDrop: false,
					allowedExtensions: ALLOWED_VIDEO_FILE_EXTENSIONS.join(','),
					createItemURL: CMS_CREATE_ITEM_URL,
					groupId: Liferay.ThemeDisplay.getSiteGroupId(),
					itemTypeLabel: Liferay.Language.get('video'),
					onSelect: (items) => {
						const item = items[0] as IVideoSelectedItem;

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
