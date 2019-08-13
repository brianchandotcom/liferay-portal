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

import {EventHandler} from 'metal-events';
import dom from 'metal-dom';

let _editableElement;
let _editor;
let _editorEventHandler;

/**
 * Destroys, if any, an existing instance of AlloyEditor.
 */

function destroy() {
	if (
		_editor &&
		!dom.hasClass(_editableElement, 'lfr-source-editor-focused')
	) {
		_editorEventHandler.dispose();

		const editorData = _editor.getEditor().getValue();

		_editableElement.removeAttribute('style');

		dom.removeClasses(
			_editableElement,
			'yui3-widget ace-editor lfr-source-editor lfr-source-editor-content'
		);

		_editableElement.innerHTML = '';

		_editableElement.innerHTML = editorData;

		_editor.getEditor().destroy();

		_editableElement = null;
		_editor = null;
		_editorEventHandler = null;
	}
}

/**
 * Creates an instance of AlloyEditor and destroys the existing one if any.
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {string} portletNamespace
 * @param {Object} options
 * @param {function} callback
 */

function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	callback
) {
	if (_editor && dom.hasClass(editableElement, 'lfr-source-editor-focused')) {
		return;
	}

	const editableContent = editableElement.innerHTML;

	editableElement.innerHTML = '';

	_editableElement = editableElement;
	_editorEventHandler = new EventHandler();

	const A = new AUI();

	A.use('liferay-source-editor', A => {
		var sourceEditor = new A.LiferaySourceEditor({
			boundingBox: editableElement,
			mode: 'html',
			value: editableContent
		}).render();

		_editor = sourceEditor;

		const nativeEditor = _editor.getEditor();

		_editorEventHandler.add(nativeEditor.on('blur', () => destroy()));

		_editorEventHandler.add(
			nativeEditor.on('change', () => callback(nativeEditor.getValue()))
		);

		nativeEditor.focus();
	});
}

export {destroy, init};

export default {
	destroy,
	init
};
