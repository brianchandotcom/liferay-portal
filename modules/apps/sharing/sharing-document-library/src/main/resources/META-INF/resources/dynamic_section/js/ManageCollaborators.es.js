import 'frontend-js-web/liferay/compat/modal/Modal.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './ManageCollaborators.soy';
import {Config} from 'metal-state';

class ManageCollaborators extends Component {

	_handleManageCollaboratorsButtonClick() {
		this._collaboratorsDialogOpen = true;
	}

	_handleCancelButtonClick() {
		this._collaboratorsDialogOpen = false;
	}

	_handleSaveButtonClick() {
		this._collaboratorsDialogOpen = false;
	}
}

ManageCollaborators.STATE = {

	/**
	 * Flag to indicate if dialog should be open.
	 * @default false
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {Boolean}
	 */
	_collaboratorsDialogOpen: Config.bool().internal().value(false),

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */

	pathThemeImages: Config.string().required(),

}

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;
