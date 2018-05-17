import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import {showImageEditing} from './fragment_processors/EditableImageFragmentProcessor.es';
import {createTextEditor} from './fragment_processors/EditableTextFragmentProcessor.es';
import templates from './FragmentEditableField.soy';

class FragmentEditableField extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._handleImageSelected = this._handleImageSelected.bind(this);
		this._handleTextEditorChanged = this._handleTextEditorChanged.bind(this);
	}

	/**
	 * Handle editable click event
	 * @param {Event} event
	 * @private
	 */

	_handleEditableClick(event) {
		event.preventDefault();
		event.stopPropagation();

		this._showTooltip = true;
	}

	/**
	 * Handle edit button click event
	 * @private
	 */

	_handleEditButtonClick() {
		if (this.type === 'image') {
			showImageEditing(
				this.refs.editable,
				this.portletNamespace,
				this.imageSelectorURL,
				this.fragmentEntryLinkId,
				this._handleImageSelected
			);
		}
		else {
			createTextEditor(
				this.refs.editable,
				this.defaultEditorConfiguration,
				this.portletNamespace,
				this.fragmentEntryLinkId,
				this._handleTextEditorChanged
			);
		}
	}

	/**
	 * Handle image editor select event
	 * @param {string} imageUrl
	 * @private
	 */

	_handleImageSelected(imageUrl) {
		this.emit(
			'imageSelected',
			{imageUrl}
		);
	}

	/**
	 * Handle map button click event
	 * @private
	 */

	_handleMapButtonClick() {
		this.emit(
			'mapButtonClicked',
			{editableId: this.editableId}
		);
	}

	/**
	 * Handle text editor change event
	 * @param {string} newValue
	 * @private
	 */

	_handleTextEditorChanged(newValue) {
		this.emit(
			'textEditorChanged',
			{newValue}
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentEditableField.STATE = {

	/**
	 * Editable content to be rendered
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	content: Config.setter(content => Soy.toIncDom(content || '')),

	/**
	 * Default configuration for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {object}
	 */

	defaultEditorConfiguration: Config.object().value({}),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Image selector url
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	imageSelectorURL: Config.string().required(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	editableId: Config.string().required(),

	/**
	 * Portlet namespace
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * Editable type
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	type: Config.string().required(),

	/**
	 * Flag indicating if the click tooltip should be visible.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {boolean}
	 */

	_showTooltip: Config.internal().bool().value(false)
};

Soy.register(FragmentEditableField, templates);

export {FragmentEditableField};
export default FragmentEditableField;