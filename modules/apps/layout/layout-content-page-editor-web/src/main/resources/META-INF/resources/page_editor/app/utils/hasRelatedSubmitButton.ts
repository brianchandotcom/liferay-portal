/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemTopperUniqueClassName from './getLayoutDataItemTopperUniqueClassName';

export function hasRelatedSubmitButton(formId: string, document: Document) {
	const form = document.querySelector(
		`.${getLayoutDataItemTopperUniqueClassName(formId)} .lfr-layout-structure-item-form`
	);

	if (!form) {
		return false;
	}

	const button = document.querySelector(
		`button[form="${form.getAttribute('id')}"]`
	);

	return Boolean(button);
}
