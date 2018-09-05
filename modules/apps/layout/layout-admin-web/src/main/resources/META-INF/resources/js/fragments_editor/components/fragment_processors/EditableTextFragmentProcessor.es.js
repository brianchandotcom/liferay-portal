import {EventHandler} from 'metal-events';
import {object} from 'metal';

/**
 * Enter key keycode
 * @review
 * @type {number}
 */

const KEY_ENTER = 13;

let _destroyedCallback;
let _editableElement;
let _editor;
let _editorEventHandler;

/**
 * Destroys, if any, an existing instance of AlloyEditor.
 */

function destroy() {
	if (_editor) {
		_editorEventHandler.removeAllListeners();
		_editorEventHandler.dispose();

		const editorData = _editor.get('nativeEditor').getData();

		_editableElement.innerHTML = editorData;

		_editor.destroy();

		_editableElement = null;
		_editor = null;
		_editorEventHandler = null;

		_destroyedCallback();
		_destroyedCallback = null;
	}
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
	destroyedCallback
) {
	destroy();

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

	_editorEventHandler.add(
		nativeEditor.on(
			'key',
			_handleNativeEditorKey
		)
	);

	_editorEventHandler.add(
		nativeEditor.on(
			'change',
			() => changedCallback(nativeEditor.getData())
		)
	);

	_editorEventHandler.add(
		nativeEditor.on(
			'actionPerformed',
			() => changedCallback(nativeEditor.getData())
		)
	);

	_editorEventHandler.add(
		nativeEditor.on(
			'blur',
			(event) => {
				if (_editor._mainUI.state.hidden) {
					requestAnimationFrame(destroy);
				}
			}
		)
	);

	_editorEventHandler.add(
		nativeEditor.on(
			'instanceReady',
			() => nativeEditor.focus()
		)
	);
}

/**
 * Returns a configuration object for a AlloyEditor instance.
 * @param {HTMLElement} editableElement
 * @param {string} portletNamespace
 * @param {string} fragmentEntryLinkId
 * @param {object} defaultEditorConfiguration
 */

function _getEditorConfiguration(
	editableElement,
	portletNamespace,
	fragmentEntryLinkId,
	defaultEditorConfiguration,
	editorName
) {
	return object.mixin(
		{},
		defaultEditorConfiguration.editorConfig || {},
		{
			filebrowserImageBrowseLinkUrl: defaultEditorConfiguration
				.editorConfig
				.filebrowserImageBrowseLinkUrl
				.replace('_EDITOR_NAME_', editorName),

			filebrowserImageBrowseUrl: defaultEditorConfiguration
				.editorConfig
				.filebrowserImageBrowseUrl
				.replace('_EDITOR_NAME_', editorName),

			title: editorName
		}
	);
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

export {destroy, getActiveEditableElement, init};

export default {
	destroy,
	getActiveEditableElement,
	init
};