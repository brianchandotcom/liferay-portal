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
	const pluginName = 'toolbarbuttons';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
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
				init() {
					const headersPrefix = editor.lang.table.headers;

					const headersNone = `${headersPrefix}: ${editor.lang.table.headersNone}`;
					const headersRow = `${headersPrefix}: ${editor.lang.table.headersRow}`;
					const headersColumn = `${headersPrefix}: ${editor.lang.table.headersColumn}`;
					const headersBoth = `${headersPrefix}: ${editor.lang.table.headersBoth}`;

					this.add(headersNone, headersNone, headersNone);
					this.add(headersRow, headersRow, headersRow);
					this.add(headersColumn, headersColumn, headersColumn);
					this.add(headersBoth, headersBoth, headersBoth);
				},

				label: editor.lang.table.headers,

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
		},

		requires: ['uibutton', 'uiselect', 'uitextinput'],
	});
})();
