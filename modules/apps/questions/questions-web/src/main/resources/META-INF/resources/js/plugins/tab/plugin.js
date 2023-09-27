/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	CKEDITOR.plugins.add('tab', {
		init(editor) {
			let tabSpaces = editor.config.tabSpaces || 0;
			let tabText = '';

			while (tabSpaces--) {
				tabText += '\xa0';
			}

			if (tabText) {
				editor.on('key', (ev) => {
					if (ev.data.keyCode === 9) {
						editor.insertText(tabText);
						ev.cancel();
					}
				});
			}
		},
	});
})();
