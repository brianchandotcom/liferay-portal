import ClayModal from 'clay-modal';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './EditTags.soy';

class EditTags extends Component {
	open() {
		this.refs.modal.visible = true;
	}
}

EditTags.STATE = {
	/**
	 * Tags that want to be edited.
	 *
	 * @type {String}
	 */
	commonTags: Config.string(),

	/**
	 * Description
	 * @type {String}
	 */
	description: Config.string(),

	/**
	 * Flag that indicate if multiple
	 * file entries has been selected.
	 *
	 * @type {Boolean}
	 */
	multiple: Config.bool().value(false),

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required()
}

// Register component

Soy.register(EditTags, templates);

export default EditTags;