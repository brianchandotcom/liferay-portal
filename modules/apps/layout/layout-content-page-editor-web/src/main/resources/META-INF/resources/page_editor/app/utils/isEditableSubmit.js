/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';

export default function isEditableSubmit(editableId, parentItemId) {
	const parentElement = document.querySelector(
		`.${getLayoutDataItemUniqueClassName(parentItemId)}`
	);

	if (!parentElement) {
		return false;
	}

	const editableElement = parentElement.querySelector(
		`[data-lfr-editable-id='${editableId}']`
	);

	if (!editableElement) {
		return false;
	}

	const type = editableElement.getAttribute('type');

	return (
		(editableElement.tagName === 'INPUT' && type === 'submit') ||
		(editableElement.tagName === 'BUTTON' && (!type || type === 'submit'))
	);
}
