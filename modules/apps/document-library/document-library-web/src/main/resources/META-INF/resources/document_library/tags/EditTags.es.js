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

	attached() {
		this._fetchTagsData();
	}

	close() {
		this.refs.modal.visible = false;
	}

	open() {
		this.refs.modal.visible = true;
		this._fetchTagsData();
	}

	_fetchTagsData() {
		this.loading = true;

		let bodyData = {
			"bulkAssetEntryCommonTagsActionModel": {
				repositoryId: this.repositoryId,
				selection: this.fileEntries
			}
		};

		let body = JSON.stringify(bodyData);

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			body,
			credentials: 'include',
			headers,
			method: 'POST'
		};

		fetch(this.urlTags, request)
			.then(
				response => response.json()
			)
			.then(
				response => {
					var responseData = response.bulkAssetEntryCommonTagsModel;

					if (responseData) {
						this.loading = false;
						this.commonTags = responseData.tagNames;
						this.description = responseData.description;
					}
				}
			)
			.catch(
				(xhr) => {
					this.close();
					//TODO open toast error
				}
			);

	}

}

EditTags.STATE = {
	/**
	 * Tags that want to be edited.
	 *
	 * @type {List<String>}
	 */
	commonTags: Config.array().value([]),

	/**
	 * Description
	 * @type {String}
	 */
	description: Config.string(),

	/**
	 * List of selected file entries.
	 *
	 * @type {List<String>}
	 */
	fileEntries: Config.array().required(),

	/**
	 * TODO
	 * @type {Boolean}
	 */
	loading: Config.bool().value(false).internal(),

	/**
	 * Flag that indicate if multiple
	 * file entries has been selected.
	 *
	 * @type {Boolean}
	 */
	multiple: Config.bool().value(false),

	/**
	 * RepositoryId
	 * @type {Number}
	 */
	repositoryId: Config.number().required(),

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Url to backend service that provides
	 * the common tags info.
	 *
	 * @type {String}
	 */
	urlTags: Config.string().required()
}

// Register component

Soy.register(EditTags, templates);

export default EditTags;