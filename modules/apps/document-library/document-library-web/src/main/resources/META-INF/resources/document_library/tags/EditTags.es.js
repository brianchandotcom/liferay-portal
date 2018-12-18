import 'clay-multi-select';
import ClayButton from 'clay-button';
import ClayRadio from 'clay-radio';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './EditTags.soy';

class EditTags extends Component {
	created() {
		this.dataSource = [
			'Bread',
			'Ammonia cookie',
			'Cuisine of Antebellum America',
			'Apple butter',
			'Apple sauce',
			'Baked potato',
			'Barbecue',
			'Bear claw',
			'Beef Manhattan',
			'Blue cheese dressing',
			'Blue-plate special',
			'Bookbinder soup',
			'Breakfast burrito',
			'Brunswick stew',
			'Buffalo burger',
			'Buffalo wing',
			'Bull roast',
			'Burnt ends',
			'Butter cookie',
		];
	}

	close() {
		this.refs.modal.visible = false;
		this.emptyState = true;
	}

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
	commonTags: Config.array().value([]),

	/**
	 * Description
	 * @type {String}
	 */
	description: Config.string(),

	emptyState: Config.bool().value(true).internal(),

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