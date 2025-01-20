/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EditorConfig} from 'ckeditor5';

const CKEditor5DefaultPreset: EditorConfig = {
	alignment: {
		options: ['left', 'center', 'right'],
	},
	table: {
		contentToolbar: [
			'tableColumn',
			'tableRow',
			'tableProperties',
			'toggleTableCaption',
		],
	},
	toolbar: [
		'undo',
		'redo',
		'|',
		'bold',
		'italic',
		'underline',
		'strikethrough',
		'|',
		'fontColor',
		'fontBackgroundColor',
		'|',
		'removeFormat',
		'|',
		'numberedList',
		'bulletedList',
		'|',
		'indent',
		'outdent',
		'|',
		'blockQuote',
		'|',
		'link',
		'|',
		'insertTable',
		'|',
		'mediaEmbed',
		'|',
		'horizontalLine',
		'|',
		'alignment',
		'sourceEditing',
	],
};

export default CKEditor5DefaultPreset;
