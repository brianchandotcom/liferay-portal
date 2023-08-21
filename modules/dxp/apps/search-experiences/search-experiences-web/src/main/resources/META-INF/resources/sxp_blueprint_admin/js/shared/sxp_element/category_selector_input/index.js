/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import useDebounceCallback from '../../../hooks/useDebounceCallback';
import {DEFAULT_HEADERS} from '../../../utils/fetch/fetch_data';
import removeDuplicates from '../../../utils/functions/remove_duplicates';
import toNumber from '../../../utils/functions/to_number';
import CategorySelectorModal from './CategorySelectorModal';

export const FETCH_URLS = {
	getCategories: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${id}/taxonomy-categories`,
	getCategory: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-categories/${id}/`,
	getSites: () => '/o/headless-admin-user/v1.0/my-user-account/sites',
	getSubCategories: (id) =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-categories/${id}/taxonomy-categories`,
	getVocabularies: (id) =>
		`/o/headless-admin-taxonomy/v1.0/sites/${id}/taxonomy-vocabularies`,
};

/**
 * Returns the name of the site by matching the siteId to the site inside
 * categoryTree.
 * @param {String} siteId Site ID to get the name of
 * @param {Object} categoryTree Contains the names and IDs of sites
 * @returns {String}
 */
function getSiteName(siteId, categoryTree) {
	const site = categoryTree.find((site) => siteId === site.id);

	return site.descriptiveName || site.name;
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
							{item[locator.label]}
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
	const [categoryTree, setCategoryTree] = useState([]);

	const [inputValue, setInputValue] = useState(
		multiple ? '' : value.label || value.toString() || ''
	);
	const [matchingCategories, setMatchingCategories] = useState([]);
	const [
		autocompleteDropdownActive,
		setAutocompleteDropdownActive,
	] = useState(false);

	const _handleSetMatchingCategories = (inputValue, categoryTree) => {
		const categories = [];
		const vocabularyIds = categoryTree
			.map(
				(site) =>
					site.children?.map((vocabulary) => vocabulary.id) || []
			)
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
						description: `${getSiteName(groupId, categoryTree)} - ${
							path.split(' > ')?.[0]
						}`, // First word in path is vocabulary name
						label: `${name} (ID: ${categoryId})`,
						value: categoryId,
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
						categoryTree
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

	const _handleBlur = () => {
		setFieldTouched(name);
	};

	const _handleFetchCategoryFromID = (
		categories = [],
		currentInputValue,
		categoryTree
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
								categoryTree
							)} - ${parentTaxonomyVocabulary.name}`,
							label: `${name} (ID: ${id})`,
							value: id,
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
		if (!multiple) {
			_handleSingleItemChange(newFieldValue);
		}
		else {
			setFieldValue(name, newFieldValue);
		}
	};

	const _handleFocus = () => {
		setAutocompleteDropdownActive(true);
	};

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();

			if (!multiple && !!matchingCategories[0]) {
				_handleSingleItemChange(matchingCategories[0]);
			}
		}
	};

	const _handleSingleInputValueChange = (event) => {
		const newValue = event.target.value;

		setInputValue(newValue);

		if (newValue.trim()) {
			handleSetMatchingCategoriesDebounced(newValue.trim(), categoryTree);

			const newValueNumber = toNumber(newValue);

			setFieldValue(
				name,
				typeof newValueNumber === 'number' ? newValueNumber : ''
			);
		}
		else {
			setMatchingCategories([]);
			setFieldValue(name, '');
		}
	};

	const _handleSingleItemChange = (item) => {
		setFieldValue(name, {label: item.label, value: item.value});
		setInputValue(item.label);
		setMatchingCategories([]);
	};

	const _handleMultiInputValueChange = (newValue) => {
		setInputValue(newValue);

		if (newValue.trim()) {
			handleSetMatchingCategoriesDebounced(newValue.trim(), categoryTree);
		}
		else {
			setMatchingCategories([]);
		}
	};

	const _handleMultiItemsChange = (items) => {
		const uniqueArray = [];

		removeDuplicates(items, 'value').map(({label, value}) => {
			if (typeof toNumber(value) === 'number') {
				if (value === label) {

					// Case: ID is manually input as a number but does not have
					// a proper name. Attempt to search for a proper name from
					// original 'items' and from matching categories. Otherwise,
					// save as is.

					const uniqueItem =
						items.find(
							(item) =>
								value === item.value && value !== item.label
						) ||
						matchingCategories.find((item) => item.value === value);

					uniqueArray.push({
						label: uniqueItem?.label || label,
						value,
					});
				}
				else {

					// Case: ID was chosen through selector or autocomplete list
					// and already has a proper name.

					uniqueArray.push({label, value});
				}
			}
			else {

				// Case: ID is manually input and is not a valid number. Add the
				// first item from matchingCategories instead (if available
				// and not already added).

				if (
					!!matchingCategories[0] &&
					!uniqueArray.some(
						(item) => matchingCategories[0].value === item.value
					)
				) {
					uniqueArray.push({
						label: matchingCategories[0].label,
						value: matchingCategories[0].value,
					});
				}
			}
		});

		setFieldValue(name, uniqueArray);
	};

	useEffect(() => {

		// Fetches the site information that the user has access to + all
		// vocabularies associated with those sites. This information will
		// be the start of the category tree, in which the children of the
		// vocabulary get added on as the tree gets expanded (in modal).

		fetch(FETCH_URLS.getSites(), {
			headers: DEFAULT_HEADERS,
			method: 'GET',
		})
			.then((response) => response.json())
			.then(({items}) => {
				const itemsWithGlobalSite = items.some(
					({id}) =>
						id.toString() ===
						Liferay.ThemeDisplay.getCompanyGroupId().toString()
				)
					? items
					: [
							{
								descriptiveName: Liferay.Language.get('global'),
								id: Number(
									Liferay.ThemeDisplay.getCompanyGroupId()
								),
							},
							...items,
					  ];

				const tree = [];

				itemsWithGlobalSite.forEach((site, siteIndex) => {
					fetch(FETCH_URLS.getVocabularies(site.id), {
						headers: DEFAULT_HEADERS,
						method: 'GET',
					})
						.then((response) => response.json())
						.then((vocabularies) => {
							tree[siteIndex] = {
								children: vocabularies.items.map(
									({
										id,
										name,
										numberOfTaxonomyCategories,
									}) => ({

										// In certain responses, 'id' is a number,
										// so JSON.stringify for consistency.

										id: JSON.stringify(id),
										name,
										numberOfTaxonomyCategories,
									})
								),
								descriptiveName: site.descriptiveName,
								id: JSON.stringify(site.id),
								name: site.name,
							};
						})
						.catch(() => {
							tree[siteIndex] = {
								descriptiveName: site.descriptiveName,
								id: JSON.stringify(site.id),
								name: site.name,
							};
						});
				});

				setCategoryTree(tree);
			})
			.catch(() => setCategoryTree([]));
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
						loadingState={4}
						onBlur={_handleBlur}
						onChange={_handleMultiInputValueChange}
						onItemsChange={_handleMultiItemsChange}
						onKeyDown={_handleKeyDown}
						sourceItems={matchingCategories}
						value={inputValue}
					>
						{(item) => (
							<ClayMultiSelect.Item
								key={item.value}
								textValue={item.label}
							>
								<div className="autofit-col">
									<span className="list-group-text">
										{item.label}
									</span>

									{item.description && (
										<span className="list-group-subtext">
											{item.description}
										</span>
									)}
								</div>
							</ClayMultiSelect.Item>
						)}
					</ClayMultiSelect>
				) : (
					<ClayAutocomplete>
						<ClayAutocomplete.Input
							aria-label={label}
							disabled={disabled}
							id={id}
							onBlur={_handleBlur}
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
								locator={{label: 'label', value: 'value'}}
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
