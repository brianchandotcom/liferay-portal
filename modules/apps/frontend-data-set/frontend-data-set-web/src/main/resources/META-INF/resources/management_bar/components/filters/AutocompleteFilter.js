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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayRadio, ClayToggle} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {getValueFromItem, isValuesArrayChanged} from '../../../utils/index';
import {logError} from '../../../utils/logError';

const DEFAULT_PAGE_SIZE = 10;

function fetchData(apiURL, searchParam, currentPage = 1) {
	const url = new URL(apiURL, themeDisplay.getPortalURL());

	url.searchParams.append('page', currentPage);
	url.searchParams.append('pageSize', DEFAULT_PAGE_SIZE);

	if (searchParam) {
		url.searchParams.append('search', encodeURIComponent(searchParam));
	}

	return fetch(url, {
		headers: {
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
		},
	}).then((response) => response.json());
}

function Item(props) {
	const Input = props.selectionType === 'single' ? ClayRadio : ClayCheckbox;

	return (
		<li>
			<Input
				checked={props.selected}
				label={props.label}
				onChange={props.onChange}
				value={props.value}
			/>
		</li>
	);
}

Item.propTypes = {
	label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
	onChange: PropTypes.func.isRequired,
	selected: PropTypes.bool.isRequired,
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
};

function composeMultipleValuesOdataString(key, values, exclude) {
	return `${key}/any(x:${values
		.map(
			(value) =>
				`(x ${exclude ? 'ne' : 'eq'} ${
					typeof value === 'string' ? `'${value}'` : value
				})`
		)
		.join(exclude ? ' and ' : ' or ')})`;
}

function composeSingleValuesOdataString(key, value, exclude) {
	return `${key} ${exclude ? 'ne' : 'eq'} ${
		typeof value === 'string' ? `'${value}'` : value
	}`;
}

const getSelectedItemsLabel = ({selectedData}) => {
	const {exclude, itemsValues} = selectedData;

	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		itemsValues.map((itemsValue) => itemsValue.label).join(', ')
	);
};

const getOdataString = ({id, selectedData, selectionType}) => {
	const {exclude, itemsValues} = selectedData;

	if (itemsValues?.length) {
		const values = itemsValues.map((item) => item.value);

		return selectionType === 'multiple'
			? composeMultipleValuesOdataString(id, values, exclude)
			: composeSingleValuesOdataString(id, values[0], exclude);
	}

	return null;
};
function AutocompleteFilter({
	apiURL,
	id,
	inputPlaceholder,
	itemKey,
	itemLabel: itemLabelProp,
	selectedData,
	selectionType,
	setFilter,
}) {
	const [query, setQuery] = useState('');
	const [search, setSearch] = useState('');
	const [selectedItems, setSelectedItems] = useState(
		selectedData?.itemsValues || []
	);
	const [items, setItems] = useState(null);
	const [loading, setLoading] = useState(false);
	const [currentPage, setCurrentPage] = useState(1);
	const [total, setTotal] = useState(0);
	const scrollingAreaRef = useRef(null);
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoaderRef = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);
	const [exclude, setExclude] = useState(!!selectedData?.exclude);

	const loaderVisible = items && items.length < total;

	useEffect(() => {
		setSelectedItems(selectedData?.itemsValues || []);
	}, [selectedData]);

	useEffect(() => {
		if (query === search) {
			return;
		}
		setCurrentPage(1);
		setSearch(query);
	}, [query, search]);

	const isMounted = useIsMounted();

	useEffect(() => {
		setLoading(true);
		fetchData(apiURL, search, currentPage)
			.then((data) => {
				if (!isMounted()) {
					return;
				}

				setLoading(false);
				if (currentPage === 1) {
					setItems(data.items);
				}
				else {
					setItems((items) => [...items, ...data.items]);
				}
				setTotal(data.totalCount);
			})
			.catch((error) => {
				logError(error);

				if (isMounted()) {
					setLoading(false);
				}
			});
	}, [currentPage, isMounted, search, apiURL]);

	const setScrollingArea = useCallback((node) => {
		scrollingAreaRef.current = node;
		setScrollingAreaRendered(true);
	}, []);

	const setInfiniteLoader = useCallback((node) => {
		infiniteLoaderRef.current = node;
		setInfiniteLoaderRendered(true);
	}, []);

	const setObserver = useCallback(() => {
		if (
			!scrollingAreaRef.current ||
			!infiniteLoaderRef.current ||
			!IntersectionObserver
		) {
			return;
		}

		const options = {
			root: scrollingAreaRef.current,
			rootMargin: '0px',
			threshold: 1.0,
		};

		const observer = new IntersectionObserver((entries) => {
			if (entries[0].intersectionRatio <= 0) {
				return;
			}
			setCurrentPage((page) => page + 1);
		}, options);

		observer.observe(infiniteLoaderRef.current);
	}, []);

	useEffect(() => {
		if (scrollingAreaRendered && infiniteLoaderRendered && loaderVisible) {
			setObserver();
		}
	}, [
		scrollingAreaRendered,
		infiniteLoaderRendered,
		loaderVisible,
		setObserver,
	]);

	let actionType = 'edit';

	if (selectedData?.itemsValues && !selectedItems.length) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!selectedData && selectedItems.length) ||
		(selectedData &&
			isValuesArrayChanged(selectedData.itemsValues, selectedItems)) ||
		(selectedData &&
			selectedItems.length &&
			selectedData.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption>
				<ClayAutocomplete>
					<ClayAutocomplete.Input
						onChange={(event) => setQuery(event.target.value)}
						placeholder={inputPlaceholder}
					/>

					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>

				{selectedItems.length ? (
					<div className="mt-2 selected-elements-wrapper">
						{selectedItems.map((selectedItem) => (
							<ClayLabel
								closeButtonProps={{
									onClick: () =>
										setSelectedItems((items) =>
											items.filter(
												(item) =>
													item.value !==
													selectedItem.value
											)
										),
								}}
								key={selectedItem.value}
							>
								{selectedItem.label}
							</ClayLabel>
						))}
					</div>
				) : null}
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption className="py-0">
				<div className="row">
					<div className="col">
						<label htmlFor={`autocomplete-exclude-${id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>

					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="form-group">
					{items && !!items.length ? (
						<ul
							className="inline-scroller mb-n2 mx-n2 px-2"
							ref={setScrollingArea}
						>
							{items.map((item) => {
								const itemValue = item[itemKey];
								const itemLabel = getValueFromItem(
									item,
									itemLabelProp
								);
								const newValue = {
									label: itemLabel,
									value: itemValue,
								};

								return (
									<Item
										key={itemValue}
										label={itemLabel}
										onChange={() => {
											setSelectedItems(
												selectedItems.find(
													(element) =>
														element.value ===
														itemValue
												)
													? selectedItems.filter(
															(element) =>
																element.value !==
																itemValue
													  )
													: selectionType ===
													  'multiple'
													? [
															...selectedItems,
															newValue,
													  ]
													: [newValue]
											);
										}}
										selected={Boolean(
											selectedItems.find(
												(element) =>
													element.value === itemValue
											)
										)}
										selectionType={selectionType}
										value={itemValue}
									/>
								);
							})}

							{loaderVisible && (
								<ClayLoadingIndicator
									ref={setInfiniteLoader}
									small
								/>
							)}
						</ul>
					) : (
						<div className="mt-2 p-2 text-muted">
							{Liferay.Language.get('no-items-were-found')}
						</div>
					)}
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							setFilter({active: false, id});
						}
						else {
							const newSelectedData = {
								exclude,
								itemsValues: selectedItems,
							};

							setFilter({
								active: true,
								id,
								odataFilterString: getOdataString({
									id,
									selectedData: newSelectedData,
									selectionType,
								}),
								selectedData: newSelectedData,
								selectedItemsLabel: getSelectedItemsLabel({
									items,
									selectedData: newSelectedData,
								}),
							});
						}
					}}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}

					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}

					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

AutocompleteFilter.propTypes = {
	apiURL: PropTypes.string.isRequired,
	id: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemKey: PropTypes.string.isRequired,
	itemLabel: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
		.isRequired,
	selectedData: PropTypes.shape({
		exclude: PropTypes.bool,
		items: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.number,
				]),
				value: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.number,
				]),
			})
		),
	}),
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
};

AutocompleteFilter.defaultProps = {
	selectionType: 'multiple',
};

export {getSelectedItemsLabel, getOdataString};
export default AutocompleteFilter;
