import 'clay-multi-select';
import 'clay-radio';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'asset-taglib/asset_tags_selector/AssetTagsSelector.es';
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
		this._bulkStatusComponent =	Liferay.component(this.namespace + 'BulkStatus');
	}

	/**
	 * Close the modal.
	 */
	close() {
		this._showModal = false;
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this.append = true;
	}

	/**
	 * Open the modal and get the
	 * commont tags.
	 */
	open(fileEntries, selectAll, folderId) {
		this.fileEntries = fileEntries;
		this.selectAll = selectAll;
		this.folderId = folderId;
		this._showModal = true;

		this._getCommonTags();
	}

	/**
	 * Creates the ajax request.
	 *
	 * @param {String} url Url of the request
	 * @param {Object} bodyData The body of the request
	 * @param {Function} callback Callback function
	 */
	_fetchTagsRequest(url, method, bodyData) {
		let body = JSON.stringify(bodyData);

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			body,
			credentials: 'include',
			headers,
			method
		};

		return fetch(this.pathModule + url, request)
			.then(
				response => response.json()
			)
			.catch(
				(xhr) => {
					this.close();
				}
			);
	}

	_getDescription(size) {
		if (size === 1) {
			return Liferay.Language.get('you-are-editing-the-tags-for-the-selected-item');
		}

		return Liferay.Util.sub(
			Liferay.Language.get('you-are-editing-the-common-tags-for-x-items.-select-edit-or-replace-current-tags'),
			size
		);
	}

	_handleSelectedItemsChange(event) {
		this._commonTags = event.selectedItems;
	}

	/**
	 * Gets the common tags for the selected
	 * file entries and updates the state.
	 *
	 * @private
	 * @review
	 */
	_getCommonTags() {
		this._loading = true;

		let selection = this._getSelection();

		Promise.all(
			[
				this._fetchTagsRequest(this.urlTags, 'POST', selection),
				this._fetchTagsRequest(this.urlSelection, 'POST', selection)
			]
		).then(
			([responseTags, responseSelection]) => {
				if (responseTags && responseSelection) {
					this._loading = false;
					this._commonTags = this._setCommonTags((responseTags.items || []).map(item => item.name));
					this.description = this._getDescription(responseSelection.size);
					this.multiple = (this.fileEntries.length > 1) || this.selectAll;
				}
			}
		);
	}

	_handleInputFocus(event) {
		const dataProvider = event.target.refs.autocomplete.refs.dataProvider;
		const modal = this.element.querySelector('.modal');

		if (modal && dataProvider && !modal.contains(dataProvider.element)) {
			modal.appendChild(dataProvider.element)
		}
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
	 * @param {!Event} event
	 *
	 * @private
	 * @review
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		let finalTags = this._commonTags.map(tag => tag.label);

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

		let instance = this;

		this._fetchTagsRequest(
			this.urlUpdateTags,
			this.append ? 'PATCH' : 'PUT',
			{
				documentBulkSelection: this._getSelection(),
				keywordsToAdd: addedTags,
				keywordsToRemove: removedTags
			}
		).then(
			response => {
				instance.close();

				if (instance._bulkStatusComponent) {
					instance._bulkStatusComponent.startWatch();
				}
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
						'label': tag,
						'value': tag
					};

					commonTagsObjList.push(tagObj);
				}
			);
		}

		return commonTagsObjList;
	}

	_getSelection() {
		return {
			documentIds: this.fileEntries,
			selectionScope: {
				folderId: this.folderId,
				repositoryId: this.repositoryId,
				selectAll: this.selectAll
			}
		};
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
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {List<String>}
	 */
	_commonTags: Config.array().value([]).internal(),

	/**
	 * Flag that indicate if loading icon must
	 * be shown.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {Boolean}
	 */
	_loading: Config.bool().value(false).internal(),

	/**
	 * Flag that indicate if the modal must
	 * be shown.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {Boolean}
	 */
	_showModal: Config.bool().value(false).internal(),

	/**
	 * Description
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	description: Config.string(),

	/**
	 * List of selected file entries.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {List<String>}
	 */
	fileEntries: Config.array(),

	/**
	 * Folder Id
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	folderId: Config.string(),

	/**
	 * [groupIds description]
	 * @type {[type]}
	 */
	groupIds: Config.array().required(),

	/**
	 * Flag that indicate if multiple
	 * file entries has been selected.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {Boolean}
	 */
	multiple: Config.bool().value(false),

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {string}
	 */
	namespace: Config.string().required(),

	/**
	 * RepositoryId
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	repositoryId: Config.string().required(),

	/**
	 * PathModule
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	pathModule: Config.string().required(),

	/**
	 * Flag that indicate if "select all" checkbox
	 * is checked.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {Boolean}
	 */
	selectAll: Config.bool(),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Url to backend service that provides
	 * the common tags info.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	urlTags: Config.string().value('/bulk-rest/v1.0/keywords/common'),

	/**
	 * Url to backend service that provides
	 * the selection information.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	urlSelection: Config.string().value('/bulk-rest/v1.0/bulk-selection'),

	/**
	 * Url to backend service that updates
	 * the tags.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	urlUpdateTags: Config.string().value('/bulk-rest/v1.0/keywords/batch')
};

// Register component

Soy.register(EditTags, templates);

export default EditTags;