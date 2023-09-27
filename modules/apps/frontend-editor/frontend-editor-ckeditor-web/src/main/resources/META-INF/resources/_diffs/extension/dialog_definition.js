/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

CKEDITOR.on('dialogDefinition', (event) => {
	const boundingWindow = event.editor.window;

	const dialogDefinition = event.data.definition;

	const dialog = event.data.dialog;

	const onShow = dialogDefinition.onShow;

	const centerDialog = function () {
		const dialogSize = dialog.getSize();

		const x = window.innerWidth / 2 - dialogSize.width / 2;
		const y = window.innerHeight / 2 - dialogSize.height / 2;

		dialog.move(x, y, false);
	};

	dialogDefinition.onShow = function () {
		if (typeof onShow === 'function') {
			onShow.apply(this, arguments);
		}

		centerDialog();
	};

	const debounce = function (fn, delay) {
		return function debounced() {
			clearTimeout(debounced.id);
			debounced.id = setTimeout(() => {
				fn();
			}, delay);
		};
	};

	const debounced = boundingWindow.on(
		'resize',
		debounce(() => {
			centerDialog();
		}, 250)
	);

	const clearEventHandler = function () {
		clearTimeout(debounced.id);
	};

	Liferay.once('destroyPortlet', clearEventHandler);
});
