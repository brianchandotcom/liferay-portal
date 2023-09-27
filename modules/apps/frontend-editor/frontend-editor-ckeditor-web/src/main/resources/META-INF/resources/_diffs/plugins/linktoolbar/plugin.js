/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const pluginName = 'linktoolbar';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		_createToolbar({editor}) {
			const toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			toolbar.addItem(
				'LinkRemove',
				new CKEDITOR.ui.balloonToolbarButton({
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

						toolbar.hide();
					},
					icon: 'unlink',
					title: editor.lang.link.unlink,
				})
			);

			toolbar.addItem(
				'LinkTarget',
				new CKEDITOR.ui.balloonToolbarSelect({
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
				})
			);

			toolbar.addItem(
				'LinkInput',
				new CKEDITOR.ui.balloonToolbarTextInput({
					placeholder: editor.lang.link.title,
				})
			);

			toolbar.addItem(
				'LinkConfirm',
				new CKEDITOR.ui.balloonToolbarButton({
					click: () => {
						const linkInput = toolbar.getItem('LinkInput');

						if (!linkInput?.value) {
							toolbar.hide();

							return;
						}

						const attributes = {
							'data-cke-saved-href': linkInput.value,
							'href': linkInput.value,
							'rel': 'noopener',
						};

						const linkTarget = toolbar.getItem('LinkTarget');

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
								const newData = Object.assign(
									imageWidget.data,
									{
										link: {
											url: linkInput.value,
										},
									}
								);

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

							toolbar.hide();

							imageWidget.focus();
							imageWidget.element.focus();
						}
						else {
							const selection = editor.getSelection();

							const startElement = selection.getStartElement();

							let linkElement;

							if (startElement.getName() === 'a') {
								linkElement = startElement;

								linkElement.setAttributes(attributes);

								linkInput.clear();

								toolbar.hide();
							}
							else if (selection.getSelectedText()) {
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

								toolbar.hide();

								selection.unlock(true);
							}
						}
					},
					icon: 'check',
					title: editor.lang.common.confirm,
				})
			);

			toolbar.addItem(
				'LinkBrowse',
				new CKEDITOR.ui.balloonToolbarButton({
					click() {
						editor.execCommand(
							'linkselector',
							(selectedItemURL) => {
								const linkInput = toolbar.getItem('LinkInput');

								linkInput?.setValue(selectedItemURL);
							}
						);
					},
					icon: 'folder',
					title: editor.lang.common.browseServer,
				})
			);

			return toolbar;
		},

		_focusedEditorName: null,

		_getToolbar({editor}) {
			let toolbar = editor.liferayToolbars.linkToolbar;

			if (toolbar) {
				return toolbar;
			}

			toolbar = this._createToolbar({editor});

			editor.liferayToolbars.linkToolbar = toolbar;

			return toolbar;
		},

		init(editor) {
			const instance = this;

			editor.liferayToolbars = editor.liferayToolbars ?? {};

			const eventListeners = [];

			eventListeners.push(
				editor.on('blur', () => {
					requestAnimationFrame(() => {
						if (this._focusedEditorName !== editor.name) {
							editor.liferayToolbars.linkToolbar?.hide();
						}
					});
				}),

				editor.on('destroy', () => {
					eventListeners.forEach((listener) => {
						listener.removeListener();
					});
				}),

				editor.on('focus', () => {
					this._focusedEditorName = editor.name;
				}),

				editor.on('selectionChange', () => {
					const selection = editor.getSelection();

					const selectedElement = selection.getSelectedElement();

					const startElement = selection.getStartElement();

					const linkToolbar = editor.liferayToolbars.linkToolbar;

					if (selectedElement || selection.getSelectedText()) {
						linkToolbar?.hide();

						return;
					}

					if (startElement.getName() === 'a') {
						editor.fire('showToolbar', {
							toolbarCommand: 'linkToolbar',
						});
					}
					else {
						linkToolbar?.hide();
					}
				})
			);

			editor.addCommand('linkToolbar', {
				exec(editor) {
					editor.balloonToolbars.hide();

					const toolbar = instance._getToolbar({editor});

					const selection = editor.getSelection();

					selection.lock();

					toolbar.attach(selection);

					const startElement = selection.getStartElement();

					let link;

					if (editor.widgets.selected[0]?.name === 'image') {
						link = startElement.findOne('a');
					}
					else if (startElement.getName() === 'a') {
						link = startElement;
					}

					const linkInput = toolbar.getItem('LinkInput');

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
