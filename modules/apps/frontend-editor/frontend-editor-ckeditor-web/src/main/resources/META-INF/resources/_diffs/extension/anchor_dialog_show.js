/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

ckEditor.on('dialogShow', (event) => {
	const dialog = event.data.definition.dialog;

	if (dialog.getName() === 'anchor') {
		const originalFn = dialog.getValueOf.bind(dialog);

		dialog.getValueOf = function (pageId, elementId) {
			let value = originalFn(pageId, elementId);

			if (pageId === 'info' && elementId === 'txtName') {
				value = value.replace(/ /g, '_');
			}

			return value;
		};
	}
});
