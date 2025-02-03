/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	exports: [
		'@ckeditor/ckeditor5-react',
		'ckeditor5',
		'ckeditor5/ckeditor5.css',
	],
	main: './src/main/resources/META-INF/resources/js/index.ts',
	symbols: {
		ckeditor5: [
			'Alignment',
			'BlockQuote',
			'Bold',
			'ClassicEditor',
			'EditorConfig',
			'Essentials',
			'Font',
			'GeneralHtmlSupport',
			'HorizontalLine',
			'Indent',
			'IndentBlock',
			'Italic',
			'Link',
			'List',
			'MediaEmbed',
			'Paragraph',
			'RemoveFormat',
			'SourceEditing',
			'Strikethrough',
			'Table',
			'TableCaption',
			'TableProperties',
			'TableToolbar',
			'Underline',
		],
	},
};
