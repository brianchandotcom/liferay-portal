/* global AlloyEditor, CKEDITOR */

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutPageTemplateFragment.soy';

/**
 * LayoutPageTemplateFragment
 * @review
 */
class LayoutPageTemplateFragment extends Component {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleEditorChange = this._handleEditorChange.bind(this);

		this._fetchFragmentContent(this.fragmentEntryId, this.index);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	detached() {
		for (let editor of this._editors) {
			editor.destroy();
		}

		this._editors = [];
	}

	/**
	 * After each render, script tags need to be reapended to the DOM
	 * in order to trigger an execution (content changes do not trigger it).
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this.refs.content) {
			this._enableEditableFields(this.refs.content);
		}
	}

	/**
	 * Allow inline edition using AlloyEditor
	 * @param {!HTMLElement} content
	 * @private
	 * @review
	 */
	_enableEditableFields(content) {
		const editors = [];

		for (let editableElement of content.querySelectorAll('lfr-editable')) {
			const wrapper = document.createElement('div');
			const editableId = editableElement.id;
			const editableContent =
				typeof this.editableValues[editableId] === 'undefined'
					? editableElement.innerHTML
					: this.editableValues[editableId];

			wrapper.dataset.lfrEditableId = editableId;
			wrapper.innerHTML = editableContent;
			editableElement.parentNode.replaceChild(wrapper, editableElement);

			const editor = AlloyEditor.editable(wrapper, {
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
					'adaptivemedia',
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
					'ae_embed',
				].join(','),
			});

			editor.get('nativeEditor').on('change', this._handleEditorChange);
			editors.push(editor);
		}

		this._editors = editors;
	}

	/**
	 * Fetches a fragment entry from the given ID, and stores the HTML,
	 * CSS and JS result into component properties.
	 * @param {!string} fragmentEntryId
	 * @param {!number} position
	 * @private
	 * @review
	 */
	_fetchFragmentContent(fragmentEntryId, position) {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryId`,
			fragmentEntryId
		);
		formData.append(`${this.portletNamespace}position`, position);

		this._loading = true;

		fetch(this.renderFragmentEntryURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
			.then(response => response.json())
			.then(response => {
				this._content = Soy.toIncDom(response.content);
				this._loading = false;
			});
	}

	/**
	 * Handle AlloyEditor changes and propagate them with an
	 * "editableChanged" event.
	 * @param {!Object} event
	 * @private
	 * @review
	 */
	_handleEditorChange(event) {
		this.emit('editableChanged', {
			editableId: event.editor.element.$.dataset.lfrEditableId,
			fragmentIndex: this.index,
			value: event.editor.getData(),
		});
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @private
	 */
	_handleFragmentRemoveButtonClick() {
		this.emit('fragmentRemoveButtonClick', {
			fragmentIndex: this.index,
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
LayoutPageTemplateFragment.STATE = {
	/**
	 * Editable values that should be used instead of the default ones
	 * inside editable fields.
	 * @default {}
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!Object}
	 */
	editableValues: Config.object().value({}),

	/**
	 * Fragment entry ID
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!string}
	 */
	fragmentEntryId: Config.string().required(),

	/**
	 * Fragment index
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!number}
	 */
	index: Config.number().required(),

	/**
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {string}
	 */
	name: Config.string().value(''),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * URL for getting a fragment render result.
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!string}
	 */
	renderFragmentEntryURL: Config.string().required(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @review
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Fragment content to be rendered
	 * @default Soy.toIncDom('')
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @review
	 * @type {function}
	 */
	_content: Config.func()
		.internal()
		.value(Soy.toIncDom('')),

	/**
	 * List of AlloyEditor instances used for inline edition
	 * @default []
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @review
	 * @type {Array<AlloyEditor>}
	 */
	_editors: Config.arrayOf(Config.object())
		.internal()
		.value([]),

	/**
	 * Flag indicating that fragment information is being loaded
	 * @default false
	 * @instance
	 * @memberOf LayoutPageTemplateFragment
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_loading: Config.bool().value(false),
};

Soy.register(LayoutPageTemplateFragment, templates);

export {LayoutPageTemplateFragment};
export default LayoutPageTemplateFragment;
