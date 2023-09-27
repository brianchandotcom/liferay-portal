/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function disableSubmitButton(submitButtonId) {
	return document
		.getElementById(submitButtonId)
		?.setAttribute('disabled', 'true');
}

export function enableSubmitButton(submitButtonId) {
	return document.getElementById(submitButtonId)?.removeAttribute('disabled');
}
