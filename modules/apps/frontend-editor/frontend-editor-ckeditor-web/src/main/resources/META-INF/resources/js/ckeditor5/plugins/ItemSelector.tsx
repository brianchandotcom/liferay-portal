/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ButtonView, Config, Plugin, icons} from 'ckeditor5';
import {openSelectionModal} from 'frontend-js-components-web';

import {ClassicEditorConfig} from '../utils/types';

class ItemSelector extends Plugin {
	init() {
		const editor = this.editor;

		const config: Config<ClassicEditorConfig> = editor.config;

		const url = config.get('filebrowserImageBrowseUrl');

		if (!url) {
			return;
		}

		editor.ui.componentFactory.add('imageSelector', () => {
			const buttonView = new ButtonView();

			buttonView.set({
				icon: icons.image,
				label: Liferay.Language.get('image'),
				tooltip: true,
			});

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

						const viewFragment = editor.data.processor.toView(
							`<img src="${url}">`
						);

						const modelFragment = editor.data.toModel(viewFragment);

						editor.model.insertContent(modelFragment);
					},
					selectEventName: config.get('itemSelectorEventName'),
					title: Liferay.Language.get('select-item'),
					url,
					zIndex: Liferay.zIndex.WINDOW + 10,
				});
			});

			return buttonView;
		});
	}
}

export default ItemSelector;
