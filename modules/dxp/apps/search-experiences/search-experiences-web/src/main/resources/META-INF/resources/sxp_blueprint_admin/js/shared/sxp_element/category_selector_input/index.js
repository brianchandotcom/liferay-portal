/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import useDebounceCallback from '../../../hooks/useDebounceCallback';
import {DEFAULT_HEADERS, fetchData} from '../../../utils/fetch';
import {removeDuplicates, toNumber} from '../../../utils/utils';
import ThemeContext from '../../ThemeContext';
import CategorySelectorModal from './CategorySelectorModal';

export const FETCH_URLS = {
	getCategories: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${id}/taxonomy-categories`,
	getCategory: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-categories/${id}/`,
	getSubCategories: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-categories/${id}/taxonomy-categories`,
	getUserAccount: () => '/o/headless-admin-user/v1.0/my-user-account',
	getVocabularies: (id) =>
		`/o/headless-admin-taxonomy/v1.0/sites/${id}/taxonomy-vocabularies`,
};

/**
 * Returns the name of the site by matching the siteId to the site inside
 * categoryTree.
 * @param {String} siteId Site ID to get the name of
 * @param {Object} categoryTree Contains the names and IDs of sites
 * @param {String} locale
 * @returns {String}
 */
function getSiteName(siteId, categoryTree, locale) {
	const site = categoryTree.find((site) => siteId === site.id);

	return (
		site.descriptiveName_i18n?.[locale] || site.descriptiveName || site.name
	);
}

function CategoryMenu({locator, onItemClick = () => {}, sourceItems}) {
	return (
		<ClayDropDown.ItemList>
			{sourceItems.map((item) => (
				<ClayDropDown.Item
					key={item[locator.value]}
					onClick={() => onItemClick(item)}
				>
					<div className="autofit-col">
						<span className="list-group-text">
							{`${item[locator.label]} (ID: ${
								item[locator.value]
							})`}
						</span>

						{item.description && (
							<span className="list-group-subtext">
								{item.description}
							</span>
						)}
					</div>
				</ClayDropDown.Item>
			))}
		</ClayDropDown.ItemList>
	);
}

/**
 * CategorySelectorInput uses APIs in order for the user to quickly find
 * asset categories. Type into the input for the autocomplete dropdown
 * to appear, or click on the 'select' button to open a modal with
 * the category tree.
 *
 * The category selector is by default a text input. When formatted into the
 * 'View Element JSON', the category is shown as a single string ID. If
 * the typeOption 'format' is set to 'array', the category selector input is a
 * multiselect, in which the IDs are formatted as an array for the 'View Element
 * JSON'.
 */
function CategorySelectorInput({
	disabled,
	id,
	label,
	multiple = false,
	name,
	setFieldTouched,
	setFieldValue,
	value,
}) {
	const {locale} = useContext(ThemeContext);

	const [categoryTree, setCategoryTree] = useState([]);

	const [inputValue, setInputValue] = useState(
		multiple ? '' : value[0]?.name || ''
	);
	const [matchingCategories, setMatchingCategories] = useState([]);
	const [
		autocompleteDropdownActive,
		setAutocompleteDropdownActive,
	] = useState(false);

	const _handleSetMatchingCategories = (inputValue, categoryTree, locale) => {
		const categories = [];
		const vocabularyIds = categoryTree
			.map((site) => site.children.map((vocabulary) => vocabulary.id))
			.flat();

		fetch(`/api/jsonws/invoke`, {
			body: new URLSearchParams({
				cmd: JSON.stringify({
					'/assetcategory/search': {
						end: 20,
						groupIds: categoryTree.map((site) => site.id),
						name: `%${inputValue.toLowerCase()}%`,
						start: 0,
						vocabularyIds,
					},
				}),
				p_auth: Liferay.authToken,
			}),
			headers: new Headers({
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type':
					'application/x-www-form-urlencoded;charset=UTF-8',
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				responseContent.forEach(({categoryId, groupId, name, path}) => {

					// Collects id, name, and a description of any categories
					// that match the query. Description has the names of the
					// category's site and vocabulary.

					categories.push({
						description: `${getSiteName(
							groupId,
							categoryTree,
							locale
						)} - ${path.split(' > ')?.[0]}`, // First word in path is vocabulary name
						id: categoryId,
						name,
					});
				});
			})
			.catch(() => {}) // Catches response.json() parsing error from 404
			.finally(() => {
				if (typeof toNumber(inputValue) === 'number') {

					// If inputValue is a number, check if it is associated to a
					// category and add it to the list of `matchingCategories`.

					_handleFetchCategoryFromID(
						categories,
						inputValue,
						categoryTree,
						locale
					);
				}
				else {
					setMatchingCategories(categories);
				}
			});
	};

	const [handleSetMatchingCategoriesDebounced] = useDebounceCallback(
		_handleSetMatchingCategories,
		300
	);

	const _handleFetchCategoryFromID = (
		categories = [],
		currentInputValue,
		categoryTree,
		locale
	) => {
		fetch(FETCH_URLS.getCategory(currentInputValue), {
			headers: DEFAULT_HEADERS,
			method: 'GET',
		})
			.then((response) => response.json())
			.then(({id, name, parentTaxonomyVocabulary, siteId}) => {
				if (id) {

					// A match by category ID will be included at the very
					// beginning of the list. Description has the names of
					// the category's site and vocabulary.

					setMatchingCategories([
						{
							description: `${getSiteName(
								JSON.stringify(siteId), // Site ID is a number
								categoryTree,
								locale
							)} - ${parentTaxonomyVocabulary.name}`,
							id,
							name,
						},
						...categories,
					]);
				}
				else {
					setMatchingCategories(categories);
				}
			})
			.catch(() => {
				setMatchingCategories(categories);
			});
	};

	const _handleFieldValueChange = (newFieldValue) => {
		setFieldValue(name, newFieldValue);

		if (!multiple) {
			setInputValue(newFieldValue[0]?.name);
		}
	};

	const _handleFocus = () => {
		setAutocompleteDropdownActive(true);
	};

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	const _handleSingleInputValueBlur = () => {
		setFieldTouched(name);
	};

	const _handleSingleInputValueChange = (event) => {
		const newValue = event.target.value;

		setInputValue(newValue);

		if (newValue.trim()) {
			handleSetMatchingCategoriesDebounced(
				newValue.trim(),
				categoryTree,
				locale
			);

			setFieldValue(name, [{id: newValue.trim(), name: newValue.trim()}]);
		}
		else {
			setMatchingCategories([]);
			setFieldValue(name, []);
		}
	};

	const _handleSingleItemChange = (item) => {
		setFieldValue(name, [{id: item.id, name: item.name}]);
		setInputValue(item.name);
		setAutocompleteDropdownActive(false);
	};

	const _handleMultiInputValueBlur = () => {
		setFieldTouched(name);
	};

	const _handleMultiInputValueChange = (newValue) => {
		setInputValue(newValue);

		if (newValue.trim()) {
			handleSetMatchingCategoriesDebounced(
				newValue.trim(),
				categoryTree,
				locale
			);
		}
		else {
			setMatchingCategories([]);
		}
	};

	const _handleMultiItemsChange = (items) => {
		const uniqueArray = [];

		// Give preference to keeping the duplicate that has a name,
		// determined from selector.

		removeDuplicates(items, 'id').map(({id, name}) => {
			if (name === id) {
				const uniqueItem = items.find(
					(item) => id === item.id && item.name !== id
				);

				uniqueArray.push({id, name: uniqueItem?.name || name});
			}
			else {
				uniqueArray.push({id, name});
			}
		});

		setFieldValue(name, uniqueArray);
	};

	useEffect(() => {

		// Fetches the site information that the user has access to + all
		// vocabularies associated with those sites. This information will
		// be the start of the category tree, in which the children of the
		// vocabulary get added on as the tree gets expanded (in modal).

		fetchData(FETCH_URLS.getUserAccount()).then((userData) => {
			const tree = [];

			userData.siteBriefs.forEach((site, siteIndex) => {
				fetchData(FETCH_URLS.getVocabularies(site.id))
					.then((vocabularies) => {
						tree[siteIndex] = {
							...site,
							children: vocabularies.items.map(
								({id, name, numberOfTaxonomyCategories}) => ({

									// In certain responses, 'id' is a number,
									// so JSON.stringify for consistency.

									id: JSON.stringify(id),
									name,
									numberOfTaxonomyCategories,
								})
							),
							id: JSON.stringify(site.id),
						};
					})
					.catch(() => {
						tree[siteIndex] = {
							...site,
							id: JSON.stringify(site.id),
						};
					});
			});

			setCategoryTree(tree);
		});
	}, []);

	return (
		<ClayInput.Group small>
			<ClayInput.GroupItem>
				{multiple ? (
					<ClayMultiSelect
						aria-label={label}
						disabled={disabled}
						id={id}
						items={value || []}
						locator={{label: 'name', value: 'id'}}
						menuRenderer={CategoryMenu}
						onBlur={_handleMultiInputValueBlur}
						onChange={_handleMultiInputValueChange}
						onItemsChange={_handleMultiItemsChange}
						onKeyDown={_handleKeyDown}
						sourceItems={matchingCategories}
						value={inputValue}
					/>
				) : (
					<ClayAutocomplete>
						<ClayAutocomplete.Input
							aria-label={label}
							disabled={disabled}
							id={id}
							onBlur={_handleSingleInputValueBlur}
							onChange={_handleSingleInputValueChange}
							onFocus={_handleFocus}
							onKeyDown={_handleKeyDown}
							value={inputValue}
						/>

						<ClayAutocomplete.DropDown
							active={
								autocompleteDropdownActive &&
								!!matchingCategories.length
							}
							closeOnClickOutside
							onSetActive={setAutocompleteDropdownActive}
						>
							<CategoryMenu
								locator={{label: 'name', value: 'id'}}
								onItemClick={_handleSingleItemChange}
								sourceItems={matchingCategories}
							/>
						</ClayAutocomplete.DropDown>
					</ClayAutocomplete>
				)}
			</ClayInput.GroupItem>

			<ClayInput.GroupItem shrink>
				<CategorySelectorModal
					multiple={multiple}
					onChangeTree={setCategoryTree}
					onChangeValue={_handleFieldValueChange}
					tree={categoryTree}
					value={value}
				>
					<ClayButton
						aria-label={Liferay.Language.get('select')}
						disabled={disabled}
						displayType="secondary"
						small
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</CategorySelectorModal>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default CategorySelectorInput;
