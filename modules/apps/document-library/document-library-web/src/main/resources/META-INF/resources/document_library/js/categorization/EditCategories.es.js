/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import 'asset-taglib/asset_categories_selector/soy/AssetCategoriesSelector.es';

import 'clay-alert';

import 'clay-multi-select';

import 'clay-radio';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

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
		this._assetVocabularyCategories = new Map();

		this._bulkStatusComponent = Liferay.component(
			this.namespace + 'BulkStatus'
		);
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
		this.urlCategories = `/bulk/v1.0/sites/${
			this.groupIds[0]
		}/taxonomy-vocabularies/common`;

		this._feedbackErrorClass = 'form-feedback-item';
		this._requiredVocabularyErrorMarkupText =
			'<div class="' +
			this._feedbackErrorClass +
			'">' +
			Liferay.Language.get('this-field-is-required') +
			'</div>';
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
		const inputNode = this._getVocabularyInputNode(vocabularyId);
		let valid = true;

		if (inputNode.value) {
			inputNode.parentElement.parentElement.classList.remove('has-error');
		} else {
			inputNode.parentElement.parentElement.classList.add('has-error');

			const feedbackErrorNode = inputNode.parentElement.querySelector(
				'.' + this._feedbackErrorClass
			);

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
	 * Checks if the vocabulary have errors
	 *
	 * @private
	 * @review
	 * @return {Boolean} true if it has a error, false if has not error.
	 */
	_checkErrors() {
		return !!this.element.querySelector('.has-error');
	}

	/**
	 * Creates the ajax request.
	 *
	 * @param {String} url Url of the request
	 * @param {Object} bodyData The body of the request
	 * @param {Function} callback Callback function
	 */
	_fetchCategoriesRequest(url, method, bodyData) {
		const init = {
			body: JSON.stringify(bodyData),
			headers: {
				'content-type': 'application/json'
			},
			method
		};

		return fetch(this.pathModule + url, init)
			.then(response => response.json())
			.catch(() => {
				this.close();
			});
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

		const selection = this._getSelection();

		Promise.all([
			this._fetchCategoriesRequest(this.urlCategories, 'POST', selection),
			this._fetchCategoriesRequest(this.urlSelection, 'POST', selection)
		]).then(([responseCategories, responseSelection]) => {
			if (responseCategories && responseSelection) {
				this.loading = false;
				this.description = this._getDescription(responseSelection.size);
				this.multiple = this.selectAll || this.fileEntries.length > 1;
				this.vocabularies = this._parseVocabularies(
					responseCategories.items || []
				);
			}
		});
	}

	_getDescription(size) {
		if (size === 1) {
			return Liferay.Language.get(
				'you-are-editing-the-categories-for-the-selected-item'
			);
		}

		return Liferay.Util.sub(
			Liferay.Language.get(
				'you-are-editing-the-common-categories-for-x-items.-select-edit-or-replace-current-categories'
			),
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

		this._assetVocabularyCategories.forEach(category => {
			const categoryIds = category.map(item => item.value);
			finalCategories = finalCategories.concat(categoryIds);
		});

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
		return document.getElementById(
			this.namespace + this.hiddenInput + vocabularyId
		);
	}

	_handleInputFocus(event) {
		const dataProvider = event.target.refs.autocomplete.refs.dataProvider;
		const modal = this.element.querySelector('.modal');

		if (modal && dataProvider && !modal.contains(dataProvider.element)) {
			modal.appendChild(dataProvider.element);
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

	_handleSelectedItemsChange(event) {
		const vocabularyId = event.vocabularyId;

		this._assetVocabularyCategories.set(vocabularyId, event.selectedItems);

		if (this._requiredVocabularies.includes(parseInt(vocabularyId, 10))) {
			setTimeout(() => {
				this._checkRequiredVocabulary(vocabularyId);
			}, 0);
		}
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

		setTimeout(() => {
			if (this._checkErrors()) {
				return;
			}

			const finalCategories = this._getFinalCategories();

			let addedCategories = [];

			if (!this.append) {
				addedCategories = finalCategories;
			} else {
				addedCategories = finalCategories.filter(
					categoryId =>
						this.initialCategories.indexOf(categoryId) == -1
				);
			}

			const removedCategories = this.initialCategories.filter(
				category => finalCategories.indexOf(category) == -1
			);

			const instance = this;

			this._fetchCategoriesRequest(
				this.urlUpdateCategories,
				this.append ? 'PATCH' : 'PUT',
				{
					documentBulkSelection: this._getSelection(),
					taxonomyCategoryIdsToAdd: addedCategories,
					taxonomyCategoryIdsToRemove: removedCategories
				}
			).then(() => {
				instance.close();

				if (instance._bulkStatusComponent) {
					instance._bulkStatusComponent.startWatch();
				}
			});
		}, 250);
	}

	_parseVocabularies(vocabularies) {
		let initialCategories = [];
		const requiredVocabularies = [];
		const vocabulariesList = [];

		vocabularies.forEach(vocabulary => {
			const categories = this._parseCategories(
				vocabulary.taxonomyCategories || []
			);

			const categoryIds = categories.map(item => item.value);

			const obj = {
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
		});

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
		const categoriesObjList = [];

		if (categories.length > 0) {
			categories.forEach(item => {
				const itemObj = {
					label: item.taxonomyCategoryName,
					value: item.taxonomyCategoryId
				};

				categoriesObjList.push(itemObj);
			});
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
	hiddenInput: Config.string()
		.value('assetCategoryIds_')
		.internal(),

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
	loading: Config.bool()
		.value(false)
		.internal(),

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
	showModal: Config.bool()
		.value(false)
		.internal(),

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
	urlSelection: Config.string().value('/bulk/v1.0/bulk-selection'),

	/**
	 * Url to backend service that updates
	 * the categories.
	 *
	 * @instance
	 * @memberof EditCategories
	 * @review
	 * @type {String}
	 */
	urlUpdateCategories: Config.string().value(
		'/bulk/v1.0/taxonomy-categories/batch'
	),

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
