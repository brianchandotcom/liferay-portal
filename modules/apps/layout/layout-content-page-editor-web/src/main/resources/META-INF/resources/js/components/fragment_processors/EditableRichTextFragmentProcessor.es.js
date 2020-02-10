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

import {object} from 'metal';
import {EventHandler} from 'metal-events';

import {
	CREATE_PROCESSOR_EVENT_TYPES,
	FLOATING_TOOLBAR_BUTTONS
} from '../../utils/constants';

const KEY_ENTER = 13;

let _destroyedCallback = null;
let _editableElement = null;
let _editor = null;
let _editorEventHandler = null;

/**
 * @param {Event}
 */
function _stopEventPropagation(event) {
	event.stopPropagation();
}

/**
 * Destroys, if any, an existing instance of AlloyEditor.
 */
function destroy() {
	if (_editor) {
		_editorEventHandler.removeAllListeners();
		_editorEventHandler.dispose();

		const editorData = _editor.get('nativeEditor').getData();

		_editableElement.innerHTML = editorData;

		_editableElement.removeEventListener('keydown', _stopEventPropagation);
		_editableElement.removeEventListener('keyup', _stopEventPropagation);
		_editableElement.removeEventListener('keypress', _stopEventPropagation);

		_editor.destroy();

		_editableElement = null;
		_editor = null;
		_editorEventHandler = null;

		_destroyedCallback();
		_destroyedCallback = null;
	}
}

/**
 * @param {object} editableValues
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarButtons(editableValues) {
	const buttons = [];

	const editButton = {...FLOATING_TOOLBAR_BUTTONS.edit};

	if (editableValues.mappedField || editableValues.fieldId) {
		editButton.cssClass =
			'disabled fragments-editor__floating-toolbar--disabled fragments-editor__floating-toolbar--mapped-field';
	}

	buttons.push(editButton);

	const mapButton = {...FLOATING_TOOLBAR_BUTTONS.map};

	if (editableValues.fieldId || editableValues.mappedField) {
		mapButton.cssClass = 'fragments-editor__floating-toolbar--mapped-field';
	}

	buttons.push(mapButton);

	return buttons;
}

/**
 * Returns the existing editable element or null.
 * @returns {HTMLElement|null}
 */
function getActiveEditableElement() {
	return _editableElement;
}

/**
 * Creates an instance of AlloyEditor and destroys the existing one if any.
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {string} portletNamespace
 * @param {Object} options
 * @param {function} changedCallback
 * @param {function} destroyedCallback
 */
function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	changedCallback,
	destroyedCallback,
	event,
	type
) {
	destroy();

	editableElement.addEventListener('keydown', _stopEventPropagation);
	editableElement.addEventListener('keyup', _stopEventPropagation);
	editableElement.addEventListener('keypress', _stopEventPropagation);

	const {defaultEditorConfiguration} = options;
	const editableContent = editableElement.innerHTML;
	const wrapper = document.createElement('div');

	wrapper.dataset.lfrEditableId = editableElement.id;
	wrapper.innerHTML = editableContent;

	const editorName = `${portletNamespace}FragmentEntryLinkEditable_${editableElement.id}`;

	wrapper.setAttribute('id', editorName);
	wrapper.setAttribute('name', editorName);

	editableElement.innerHTML = '';
	editableElement.appendChild(wrapper);

	_editableElement = editableElement;
	_editorEventHandler = new EventHandler();
	_destroyedCallback = destroyedCallback;

	_editor = AlloyEditor.editable(
		wrapper,
		_getEditorConfiguration(
			editableElement,
			portletNamespace,
			fragmentEntryLinkId,
			defaultEditorConfiguration,
			editorName
		)
	);

	const nativeEditor = _editor.get('nativeEditor');

	_editorEventHandler.add(nativeEditor.on('key', _handleNativeEditorKey));

	_editorEventHandler.add(
		nativeEditor.on('change', () => changedCallback(nativeEditor.getData()))
	);

	_editorEventHandler.add(
		nativeEditor.on('actionPerformed', () =>
			changedCallback(nativeEditor.getData())
		)
	);

	_editorEventHandler.add(
		nativeEditor.on('blur', () => {
			if (_editor._mainUI.state.hidden) {
				requestAnimationFrame(destroy);
			}
		})
	);

	_editorEventHandler.add(
		nativeEditor.on('instanceReady', () => {
			nativeEditor.focus();

			if (type === CREATE_PROCESSOR_EVENT_TYPES.button) {
				nativeEditor.execCommand('selectAll');
			}
			else if (event) {
				_selectRange(event, nativeEditor);
			}
		})
	);
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @return {string} Transformed content
 */
function render(content, value) {
	return value;
}

/**
 * Returns a configuration object for a AlloyEditor instance.
 * @param {HTMLElement} editableElement
 * @param {string} portletNamespace
 * @param {string} fragmentEntryLinkId
 * @param {object} defaultEditorConfiguration
 * @param {string} editorName
 * @return {object}
 */
function _getEditorConfiguration(
	editableElement,
	portletNamespace,
	fragmentEntryLinkId,
	defaultEditorConfiguration,
	editorName
) {
	return object.mixin({}, defaultEditorConfiguration.editorConfig || {}, {
		filebrowserImageBrowseLinkUrl: defaultEditorConfiguration.editorConfig.filebrowserImageBrowseLinkUrl.replace(
			'_EDITOR_NAME_',
			editorName
		),

		filebrowserImageBrowseUrl: defaultEditorConfiguration.editorConfig.filebrowserImageBrowseUrl.replace(
			'_EDITOR_NAME_',
			editorName
		),

		title: editorName
	});
}

/**
 * Place the caret in the click position
 * @param {Event} event
 * @param {CKEditor} nativeEditor
 */
function _selectRange(event, nativeEditor) {
	const ckRange = nativeEditor.getSelection().getRanges()[0];

	if (document.caretPositionFromPoint) {
		const range = document.caretPositionFromPoint(
			event.clientX,
			event.clientY
		);

		const textNode = range.offsetNode;

		ckRange.setStart(CKEDITOR.dom.node(textNode), range.offset);
		ckRange.setEnd(CKEDITOR.dom.node(textNode), range.offset);
	}
	else if (document.caretRangeFromPoint) {
		const range = document.caretRangeFromPoint(
			event.clientX,
			event.clientY
		);

		const offset = range.startOffset || 0;

		ckRange.setStart(CKEDITOR.dom.node(range.startContainer), offset);
		ckRange.setEnd(CKEDITOR.dom.node(range.endContainer), offset);
	}

	nativeEditor.getSelection().selectRanges([ckRange]);
}

/**
 * Handle native editor key presses.
 * It avoids including line breaks on text editors.
 * @param {Event} event
 * @private
 * @review
 */
function _handleNativeEditorKey(event) {
	if (
		event.data.keyCode === KEY_ENTER &&
		_editableElement &&
		_editableElement.getAttribute('type') === 'text'
	) {
		event.cancel();
	}
}

export {
	destroy,
	getActiveEditableElement,
	getFloatingToolbarButtons,
	init,
	render
};

export default {
	destroy,
	getActiveEditableElement,
	getFloatingToolbarButtons,
	init,
	render
};
