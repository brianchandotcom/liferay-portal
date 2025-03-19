/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function disableFormSubmitButton(
	disabled: boolean,
	portletNamespace: string
) {
	const submitButton = document.getElementById(
		portletNamespace + 'editClientExtensionEntrySubmitButton'
	) as HTMLButtonElement;

	if (submitButton) {
		submitButton.disabled = disabled;
	}
}
