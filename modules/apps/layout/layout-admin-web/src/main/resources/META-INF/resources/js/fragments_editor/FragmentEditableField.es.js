import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import EditableImageFragmentProcessor from './fragment_processors/EditableImageFragmentProcessor.es';
import EditableTextFragmentProcessor from './fragment_processors/EditableTextFragmentProcessor.es';
import templates from './FragmentEditableField.soy';

class FragmentEditableField extends Component {

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
			EditableImageFragmentProcessor.showImageEditing();
		}
		else {
			EditableTextFragmentProcessor.createTextEditor();
		}
	}

	/**
	 * Handle map button click event
	 * @private
	 */

	_handleMapButtonClick() {
		this.emit(
			'mapButtonClicked',
			{editableId: this.id}
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
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	id: Config.string().required(),

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