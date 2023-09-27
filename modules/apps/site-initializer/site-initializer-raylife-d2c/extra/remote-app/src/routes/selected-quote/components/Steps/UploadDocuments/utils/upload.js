/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const fileTypes = {
	'application/pdf': 'document-pdf',
	'application/vnd.openxmlformats-officedocument.presentationml.presentation':
		'document-presentation',
	'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet':
		'document-table',
	'application/vnd.openxmlformats-officedocument.wordprocessingml.document':
		'document-text',
	'text/plain': 'document-text',
};

export function chooseIcon(fileType) {
	return fileTypes[fileType] || 'document-unknown';
}

export function validateExtensions(fileType, type) {
	const validExtensions =
		type === 'image'
			? ['image/jpeg', 'image/jpg', 'image/png']
			: Object.keys(fileTypes);

	return validExtensions.includes(fileType);
}

export function sectionsHasError(sections) {
	return sections.some((section) => section.error);
}
