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

import {debounce} from 'frontend-js-web';

const KEY_ENTER = 13;

const defaultGetEditorWrapper = element => {
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

	return {
		createEditor: (element, changeCallback, destroyCallback, config) => {
			const {portletNamespace} = config;

			const {editorConfig} = config.defaultEditorConfigurations[
				editorConfigurationName
			];

			_element = element;

			const editorName = `${portletNamespace}FragmentEntryLinkEditable_${element.id}`;
			_editor = AlloyEditor.editable(getEditorWrapper(element), {
				...editorConfig,

				filebrowserImageBrowseLinkUrl: editorConfig.filebrowserImageBrowseLinkUrl.replace(
					'_EDITOR_NAME_',
					editorName
				),

				filebrowserImageBrowseUrl: editorConfig.filebrowserImageBrowseUrl.replace(
					'_EDITOR_NAME_',
					editorName
				)
			});

			const nativeEditor = _editor.get('nativeEditor');

			_eventHandlers = [
				nativeEditor.on('key', event => {
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
						changeCallback(nativeEditor.getData());
					}, 500)
				),

				nativeEditor.on('blur', () => {
					if (_editor._mainUI.state.hidden) {
						changeCallback(nativeEditor.getData());

						requestAnimationFrame(() => {
							destroyCallback();
						});
					}
				}),

				nativeEditor.on('instanceReady', () => {
					nativeEditor.focus();
					nativeEditor.execCommand('selectAll');
				}),

				_stopEventPropagation(element, 'keydown'),
				_stopEventPropagation(element, 'keyup'),
				_stopEventPropagation(element, 'keypress')
			];
		},

		/**
		 */
		destroyEditor: (element, editableConfig) => {
			if (_editor) {
				const lastValue = _editor.get('nativeEditor').getData();

				_editor.destroy();

				_eventHandlers.forEach(handler => {
					handler.removeListener();
				});

				render(_element, lastValue, editableConfig);

				_editor = null;
				_eventHandlers = null;
				_element = null;
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
		}
	};
}

/**
 * Adds a listener to stop the given element event propagation
 * @param {HTMLElement} element
 * @param {string} eventName
 */
function _stopEventPropagation(element, eventName) {
	const handler = event => event.stopPropagation();

	element.addEventListener(eventName, handler);

	return {
		removeListener: () => {
			element.removeEventListener(eventName, handler);
		}
	};
}
