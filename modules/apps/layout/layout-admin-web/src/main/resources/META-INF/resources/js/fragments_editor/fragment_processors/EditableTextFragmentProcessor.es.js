import debounce from 'metal-debounce';
import {dom} from 'metal-dom';
import {EventHandler} from 'metal-events';
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

		this._eventHandler = new EventHandler();

		this._editableField = null;
		this._editableId = null;
		this._editor = null;
		this._editorEventHandler = new EventHandler();

		this._handleEditableClick = this._handleEditableClick.bind(this);

		this._handleEditorChange = debounce(
			this._handleEditorChange.bind(this),
			300
		);

		Liferay.on(
			'beforeNavigate',
			this.dispose.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	dispose() {
		this._eventHandler.removeAllListeners();
		this._destroyEditor();
	}

	/**
	 * Finds an associated editor for a given editable id
	 * @param {string} editableId The id of editable section
	 * @return {?AlloyEditor}
	 * @review
	 */

	findEditor(editableId) {
		const setData = data => {
			const editableField = this.fragmentEntryLink.element.querySelector(
				`lfr-editable[id="${editableId}"][type="text"]`
			);

			if (editableField) {
				this._destroyEditor();
				editableField.innerHTML = data;
			}
		};

		return {setData};
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	process() {
		this._destroyEditor();
		this._eventHandler.removeAllListeners();

		if (!this.fragmentEntryLink.showMapping) {
			this._eventHandler.add(
				dom.delegate(
					this.fragmentEntryLink.refs.content,
					'click',
					'lfr-editable[type="text"]',
					this._handleEditableClick
				)
			);
		}
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
		this._editableField = editableField;
		this._editableId = editableField.id;

		const editableContent = editableField.innerHTML;
		const wrapper = document.createElement('div');

		wrapper.dataset.lfrEditableId = this._editableId;
		wrapper.innerHTML = editableContent;

		editableField.innerHTML = '';
		editableField.appendChild(wrapper);

		const editor = AlloyEditor.editable(
			wrapper,
			object.mixin(
				this.fragmentEntryLink.defaultEditorConfiguration.editorConfig,
				EditableTextFragmentProcessor.EDITOR_CONFIGURATION,
				{
					title: [
						this.fragmentEntryLink.portletNamespace,
						'_FragmentEntryLinkEditable_',
						this.fragmentEntryLink.fragmentEntryLinkId
					].join('')
				}
			)
		);

		const nativeEditor = editor.get('nativeEditor');

		const editorChangeHandler = nativeEditor.on(
			'change',
			this._handleEditorChange
		);

		const editorSelectionChangeHandler = nativeEditor.on(
			'actionPerformed',
			this._handleEditorChange
		);

		this._editor = editor;
		this._editorEventHandler.add(editorChangeHandler);
		this._editorEventHandler.add(editorSelectionChangeHandler);

		nativeEditor.on('instanceReady', () => nativeEditor.focus());
	}

	/**
	 * Destroy existing editor.
	 * @private
	 */

	_destroyEditor() {
		if (this._editor) {
			this._editorEventHandler.removeAllListeners();

			const editorData = this._editor.get('nativeEditor').getData();

			this._editableField.innerHTML = editorData;

			this._editor.destroy();
			this._editor = null;
		}
	}

	_handleEditableClick(event) {
		const editableField = event.delegateTarget;

		if (editableField !== this._editableField) {
			this._destroyEditor();

			this._createEditor(editableField);
		}
	}

	/**
	 * Handles an AlloyEditor change event and propagates it as
	 * editableChanged event from the FragmentEntryLink
	 * @param {Event} event
	 * @private
	 */

	_handleEditorChange(event) {
		const editor = event.editor;
		const fragmentEntryLinkId = this.fragmentEntryLink.fragmentEntryLinkId;

		this.fragmentEntryLink.emit(
			'editableChanged',
			{
				editableId: editor.element.$.dataset.lfrEditableId,
				fragmentEntryLinkId,
				value: editor.getData()
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