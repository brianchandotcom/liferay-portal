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
	const HEADING = {
		BOTH: 'Both',
		COL: 'Column',
		NONE: 'None',
		ROW: 'Row',
	};

	const pluginName = 'toolbarbuttons';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	/**
	 * Returns which heading style is set for the given table.
	 *
	 * @param {CKEDITOR.dom.element} table The table to gather the heading from. If null, it will be retrieved from the current selection.
	 * @param {Object} editor The CKEditor instance.
	 * @return {String} The heading of the table. Expected values are `HEADING.NONE`, `HEADING.ROW`, `HEADING.COL` and `HEADING.BOTH`.
	 */
	function getCurrentHeading({editor, table}) {
		table = table || getFromSelection(editor);

		if (!table) {
			return null;
		}

		let isColumnHeading = true;

		for (let row = 0; row < table.$.rows.length; row++) {
			const cell = table.$.rows[row].cells[0];

			if (cell && cell.nodeName.toLowerCase() !== 'th') {
				isColumnHeading = false;
				break;
			}
		}

		let heading = HEADING.NONE;

		if (table.$.tHead !== null) {
			heading = HEADING.ROW;
		}

		if (isColumnHeading) {
			heading = heading === HEADING.ROW ? HEADING.BOTH : HEADING.COL;
		}

		return heading;
	}

	/**
	 * Retrieves a table from the current selection.
	 *
	 * @param {Object} editor The CKEditor instance.
	 * @return {CKEDITOR.dom.element} The retrieved table or null if not found.
	 */
	function getFromSelection(editor) {
		let table;
		const selection = editor.getSelection();
		const selectedElement = selection.getSelectedElement();

		if (selectedElement?.is('table')) {
			table = selectedElement;
		}
		else {
			const ranges = selection.getRanges();

			if (ranges.length > 0) {

				// Webkit could report the following range on cell selection (#4948):
				// <table><tr><td>[&nbsp;</td></tr></table>]

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

	function setHeadingsHTML({editor, heading, table}) {
		table = table || getFromSelection(editor);

		let i;
		let newCell;
		let tableHead;
		const tableBody = table.getElementsByTag('tbody').getItem(0);

		let tableHeading = getCurrentHeading({editor, table});
		const hadColHeading =
			tableHeading === HEADING.COL || tableHeading === HEADING.BOTH;

		const needColHeading =
			heading === HEADING.COL || heading === HEADING.BOTH;
		const needRowHeading =
			heading === HEADING.ROW || heading === HEADING.BOTH;

		if (!table.$.tHead && needRowHeading) {
			const tableFirstRow = tableBody.getElementsByTag('tr').getItem(0);
			const tableFirstRowChildCount = tableFirstRow.getChildCount();

			for (i = 0; i < tableFirstRowChildCount; i++) {
				const cell = tableFirstRow.getChild(i);

				if (
					cell.type === CKEDITOR.NODE_ELEMENT &&
					!cell.data('cke-bookmark')
				) {
					cell.renameNode('th');
					cell.setAttribute('scope', 'col');
				}
			}

			tableHead = new CKEDITOR.dom.element(
				table.$.createTHead(),
				editor.document
			);
			tableHead.append(tableFirstRow.remove());
		}

		if (table.$.tHead !== null && !needRowHeading) {
			tableHead = new CKEDITOR.dom.element(table.$.createTHead(), editor);

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

		tableHeading = getCurrentHeading({editor, table});
		const hasColHeading =
			tableHeading === HEADING.COL || tableHeading === HEADING.BOTH;

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
					[HEADING.BOTH]: `${editor.lang.table.headers}: ${editor.lang.table.headersBoth}`,
					[HEADING.COL]: `${editor.lang.table.headers}: ${editor.lang.table.headersColumn}`,
					[HEADING.NONE]: `${editor.lang.table.headers}: ${editor.lang.table.headersNone}`,
					[HEADING.ROW]: `${editor.lang.table.headers}: ${editor.lang.table.headersRow}`,
				},

				_setHeading(heading, editor) {
					setHeadingsHTML({editor, heading});

					this.setValue(heading, this._headersLabels[heading]);
				},

				init() {
					this.add(
						HEADING.NONE,
						this._headersLabels[HEADING.NONE],
						this._headersLabels[HEADING.NONE]
					);
					this.add(
						HEADING.ROW,
						this._headersLabels[HEADING.ROW],
						this._headersLabels[HEADING.ROW]
					);
					this.add(
						HEADING.COL,
						this._headersLabels[HEADING.COL],
						this._headersLabels[HEADING.COL]
					);
					this.add(
						HEADING.BOTH,
						this._headersLabels[HEADING.BOTH],
						this._headersLabels[HEADING.BOTH]
					);

					const currentHeading = getCurrentHeading({
						editor,
					});

					this._setHeading(currentHeading, editor);
				},

				label: editor.lang.table.headers,

				onClick(selectedHeading) {
					this._setHeading(selectedHeading, editor);
				},

				onRender() {
					const currentHeading = getCurrentHeading({editor});

					this.label = this._headersLabels[currentHeading];
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
