/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getKeyboardFocusableElements(element = document) {
	return [
		...element.querySelectorAll(
			'a[href], button, input, textarea, select, details,[tabindex]:not([tabindex="-1"])'
		),
	].filter(
		(element) =>
			!element.hasAttribute('disabled') &&
			!element.getAttribute('aria-hidden')
	);
}
