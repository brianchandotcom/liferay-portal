import 'clay-multi-select';
import ClayButton from 'clay-button';
import ClayRadio from 'clay-radio';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import InputCategoriesSelector from './InputCategoriesSelector.es';
import templates from './EditCategories.soy';

/**
 * Handles the categories of the selected
 * fileEntries inside a modal.
 */
class EditCategories extends Component {

	/**
	 * @inheritDoc
	 */
	attached() {
		this._bulkStatusComponent =	Liferay.component(this.portletNamespace + 'BulkStatus');
	}

	/**
	 * Close the modal.
	 */
	close() {
		this.showModal = false;
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
	 * commont categories.
	 */
	open() {
		this.showModal = true;
		this._getCommonCategories();
	}

	/**
	 * Creates the ajax request.
	 *
	 * @param {String} url Url of the request
	 * @param {Object} bodyData The body of the request
	 * @param {Function} callback Callback function
	 */
	_fetchCategoriesRequest(url, bodyData, callback) {
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
					callback(response);
				}
			)
			.catch(
				(xhr) => {
					this.close();
				}
			);
	}

	/**
	 * Gets the common categories for the selected
	 * file entries and updates the state.
	 *
	 * @private
	 * @review
	 */
	_getCommonCategories() {
		this.loading = true;

		let bodyData = {
			folderId: this.folderId,
			repositoryId: this.repositoryId,
			selectAll: this.selectAll,
			selection: this.fileEntries
		};

		this._fetchCategoriesRequest(
			this.urlCategories,
			bodyData,
			response => {
				if (response) {
					this.loading = false;
					this.description = response.description;
					this.multiple = (this.fileEntries.length > 1) || this.selectAll;
					this.vocabularies = this._parseVocabularies(response.vocabularies);
				}
			}
		);
	}

	/**
	 * Get all the categoryIds selected for all
	 * the vocabularies.
	 *
	 * @return {List<Long>} List of categoryIds.
	 */
	_getFinalCategories() {
		let finalCategories = [];

		this.vocabularies.forEach(
			vocabulary => {
				let categoryIds = vocabulary.categories.map(item => item.value);
				finalCategories = finalCategories.concat(categoryIds);
			}
		);

		return finalCategories;
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
	 * to update the categories.
	 *
	 * @private
	 * @review
	 */
	_handleSaveBtnClick() {
		let finalCategories = this._getFinalCategories();

		let addedCategories = [];

		if (!this.append) {
			addedCategories = finalCategories;
		}
		else {
			addedCategories = finalCategories.filter(
				categoryId => this.initialCategories.indexOf(categoryId) == -1
			);
		}

		let removedCategories = this.initialCategories.filter(
			category => finalCategories.indexOf(category) == -1
		);

		let bodyData = {
			append: this.append,
			folderId: this.folderId,
			repositoryId: this.repositoryId,
			selectAll: this.selectAll,
			selection: this.fileEntries,
			toAddCategoryIds: addedCategories,
			toRemoveCategoryIds: removedCategories
		};

		let instance = this;

		this._fetchCategoriesRequest(
			this.urlUpdateCategories,
			bodyData,
			response => {
				instance.close();

				if (instance._bulkStatusComponent) {
					instance._bulkStatusComponent.startWatch();
				}
			}
		);
	}

	_parseVocabularies(vocabularies) {
		let initialCategories = [];
		let vocabulariesList = [];

		vocabularies.forEach(
			vocabulary => {
				let categories = this._parseCategories(vocabulary.categories);

				let obj = {
					categories: categories,
					id: vocabulary.vocabularyId,
					multiValued: vocabulary.multiValued,
					name: vocabulary.name
				};

				vocabulariesList.push(obj);

				let categoryIds = categories.map(item => item.value);

				initialCategories = initialCategories.concat(categoryIds);
			}
		);

		this.initialCategories = initialCategories;

		return vocabulariesList;
	}

	/**
	 * Transforms the categories list in the object needed
	 * for the ClayMultiSelect component.
	 *
	 * @param {List<Long, String>} categories
	 * @return {List<{label, value}>} new commonItems object list
	 */
	_parseCategories(categories) {
		let categoriesObjList = [];

		if (categories.length > 0) {
			categories.forEach(
				item => {
					let itemObj = {
						'label': item.name,
						'value': item.categoryId
					};

					categoriesObjList.push(itemObj);
				}
			);
		}

		return categoriesObjList;
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
EditCategories.STATE = {

	/**
	 * Description
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	description: Config.string(),

	/**
	 * List of selected file entries.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {List<String>}
	 */
	fileEntries: Config.array(),

	/**
	 * Folder Id
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	folderId: Config.string().required(),

	/**
	 * Original categoryIds
	 *
	 * @type {List<Long>}
	 */
	initialCategories: Config.array().internal(),

	/**
	 * Flag that indicate if loading icon must
	 * be shown.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {Boolean}
	 */
	loading: Config.bool().value(false).internal(),

	/**
	 * Flag that indicate if multiple
	 * file entries has been selected.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {Boolean}
	 */
	multiple: Config.bool().value(false),

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * RepositoryId
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {Number}
	 */
	repositoryId: Config.number().required(),

	/**
	 * Flag that indicate if "select all" checkbox
	 * is checked.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {Boolean}
	 */
	selectAll: Config.bool(),

	/**
	 * Url to the categories selector page
	 * @type {String}
	 */
	selectCategoriesUrl: Config.string().required(),

	/**
	 * Flag that indicate if the modal must
	 * be shown.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {Boolean}
	 */
	showModal: Config.bool().value(false).internal(),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Url to backend service that provides
	 * the common categories info.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	urlCategories: Config.string().required(),

	/**
	 * Url to backend service that updates
	 * the categories.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	urlUpdateCategories: Config.string().required(),

	/**
	 * List of vocabularies
	 *
	 * @type {Array}
	 */
	vocabularies: Config.array().value([])
};

// Register component

Soy.register(EditCategories, templates);

export default EditCategories;