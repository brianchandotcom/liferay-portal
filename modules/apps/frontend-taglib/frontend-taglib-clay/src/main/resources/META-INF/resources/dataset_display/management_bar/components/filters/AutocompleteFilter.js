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
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {fetchParams, getValueFromItem} from '../../../utilities/index';
import {logError} from '../../../utilities/logError';

const DEFAULT_PAGE_SIZE = 10;

function fetchData(apiURL, searchParam, currentPage = 1) {
	return fetch(
		`${apiURL}?page=${currentPage}&pageSize=${DEFAULT_PAGE_SIZE}${
			searchParam ? `&search=${encodeURIComponent(searchParam)}` : ''
		}`,
		{
			...fetchParams,
			method: 'GET',
		}
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

function getOdataString(value, key, selectionType) {
	if (!value || !value.length) {
		return null;
	}

	return selectionType === 'multiple'
		? `${key}/any(x:${value
				.map(
					(v) =>
						`(x eq ${
							typeof v.value === 'string'
								? `'${v.value}'`
								: v.value
						})`
				)
				.join(' or ')})`
		: `${key} eq ${
				typeof value[0].value === 'string'
					? `'${value[0].value}'`
					: value[0].value
		  }`;
}

function AutocompleteFilter(props) {
	const [query, setQuery] = useState('');
	const [search, setSearch] = useState('');
	const [selectedItems, setSelectedItems] = useState(props.value || []);
	const [items, updateItems] = useState(null);
	const [loading, setLoading] = useState(false);
	const [currentPage, setCurrentPage] = useState(1);
	const [total, updateTotal] = useState(0);
	const scrollingArea = useRef(null);
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoader = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);

	const loaderVisible = items && items.length < total;

	useEffect(() => {
		setSelectedItems(props.value || []);
	}, [props.value]);

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
		fetchData(props.apiURL, search, currentPage)
			.then((data) => {
				if (!isMounted()) {
					return;
				}

				setLoading(false);

				if (currentPage === 1) {
					updateItems(data.items);
				}
				else {
					updateItems((items) => [...items, ...data.items]);
				}

				updateTotal(data.total);
			})
			.catch((error) => {
				logError(error);

				if (isMounted()) {
					setLoading(false);
				}
			});
	}, [currentPage, isMounted, props.apiURL, search]);

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
		<div className="form-group">
			<ClayAutocomplete className="mb-2">
				<ClayAutocomplete.Input
					onChange={(event) => setQuery(event.target.value)}
				/>
				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>
			<div className="selected-elements-wrapper">
				{selectedItems.map((selectedItem) => {
					return (
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
					);
				})}
			</div>
			{items && items.length ? (
				<ul
					className="inline-scroller mt-2 mx-n3 px-3"
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
											(el) => el.value === itemValue
										)
											? selectedItems.filter(
													(el) =>
														el.value !== itemValue
											  )
											: props.selectionType === 'multiple'
											? [...selectedItems, newValue]
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
						<ClayLoadingIndicator ref={setInfiniteLoader} small />
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
					disabled={!isValueChanged(props.value || [], selectedItems)}
					onClick={() =>
						props.actions.updateFilterState(
							props.id,
							selectedItems.length ? selectedItems : null,
							getOdataString(
								selectedItems,
								props.id,
								props.selectionType
							)
						)
					}
				>
					{props.value
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</div>
	);
}

AutocompleteFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	invisible: PropTypes.bool,
	itemKey: PropTypes.string.isRequired,
	itemLabel: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
		.isRequired,
	label: PropTypes.string.isRequired,
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
	type: PropTypes.oneOf(['autocomplete']).isRequired,
	value: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
			label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
};

AutocompleteFilter.defaultProps = {
	selectionType: 'multiple',
};

export default AutocompleteFilter;
