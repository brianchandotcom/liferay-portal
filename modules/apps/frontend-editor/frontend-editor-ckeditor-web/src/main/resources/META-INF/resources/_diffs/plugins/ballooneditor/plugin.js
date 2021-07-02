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

	const SELECTION_DIRECTION = {
		BOTTOM_TO_TOP: 1,
		TOP_TO_BOTTOM: 0,
	};

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const eventListeners = [];

			editor.on('contentDom', () => {
				const document = editor.document;

				eventListeners.push(
					document.on('keyup', () => {
						editor.forceNextSelectionCheck();
					})
				);

				eventListeners.push(
					document.on('mouseup', () => {
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
			CKEDITOR.ui.balloonPanel.DEFAULT_PANEL_PADDING = 14;

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
				const padding = CKEDITOR.ui.balloonPanel.DEFAULT_PANEL_PADDING;

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

				const ranges = elementOrSelection.getRanges();
				const type = elementOrSelection.getType();

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
						selectionDirection = getSelectionDirection(
							this.editor.getSelection()
						);
					}

					if (firstSelectedRect === lastSelectedRect) {
						x =
							firstSelectedRect.x +
							firstSelectedRect.width / 2 -
							panelRect.width / 2;
						y = firstSelectedRect.y - panelRect.height - padding;

						triangleSide = 'bottom';
					}
					else if (
						selectionDirection === SELECTION_DIRECTION.BOTTOM_TO_TOP
					) {
						x = firstSelectedRect.x - panelRect.width / 2;
						y = firstSelectedRect.y - panelRect.height - padding;

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
							padding;

						triangleSide = 'top';
					}
				}
				else if (type === CKEDITOR.SELECTION_ELEMENT) {
					let selectedElement = elementOrSelection.getSelectedElement();

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
						padding;
				}

				const editable = this.editor.editable();

				const editableRect = editable.getClientRect(true);

				if (editableRect.width < panelRect.width + padding) {
					x = editableRect.x + padding;
				}
				else if (x < editableRect.x) {
					x = editableRect.x + padding;
				}
				else if (
					x + panelRect.width >
					editableRect.x + editableRect.width
				) {
					x =
						editableRect.x +
						editableRect.width -
						panelRect.width -
						padding;
				}

				this.move(y, x);

				this.setTriangle(triangleSide, 'hcenter');

				if (options.focusElement !== false) {
					(options.focusElement || this.parts.panel).focus();
				}
			};

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

			CKEDITOR.plugins.balloontoolbar.contextManager.prototype.check = function (
				selection
			) {
				if (!selection) {
					selection = this.editor.getSelection();

					CKEDITOR.tools.array.forEach(
						selection.getRanges(),
						(range) => {
							range.shrink(CKEDITOR.SHRINK_ELEMENT, true);
						}
					);
				}

				if (!selection) {
					return;
				}

				const path = selection.getRanges()[0]?.startPath();

				let contextMatched;

				function matchEachContext(
					contexts,
					matchingFunction,
					matchingArg1
				) {
					CKEDITOR.tools.array.forEach(contexts, (curContext) => {
						if (
							!contextMatched ||
							contextMatched.options.priority >
								curContext.options.priority
						) {
							const result = matchingFunction(
								curContext,
								matchingArg1
							);

							if (result instanceof CKEDITOR.dom.element) {
								contextMatched = curContext;
							}
						}
					});
				}

				function elementsMatcher(curContext, curElement) {
					return curContext._matchElement(curElement);
				}

				matchEachContext(this._contexts, (curContext) => {
					return curContext._matchRefresh(path, selection);
				});

				matchEachContext(this._contexts, (curContext) => {
					return curContext._matchWidget();
				});

				if (path) {
					const selectedElem = selection.getSelectedElement();

					if (selectedElem && !selectedElem.isReadOnly()) {
						matchEachContext(
							this._contexts,
							elementsMatcher,
							selectedElem
						);
					}

					for (let i = 0; i < path.elements.length; i++) {
						const curElement = path.elements[i];

						if (!curElement.isReadOnly()) {
							matchEachContext(
								this._contexts,
								elementsMatcher,
								curElement
							);
						}
					}
				}

				this.hide();

				if (contextMatched) {
					CKEDITOR.tools.array.forEach(
						selection.getRanges(),
						(range) => {
							range.shrink(CKEDITOR.SHRINK_ELEMENT, true);
						}
					);

					if (
						!selection.getSelectedElement() &&
						!selection.getSelectedText()
					) {
						return;
					}

					contextMatched.show(selection);
				}
			};

			CKEDITOR.ui.panel.prototype.showBlock = function (name) {
				if (!this.name) {
					this.name = name;
				}

				return CKEDITOR.ui.panel.prototype.showBlock.call(
					this,
					this.name
				);
			};
		},

		requires: [
			'balloonpanel',
			'balloontoolbar',
			'imagealt',
			'insertbutton',
			'linktoolbar',
			'tabletoolbar',
			'toolbarbuttons',
		],
	});
})();
