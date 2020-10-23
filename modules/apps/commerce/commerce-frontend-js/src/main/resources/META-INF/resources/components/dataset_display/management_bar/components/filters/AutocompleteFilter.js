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
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {getValueFromItem} from '../../../../../utilities/index';
import {isValuesArrayChanged} from '../../../utilities/filters';

const DEFAULT_PAGE_SIZE = 10;

function fetchData(apiUrl, searchParam, currentPage = 1) {
	return fetch(
		`${apiUrl}${
			apiUrl.includes('?') ? '&' : '?'
		}page=${currentPage}&pageSize=${DEFAULT_PAGE_SIZE}${
			searchParam ? `&search=${encodeURIComponent(searchParam)}` : ''
		}`
	).then((response) => response.json());
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

const formatValue = (value, exclude) =>
	(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
	value.map((el) => el.label).join(', ');

function getOdataString(value, key, selectionType, exclude) {
	if (!value || !value.length) {
		return null;
	}

	return selectionType === 'multiple'
		? `${key}/any(x:${value
				.map(
					(v) =>
						`(x ${exclude ? 'ne' : 'eq'} ${
							typeof v.value === 'string'
								? `'${v.value}'`
								: v.value
						})`
				)
				.join(exclude ? ' and ' : ' or ')})`
		: `${key} ${exclude ? 'ne' : 'eq'} ${
				typeof value[0].value === 'string'
					? `'${value[0].value}'`
					: value[0].value
		  }`;
}
function AutocompleteFilter(props) {
	const [query, setQuery] = useState('');
	const [search, setSearch] = useState('');
	const [selectedItems, setSelectedItems] = useState(
		(props.value && props.value.items) || []
	);
	const [items, updateItems] = useState(null);
	const [loading, setLoading] = useState(false);
	const [currentPage, setCurrentPage] = useState(1);
	const [totalItems, updateTotalItems] = useState(0);
	const scrollingArea = useRef(null);
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoader = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);
	const [exclude, setExclude] = useState(
		(props.value && props.value.exclude) || false
	);

	const loaderVisible = items && items.length < totalItems;

	useEffect(() => {
		setSelectedItems((props.value && props.value.items) || []);
	}, [props.value]);

	useEffect(() => {
		if (query === search) {
			return;
		}
		setCurrentPage(1);
		setSearch(query);
	}, [query, search]);

	useEffect(() => {
		setLoading(true);
		fetchData(props.apiUrl, search, currentPage)
			.then((data) => {
				setLoading(false);
				if (currentPage === 1) {
					updateItems(data.items);
				}
				else {
					updateItems((items) => [...items, ...data.items]);
				}
				updateTotalItems(data.totalCount);
			})
			.catch((e) => {
				console.error(e);
				setLoading(false);
			});
	}, [currentPage, props.apiUrl, search]);

	const setScrollingArea = useCallback((node) => {
		scrollingArea.current = node;
		setScrollingAreaRendered(true);
	}, []);

	const setInfiniteLoader = useCallback((node) => {
		infiniteLoader.current = node;
		setInfiniteLoaderRendered(true);
	}, []);

	const setObserver = useCallback(() => {
		if (
			!scrollingArea.current ||
			!infiniteLoader.current ||
			!IntersectionObserver
		) {
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

	if (props.value && props.value.items && !selectedItems.length) {
		actionType = 'delete';
	}

	if (!props.value) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!props.value && selectedItems.length) ||
		(props.value &&
			isValuesArrayChanged(props.value.items, selectedItems)) ||
		(props.value && selectedItems.length && props.value.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption>
				<ClayAutocomplete>
					<ClayAutocomplete.Input
						onChange={(event) => setQuery(event.target.value)}
						placeholder={props.inputPlaceholder}
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
						<label htmlFor={`autocomplete-exclude-${props.id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>
					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${props.id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="form-group">
					{items && items.length ? (
						<ul
							className="inline-scroller mb-n2 mx-n2 px-2"
							ref={setScrollingArea}
						>
							{items.map((item) => {
								const itemValue = item[props.itemKey];
								const itemLabel = getValueFromItem(
									item,
									props.itemLabel
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
													(el) =>
														el.value === itemValue
												)
													? selectedItems.filter(
															(el) =>
																el.value !==
																itemValue
													  )
													: props.selectionType ===
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
												(el) => el.value === itemValue
											)
										)}
										selectionType={props.selectionType}
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
					onClick={() =>
						actionType !== 'delete'
							? props.actions.updateFilterState(
									props.id,
									{
										exclude,
										items: selectedItems,
									},
									formatValue(selectedItems, exclude),
									getOdataString(
										selectedItems,
										props.id,
										props.selectionType,
										exclude
									)
							  )
							: props.actions.updateFilterState(props.id)
					}
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
	id: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemKey: PropTypes.string.isRequired,
	itemLabel: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
		.isRequired,
	label: PropTypes.string.isRequired,
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
	type: PropTypes.oneOf(['autocomplete']).isRequired,
	value: PropTypes.shape({
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
};

AutocompleteFilter.defaultProps = {
	selectionType: 'multiple',
};

export default AutocompleteFilter;
