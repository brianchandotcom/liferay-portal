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

			const toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			const unlinkButton = new CKEDITOR.ui.balloonToolbarButton({
				click: instance._onUnlinkButtonClick.bind(instance),
				icon: 'unlink',
				title: editor.lang.undo.undo,
			});

			toolbar.addItem('unlinkButton', unlinkButton);

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

			toolbar.addItem('targetSelect', targetSelect);

			const linkInput = new CKEDITOR.ui.balloonToolbarTextInput({
				placeholder: editor.lang.link.title,
			});

			const changeOrClickHandler = instance._onOkButtonClick.bind(
				instance
			);

			linkInput.on('change', () => {
				changeOrClickHandler();
			});

			toolbar.addItem('linkInput', linkInput);

			const okButton = new CKEDITOR.ui.balloonToolbarButton({
				click: changeOrClickHandler,
				icon: 'check',
				title: editor.lang.common.ok,
			});

			toolbar.addItem('okButton', okButton);

			const folderButton = new CKEDITOR.ui.balloonToolbarButton({
				click() {
					editor.execCommand('linkselector', (selectedItemURL) => {
						const linkInput = toolbar.getItem('linkInput');

						linkInput.setValue(selectedItemURL);
					});
				},
				icon: 'folder',
				title: editor.lang.common.browseServer,
			});

			toolbar.addItem('folderButton', folderButton);

			instance._toolbar = toolbar;
		},

		_currentSelection: null,

		_onOkButtonClick() {
			const linkInput = this._toolbar.getItem('linkInput');

			if (!linkInput.value) {
				this._toolbar.hide();

				return;
			}

			let selection = this._currentSelection;

			const attributes = {
				'data-cke-saved-href': linkInput.value,
				href: linkInput.value,
				rel: 'noopener',
			};

			const targetSelect = this._toolbar.getItem('targetSelect');

			const hasTarget = targetSelect.value !== 'Default';

			if (hasTarget) {
				attributes.target = targetSelect.value;
			}

			if (this.editor.widgets.selected[0]) {
				const imageWidget = this.editor.widgets.selected[0];

				if (!imageWidget || imageWidget.name !== 'image') {
					return;
				}

				const link = imageWidget.wrapper.findOne('a');

				if (link) {
					link.setAttributes(attributes);
				}
				else {
					const newData = Object.assign(imageWidget.data, {
						link: {
							url: linkInput.value,
						},
					});

					imageWidget.shiftState({
						deflate() {},
						element: imageWidget.element,
						inflate() {
							const link = imageWidget.wrapper.findOne('a');

							link.setAttributes(attributes);
						},
						newData,
						oldData: imageWidget.oldData,
						widget: imageWidget,
					});
				}

				this._toolbar.hide();

				imageWidget.focus();
				imageWidget.element.focus();
			}
			else {
				const startElement = selection.getStartElement();

				let linkElement;

				if (startElement.getName() === 'a') {
					linkElement = startElement;

					linkElement.setAttributes(attributes);

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

					linkElement.setAttributes(attributes);
					linkElement.setText(selectedText);

					linkElement.insertAfter(bookmark.endNode);

					range.moveToBookmark(bookmark);

					range.deleteContents();

					linkInput.clear();

					this._toolbar.hide();

					selection.unlock(true);
				}
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

			const eventListeners = [];

			editor.on('contentDom', () => {
				const editable = editor.editable();

				const showLinkToolbar = (event) => {
					const imageWidget = editor.widgets.selected[0];

					const target = event.data.getTarget();

					if (imageWidget?.name === 'image') {
						this._toolbar?.hide();
					}
					else if (target.$.closest('a')) {
						editor.fire('showToolbar', {
							toolbarCommand: 'linkToolbar',
						});
					}
					else {
						this._toolbar?.hide();
					}
				};

				eventListeners.push(editable.on('mouseup', showLinkToolbar));
				eventListeners.push(editable.on('keyup', showLinkToolbar));
			});

			editor.on('destroy', () => {
				eventListeners.forEach((listener) => {
					listener.removeListener();
				});
			});

			editor.on('unlinkTextOrImage', (event) => {
				instance._currentSelection = event.data.selection;
				instance._onUnlinkButtonClick();
			});

			editor.addCommand('linkToolbar', {
				exec(editor) {
					instance._createToolbar();

					const selection = editor.getSelection();

					selection.lock();

					instance._currentSelection = selection;

					editor.balloonToolbars.hide();

					instance._toolbar.attach(selection);

					const startElement = selection.getStartElement();

					let link;

					if (editor.widgets.selected[0]?.name === 'image') {
						link = startElement.findOne('a');
					}
					else if (startElement.getName() === 'a') {
						link = startElement;
					}

					const linkInput = instance._toolbar.getItem('linkInput');

					if (link) {
						linkInput.setValue(link.getAttribute('href'));
					}
					else {
						linkInput.clear();
					}
				},
			});
		},

		requires: ['uibutton', 'uiselect', 'uitextinput'],
	});
})();
