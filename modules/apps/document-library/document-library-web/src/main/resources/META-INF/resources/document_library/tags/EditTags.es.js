import 'clay-multi-select';
import ClayButton from 'clay-button';
import ClayRadio from 'clay-radio';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './EditTags.soy';

/**
 * Handles the tags of the selected
 * fileEntries inside a modal.
 */
class EditTags extends Component {

	/**
	 * @inheritDoc
	 */
	attached() {
		this._getCommonTags();
	}

	/**
	 * Close the modal.
	 */
	close() {
		this.refs.modal.visible = false;
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this.append = true;
		this.dataSource = [];
	}

	/**
	 * Open the modal and get the
	 * commont tags.
	 */
	open() {
		this.refs.modal.visible = true;
		this._getCommonTags();
	}

	/**
	 * Creates the ajax request.
	 *
	 * @param {String} url Url of the request
	 * @param {Object} bodyData The body of the request
	 * @param {Function} callback Callback function
	 */
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

	/**
	 * Gets the common tags for the selected
	 * file entries and updates the state.
	 *
	 * @private
	 * @review
	 */
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
				if (response) {
					this.loading = false;
					this.commonTags = response.tagNames;
					this.description = response.description;
					this.multiple = this.fileEntries.length > 1;
				}
			}
		);
	}

	/**
	 * Sync the input radio with the state
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleRadioChange(event) {
		this.append = event.target.value === 'add';
	}

	/**
	 * Sends request to backend services
	 * to update the tags.
	 *
	 * @private
	 * @review
	 */
	_handleSaveBtnClick() {
		let finalTags = this.commonTags.map(tag => tag.label);

		let addedTags = [];

		if (!this.append) {
			addedTags = finalTags;
		}
		else {
			addedTags = finalTags.filter(
				tag => this._initialTags.indexOf(tag) == -1
			);
		}

		let removedTags = this._initialTags.filter(
			tag => finalTags.indexOf(tag) == -1
		);

		let bodyData = {
			"bulkAssetEntryUpdateTagsActionModel": {
				append: this.append,
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

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
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
	 * Flag that indicate if loading icon must
	 * be shown.
	 *
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