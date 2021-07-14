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

(function () {
	const pluginName = 'imagealt';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		_createToolbar() {
			if (this._toolbar) {
				return this._toolbar;
			}

			const instance = this;

			this._toolbar = new CKEDITOR.ui.balloonToolbar(this.editor);

			const altInput = new CKEDITOR.ui.balloonToolbarTextInput({
				placeholder: this.editor.lang.image.alt,
			});

			altInput.on('cancel', () => {
				instance._toolbar.hide();

				instance._parentToolbar.show();
			});

			altInput.on('change', () => {
				instance.editor.execCommand('imageAlt');
			});

			this._toolbar.addItem('altInput', altInput);

			const okButton = new CKEDITOR.ui.balloonToolbarButton({
				command: 'imageAlt',
				icon: 'check',
				title: this.editor.lang.common.ok,
			});

			this._toolbar.addItem('okButton', okButton);

			return this._toolbar;
		},

		_onSelectionChange() {
			if (this._toolbar) {
				this._toolbar.hide();
			}
		},

		_parentToolbar: null,

		_toolbar: null,

		init(editor) {
			const instance = this;

			instance.editor = editor;

			editor.on('selectionChange', this._onSelectionChange.bind(this));

			editor.addCommand('imageAlt', {
				exec(editor) {
					const imageWidget =
						editor.widgets.focused || editor.widgets.selected[0];

					if (!imageWidget || imageWidget.name !== 'image') {
						return;
					}

					const altInput = instance._toolbar.getItem('altInput');

					imageWidget.data.alt = altInput.value;
					imageWidget.element.setAttribute('alt', altInput.value);

					instance._toolbar.hide();

					if (instance._parentToolbar) {
						instance._parentToolbar.show();
					}
				},
			});

			editor.addCommand('imageAltToolbar', {
				exec(editor) {
					const toolbar = instance._createToolbar();

					const imageWidget =
						editor.widgets.focused || editor.widgets.selected[0];

					if (!imageWidget || imageWidget.name !== 'image') {
						return;
					}

					toolbar.getItem('altInput').value = imageWidget.data.alt;

					const selection = editor.getSelection();

					toolbar.attach(selection);
				},
			});

			editor.ui.addBalloonToolbarButton('AltImg', {
				click() {
					instance._parentToolbar = this.balloonToolbar;
					editor.execCommand('imageAltToolbar');
				},
				icon: 'low-vision',
				title: editor.lang.image.alt,
			});
		},
		requires: ['uibutton', 'uitextinput'],
	});
})();
