/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CKEditor} from '@ckeditor/ckeditor5-react';
import {
	Alignment,
	BlockQuote,
	Bold,
	ClassicEditor as BaseClassicEditor,
	EditorConfig,
	Essentials,
	Font,
	GeneralHtmlSupport,
	Heading,
	HorizontalLine,
	Indent,
	Italic,
	Link,
	List,
	MediaEmbed,
	Paragraph,
	RemoveFormat,
	SourceEditing,
	Strikethrough,
	Style,
	Table,
	TableCaption,
	TableProperties,
	TableToolbar,
	Underline,
} from 'ckeditor5';
import React from 'react';

import '../../css/ckeditor5/editor.scss';

const ClassicEditor = ({config}: {config?: EditorConfig}) => {
	const defaultConfig: EditorConfig = {
		plugins: [
			Alignment,
			BlockQuote,
			Bold,
			Essentials,
			Font,
			GeneralHtmlSupport,
			Heading,
			HorizontalLine,
			Indent,
			Italic,
			Link,
			List,
			MediaEmbed,
			Paragraph,
			RemoveFormat,
			SourceEditing,
			Strikethrough,
			Style,
			Table,
			TableCaption,
			TableProperties,
			TableToolbar,
			Underline,
		],
		toolbar: ['undo', 'redo', '|', 'bold', 'italic', 'underline'],
		ui: {
			viewportOffset: {
				top: 56
			}
		}
	};

	if (!Liferay.FeatureFlags['LPD-11235']) {
		return <></>;
	}

	return (
		<CKEditor
			config={{
				...defaultConfig,
				...config,
			}}
			editor={BaseClassicEditor}
			onReady={(editor: BaseClassicEditor) => {
				editor.ui.view.toolbar.items.map((item: any) => {
					if (item.buttonView) {
						item.buttonView.tooltipPosition = 'n';
					}

					item.tooltipPosition = 'n';
				});
			}}
		/>
	);
};

export default ClassicEditor;
