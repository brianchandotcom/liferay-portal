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

			const editor = this.editor;

			const toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			const configToolbarItems = editor.config.toolbarLink?.split(',');

			if (!configToolbarItems) {
				return;
			}

			configToolbarItems.forEach((item) => {
				const toolbarItem = this._toolbarItems[item];

				if (toolbarItem) {
					toolbar.addItem(item, toolbarItem);

					return;
				}

				const globalToolbarItem = editor.ui.create(item);

				if (globalToolbarItem) {
					toolbar.addItem(item, globalToolbarItem);
				}
			});

			if (CKEDITOR.tools.isEmpty(toolbar._items)) {
				return;
			}

			this._toolbar = toolbar;
		},

		_currentSelection: null,

		_initToolbarItems() {
			const instance = this;

			const toolbarItems = {};

			const editor = instance.editor;

			toolbarItems['LinkRemove'] = new CKEDITOR.ui.balloonToolbarButton({
				click: () => {
					const selection = editor.getSelection();

					const bookmarks = selection.createBookmarks();
					const ranges = selection.getRanges();

					for (let i = 0; i < ranges.length; i++) {
						const rangeRoot = ranges[i].getCommonAncestor(true);

						const element = rangeRoot.getAscendant('a', true);

						if (!element) {
							continue;
						}

						ranges[i].selectNodeContents(element);
					}

					selection.selectRanges(ranges);

					editor.document.$.execCommand('unlink', false, null);

					selection.selectBookmarks(bookmarks);

					instance._toolbar.hide();
				},
				icon: 'unlink',
				title: editor.lang.link.unlink,
			});

			toolbarItems['LinkTarget'] = new CKEDITOR.ui.balloonToolbarSelect({
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

			toolbarItems['LinkInput'] = new CKEDITOR.ui.balloonToolbarTextInput(
				{
					placeholder: editor.lang.link.title,
				}
			);

			toolbarItems['LinkConfirm'] = new CKEDITOR.ui.balloonToolbarButton({
				click: () => {
					const linkInput = instance._toolbar.getItem('LinkInput');

					if (!linkInput?.value) {
						instance._toolbar.hide();

						return;
					}

					let selection = instance._currentSelection;

					const attributes = {
						'data-cke-saved-href': linkInput.value,
						'href': linkInput.value,
						'rel': 'noopener',
					};

					const linkTarget = instance._toolbar.getItem('LinkTarget');

					if (linkTarget) {
						const hasTarget = linkTarget.value !== 'Default';

						if (hasTarget) {
							attributes.target = linkTarget.value;
						}
					}

					if (editor.widgets.selected[0]) {
						const imageWidget = editor.widgets.selected[0];

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
									const link = imageWidget.wrapper.findOne(
										'a'
									);

									link.setAttributes(attributes);
								},
								newData,
								oldData: imageWidget.oldData,
								widget: imageWidget,
							});
						}

						instance._toolbar.hide();

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

							instance._toolbar.hide();
						}
						else if (selection.getSelectedText()) {
							selection = editor.getSelection();

							const selectedText = selection.getSelectedText();

							const [firstRange] = selection.getRanges();

							const bookmark = firstRange.createBookmark();

							linkElement = new CKEDITOR.dom.element('a');

							linkElement.setAttributes(attributes);
							linkElement.setText(selectedText);

							linkElement.insertAfter(bookmark.endNode);

							firstRange.moveToBookmark(bookmark);

							firstRange.deleteContents();

							linkInput.clear();

							instance._toolbar.hide();

							selection.unlock(true);
						}
					}
				},
				icon: 'check',
				title: editor.lang.common.confirm,
			});

			toolbarItems['LinkBrowse'] = new CKEDITOR.ui.balloonToolbarButton({
				click() {
					editor.execCommand('linkselector', (selectedItemURL) => {
						const linkInput = instance._toolbar.getItem(
							'LinkInput'
						);

						linkInput?.setValue(selectedItemURL);
					});
				},
				icon: 'folder',
				title: editor.lang.common.browseServer,
			});

			instance._toolbarItems = toolbarItems;
		},

		_toolbar: null,

		_toolbarItems: null,

		init(editor) {
			const instance = this;

			instance.editor = editor;

			instance._initToolbarItems();

			editor.on('selectionChange', () => {
				const selection = editor.getSelection();

				const selectedElement = selection.getSelectedElement();

				const startElement = selection.getStartElement();

				if (selectedElement || selection.getSelectedText()) {
					instance._toolbar?.hide();

					return;
				}

				if (startElement.getName() === 'a') {
					editor.fire('showToolbar', {
						toolbarCommand: 'linkToolbar',
					});
				}
				else {
					instance._toolbar?.hide();
				}
			});

			editor.addCommand('linkToolbar', {
				exec(editor) {
					instance._createToolbar();

					if (!instance._toolbar) {
						return;
					}

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

					const linkInput = instance._toolbar.getItem('LinkInput');

					if (linkInput) {
						if (link) {
							linkInput.setValue(link.getAttribute('href'));
						}
						else {
							linkInput.clear();
						}
					}
				},
			});
		},

		requires: ['uibutton', 'uiselect', 'uitextinput'],
	});
})();
