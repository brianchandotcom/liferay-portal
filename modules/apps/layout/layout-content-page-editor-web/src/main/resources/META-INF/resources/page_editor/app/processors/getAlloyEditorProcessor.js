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

import {ItemSelectorDialog, debounce} from 'frontend-js-web';

import {config} from '../config/index';

const KEY_ENTER = 13;

const defaultGetEditorWrapper = (element) => {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = element.innerHTML;
	element.innerHTML = '';
	element.appendChild(wrapper);

	return wrapper;
};

const defaultRender = (element, value) => {
	element.innerHTML = value;
};

/**
 * @param {'text'|'rich-text'} editorConfigurationName
 * @param {function} [getEditorWrapper=defaultGetEditorWrapper] Optionally
 *  override getEditorWrapper function, where the editor will be instanciated
 * @param {function} [render=defaultRender] Optionally override render function
 */
export default function getAlloyEditorProcessor(
	editorConfigurationName,
	getEditorWrapper = defaultGetEditorWrapper,
	render = defaultRender
) {
	let _editor;
	let _eventHandlers;
	let _element;
	let _callbacks = {};

	return {
		createEditor: (
			element,
			changeCallback,
			destroyCallback,
			clickPosition
		) => {
			_callbacks.changeCallback = changeCallback;
			_callbacks.destroyCallback = destroyCallback;

			if (_editor) {
				return;
			}

			const {editorConfig} = config.defaultEditorConfigurations[
				editorConfigurationName
			];

			_element = element;

			const editorName = `${config.portletNamespace}FragmentEntryLinkEditable_${element.id}`;

			const editorWrapper = getEditorWrapper(element);

			editorWrapper.setAttribute('id', editorName);
			editorWrapper.setAttribute('name', editorName);

			_editor = AlloyEditor.editable(editorWrapper, {
				...editorConfig,

				documentBrowseLinkCallback: (
					editor,
					url,
					changeLinkCallback
				) => {
					const itemSelectorDialog = new ItemSelectorDialog({
						eventName: editor.title + 'selectItem',
						singleSelect: true,
						title: Liferay.Language.get('select-item'),
						url,
					});

					itemSelectorDialog.open();

					itemSelectorDialog.on('selectedItemChange', (event) => {
						const selectedItem = event.selectedItem;

						if (selectedItem) {
							changeLinkCallback(selectedItem);
						}
					});
				},

				documentBrowseLinkUrl: editorConfig.documentBrowseLinkUrl.replace(
					'_EDITOR_NAME_',
					editorName
				),

				filebrowserImageBrowseLinkUrl: editorConfig.filebrowserImageBrowseLinkUrl.replace(
					'_EDITOR_NAME_',
					editorName
				),

				filebrowserImageBrowseUrl: editorConfig.filebrowserImageBrowseUrl.replace(
					'_EDITOR_NAME_',
					editorName
				),

				title: editorName,
			});

			const nativeEditor = _editor.get('nativeEditor');

			_eventHandlers = [
				nativeEditor.on('key', (event) => {
					if (
						event.data.keyCode === KEY_ENTER &&
						_element &&
						_element.getAttribute('type') === 'text'
					) {
						event.cancel();
					}
				}),

				nativeEditor.on(
					'change',
					debounce(() => {
						if (_callbacks.changeCallback) {
							_callbacks.changeCallback(nativeEditor.getData());
						}
					}, 500)
				),

				nativeEditor.on('blur', () => {
					if (_editor._mainUI.state.hidden) {
						if (_callbacks.changeCallback) {
							_callbacks.changeCallback(nativeEditor.getData());
						}

						requestAnimationFrame(() => {
							if (_callbacks.destroyCallback) {
								_callbacks.destroyCallback();
							}
						});
					}
				}),

				nativeEditor.on('instanceReady', () => {
					nativeEditor.focus();

					if (clickPosition) {
						_selectRange(clickPosition, nativeEditor);
					}
					else {
						nativeEditor.execCommand('selectAll');
					}
				}),

				_stopEventPropagation(element, 'keydown'),
				_stopEventPropagation(element, 'keyup'),
				_stopEventPropagation(element, 'keypress'),
			];
		},

		/**
		 */
		destroyEditor: (element, editableConfig) => {
			if (_editor) {
				const lastValue = _editor.get('nativeEditor').getData();

				_editor.destroy();

				_eventHandlers.forEach((handler) => {
					handler.removeListener();
				});

				render(_element, lastValue, editableConfig);

				_editor = null;
				_eventHandlers = null;
				_element = null;
				_callbacks = {};
			}
		},

		/**
		 * @param {HTMLElement} element HTMLElement that should be mutated with the
		 *  given value.
		 * @param {string} value Element content
		 * @param {Object} editableConfig
		 */
		render: (element, value, editableConfig) => {
			if (element !== _element) {
				render(element, value, editableConfig);
			}
		},
	};
}

/**
 * Adds a listener to stop the given element event propagation
 * @param {HTMLElement} element
 * @param {string} eventName
 */
function _stopEventPropagation(element, eventName) {
	const handler = (event) => event.stopPropagation();

	element.addEventListener(eventName, handler);

	return {
		removeListener: () => {
			element.removeEventListener(eventName, handler);
		},
	};
}

/**
 * Place the caret in the click position
 * @param {Event} event
 * @param {CKEditor} nativeEditor
 */
function _selectRange(clickPosition, nativeEditor) {
	const ckRange = nativeEditor.getSelection().getRanges()[0];

	if (document.caretPositionFromPoint) {
		const range = document.caretPositionFromPoint(
			clickPosition.clientX,
			clickPosition.clientY
		);

		const node = range.offsetNode;

		if (isTextNode(node)) {
			ckRange.setStart(CKEDITOR.dom.node(node), range.offset);
			ckRange.setEnd(CKEDITOR.dom.node(node), range.offset);
		}
	}
	else if (document.caretRangeFromPoint) {
		const range = document.caretRangeFromPoint(
			clickPosition.clientX,
			clickPosition.clientY
		);

		const offset = range.startOffset || 0;

		if (
			isTextNode(range.startContainer) &&
			isTextNode(range.endContainer)
		) {
			ckRange.setStart(CKEDITOR.dom.node(range.startContainer), offset);
			ckRange.setEnd(CKEDITOR.dom.node(range.endContainer), offset);
		}
	}

	nativeEditor.getSelection().selectRanges([ckRange]);
}

function isTextNode(node) {
	return node.nodeType === Node.TEXT_NODE;
}
