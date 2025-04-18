/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ButtonView, Command, Config, Plugin, icons} from 'ckeditor5';
import {openSelectionModal} from 'frontend-js-components-web';

import {ClassicEditorConfig} from '../utils/types';

class ItemSelector extends Plugin {
	init() {
		const editor = this.editor;

		const commandName = 'itemSelectorCommand';

		editor.commands.add(commandName, new Command(editor));

		const command = editor.commands.get(commandName)!;

		const config: Config<ClassicEditorConfig> = editor.config;

		const filebrowserImageBrowseUrl = config.get(
			'filebrowserImageBrowseUrl'
		);

		if (filebrowserImageBrowseUrl) {
			editor.ui.componentFactory.add('imageSelector', () => {
				const buttonView = new ButtonView();

				buttonView.set({
					icon: icons.image,
					label: Liferay.Language.get('image'),
					tooltip: true,
				});

				buttonView.bind('isEnabled').to(command, 'isEnabled');

				buttonView.on('execute', () => {
					openSelectionModal({
						onSelect: ({value}: {value: string}) => {
							let url;

							try {
								url = JSON.parse(value).url;
							}
							catch (error) {
								url = value;
							}

							if (!url) {
								return;
							}

							const viewFragment = editor.data.processor.toView(
								`<img src="${url}">`
							);

							const modelFragment =
								editor.data.toModel(viewFragment);

							editor.model.insertContent(modelFragment);
						},
						selectEventName: config.get('itemSelectorEventName'),
						title: Liferay.Language.get('select-item'),
						url: filebrowserImageBrowseUrl,
						zIndex: Liferay.zIndex.WINDOW + 10,
					});
				});

				return buttonView;
			});
		}

		const filebrowserVideoBrowseUrl = config.get(
			'filebrowserVideoBrowseUrl'
		);

		if (filebrowserVideoBrowseUrl) {
			editor.ui.componentFactory.add('videoSelector', () => {
				const buttonView = new ButtonView();

				const videoIcon = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 20">
					<path d="M1.587 1.5c-.612 0-.601-.029-.601.551v14.84c0 .59-.01.559.591.559h18.846c.602 0 .591.03.591-.56V2.052c0-.58.01-.55-.591-.55H1.587Zm.701.971h1.003v1H2.288v-1Zm16.448 0h1.003v1h-1.003v-1Zm-14.24 1h13.008v12H4.467l.029-12Zm-2.208 1h1.003v1H2.288v-1Zm16.448 0h1.003v1h-1.003v-1Zm-16.448 2h1.003v1H2.288v-1Zm16.448 0h1.003v1h-1.003v-1Zm-16.448 2h1.003v1H2.288v-1Zm16.448 0h1.003v1h-1.003v-1Zm-16.448 2h1.003v1H2.288v-1Zm16.448 0h1.003v1h-1.003v-1Zm-16.448 2h1.003l-.029 1h-.974v-1Zm16.448 0h1.003v1h-1.003v-1Zm-16.448 2h.974v1h-.974v-1Zm16.448 0h1.003v1h-1.003v-1Z"></path>
					<path d="M8.374 6.648a.399.399 0 0 1 .395-.4.402.402 0 0 1 .2.049l5.148 2.824a.4.4 0 0 1 0 .7l-5.148 2.824a.403.403 0 0 1-.595-.35V6.648Z"></path>
				</svg>`;

				buttonView.set({
					icon: videoIcon,
					label: Liferay.Language.get('video'),
					tooltip: true,
				});

				buttonView.bind('isEnabled').to(command, 'isEnabled');

				buttonView.on('execute', () => {
					openSelectionModal({
						onSelect: ({value}: {value: any}) => {
							let url: string;

							try {
								url = JSON.parse(value).url;
							}
							catch (error) {
								url = value.url;
							}

							if (!url) {
								return;
							}

							const viewFragment = editor.data.processor.toView(
								`<oembed url="${url}"></oembed>`
							);

							const modelFragment =
								editor.data.toModel(viewFragment);

							editor.model.insertContent(modelFragment);
						},
						selectEventName: config.get('itemSelectorEventName'),
						title: Liferay.Language.get('select-item'),
						url: filebrowserVideoBrowseUrl,
						zIndex: Liferay.zIndex.WINDOW + 10,
					});
				});

				return buttonView;
			});
		}
	}
}

export default ItemSelector;
