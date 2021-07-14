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

	const template = new CKEDITOR.template(
		'<button class="lfr-ballon-editor-insert-button">+</button>'
	);

	CKEDITOR.plugins.add(pluginName, {
		_createTable() {
			const editor = this.editor;

			const tableElement = new CKEDITOR.dom.element('table');

			tableElement.setAttributes({
				border: 1,
				cellpadding: 1,
				cellspacing: 1,
			});

			tableElement.setStyle('width', '500px');

			const tbodyElement = new CKEDITOR.dom.element('tbody');

			tableElement.append(tbodyElement);

			const columns = this._tableData.columns;
			const rows = this._tableData.rows;

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

			this._tableToolbar.destroy();
			this._tableToolbar = null;

			setTimeout(() => {
				const range = editor.createRange();

				range.selectNodeContents(tableElement);

				range.select();
			}, 0);
		},

		_createTableToolbar() {
			const instance = this;

			if (instance._tableToolbar) {
				return instance._tableToolbar;
			}

			const editor = instance.editor;

			instance._tableToolbar = new CKEDITOR.ui.balloonToolbar(editor);

			const rowsInput = new CKEDITOR.ui.balloonToolbarNumberInput({
				change(value) {
					instance._tableData.rows = value;
				},
				label: editor.lang.table.rows,
				min: 1,
				step: 1,
				value: this._tableData.rows,
			});

			instance._tableToolbar.addItem('rowsInput', rowsInput);

			const columnsInput = new CKEDITOR.ui.balloonToolbarNumberInput({
				change(value) {
					instance._tableData.columns = value;
				},
				label: editor.lang.table.columns,
				min: 1,
				step: 1,
				value: this._tableData.columns,
			});

			instance._tableToolbar.addItem('columnsInput', columnsInput);

			const okButton = new CKEDITOR.ui.balloonToolbarButton({
				click: instance._createTable.bind(instance),
				icon: 'check',
				title: editor.lang.common.ok,
			});

			instance._tableToolbar.addItem('okButton', okButton);

			return instance._tableToolbar;
		},

		_createToolbar() {
			if (this._toolbar) {
				return;
			}

			const instance = this;

			const editor = instance.editor;

			instance._toolbar = new CKEDITOR.ui.balloonToolbar(editor);

			instance._toolbar.addItem(
				'image',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'imageselector',
					icon: 'image',
					title: CKEDITOR.tools.capitalize(
						editor.lang.image2.pathName
					),
				})
			);

			instance._toolbar.addItem(
				'video',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'videoselector',
					icon: 'videoselector',
					title: 'Video',
				})
			);

			instance._toolbar.addItem(
				'table',
				new CKEDITOR.ui.balloonToolbarButton({
					click() {
						instance._toolbar.hide();

						const tableToolbar = instance._createTableToolbar();

						tableToolbar.attach(editor.getSelection());
					},
					icon: 'table',
					title: editor.lang.table.toolbar,
				})
			);

			instance._toolbar.addItem(
				'horizontalrule',
				new CKEDITOR.ui.balloonToolbarButton({
					command: 'horizontalrule',
					icon: 'horizontalrule',
					title: editor.lang.horizontalrule.toolbar,
				})
			);
		},

		_eventListeners: [],

		_onAfterCommandExec() {
			this.hide();
		},

		_onBlur() {
			if (!this._toolbarVisible) {
				this.hide();
			}
		},

		_onButtonClick(event) {
			event.cancel();

			this._createToolbar();
			this._toolbarVisible = true;
			this._toolbar.attach(this.editor.getSelection());
		},

		_onChange() {
			this.hide();
		},

		_onContentDom() {
			this.documentBody = this.editor.document.getBody();

			if (!this.documentBody.contains(this._button)) {
				this.documentBody.append(this._button);
			}
		},

		_onDestroy() {
			CKEDITOR.tools.array.forEach(this._eventListeners, (listener) => {
				listener.removeListener();
			});

			this._eventListeners = [];
		},

		_onSelectionChange() {
			const selection = this.editor.getSelection();

			const type = selection.getType();

			if (type === CKEDITOR.SELECTION_ELEMENT || selection.isHidden()) {
				this.hide();

				return;
			}

			if (
				type === CKEDITOR.SELECTION_TEXT &&
				selection.getSelectedText()
			) {
				this.hide();

				return;
			}

			const ranges = selection.getRanges();

			const rangesNotCollapsed = CKEDITOR.tools.array.some(
				ranges,
				(range) => {
					return !range.collapsed;
				}
			);

			if (rangesNotCollapsed) {
				this.hide();

				return;
			}

			const range = ranges[ranges.length - 1];

			const startContainer = range.startContainer;

			if (
				typeof startContainer.getName === 'function' &&
				startContainer.getName() === 'td'
			) {
				this.hide();

				return;
			}

			const nodeType = startContainer.$.nodeType;

			if (nodeType === CKEDITOR.NODE_ELEMENT) {
				if (startContainer.getChildCount() !== 1) {
					this.hide();

					return;
				}
				else if (startContainer.getChild(0)) {
					const child = startContainer.getChild(0);

					if (
						typeof child.getName === 'function' &&
						child.getName() !== 'br'
					) {
						this.hide();

						return;
					}
				}
			}
			else if (
				nodeType === CKEDITOR.NODE_TEXT &&
				!startContainer.isEmpty()
			) {
				this.hide();

				return;
			}

			const rangeClientRects = range.getClientRects(true);

			const rangeClientRect =
				rangeClientRects[rangeClientRects.length - 1];

			this._positionButton(rangeClientRect);

			this.show();
		},

		_positionButton(rangeClientRect) {
			if (!this._buttonClientRect) {
				this._buttonClientRect = this._button.getClientRect(true);
			}

			this._button.setStyles({
				left:
					rangeClientRect.x - this._buttonClientRect.width / 2 + 'px',
				top: rangeClientRect.y + 'px',
			});
		},

		_tableData: {
			columns: 3,
			rows: 3,
		},

		_tableToolbar: null,

		_toolbarVisible: false,

		hide() {
			if (this._button.getStyle('display') !== 'none') {
				this._button.setStyle('display', 'none');
			}
			if (this._toolbar) {
				this._toolbar.hide();
			}
			if (this._tableToolbar) {
				this._tableToolbar.destroy();
			}
		},

		init(editor) {
			this.editor = editor;

			this._eventListeners.push(
				this.editor.on(
					'afterCommandExec',
					this._onAfterCommandExec.bind(this)
				),
				this.editor.on('blur', this._onBlur.bind(this)),
				this.editor.on('change', this._onChange.bind(this)),
				this.editor.on('contentDom', this._onContentDom.bind(this)),
				this.editor.on('destroy', this._onDestroy.bind(this)),
				this.editor.on('paste', this._onChange.bind(this)),
				this.editor.on(
					'selectionChange',
					this._onSelectionChange.bind(this)
				)
			);

			this._button = CKEDITOR.dom.element.createFromHtml(template.source);

			this._button.setStyles({
				display: 'none',
				position: 'absolute',
			});

			this._eventListeners.push(
				this._button.on('click', this._onButtonClick.bind(this))
			);
		},

		requires: ['balloontoolbar', 'uibutton', 'uinumberinput'],

		show() {
			if (this._button.getStyle('display') !== '') {
				this._button.setStyle('display', '');
			}
		},
	});
})();
