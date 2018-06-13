import {EventHandler} from 'metal-events';
import {object} from 'metal';

/**
 * Default configuration used for creating AlloyEditor instances.
 */

const EDITOR_CONFIGURATION = {
	enterMode: CKEDITOR.ENTER_BR,

	extraPlugins: [
		'ae_autolink',
		'ae_dragresize',
		'ae_addimages',
		'ae_imagealignment',
		'ae_placeholder',
		'ae_selectionregion',
		'ae_tableresize',
		'ae_tabletools',
		'ae_uicore',
		'itemselector',
		'media',
		'adaptivemedia'
	].join(','),

	removePlugins: [
		'contextmenu',
		'elementspath',
		'image',
		'link',
		'liststyle',
		'magicline',
		'resize',
		'tabletools',
		'toolbar',
		'ae_embed'
	].join(',')
};

/**
 * Enter key keycode
 * @review
 * @type {number}
 */

const KEY_ENTER = 13;

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
 * @param {function} callback
 */

function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	callback
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
			() => callback(nativeEditor.getData())
		)
	);

	_editorEventHandler.add(
		nativeEditor.on(
			'actionPerformed',
			() => callback(nativeEditor.getData())
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
	const configuration = {};

	configuration.title = [
		portletNamespace,
		'_FragmentEntryLinkEditable_',
		fragmentEntryLinkId
	].join('');

	if (editableElement.getAttribute('type') === 'text') {
		configuration.allowedContent = '';
		configuration.disallowedContent = 'br';
		configuration.toolbars = {};
	}

	configuration.filebrowserImageBrowseLinkUrl = defaultEditorConfiguration
		.editorConfig
		.filebrowserImageBrowseLinkUrl
		.replace('_EDITOR_NAME_', editorName);

	configuration.filebrowserImageBrowseUrl = defaultEditorConfiguration
		.editorConfig
		.filebrowserImageBrowseUrl
		.replace('_EDITOR_NAME_', editorName);

	return object.mixin(
		{},
		defaultEditorConfiguration.editorConfig || {},
		EDITOR_CONFIGURATION,
		configuration
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