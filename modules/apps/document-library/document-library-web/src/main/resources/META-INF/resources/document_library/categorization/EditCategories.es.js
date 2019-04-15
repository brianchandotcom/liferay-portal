import 'clay-multi-select';
import 'clay-radio';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import 'asset-taglib/asset_categories_selector/AssetCategoriesSelector.es';
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
		this._bulkStatusComponent =	Liferay.component(this.namespace + 'BulkStatus');
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
		this.urlCategories = `/bulk-rest/v1.0/sites/${this.groupIds[0]}/taxonomy-vocabularies/common`;

		this._feedbackErrorClass = 'form-feedback-item';
		this._requiredVocabularyErrorMarkupText = '<div class="' + this._feedbackErrorClass + '">' + Liferay.Language.get('this-field-is-required') + '</div>';
	}

	/**
	 * Open the modal and get the
	 * commont categories.
	 */
	open(fileEntries, selectAll, folderId) {
		this.fileEntries = fileEntries;
		this.selectAll = selectAll;
		this.folderId = folderId;
		this.showModal = true;

		this._getCommonCategories();
	}

	/**
	 * Checks if the vocabulary is empty or not.
	 *
	 * @param  {String} vocabularyId
	 * @return {Boolean} true if it has a category, false if is empty.
	 */
	_checkRequiredVocabulary(vocabularyId) {
		let inputNode = this._getVocabularyInputNode(vocabularyId);
		let valid = true;

		if (inputNode.value) {
			inputNode.parentElement.classList.remove('has-error');
		}
		else {
			inputNode.parentElement.classList.add('has-error');

			let feedbackErrorNode = inputNode.parentElement.querySelector('.' + this._feedbackErrorClass);

			if (!feedbackErrorNode) {
				inputNode.parentElement.insertAdjacentHTML(
					'beforeend',
					this._requiredVocabularyErrorMarkupText
				);
			}

			valid = false;
		}

		return valid;
	}

	/**
	 * Creates the ajax request.
	 *
	 * @param {String} url Url of the request
	 * @param {Object} bodyData The body of the request
	 * @param {Function} callback Callback function
	 */
	_fetchCategoriesRequest(url, method, bodyData) {
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

	/**
	 * Gets the common categories for the selected
	 * file entries and updates the state.
	 *
	 * @private
	 * @review
	 */
	_getCommonCategories() {
		this.loading = true;

		let selection = this._getSelection();

		Promise.all(
			[
				this._fetchCategoriesRequest(this.urlCategories, 'POST', selection),
				this._fetchCategoriesRequest(this.urlSelection, 'POST', selection)
			]
		).then(
			([responseCategories, responseSelection]) => {
				if (responseCategories && responseSelection) {
					this.loading = false;
					this.description = this._getDescription(responseSelection.size);
					this.multiple = (this.fileEntries.length > 1) || this.selectAll;
					this.vocabularies = this._parseVocabularies(responseCategories.items || []);
				}
			}
		);
	}

	_getDescription(size) {
		if (size === 1) {
			return Liferay.Language.get('you-are-editing-the-categories-for-the-selected-item');
		}

		return Liferay.Util.sub(
			Liferay.Language.get('you-are-editing-the-common-categories-for-x-items.-select-edit-or-replace-current-categories'),
			size
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
		let inputElementName = this.namespace + this.hiddenInput;

		this.vocabularies.forEach(
			vocabulary => {
				let inputNode = document.getElementById(inputElementName + vocabulary.id);

				if (inputNode.value) {
					let categoryIds = inputNode.value.split(',').map(Number);
					finalCategories = finalCategories.concat(categoryIds);
				}
			}
		);

		return finalCategories;
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

	/**
	 * Gets the input where categories are saved for a vocabulary.
	 *
	 * @param  {String} vocabularyId [description]
	 * @return {DOMElement} input node.
	 */
	_getVocabularyInputNode(vocabularyId) {
		return document.getElementById(this.namespace + this.hiddenInput + vocabularyId);
	}

	/**
	 * Checks if a required vocabulary has categories or not.
	 *
	 * @param  {Event} event
	 */
	_handleCategoriesChange(event) {
		let vocabularyId = event.vocabularyId[0];

		if (this._requiredVocabularies.includes(parseInt(vocabularyId, 10))) {
			this._checkRequiredVocabulary(vocabularyId);
		}
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
	 * to update the categories.
	 * @param {!Event} event
	 *
	 * @private
	 * @review
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		if (!this._validateRequiredVocabularies()) {
			return;
		}

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

		let instance = this;

		this._fetchCategoriesRequest(
			this.urlUpdateCategories,
			this.append ? 'PATCH' : 'PUT',
			{
				documentBulkSelection: this._getSelection(),
				taxonomyCategoryIdsToAdd: addedCategories,
				taxonomyCategoryIdsToRemove: removedCategories
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

	_parseVocabularies(vocabularies) {
		let initialCategories = [];
		let requiredVocabularies = [];
		let vocabulariesList = [];

		vocabularies.forEach(
			vocabulary => {
				let categories = this._parseCategories(vocabulary.taxonomyCategories || []);

				let categoryIds = categories.map(item => item.value);

				let obj = {
					id: vocabulary.taxonomyVocabularyId.toString(),
					required: vocabulary.required,
					selectedCategoryIds: categoryIds.join(','),
					selectedItems: categories,
					singleSelect: !vocabulary.multiValued,
					title: vocabulary.name
				};

				vocabulariesList.push(obj);

				if (vocabulary.required) {
					requiredVocabularies.push(vocabulary.taxonomyVocabularyId);
				}

				initialCategories = initialCategories.concat(categoryIds);
			}
		);

		this.initialCategories = initialCategories;
		this._requiredVocabularies = requiredVocabularies;

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
						'label': item.taxonomyCategoryName,
						'value': item.taxonomyCategoryId
					};

					categoriesObjList.push(itemObj);
				}
			);
		}

		return categoriesObjList;
	}

	_validateRequiredVocabularies() {
		let requiredVocabularies = this._requiredVocabularies;
		let valid = true;

		if (requiredVocabularies) {
			requiredVocabularies.forEach(
				vocabularyId => {
					if (!this._checkRequiredVocabulary(vocabularyId)) {
						valid = false;
					}
				}
			);
		}

		return valid;
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
	folderId: Config.string(),

	/**
	 * Group Ids.
	 *
	 * @type {List<String>}
	 */
	groupIds: Config.array().required(),

	/**
	* Hidden input name
	*
	* @type {String}
	 */
	hiddenInput: Config.string().value('assetCategoryIds_').internal(),

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
	namespace: Config.string().required(),

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
	 * RepositoryId
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	repositoryId: Config.string().required(),

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
	urlCategories: Config.string(),

	/**
	 * Url to backend service that provides
	 * the selection description.
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	urlSelection: Config.string().value('/bulk-rest/v1.0/bulk-selection'),

	/**
	 * Url to backend service that updates
	 * the categories.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	urlUpdateCategories: Config.string().value('/bulk-rest/v1.0/taxonomy-categories/batch'),

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