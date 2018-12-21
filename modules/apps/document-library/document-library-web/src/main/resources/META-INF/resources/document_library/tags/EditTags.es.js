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
		this._getCommonTags();
	}

	close() {
		this.refs.modal.visible = false;
	}

	open() {
		this.refs.modal.visible = true;
		this._getCommonTags();
	}

	_fetchTagsRequest(url, bodyData, callback) {
		let body = JSON.stringify(bodyData);

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			body,
			credentials: 'include',
			headers,
			method: 'POST'
		};

		fetch(url, request)
			.then(
				response => response.json()
			)
			.then(
				response => {
					callback(response)
				}
			)
			.catch(
				(xhr) => {
					this.close();
					//TODO open toast error
				}
			);
	}

	_getCommonTags() {
		this.loading = true;

		let bodyData = {
			"bulkAssetEntryCommonTagsActionModel": {
				repositoryId: this.repositoryId,
				selection: this.fileEntries
			}
		};

		this._fetchTagsRequest(
			this.urlTags,
			bodyData,
			response => {
				var responseData = response.bulkAssetEntryCommonTagsModel;

				if (responseData) {
					this.loading = false;
					this.commonTags = responseData.tagNames;
					this.description = responseData.description;
				}
			}
		);
	}

	_handleAddTag(event) {
		event.target.selectedItems.push({label: event.data.label, value: event.data.label});
		event.target.selectedItems = event.target.selectedItems;
	}

	_handleSaveBtnClick() {
		let finalTags = this.commonTags.map(tag => tag.label);

		let addedTags = finalTags.filter(
			tag => this._initialTags.indexOf(tag) == -1
		);

		let removedTags = this._initialTags.filter(
			tag => finalTags.indexOf(tag) == -1
		);

		let bodyData = {
			"bulkAssetEntryUpdateTagsActionModel": {
				append: true,
				repositoryId: this.repositoryId,
				selection: this.fileEntries,
				toAddTagNames: addedTags,
				toRemoveTagNames: removedTags
			}
		};

		let instance = this;

		this._fetchTagsRequest(
			this.urlUpdateTags,
			bodyData,
			response => {
				instance.close();
			}
		);

	}

	_handleRemoveTag(event) {
		event.target.selectedItems.splice(event.data.index, 1);
		event.target.selectedItems = event.target.selectedItems;
	}

	/**
	 * Transforms the tags list in the object needed
	 * for the ClayMultiSelect component.
	 *
	 * @param {List<String>} commonTags
	 * @return {List<{label, value}>} new commonTags object list
	 */
	_setCommonTags(commonTags) {
		this._initialTags = commonTags;

		let commonTagsObjList = [];

		if (commonTags.length > 0) {
			commonTags.forEach(
				tag => {
					let tagObj = {
						"label": tag,
						"value": tag
					};

					commonTagsObjList.push(tagObj);
				}
			);
		}

		return commonTagsObjList;
	}

}

EditTags.STATE = {
	/**
	 * Tags that want to be edited.
	 *
	 * @type {List<String>}
	 */
	commonTags: Config.array().setter('_setCommonTags').value([]),

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
	urlTags: Config.string().required(),

	/**
	 * Url to backend service that updates
	 * the tags.
	 *
	 * @type {String}
	 */
	urlUpdateTags: Config.string().required()
}

// Register component

Soy.register(EditTags, templates);

export default EditTags;