/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function focusInput(inputElement: HTMLInputElement) {

	// This delay is intentional to give screen readers time to process and
	// accept the focus change. Without this delay, the focus is often
	// ignored by the screen reader even though it works visually.

	setTimeout(() => {
		inputElement.focus();

		inputElement.scrollIntoView({
			block: 'center',
		});
	}, 50);
}
