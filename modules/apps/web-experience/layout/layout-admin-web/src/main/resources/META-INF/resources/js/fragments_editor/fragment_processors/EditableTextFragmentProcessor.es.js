import debounce from 'metal-debounce';
import {object} from 'metal';

/**
 * Allow having editable text fields inside Fragments
 */

class EditableTextFragmentProcessor {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(fragmentEntryLink) {
		this.fragmentEntryLink = fragmentEntryLink;

		this._editors = [];

		this._handleEditorChange = debounce(
			this._handleEditorChange.bind(this),
			300
		);

		this._destroyEditors = this._destroyEditors.bind(this);
		Liferay.on('beforeNavigate', this._destroyEditors);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	dispose() {
		this._destroyEditors();
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	process() {
		this._destroyEditors();
		this._createEditors();
	}

	/**
	 * For a given editableField, creates an AlloyEditor instance
	 * and returns it
	 * @param {HTMLElement} editableField
	 * @return {AlloyEditor}
	 * @private
	 * @review
	 */

	_createEditor(editableField) {
		const editableContent = editableField.innerHTML;
		const editableId = editableField.id;
		const wrapper = document.createElement('div');

		wrapper.dataset.lfrEditableId = editableId;
		wrapper.innerHTML = editableContent;
		editableField.parentNode.replaceChild(wrapper, editableField);

		const editor = AlloyEditor.editable(
			wrapper,
			object.mixin(
				this.fragmentEntryLink.defaultEditorConfiguration.editorConfig,
				EditableTextFragmentProcessor.EDITOR_CONFIGURATION
			)
		);

		const nativeEditor = editor.get('nativeEditor');

		nativeEditor.name = `${this.fragmentEntryLink.portletNamespace}fragmentEntryLink_`;
		nativeEditor.on('change', this._handleEditorChange);
		nativeEditor.on('selectionChange', this._handleEditorChange);

		return editor;
	}

	/**
	 * Creates a list of editors for every editable field inside Fragment
	 * and stores them inside _editors attribute.
	 * @private
	 */

	_createEditors() {
		const content = this.fragmentEntryLink.refs.content;

		this._editors = []
			.slice
			.call(content.querySelectorAll('lfr-editable[type="text"]'))
			.map(editableField => this._createEditor(editableField));
	}

	/**
	 * Destroy all existing editors and reset _editors array.
	 * @private
	 */

	_destroyEditors() {
		this._editors.forEach(
			editor => {
				editor.destroy();
			}
		);

		this._editors = [];
	}

	/**
	 * Handles an AlloyEditor change event and propagates it as
	 * editableChanged event from the FragmentEntryLink
	 * @param {Event} event
	 * @private
	 */

	_handleEditorChange(event) {
		this.fragmentEntryLink.emit(
			'editableChanged',
			{
				editableId: event.editor.element.$.dataset.lfrEditableId,
				fragmentEntryLinkId: this.fragmentEntryLink.fragmentEntryLinkId,
				value: event.editor.getData()
			}
		);
	}
}

/**
 * Default configuration used for creating AlloyEditor instances.
 */

EditableTextFragmentProcessor.EDITOR_CONFIGURATION = {
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

export {EditableTextFragmentProcessor};
export default EditableTextFragmentProcessor;