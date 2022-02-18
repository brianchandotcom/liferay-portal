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
	const HEADING_BOTH = 'Both';
	const HEADING_COL = 'Column';
	const HEADING_NONE = 'None';
	const HEADING_ROW = 'Row';

	const pluginName = 'toolbarbuttons';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	/**
	 * Creates a new CKEDITOR.dom.element using the passed tag name.
	 *
	 * @instance
	 * @memberof CKEDITOR.Table
	 * @protected
	 * @method _createElement
	 * @param {String} name The tag name from which an element should be created
	 * @param {Object} editor The CKEditor instance.
	 * @return {CKEDITOR.dom.element} Instance of CKEDITOR DOM element class
	 */
	function _createElement(name, editor) {
		return new CKEDITOR.dom.element(name, editor.document);
	}

	/**
	 * Returns which heading style is set for the given table.
	 *
	 * @instance
	 * @memberof CKEDITOR.Table
	 * @method getHeading
	 * @param {CKEDITOR.dom.element} table The table to gather the heading from. If null, it will be retrieved from the current selection.
	 * @param {Object} editor The CKEditor instance.
	 * @return {String} The heading of the table. Expected values are `CKEDITOR.Table.NONE`, `CKEDITOR.Table.ROW`, `CKEDITOR.Table.COL` and `CKEDITOR.Table.BOTH`.
	 */
	function getHeading(table, editor) {
		table = table || getFromSelection(editor);

		if (!table) {
			return null;
		}

		const rowHeadingSettings = table.$.tHead !== null;

		let colHeadingSettings = true;

		// Check if all of the first cells in every row are TH

		for (let row = 0; row < table.$.rows.length; row++) {

			// If just one cell isn't a TH then it isn't a header column

			const cell = table.$.rows[row].cells[0];

			if (cell && cell.nodeName.toLowerCase() !== 'th') {
				colHeadingSettings = false;
				break;
			}
		}

		let headingSettings = HEADING_NONE;

		if (rowHeadingSettings) {
			headingSettings = HEADING_ROW;
		}

		if (colHeadingSettings) {
			headingSettings =
				headingSettings === HEADING_ROW ? HEADING_BOTH : HEADING_COL;
		}

		return headingSettings;
	}

	/**
	 * Retrieves a table from the current selection.
	 *
	 * @instance
	 * @memberof CKEDITOR.Table
	 * @method getFromSelection
	 * @param {Object} editor The CKEditor instance.
	 * @return {CKEDITOR.dom.element} The retrieved table or null if not found.
	 */
	function getFromSelection(editor) {
		let table;
		const selection = editor.getSelection();
		const selected = selection.getSelectedElement();

		if (selected && selected.is('table')) {
			table = selected;
		}
		else {
			const ranges = selection.getRanges();

			if (ranges.length > 0) {

				// Webkit could report the following range on cell selection (#4948):
				// <table><tr><td>[&nbsp;</td></tr></table>]

				/* istanbul ignore else */
				if (CKEDITOR.env.webkit) {
					ranges[0].shrink(CKEDITOR.NODE_ELEMENT);
				}

				table = editor
					.elementPath(ranges[0].getCommonAncestor(true))
					.contains('table', 1);
			}
		}

		return table;
	}

	function setHeading(table, heading, editor) {
		table = table || getFromSelection(editor);

		let i;
		let newCell;
		let tableHead;
		const tableBody = table.getElementsByTag('tbody').getItem(0);

		let tableHeading = getHeading(table, editor);
		const hadColHeading =
			tableHeading === HEADING_COL || tableHeading === HEADING_BOTH;

		const needColHeading =
			heading === HEADING_COL || heading === HEADING_BOTH;
		const needRowHeading =
			heading === HEADING_ROW || heading === HEADING_BOTH;

		// If we need row heading and don't have a <thead> element yet, move the
		// first row of the table to the head and convert the nodes to <th> ones.

		if (!table.$.tHead && needRowHeading) {
			const tableFirstRow = tableBody.getElementsByTag('tr').getItem(0);
			const tableFirstRowChildCount = tableFirstRow.getChildCount();

			// Change TD to TH:

			for (i = 0; i < tableFirstRowChildCount; i++) {
				const cell = tableFirstRow.getChild(i);

				// Skip bookmark nodes. (#6155)

				if (
					cell.type === CKEDITOR.NODE_ELEMENT &&
					!cell.data('cke-bookmark')
				) {
					cell.renameNode('th');
					cell.setAttribute('scope', 'col');
				}
			}

			tableHead = _createElement(table.$.createTHead(), editor);
			tableHead.append(tableFirstRow.remove());
		}

		// If we don't need row heading and we have a <thead> element, move the
		// row out of there and into the <tbody> element.

		if (table.$.tHead !== null && !needRowHeading) {

			// Move the row out of the THead and put it in the TBody:

			tableHead = _createElement(table.$.tHead, editor);

			const previousFirstRow = tableBody.getFirst();

			while (tableHead.getChildCount() > 0) {
				const newFirstRow = tableHead.getFirst();
				const newFirstRowChildCount = newFirstRow.getChildCount();

				for (i = 0; i < newFirstRowChildCount; i++) {
					newCell = newFirstRow.getChild(i);

					if (newCell.type === CKEDITOR.NODE_ELEMENT) {
						newCell.renameNode('td');
						newCell.removeAttribute('scope');
					}
				}

				newFirstRow.insertBefore(previousFirstRow);
			}

			tableHead.remove();
		}

		tableHeading = getHeading(table, editor);
		const hasColHeading =
			tableHeading === HEADING_COL || tableHeading === HEADING_BOTH;

		// If we need column heading and the table doesn't have it, convert every first cell in
		// every row into a `<th scope="row">` element.

		if (!hasColHeading && needColHeading) {
			for (i = 0; i < table.$.rows.length; i++) {
				if (table.$.rows[i].cells[0].nodeName.toLowerCase() !== 'th') {
					newCell = new CKEDITOR.dom.element(
						table.$.rows[i].cells[0]
					);
					newCell.renameNode('th');
					newCell.setAttribute('scope', 'row');
				}
			}
		}

		// If we don't need column heading but the table has it, convert every first cell in every
		// row back into a `<td>` element.

		if (hadColHeading && !needColHeading) {
			for (i = 0; i < table.$.rows.length; i++) {
				const row = new CKEDITOR.dom.element(table.$.rows[i]);

				if (row.getParent().getName() === 'tbody') {
					newCell = new CKEDITOR.dom.element(row.$.cells[0]);
					newCell.renameNode('td');
					newCell.removeAttribute('scope');
				}
			}
		}
	}

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const headingCommands = [
				HEADING_NONE,
				HEADING_ROW,
				HEADING_COL,
				HEADING_BOTH,
			];

			headingCommands.forEach((heading) => {
				editor.addCommand('tableHeading' + heading, {
					exec(_editor) {
						setHeading(null, heading, editor);
					},
				});
			});

			editor.ui.addBalloonToolbarButton('ImageAlignLeft', {
				command: 'justifyleft',
				icon: 'align-image-left',
				title: editor.lang.common.alignLeft,
			});

			editor.ui.addBalloonToolbarButton('ImageAlignCenter', {
				command: 'justifycenter',
				icon: 'align-image-center',
				title: editor.lang.common.alignCenter,
			});

			editor.ui.addBalloonToolbarButton('ImageAlignRight', {
				command: 'justifyright',
				icon: 'align-image-right',
				title: editor.lang.common.alignRight,
			});

			editor.ui.addBalloonToolbarButton('LinkAddOrEdit', {
				click() {
					editor.fire('showToolbar', {
						toolbarCommand: 'linkToolbar',
					});
				},
				icon: 'link',
				title: editor.lang.link.title,
			});

			editor.ui.addRichCombo('TableHeaders', {
				_headersLabels: {
					[HEADING_BOTH]: `${editor.lang.table.headers}: ${editor.lang.table.headersBoth}`,
					[HEADING_COL]: `${editor.lang.table.headers}: ${editor.lang.table.headersColumn}`,
					[HEADING_NONE]: `${editor.lang.table.headers}: ${editor.lang.table.headersNone}`,
					[HEADING_ROW]: `${editor.lang.table.headers}: ${editor.lang.table.headersRow}`,
				},

				_setHeading(heading, editor) {
					editor.execCommand(`tableHeading${heading}`);

					this.setValue(heading, this._headersLabels[heading]);
				},

				init() {
					this.add(
						HEADING_NONE,
						this._headersLabels[HEADING_NONE],
						this._headersLabels[HEADING_NONE]
					);
					this.add(
						HEADING_ROW,
						this._headersLabels[HEADING_ROW],
						this._headersLabels[HEADING_ROW]
					);
					this.add(
						HEADING_COL,
						this._headersLabels[HEADING_COL],
						this._headersLabels[HEADING_COL]
					);
					this.add(
						HEADING_BOTH,
						this._headersLabels[HEADING_BOTH],
						this._headersLabels[HEADING_BOTH]
					);

					const currentHeading = getHeading(null, editor);

					this._setHeading(currentHeading, editor);
				},

				label: editor.lang.table.headers,

				onClick(selectedHeading) {
					this._setHeading(selectedHeading, editor);
				},

				onRender() {
					const currentHeading = getHeading(null, editor);

					this._setHeading(currentHeading, editor);

					editor.on(
						'selectionChange',
						function (_) {
							const currentHeading = getHeading(null, editor);

							this._setHeading(currentHeading, editor);
						},
						this
					);
				},

				panel: {
					attributes: {'aria-label': editor.lang.table.title},
					css: [CKEDITOR.skin.getPath('editor')].concat(
						editor.config.contentsCss
					),
					multiSelect: false,
				},

				title: editor.lang.table.title,
			});

			editor.ui.addBalloonToolbarButton('TableDelete', {
				click() {
					const selection = editor.getSelection();

					const startElement = selection.getStartElement();

					const tableElement = startElement.getAscendant('table');

					if (tableElement) {
						tableElement.remove();

						editor.fire('hideToolbars');
					}
				},
				icon: 'trash',
				title: editor.lang.table.deleteTable,
			});

			editor.ui.addBalloonToolbarButton('TextLink', {
				click() {
					editor.execCommand('linkToolbar');
				},
				icon: 'link',
				title: editor.lang.link.title,
			});

			editor.ui.addBalloonToolbarSelect('LineHeight', {
				_applyStyle(className) {
					const styleConfig = {
						attributes: {
							class: className,
						},
						element: 'div',
					};

					const selection = editor.getSelection();

					const startElement = selection.getStartElement();

					const style = new CKEDITOR.style(styleConfig);

					selection.lock();

					style.applyToObject(startElement, this.editor);

					selection.unlock();
				},

				_checkActive({elementPath, styleConfig}) {
					let active = true;

					if (elementPath && elementPath.lastElement) {
						styleConfig.attributes.class
							.split(' ')
							.forEach((className) => {
								active =
									active &&
									elementPath.lastElement.hasClass(className);
							});
					}
					else {
						active = false;
					}

					return active;
				},

				_getSelectedIndex(key) {
					return this.items.findIndex((item) => {
						return item.value === key;
					});
				},

				_stylesFactory: {
					'1.0x': {
						style: {attributes: {class: ''}, element: 'div'},
					},
					'1.5x': {
						style: {
							attributes: {class: 'mt-1 mb-1'},
							element: 'div',
						},
					},
					'2.0x': {
						style: {
							attributes: {class: 'mt-2 mb-2'},
							element: 'div',
						},
					},
					'3.0x': {
						style: {
							attributes: {class: 'mt-3 mb-3'},
							element: 'div',
						},
					},
					'4.0x': {
						style: {
							attributes: {class: 'mt-4 mb-4'},
							element: 'div',
						},
					},
					'5.0x': {
						style: {
							attributes: {class: 'mt-5 mb-5'},
							element: 'div',
						},
					},
				},

				icon: 'horizontalrule',

				items: [
					{label: '1.0x', value: '1.0x'},
					{label: '1.5x', value: '1.5x'},
					{label: '2.0x', value: '2.0x'},
					{label: '3.0x', value: '3.0x'},
					{label: '4.0x', value: '4.0x'},
					{label: '5.0x', value: '5.0x'},
				],

				onChange(key) {
					this._applyStyle(
						this._stylesFactory[key].style.attributes.class
					);
				},

				onRender() {
					editor.on(
						'selectionChange',
						function (event) {
							this._editor.focusManager.add(
								this._editor.document.getById(this._id),
								1
							);

							Object.keys(this._stylesFactory).forEach(
								(spacingKey) => {
									if (
										this._checkActive({
											elementPath: event.data.path,
											styleConfig: this._stylesFactory[
												spacingKey
											].style,
										})
									) {
										const element = document.getElementById(
											this._id
										);

										if (element) {
											element.selectedIndex = this._getSelectedIndex(
												spacingKey
											);
										}
									}
								}
							);
						},
						this
					);
				},

				title: Liferay.Language.get('line-height'),
			});

			editor.ui.addBalloonToolbarButton('VideoAlignLeft', {
				command: 'justifyleft',
				icon: 'align-image-left',
				title: editor.lang.common.alignLeft,
			});

			editor.ui.addBalloonToolbarButton('VideoAlignCenter', {
				command: 'justifycenter',
				icon: 'align-image-center',
				title: editor.lang.common.alignCenter,
			});

			editor.ui.addBalloonToolbarButton('VideoAlignRight', {
				command: 'justifyright',
				icon: 'align-image-right',
				title: editor.lang.common.alignRight,
			});

			editor.ui.addBalloonToolbarButton('SourceEditor', {
				command: 'codemirrordialog',
				icon: 'source',
				label: editor.lang.codemirror.source,
				title: editor.lang.codemirror.source,
			});
		},

		requires: ['uibutton', 'uiselect', 'uitextinput'],
	});
})();
