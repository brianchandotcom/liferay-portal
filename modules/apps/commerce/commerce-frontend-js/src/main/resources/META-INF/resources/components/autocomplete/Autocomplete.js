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
import ClayDropDown from '@clayui/drop-down';
import {FocusScope} from '@clayui/shared';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';
import {createPortal} from 'react-dom';

import {debouncePromise} from '../../utilities/debounce';
import {AUTOCOMPLETE_VALUE_UPDATED} from '../../utilities/eventsDefinitions';
import {useLiferayModule} from '../../utilities/hooks';
import {getData, getValueFromItem} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';
import InfiniteScroller from '../infinite_scroller/InfiniteScroller';

function Autocomplete({onItemsUpdated, onValueUpdated, ...props}) {
	const [query, setQuery] = useState(props.initialLabel || '');
	const [initialised, setInitialised] = useState(
		Boolean(props.customViewModuleUrl || props.customView)
	);
	const [active, setActive] = useState(false);
	const [selectedItem, updateSelectedItem] = useState(props.initialValue);
	const [items, updateItems] = useState(null);
	const [loading, setLoading] = useState(false);
	const [totalCount, updateTotalCount] = useState(null);
	const [lastPage, updateLastPage] = useState(null);
	const [page, updatePage] = useState(1);
	const [pageSize, updatePageSize] = useState(props.pageSize);
	const node = useRef();
	const dropdownNode = useRef();
	const inputNode = useRef();
	const FetchedCustomView = useLiferayModule(props.customViewModuleUrl);
	const isMounted = useIsMounted();
	const [debouncedGetItems, updateDebouncedGetItems] = useState(null);

	useEffect(() => {
		updateDebouncedGetItems(() =>
			debouncePromise(getData, props.fetchDataDebounce)
		);
	}, [props.fetchDataDebounce]);

	const currentValue = selectedItem
		? getValueFromItem(selectedItem, props.itemsKey)
		: null;
	const currentLabel = selectedItem
		? getValueFromItem(selectedItem, props.itemsLabel)
		: null;

	const CustomView = props.customView || FetchedCustomView;

	useEffect(() => {
		if (items && items.length === 1 && props.autofill) {
			const firstItem = items[0];
			updateSelectedItem(firstItem);
		}
	}, [items, props.autofill, props.itemsKey, props.itemsLabel]);

	useEffect(() => {
		const value =
			selectedItem && getValueFromItem(selectedItem, props.itemsKey);

		if (props.id) {
			Liferay.fire(AUTOCOMPLETE_VALUE_UPDATED, {
				id: props.id,
				itemData: selectedItem,
				value,
			});
		}

		if (onValueUpdated) {
			onValueUpdated(value, selectedItem);
		}
	}, [selectedItem, props.id, props.itemsKey, onValueUpdated]);

	useEffect(() => {
		if (query) {
			setInitialised(true);
		}

		if (props.infiniteScrollMode) {
			updateItems(null);
		}

		updatePage(1);
		updateTotalCount(null);
		updateLastPage(null);
	}, [props.infiniteScrollMode, query]);

	useEffect(() => {
		if (initialised && debouncedGetItems && !props.disabled) {
			setLoading(true);

			debouncedGetItems(props.apiUrl, query, page, pageSize)
				.then((jsonResponse) => {
					if (!isMounted()) {
						return;
					}

					updateItems((prevItems) => {
						if (
							props.infiniteScrollMode &&
							prevItems?.length &&
							page > 1
						) {
							return [...prevItems, ...jsonResponse.items];
						}

						return jsonResponse.items;
					});

					updateTotalCount(jsonResponse.totalCount);
					updateLastPage(jsonResponse.lastPage);
					setLoading(false);

					if (!query) {
						return;
					}
					const found = jsonResponse.items.find(
						(item) =>
							getValueFromItem(item, props.itemsLabel) === query
					);
					if (found) {
						updateSelectedItem(found);
					}
				})
				.catch(() => {
					showErrorNotification();
					setLoading(false);
				});
		}
	}, [
		debouncedGetItems,
		initialised,
		isMounted,
		query,
		page,
		pageSize,
		props.disabled,
		props.infiniteScrollMode,
		props.apiUrl,
		props.itemsLabel,
		props.showErrorNotification,
	]);

	useEffect(() => {
		if (onItemsUpdated) {
			onItemsUpdated(items);
		}
	}, [items, onItemsUpdated]);

	useEffect(() => {
		function handleClick(e) {
			if (
				node.current.contains(e.target) ||
				(dropdownNode.current &&
					dropdownNode.current.contains(e.target))
			) {
				return;
			}

			setActive(false);
		}
		if (active) {
			document.addEventListener('mousedown', handleClick);
		}

		return () => {
			document.removeEventListener('mousedown', handleClick);
		};
	}, [active]);

	let results;

	if (CustomView) {
		results = (
			<CustomView
				items={items}
				lastPage={lastPage}
				loading={loading}
				page={page}
				pageSize={pageSize}
				totalCount={totalCount}
				updatePage={updatePage}
				updatePageSize={updatePageSize}
				updateSelectedItem={updateSelectedItem}
			/>
		);
	}
	else {
		results = (
			<ClayDropDown.ItemList className="mb-0">
				{items && items.length === 0 && (
					<ClayDropDown.Item className="disabled">
						{Liferay.Language.get('no-items-were-found')}
					</ClayDropDown.Item>
				)}
				{items &&
					items.length > 0 &&
					items.map((item) => (
						<ClayAutocomplete.Item
							key={String(item[props.itemsKey])}
							onClick={() => {
								updateSelectedItem(item);
								setActive(false);
							}}
							value={String(
								getValueFromItem(item, props.itemsLabel)
							)}
						/>
					))}
			</ClayDropDown.ItemList>
		);
	}

	const wrappedResults =
		props.infiniteScrollMode && CustomView ? (
			<InfiniteScroller
				onBottomTouched={() => {
					if (!loading) {
						updatePage((currentPage) =>
							currentPage < lastPage
								? currentPage + 1
								: currentPage
						);
					}
				}}
				scrollCompleted={!items || items.length === totalCount}
			>
				{results}
			</InfiniteScroller>
		) : (
			results
		);

	return (
		<>
			<FocusScope>
				<ClayAutocomplete className={props.inputClass} ref={node}>
					<input
						id={props.inputId || props.inputName}
						name={props.inputName}
						type="hidden"
						value={currentValue || ''}
					/>
					<ClayAutocomplete.Input
						onChange={(event) => {
							updateSelectedItem(null);
							updatePage(1);
							setQuery(event.target.value);
						}}
						onFocus={(_e) => {
							setActive(true);
							setInitialised(true);
						}}
						onKeyUp={(e) => {
							setActive(e.keyCode !== 27);
						}}
						placeholder={props.inputPlaceholder}
						ref={inputNode}
						required={props.required || false}
						value={currentLabel || query}
					/>
					{!CustomView && !props.disabled && (
						<ClayAutocomplete.DropDown
							active={
								active && ((items && page === 1) || page > 1)
							}
						>
							<div
								className="autocomplete-items"
								ref={dropdownNode}
							>
								{wrappedResults}
							</div>
						</ClayAutocomplete.DropDown>
					)}
					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</FocusScope>
			{CustomView &&
				!props.disabled &&
				(props.contentWrapperRef
					? props.contentWrapperRef.current &&
					  createPortal(
							wrappedResults,
							props.contentWrapperRef.current
					  )
					: wrappedResults)}
		</>
	);
}

Autocomplete.propTypes = {
	apiUrl: PropTypes.string.isRequired,
	autofill: PropTypes.bool,
	contentWrapperRef: PropTypes.object,
	customView: PropTypes.func,
	customViewModuleUrl: PropTypes.string,
	disabled: PropTypes.bool,
	fetchDataDebounce: PropTypes.number,
	id: PropTypes.string,
	infiniteScrollMode: PropTypes.bool,
	initialLabel: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	initialValue: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	inputClass: PropTypes.string,
	inputId: PropTypes.string,
	inputName: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemsKey: PropTypes.string.isRequired,
	itemsLabel: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]).isRequired,
	loadingView: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
	onItemsUpdated: PropTypes.func,
	onValueUpdated: PropTypes.func,
	required: PropTypes.bool,
};

Autocomplete.defaultProps = {
	autofill: false,
	disabled: false,
	fetchDataDebounce: 200,
	infiniteScrollMode: false,
	initialLabel: '',
	initialValue: '',
	inputPlaceholder: Liferay.Language.get('type-here'),
	pageSize: 10,
};

export default Autocomplete;
