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
	const pluginName = 'linktoolbar';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		_createToolbar() {
			if (this._toolbar) {
				return;
			}

			const instance = this;

			const editor = instance.editor;

			instance._toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			const unlinkButton = new CKEDITOR.ui.balloonToolbarButton({
				click: instance._onUnlinkButtonClick.bind(instance),
				icon: 'unlink',
				title: editor.lang.undo.undo,
			});

			instance._toolbar.addItem('unlinkButton', unlinkButton);

			const targetSelect = new CKEDITOR.ui.balloonToolbarSelect({
				items: [
					{
						label: editor.lang.common.optionDefault,
						value: '',
					},
					{
						label: editor.lang.common.targetSelf,
						value: '_self',
					},
					{
						label: editor.lang.common.targetNew,
						value: '_blank',
					},
					{
						label: editor.lang.common.targetParent,
						value: '_parent',
					},
					{
						label: editor.lang.common.targetTop,
						value: '_top',
					},
				],
				label: editor.lang.common.target,
				name: editor.lang.common.target,
			});

			instance._toolbar.addItem('targetSelect', targetSelect);

			const linkInput = new CKEDITOR.ui.balloonToolbarTextInput({
				placeholder: editor.lang.link.title,
			});

			linkInput.on('cancel', () => {
				instance._toolbar.hide();
			});

			const changeOrClickHandler = instance._onOkButtonClick.bind(
				instance
			);

			linkInput.on('change', () => {
				changeOrClickHandler();
			});

			instance._toolbar.addItem('linkInput', linkInput);

			const okButton = new CKEDITOR.ui.balloonToolbarButton({
				click: changeOrClickHandler,
				icon: 'check',
				title: editor.lang.common.ok,
			});

			instance._toolbar.addItem('okButton', okButton);

			const folderButton = new CKEDITOR.ui.balloonToolbarButton({
				click() {
					instance._toolbar.hide();

					const editor = instance.editor;

					const imageWidget = editor.widgets.selected[0];

					if (!imageWidget || imageWidget.name !== 'image') {
						return;
					}

					editor.execCommand('imageselector');
				},
				icon: 'folder',
				title: editor.lang.common.browseServer,
			});

			instance._toolbar.addItem('folderButton', folderButton);
		},

		_currentSelection: null,

		_onOkButtonClick() {
			const linkInput = this._toolbar.getItem('linkInput');

			if (!linkInput.value) {
				this._toolbar.hide();

				return;
			}

			const targetSelect = this._toolbar.getItem('targetSelect');

			const hasTarget = targetSelect.value !== 'Default';

			if (this.editor.widgets.selected[0]) {
				const imageWidget = this.editor.widgets.selected[0];

				if (!imageWidget || imageWidget.name !== 'image') {
					return;
				}

				const newData = Object.assign(imageWidget.data, {
					link: {
						url: linkInput.value,
					},
				});

				imageWidget.shiftState({
					deflate() {},
					element: imageWidget.element,
					inflate() {
						const wrapperElement = imageWidget.wrapper;

						const linkElement = wrapperElement.findOne('a');

						if (linkElement) {
							linkElement.setAttribute('rel', 'noopener');

							if (hasTarget) {
								linkElement.setAttribute(
									'target',
									targetSelect.value
								);
							}
						}
					},
					newData,
					oldData: imageWidget.oldData,
					widget: imageWidget,
				});

				this._toolbar.hide();

				imageWidget.focus();
				imageWidget.element.focus();
			}
			else {
				let selection = this._currentSelection;

				const startElement = selection.getStartElement();

				let linkElement;

				if (startElement.getName() === 'a') {
					linkElement = startElement;

					linkElement.setAttributes({
						'data-cke-saved-href': linkInput.value,
						href: linkInput.value,
						rel: 'noopener',
					});

					if (hasTarget) {
						linkElement.setAttribute('target', targetSelect.value);
					}

					linkInput.clear();

					this._toolbar.hide();
				}
				else if (selection.getSelectedText()) {
					selection = this.editor.getSelection();

					const selectedText = selection.getSelectedText();

					const ranges = selection.getRanges();

					const range = ranges[0];

					const bookmark = range.createBookmark();

					linkElement = new CKEDITOR.dom.element('a');

					linkElement.setAttributes({
						href: linkInput.value,
						rel: 'noopener',
					});

					if (hasTarget) {
						linkElement.setAttribute('target', targetSelect.value);
					}

					linkElement.setText(selectedText);

					linkElement.insertAfter(bookmark.endNode);

					range.moveToBookmark(bookmark);
					range.deleteContents();

					linkInput.clear();

					this._toolbar.hide();

					selection = this.editor.getSelection();

					selection.unlock(true);
				}
			}
		},

		_onSelectionChange() {
			if (this._toolbar) {
				this._toolbar.hide();
			}
		},

		_onUnlinkButtonClick() {
			const startElement = this._currentSelection.getStartElement();
			const startElementName = startElement.getName();

			if (startElementName === 'a') {
				startElement.remove(true);
			}
			else if (startElement.findOne('img')) {
				const imageWidget = this.editor.widgets.selected[0];

				if (!imageWidget || imageWidget.name !== 'image') {
					return;
				}

				if (
					Object.prototype.hasOwnProperty.call(
						imageWidget.data,
						'link'
					)
				) {
					imageWidget.setData('link', {url: ''});

					const linkInput = this._toolbar.getItem('linkInput');

					linkInput.clear();

					this._toolbar.hide();
				}
			}

			this.editor.fire('hideToolbars');
		},

		_toolbar: null,

		init(editor) {
			const instance = this;

			instance.editor = editor;

			editor.on('selectionChange', this._onSelectionChange.bind(this));

			editor.on('unlinkTextOrImage', (event) => {
				instance._currentSelection = event.data.selection;
				instance._onUnlinkButtonClick();
			});

			editor.addCommand('linkToolbar', {
				exec(editor) {
					instance._createToolbar();

					if (editor.widgets.selected[0]) {
						const imageWidget = editor.widgets.selected[0];

						if (!imageWidget || imageWidget.name !== 'image') {
							return;
						}

						imageWidget.focus();
					}

					const selection = editor.getSelection();

					selection.lock();

					instance._currentSelection = selection;

					instance._toolbar.attach(selection);

					const startElement = selection.getStartElement();

					const linkInput = instance._toolbar.getItem('linkInput');

					if (startElement.getName() === 'a') {
						linkInput.setValue(
							selection.getStartElement().getAttribute('href')
						);
					}

					setTimeout(() => {
						const linkInputElement = linkInput.getInputElement();

						if (linkInputElement) {
							linkInputElement.focus();
						}
					}, 0);
				},
			});
		},

		requires: ['uibutton', 'uiselect', 'uitextinput'],
	});
})();
