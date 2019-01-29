import Component from 'metal-component';

import {INITIAL_STATE} from '../../store/state.es';

/**
 * @type string
 */
const WRAPPER_CLASS = 'fragment-entry-link-list-wrapper';

/**
 * @type string
 */
const WRAPPER_PADDED_CLASS = 'fragment-entry-link-list-wrapper--padded';

/**
 * EditModeWrapper
 * @review
 */
class EditModeWrapper extends Component {

	/**
	 * @inheritdoc
	 */
	created() {
		this._handleSidebarVisibleChanged = this._handleSidebarVisibleChanged.bind(this);

		this.on('fragmentsEditorSidebarVisibleChanged', this._handleSidebarVisibleChanged);

		this._handleSidebarVisibleChanged();
	}

	/**
	 * Callback called when the sidebar visibily changes
	 */
	_handleSidebarVisibleChanged() {
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASS);

			if (this.fragmentsEditorSidebarVisible) {
				wrapper.classList.add(WRAPPER_PADDED_CLASS);
			}
			else {
				wrapper.classList.remove(WRAPPER_PADDED_CLASS);
			}
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
EditModeWrapper.STATE = {
	fragmentsEditorSidebarVisible: INITIAL_STATE.fragmentsEditorSidebarVisible
};

export {EditModeWrapper};
export default EditModeWrapper;