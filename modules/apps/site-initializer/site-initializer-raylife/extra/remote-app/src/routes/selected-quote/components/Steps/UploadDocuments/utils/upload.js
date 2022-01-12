/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
