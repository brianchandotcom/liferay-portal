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
	const pluginName = 'ballooneditor';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	const PANEL_PADDING = 14;

	const SELECTION_DIRECTION = {
		BOTTOM_TO_TOP: 1,
		TOP_TO_BOTTOM: 0,
	};

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const eventListeners = [];

			editor.on('contentDom', () => {
				const editable = editor.editable();

				eventListeners.push(
					editable.on('keyup', () => {
						editor.forceNextSelectionCheck();
					})
				);

				eventListeners.push(
					editable.on('mouseup', () => {
						editor.forceNextSelectionCheck();
					})
				);
			});

			editor.on('destroy', () => {
				eventListeners.forEach((listener) => {
					listener.removeListener();
				});
			});

			editor.on('hideToolbars', () => {
				editor.balloonToolbars.hide();
			});

			editor.on('resize', () => {
				editor.balloonToolbars.hide();
			});

			editor.on('showToolbar', (event) => {
				const toolbarCommand = event.data.toolbarCommand;

				editor.balloonToolbars.hide();

				editor.execCommand(toolbarCommand);
			});
		},

		onLoad() {
			const getSelectionDirection = (selection) => {
				let direction = SELECTION_DIRECTION.TOP_TO_BOTTOM;

				const nativeSelection = selection.getNative();

				if (!nativeSelection) {
					return SELECTION_DIRECTION.TOP_TO_BOTTOM;
				}

				const anchorNode = nativeSelection.anchorNode;

				if (anchorNode?.compareDocumentPosition) {
					const position = anchorNode.compareDocumentPosition(
						nativeSelection.focusNode
					);

					if (
						(!position &&
							nativeSelection.anchorOffset >
								nativeSelection.focusOffset) ||
						position === Node.DOCUMENT_POSITION_PRECEDING
					) {
						direction = SELECTION_DIRECTION.BOTTOM_TO_TOP;
					}
				}

				return direction;
			};

			CKEDITOR.ui.balloonPanel.prototype.attach = function (
				elementOrSelection,
				options
			) {
				if (options instanceof CKEDITOR.dom.element || !options) {
					options = {focusElement: options};
				}

				options = CKEDITOR.tools.extend(options, {
					show: true,
				});

				if (options.show === true) {
					this.show();
				}

				this.fire('attach');

				const panelRect = this.parts.panel.getClientRect(true);

				const selection = this.editor.getSelection();

				const ranges = selection.getRanges();
				const type = selection.getType();

				let triangleSide = 'bottom';
				let x = 0;
				let y = 0;

				if (type === CKEDITOR.SELECTION_TEXT) {
					let selectedRects;

					try {
						selectedRects = ranges[0].getClientRects(true);
					}
					catch (error) {
						return;
					}

					const firstSelectedRect = selectedRects[0];
					const lastSelectedRect =
						selectedRects[selectedRects.length - 1];

					let selectionDirection = SELECTION_DIRECTION.TOP_TO_BOTTOM;

					if (firstSelectedRect !== lastSelectedRect) {
						selectionDirection = getSelectionDirection(selection);
					}

					if (firstSelectedRect === lastSelectedRect) {
						x =
							firstSelectedRect.x +
							firstSelectedRect.width / 2 -
							panelRect.width / 2;
						y =
							firstSelectedRect.y -
							panelRect.height -
							PANEL_PADDING;

						triangleSide = 'bottom';
					}
					else if (
						selectionDirection === SELECTION_DIRECTION.BOTTOM_TO_TOP
					) {
						x = firstSelectedRect.x - panelRect.width / 2;
						y =
							firstSelectedRect.y -
							panelRect.height -
							PANEL_PADDING;

						triangleSide = 'bottom';
					}
					else if (
						selectionDirection === SELECTION_DIRECTION.TOP_TO_BOTTOM
					) {
						x =
							lastSelectedRect.x +
							lastSelectedRect.width -
							panelRect.width / 2;
						y =
							lastSelectedRect.y +
							lastSelectedRect.height +
							PANEL_PADDING;

						triangleSide = 'top';
					}
				}
				else if (type === CKEDITOR.SELECTION_ELEMENT) {
					let selectedElement = selection.getSelectedElement();

					if (!selectedElement) {
						selectedElement = ranges && ranges[0].startContainer;
					}

					const selectedElementClientRect = selectedElement.getClientRect(
						true
					);

					x =
						selectedElementClientRect.x +
						selectedElementClientRect.width / 2 -
						panelRect.width / 2;
					y =
						selectedElementClientRect.y -
						panelRect.height -
						PANEL_PADDING;
				}

				const editable = this.editor.editable();

				const editableRect = editable.getClientRect(true);

				if (editableRect.width < panelRect.width + PANEL_PADDING) {
					x = editableRect.x + PANEL_PADDING;
				}
				else if (x < editableRect.x) {
					x = editableRect.x + PANEL_PADDING;
				}
				else if (
					x + panelRect.width >
					editableRect.x + editableRect.width
				) {
					x =
						editableRect.x +
						editableRect.width -
						panelRect.width -
						PANEL_PADDING;
				}

				this.move(y, x);

				this.setTriangle(triangleSide, 'hcenter');

				if (options.focusElement !== false) {
					(options.focusElement || this.parts.panel).focus();
				}
			};

			CKEDITOR.ui.balloonPanel.prototype.templateDefinitions.panel =
				'<div' +
				' aria-labelledby="cke_{name}_arialbl"' +
				' class="cke {id} cke_reset_all cke_chrome cke_balloon cke_editor_{name} cke_{langDir} lfr-balloon-editor lfr-tooltip-scope"' +
				' dir="{langDir}"' +
				' lang="{langCode}"' +
				' role="dialog"' +
				' style="{style}"' +
				' tabindex="-1"' +
				'></div>';

			CKEDITOR.plugins.balloontoolbar.context.prototype._loadButtons = function () {
				const buttons = this.options.buttons;

				if (!buttons) {
					return;
				}

				CKEDITOR.tools.array.forEach(
					buttons.split(','),
					(name) => {
						const newUiItem = this.editor.ui.create(name);

						if (newUiItem) {
							this.toolbar.addItem(name, newUiItem);

							if (
								Object.hasOwnProperty.call(
									newUiItem,
									'balloonToolbar'
								)
							) {
								newUiItem.balloonToolbar = this.toolbar;
							}
						}
					},
					this
				);
			};

			const originalContextManagerCheck =
				CKEDITOR.plugins.balloontoolbar.contextManager.prototype.check;

			CKEDITOR.plugins.balloontoolbar.contextManager.prototype.check = function (
				selection
			) {
				const editor = this.editor;

				if (!selection) {
					selection = editor.getSelection();
				}

				if (!selection) {
					return;
				}

				const selectedElement = selection.getSelectedElement();

				if (!selectedElement) {
					const selectedText = selection.getSelectedText();

					if (!selectedText?.match(/\w/)) {
						editor.balloonToolbars.hide();

						return;
					}
				}

				originalContextManagerCheck.call(this, selection);
			};

			const ckeditorWindow = CKEDITOR.document.getWindow();

			ckeditorWindow.on('click', (event) => {
				const target = event.data.getTarget();

				if (
					!target.$.closest(
						'.cke_toolgroup, .lfr-balloon-editor, .lfr-balloon-editor-insert-button, .liferay-editable'
					)
				) {
					for (const editorName in CKEDITOR.instances) {
						const editor = CKEDITOR.instances[editorName];

						editor.balloonToolbars?.hide();

						const liferayToolbars = editor.liferayToolbars;

						if (liferayToolbars) {
							for (const toolbar in liferayToolbars) {
								liferayToolbars[toolbar].hide();
							}
						}
					}

					const insertButtons = CKEDITOR.document.$.querySelectorAll(
						'.lfr-balloon-editor-insert-button'
					);

					for (const button of insertButtons) {
						button.classList.add('hide');
					}
				}
			});
		},

		requires: [
			'balloonpanel',
			'balloontoolbar',
			'imagealt',
			'linktoolbar',
			'tabletoolbar',
			'toolbarbuttons',
		],
	});
})();
