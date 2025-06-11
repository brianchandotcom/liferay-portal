/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LiferayEditorConfig, TEditor} from 'frontend-editor-ckeditor-web';
import {openSelectionModal} from 'frontend-js-components-web';

import EmptyAltImagePlugin from './plugins/EmptyAltImagePlugin';

export type EditorConfig = LiferayEditorConfig & {
	documentBrowseLinkCallback: (
		editor: TEditor,
		url: string,
		changeLinkCallback: () => void
	) => void;
	documentBrowseLinkUrl: string;
	editorTransformerURLs: string;
	filebrowserImageBrowseLinkUrl: string;
	filebrowserImageBrowseUrl: string;
};

export default function getCKEditorConfig({
	editorConfig: initialConfig,
	editorName,
	initialData,
	itemSelectorEventName,
}: {
	editorConfig: EditorConfig;
	editorName: string;
	initialData: string;
	itemSelectorEventName: string;
}) {
	let config = initialConfig;
	const toolbarItems = Array.isArray(config.toolbar)
		? config.toolbar
		: config.toolbar?.items;

	if (config.preset === 'advanced') {
		config = {
			...config,
			documentBrowseLinkCallback: (editor, url, changeLinkCallback) => {
				openSelectionModal({
					onSelect: changeLinkCallback,
					selectEventName: itemSelectorEventName,
					title: Liferay.Language.get('select-item'),
					url,
				});
			},
			documentBrowseLinkUrl: config.documentBrowseLinkUrl.replaceAll(
				'_EDITOR_NAME_',
				editorName
			),
			filebrowserImageBrowseLinkUrl:
				config.filebrowserImageBrowseLinkUrl.replaceAll(
					'_EDITOR_NAME_',
					editorName
				),
			filebrowserImageBrowseUrl:
				config.filebrowserImageBrowseUrl.replaceAll(
					'_EDITOR_NAME_',
					editorName
				),

			itemSelectorEventName,
		};
	}

	return {
		...config,
		...(toolbarItems?.includes('imageSelector') && {
			extraPlugins: [EmptyAltImagePlugin],
		}),
		initialData,
		name: editorName,
	};
}
