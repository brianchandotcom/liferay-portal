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
	const pluginName = 'insertbutton';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	const BUTTON_SIDE_OFFSET = 14;

	const template = new CKEDITOR.template(
		'<button class="lfr-balloon-editor-insert-button">+</button>'
	);

	CKEDITOR.plugins.add(pluginName, {
		_createInsertTableToolbar({editor}) {
			const instance = this;

			const tableToolbar = new CKEDITOR.ui.balloonToolbar(editor);

			const tableData = {
				columns: 3,
				rows: 3,
			};

			const rowsInput = new CKEDITOR.ui.balloonToolbarNumberInput({
				change(value) {
					tableData.rows = value;
				},
				label: editor.lang.table.rows,
				min: 1,
				step: 1,
				value: tableData.rows,
			});

			tableToolbar.addItem('rowsInput', rowsInput);

			const columnsInput = new CKEDITOR.ui.balloonToolbarNumberInput({
				change(value) {
					tableData.columns = value;
				},
				label: editor.lang.table.columns,
				min: 1,
				step: 1,
				value: tableData.columns,
			});

			tableToolbar.addItem('columnsInput', columnsInput);

			const okButton = new CKEDITOR.ui.balloonToolbarButton({
				click() {
					instance._createTable({editor, tableData, tableToolbar});
				},
				icon: 'check',
				title: editor.lang.common.ok,
			});

			tableToolbar.addItem('okButton', okButton);

			return tableToolbar;
		},

		_createInsertToolbar({editor}) {
			const instance = this;

			const toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			toolbar.addItem(
				'image',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'imageselector',
					icon: 'image',
					title: CKEDITOR.tools.capitalize(
						editor.lang.image2.pathName
					),
				})
			);

			toolbar.addItem(
				'video',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'videoselector',
					icon: 'videoselector',
					title: 'Video',
				})
			);

			toolbar.addItem(
				'table',
				new CKEDITOR.ui.balloonToolbarButton({
					click: () => {
						toolbar.hide();

						const tableToolbar = instance._getInsertTableToolbar({
							editor,
						});

						tableToolbar.attach(editor.getSelection());

						editor.focusManager.focus(tableToolbar);
					},
					icon: 'table',
					title: editor.lang.table.toolbar,
				})
			);

			toolbar.addItem(
				'horizontalrule',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'horizontalrule',
					icon: 'horizontalrule',
					title: editor.lang.horizontalrule.toolbar,
				})
			);

			return toolbar;
		},

		_createTable({editor, tableData, tableToolbar}) {
			const tableElement = new CKEDITOR.dom.element('table');

			tableElement.setAttributes({
				border: 1,
				cellpadding: 1,
				cellspacing: 1,
			});

			tableElement.setStyle('width', '500px');

			const tbodyElement = new CKEDITOR.dom.element('tbody');

			tableElement.append(tbodyElement);

			const columns = tableData.columns;
			const rows = tableData.rows;

			for (let i = 0; i < rows; i++) {
				const tableRowElement = new CKEDITOR.dom.element('tr');

				for (let j = 0; j < columns; j++) {
					const tableCellElement = new CKEDITOR.dom.element('td');
					const lineBreakElement = new CKEDITOR.dom.element('br');

					tableCellElement.append(lineBreakElement);

					tableRowElement.append(tableCellElement);
				}

				tbodyElement.append(tableRowElement);
			}

			editor.insertElement(tableElement);

			tableToolbar.hide();

			setTimeout(() => {
				const range = editor.createRange();

				range.selectNodeContents(tableElement);

				range.select();
			}, 0);
		},

		_focusedEditorName: null,

		_getInsertTableToolbar({editor}) {
			const instance = this;

			let toolbar = editor.liferayToolbars.insertTableToolbar;

			if (toolbar) {
				return toolbar;
			}

			toolbar = instance._createInsertTableToolbar({editor});

			editor.liferayToolbars.insertTableToolbar = toolbar;

			return toolbar;
		},

		_getInsertToolbar({editor}) {
			const instance = this;

			let toolbar = editor.liferayToolbars.insertToolbar;

			if (toolbar) {
				return toolbar;
			}

			toolbar = instance._createInsertToolbar({editor});

			editor.liferayToolbars.insertToolbar = toolbar;

			return toolbar;
		},

		_positionButton({button, editor}) {
			const selection = editor.getSelection();

			const startElement = selection.getStartElement();

			const selectionClientRect = startElement.getClientRect();

			const buttonStyles = window.getComputedStyle(button.$);

			const buttonHeight = parseInt(buttonStyles.height, 10);

			let sideOffset = selectionClientRect.x + BUTTON_SIDE_OFFSET;

			if (editor.config.contentsLangDirection === 'rtl') {
				const buttonWidth = parseInt(buttonStyles.width, 10);

				sideOffset =
					selectionClientRect.x +
					selectionClientRect.width -
					buttonWidth -
					BUTTON_SIDE_OFFSET;
			}

			button.setStyles({
				left: `${sideOffset}px`,
				top: `${
					selectionClientRect.y +
					document.defaultView.pageYOffset +
					(selectionClientRect.height - buttonHeight) / 2
				}px`,
			});
		},

		init(editor) {
			editor.liferayToolbars = editor.liferayToolbars ?? {};

			const button = CKEDITOR.dom.element.createFromHtml(template.source);

			const hide = () => {
				button.addClass('hide');

				editor.liferayToolbars.insertTableToolbar?.hide();
				editor.liferayToolbars.insertToolbar?.hide();
			};

			const onFocusLoss = () => {
				setTimeout(() => {
					if (this._focusedEditorName !== editor.name) {
						hide();
					}
				});
			};

			const eventListeners = [];

			eventListeners.push(
				button.on('blur', onFocusLoss),
				editor.on('blur', onFocusLoss),

				button.on('click', (event) => {
					event.cancel();

					editor.liferayToolbars.insertTableToolbar?.hide();

					const toolbar = this._getInsertToolbar({
						editor,
						eventListeners,
					});

					toolbar.attach(editor.getSelection());
				}),

				editor.on('afterCommandExec', hide),

				editor.on('focus', () => {
					this._focusedEditorName = editor.name;
				}),

				editor.on('change', hide),

				editor.on('contentDom', () => {
					const body = editor.document.getBody();

					if (!body.contains(button)) {
						body.append(button);
					}
				}),

				editor.on('destroy', () => {
					eventListeners.forEach((listener) => {
						listener.removeListener();
					});
				}),

				editor.on('paste', hide),

				editor.on('selectionChange', () => {
					const selection = editor.getSelection();

					const type = selection.getType();

					const startElement = selection.getStartElement();

					if (
						type === CKEDITOR.SELECTION_TEXT &&
						selection.getSelectedText() === '' &&
						startElement.getText() === '\n' &&
						startElement.getName() !== 'td'
					) {
						this._positionButton({button, editor});

						button.removeClass('hide');
					}
					else {
						hide();
					}
				})
			);
		},

		requires: ['balloontoolbar', 'uibutton', 'uinumberinput'],
	});
})();
