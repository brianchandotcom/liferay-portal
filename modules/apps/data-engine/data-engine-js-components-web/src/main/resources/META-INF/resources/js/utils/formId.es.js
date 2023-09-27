/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getFormId(form) {
	return form?.dataset.ddmforminstanceid;
}

export function getFormNode(element) {
	return element.closest('form');
}

export function getUid() {
	return Math.random().toString(36).substr(2, 9);
}
