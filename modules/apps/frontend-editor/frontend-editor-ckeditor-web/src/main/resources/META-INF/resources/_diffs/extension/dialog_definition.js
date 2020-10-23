/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

CKEDITOR.on('dialogDefinition', (event) => {
	if (event.editor === ckEditor) {
		var boundingWindow = event.editor.window;

		var dialogDefinition = event.data.definition;

		var dialog = event.data.dialog;

		var onShow = dialogDefinition.onShow;

		var centerDialog = function () {
			var dialogSize = dialog.getSize();

			var x = window.innerWidth / 2 - dialogSize.width / 2;
			var y = window.innerHeight / 2 - dialogSize.height / 2;

			dialog.move(x, y, false);
		};

		dialogDefinition.onShow = function () {
			if (typeof onShow === 'function') {
				onShow.apply(this, arguments);
			}

			centerDialog();
		};

		AUI().use('aui-debounce', (A) => {
			boundingWindow.on(
				'resize',
				A.debounce(() => {
					centerDialog();
				}, 250)
			);
		});

		var clearEventHandler = function () {
			Liferay.detach('resize', boundingWindow);
		};

		Liferay.once('destroyPortlet', clearEventHandler);
	}
});
