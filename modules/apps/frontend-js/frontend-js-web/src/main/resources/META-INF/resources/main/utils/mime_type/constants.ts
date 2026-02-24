/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const FILE_MIME_TYPE_CSS_CLASSES: Record<string, string> = {
	'application/pdf': 'file-icon-color-5',
	'default': 'file-icon-color-0',
	...Object.fromEntries(
		[
			'application/x-7z-compressed',
			'application/x-ace-compressed',
			'application/x-compressed',
			'application/x-rar-compressed',
			'application/x-zip-compressed',
			'application/zip',
		].map((mime) => [mime, 'file-icon-color-1'])
	),
	...Object.fromEntries(
		[
			'application/excel',
			'application/vnd.ms-excel',
			'application/vnd.oasis.opendocument.spreadsheet',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			'application/vnd.sun.xml.calc',
			'application/x-excel',
			'application/x-msexcel',
		].map((mime) => [mime, 'file-icon-color-2'])
	),
	...Object.fromEntries(
		[
			'application/vnd+liferay.video.external.shortcut+html',
			'audio',
			'image',
			'video',
		].map((mime) => [mime, 'file-icon-color-3'])
	),
	...Object.fromEntries(
		[
			'application/mspowerpoint',
			'application/powerpoint',
			'application/vnd.apple.keynote',
			'application/vnd.ms-powerpoint',
			'application/vnd.oasis.opendocument.presentation',
			'application/vnd.openxmlformats-officedocument.presentationml.presentation',
			'application/x-mspowerpoint',
		].map((mime) => [mime, 'file-icon-color-4'])
	),
	...Object.fromEntries(
		[
			'application/msword',
			'application/vnd.oasis.opendocument.text',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
			'text/plain',
		].map((mime) => [mime, 'file-icon-color-6'])
	),
	...Object.fromEntries(
		[
			'application/javascript',
			'text/asp',
			'text/css',
			'text/ecmascript',
			'text/html',
			'text/javascript',
			'text/x-c',
			'text/x-fortran',
			'text/x-java-source',
			'text/x-jsp',
			'text/x-pascal',
			'text/x-script.perl',
			'text/x-script.perl-module',
			'text/xml',
		].map((mime) => [mime, 'file-icon-color-7'])
	),
};

export const FILE_MIME_TYPE_ICONS: Record<string, string> = {
	'application/pdf': 'document-vector',
	'audio': 'document-multimedia',
	'default': 'document-default',
	'image': 'document-image',
	'video': 'document-multimedia',
	...Object.fromEntries(
		[
			'application/javascript',
			'text/asp',
			'text/css',
			'text/ecmascript',
			'text/html',
			'text/javascript',
			'text/x-c',
			'text/x-fortran',
			'text/x-java-source',
			'text/x-jsp',
			'text/x-pascal',
			'text/x-script.perl',
			'text/x-script.perl-module',
			'text/xml',
		].map((mime) => [mime, 'document-code'])
	),
	...Object.fromEntries(
		[
			'application/x-7z-compressed',
			'application/x-ace-compressed',
			'application/x-compressed',
			'application/x-rar-compressed',
			'application/x-zip-compressed',
			'application/zip',
		].map((mime) => [mime, 'document-compressed'])
	),
	'application/vnd+liferay.video.external.shortcut+html':
		'document-multimedia',
	...Object.fromEntries(
		[
			'application/mspowerpoint',
			'application/powerpoint',
			'application/vnd.apple.keynote',
			'application/vnd.ms-powerpoint',
			'application/vnd.oasis.opendocument.presentation',
			'application/vnd.openxmlformats-officedocument.presentationml.presentation',
			'application/x-mspowerpoint',
		].map((mime) => [mime, 'document-presentation'])
	),
	...Object.fromEntries(
		[
			'application/excel',
			'application/vnd.ms-excel',
			'application/vnd.oasis.opendocument.spreadsheet',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			'application/vnd.sun.xml.calc',
			'application/x-excel',
			'application/x-msexcel',
		].map((mime) => [mime, 'document-table'])
	),
	...Object.fromEntries(
		[
			'application/msword',
			'application/vnd.oasis.opendocument.text',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
			'text/plain',
		].map((mime) => [mime, 'document-text'])
	),
};
