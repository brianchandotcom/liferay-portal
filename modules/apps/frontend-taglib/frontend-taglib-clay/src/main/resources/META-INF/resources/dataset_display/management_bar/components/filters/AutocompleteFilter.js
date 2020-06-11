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

import ClayButton from '@clayui/button';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayMultiSelect from '@clayui/multi-select';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {getValueFromItem} from '../../../utilities/index';
import {logError} from '../../../utilities/logError';
import {showErrorNotification} from '../../../utilities/notifications';
import getAppContext from '../Context';

const DEFAULT_PAGE_SIZE = 10;

function fetchData(apiUrl, searchParam, currentPage = 1) {
	return fetch(
		`${apiUrl}?page=${currentPage}&pageSize=${DEFAULT_PAGE_SIZE}${
			searchParam ? `&search=${encodeURIComponent(searchParam)}` : ''
		}`,
		{
			method: 'GET',
		}
	).then((response) => response.json());
}

function Item({label, onChange, selected, selectionType, value}) {
	const Input = selectionType === 'single' ? ClayRadio : ClayCheckbox;

	return (
		<li>
			<Input
				checked={selected}
				label={label}
				onChange={onChange}
				value={value}
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

function AutocompleteFilter({
	apiUrl,
	id,
	inputPlaceholder,
	itemKey,
	itemLabel,
	panelType,
	selectionType,
	value,
}) {
	const {actions} = getAppContext();
	const [query, setQuery] = useState('');
	const [search, setSearch] = useState('');
	const [selectedItems, setSelectedItems] = useState(value || []);
	const [items, updateItems] = useState(null);
	const [currentPage, setCurrentPage] = useState(1);
	const [totalItems, updateTotalItems] = useState(0);
	const scrollingArea = useRef(null);
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoader = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);

	const loaderVisible = items?.length < totalItems;

	useEffect(() => {
		setSelectedItems(value || []);
	}, [value]);

	useEffect(() => {
		if (query === search) {
			return;
		}
		setCurrentPage(1);
		setSearch(query);
	}, [query, search]);

	useEffect(() => {
		fetchData(apiUrl, search, currentPage)
			.then((data) => {
				if (currentPage === 1) {
					updateItems(data.items);
				}
				else {
					updateItems((items) => [...items, ...data.items]);
				}
				updateTotalItems(data.totalCount);
			})
			.catch((error) => {
				showErrorNotification();
				logError(error);
			});
	}, [apiUrl, currentPage, search]);

	const setScrollingArea = useCallback((node) => {
		scrollingArea.current = node;
		setScrollingAreaRendered(true);
	}, []);

	const setInfiniteLoader = useCallback((node) => {
		infiniteLoader.current = node;
		setInfiniteLoaderRendered(true);
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

	const setObserver = useCallback(() => {
		if (!scrollingArea.current || !infiniteLoader.current) {
			return;
		}

		const options = {
			root: scrollingArea.current,
			rootMargin: '0px',
			threshold: 1.0,
		};

		const observer = new IntersectionObserver((entries) => {
			if (entries[0].intersectionRatio <= 0) {
				return;
			}
			setCurrentPage((page) => page + 1);
		}, options);

		observer.observe(infiniteLoader.current);
	}, []);

	function isValueChanged(prevValue = [], newValue = []) {
		if (prevValue.length !== newValue.length) {
			return true;
		}

		const prevValues = prevValue.map((element) => element.value).sort();
		const newValues = newValue.map((element) => element.value).sort();

		return prevValues.some((element, i) => element !== newValues[i]);
	}

	return (
		<ClayIconSpriteContext.Consumer>
			{(spritemap) => (
				<div className="form-group">
					{selectionType === 'multiple' ? (
						<ClayMultiSelect
							inputValue={query || ''}
							items={selectedItems}
							onChange={setQuery}
							onItemsChange={(event) => {
								if (event.length < selectedItems.length) {
									return setSelectedItems(event);
								}
								else {
									if (!items.length) {
										return;
									}

									const firstEl = {
										label: getValueFromItem(
											items[0],
											itemLabel
										),
										value: getValueFromItem(
											items[0],
											itemKey
										),
									};
									const added = selectedItems.find(
										(selectedItem) =>
											selectedItem.value === firstEl.value
									);

									return setSelectedItems(
										added
											? selectedItems.filter(
													(selectedItem) =>
														selectedItem.value !==
														firstEl.value
											  )
											: [...selectedItems, firstEl]
									);
								}
							}}
							placeholder={inputPlaceholder}
							spritemap={spritemap}
						/>
					) : (
						<input
							className="form-control"
							onChange={(event) => setQuery(event.target.value)}
							placeholder={inputPlaceholder}
							type="text"
							value={query}
						/>
					)}
					{items?.length ? (
						<ul
							className="inline-scroller mt-2 mx-n3 px-3"
							ref={setScrollingArea}
						>
							{items.map((item) => {
								const itemValue = item[itemKey];
								const itemLabel = getValueFromItem(
									item,
									itemLabel
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
					<div className="mt-3">
						<ClayButton
							className="btn-sm"
							disabled={
								!isValueChanged(value || [], selectedItems)
							}
							onClick={() =>
								actions.updateFilterValue(
									id,
									selectedItems.length ? selectedItems : null
								)
							}
						>
							{panelType === 'edit'
								? Liferay.Language.get('edit-filter')
								: Liferay.Language.get('add-filter')}
						</ClayButton>
					</div>
				</div>
			)}
		</ClayIconSpriteContext.Consumer>
	);
}

AutocompleteFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemKey: PropTypes.string.isRequired,
	itemLabel: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
		.isRequired,
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
	type: PropTypes.oneOf(['autocomplete']).isRequired,
	value: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
			label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
};

export default AutocompleteFilter;
