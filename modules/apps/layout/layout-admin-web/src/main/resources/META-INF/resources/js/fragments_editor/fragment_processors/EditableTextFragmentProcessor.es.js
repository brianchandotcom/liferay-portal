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

let _editableElement;
let _editor;
let _editorEventHandler;
let _navigationEventHandler;

/**
 * Destroys, if any, an existing instance of AlloyEditor.
 */

function destroy() {
	if (_editor) {
		_editorEventHandler.removeAllListeners();
		_editorEventHandler.dispose();
		_navigationEventHandler.detach();

		const editorData = _editor.get('nativeEditor').getData();

		_editableElement.innerHTML = editorData;

		_editor.destroy();

		_editableElement = null;
		_editor = null;
		_editorEventHandler = null;
		_navigationEventHandler = null;
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

	editableElement.innerHTML = '';
	editableElement.appendChild(wrapper);

	_editableElement = editableElement;
	_editorEventHandler = new EventHandler();

	_editor = AlloyEditor.editable(
		wrapper,
		object.mixin(
			defaultEditorConfiguration.editorConfig || {},
			EDITOR_CONFIGURATION,
			{
				title: [
					portletNamespace,
					'_FragmentEntryLinkEditable_',
					fragmentEntryLinkId
				].join('')
			}
		)
	);

	const nativeEditor = _editor.get('nativeEditor');

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

export {destroy, getActiveEditableElement, init};