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

/**
 *
 * @description returns an object with all fileEntryIds and respective extension
 * @returns {object}
 */

function getFileEntriesExtensions() {
	const fileEntriesExtensionsNodes = document.querySelectorAll(
		'.digital-signature-file-extensions'
	);

	const fileEntriesExtensions = {};

	fileEntriesExtensionsNodes.forEach((availableFileExtension) => {
		const [
			fileEntryId,
			fileEntryExtension,
		] = availableFileExtension.textContent.split('-');

		fileEntriesExtensions[fileEntryId] = fileEntryExtension;
	});

	return fileEntriesExtensions;
}

/**
 * @description Enable / Disable CollectDigitalSignature button in SearchToolbar
 * @param {Boolean} isValid
 */

function handleCollectDigitalSignatureActionState(isValid) {
	const button = document.querySelector(
		`[title="${Liferay.Language.get('collect-digital-signature')}"]`
	);

	if (!button) {
		return;
	}

	if (isValid) {
		button.classList.remove('disabled');
		button.removeAttribute('disabled');
	}
	else {
		button.classList.add('disabled');
		button.setAttribute('disabled', 'disabled');
	}
}

/**
 *
 * @param {string[]} fileEntryIds A string list with selected fileEntryIds
 * @param {string} availableFileExtensions A string list with available permissions for DocuSign integration
 */

export function handleCollectDigitalSignatureVisibility(
	fileEntryIds,
	availableFileExtensions
) {
	const fileEntriesExtension = getFileEntriesExtensions();

	const isValid = fileEntryIds.every((fileEntryId) =>
		availableFileExtensions
			.split(',')
			.includes(fileEntriesExtension[fileEntryId])
	);

	handleCollectDigitalSignatureActionState(isValid);
}
