/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getEditableElement(target) {
	let editableElement = target.closest('lfr-editable');

	if (!editableElement) {
		editableElement = target.closest('[data-lfr-editable-id]');
	}

	if (!editableElement) {
		editableElement = target.closest('[data-lfr-background-image-id]');
	}

	return editableElement;
}
