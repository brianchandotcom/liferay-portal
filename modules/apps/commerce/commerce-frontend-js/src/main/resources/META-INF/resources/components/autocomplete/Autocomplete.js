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

// import ClayIcon from '@clayui/icon';

import {FocusScope} from '@clayui/shared';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {debouncePromise} from '../../utilities/debounce';
import {AUTOCOMPLETE_VALUE_UPDATED} from '../../utilities/eventsDefinitions';
import {getData, getValueFromItem} from '../../utilities/index';
import {useLiferayModule} from '../../utilities/hooks';
import {showErrorNotification} from '../../utilities/notifications';

function Autocomplete({onItemsUpdated, onValueUpdated, ...props}) {
	const [query, setQuery] = useState(props.initialLabel || '');
	const [initialised, setInitialised] = useState(props.alwaysActive);
	const [debouncedGetItems, updateDebouncedGetItems] = useState(() =>
		debouncePromise(getData, props.fetchDataDebounce)
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

	useEffect(() => {
		updateDebouncedGetItems(() =>
			debouncePromise(getData, props.fetchDataDebounce)
		);
	}, [props.fetchDataDebounce]);

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
	}, [query]);

	useEffect(() => {
		if (initialised) {
			setLoading(true);

			debouncedGetItems(props.apiUrl, query, page, pageSize)
				.then((jsonResponse) => {
					if (props.infinityScrollMode) {
						updateItems((prevItems) => {
							return prevItems?.length && page > 1
								? [...prevItems, ...jsonResponse.items]
								: jsonResponse.items;
						});
					}
					else {
						updateItems(jsonResponse.items);
					}

					updateItems((prevItems) => {
						const items = jsonResponse.items;

						if (
							props.infinityScrollMode &&
							prevItems?.length &&
							page > 1
						) {
							items.push(...prevItems);
						}

						return items;
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
		query,
		page,
		pageSize,
		props.infinityScrollMode,
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

	const currentValue = selectedItem
		? getValueFromItem(selectedItem, props.itemsKey)
		: null;
	const currentLabel = selectedItem
		? getValueFromItem(selectedItem, props.itemsLabel)
		: null;

	const CustomView = props.customView || FetchedCustomView;

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
							setActive(Boolean(e.keyCode !== 27));
						}}
						placeholder={props.inputPlaceholder}
						ref={inputNode}
						required={props.required || false}
						value={currentLabel || query}
					/>
					{/* {props.inputIcon &&  (
						<ClayIcon className="input-icon" spritemap={props.spritemap} symbol={props.inputIcon} />
					)} */}
					{!CustomView && (
						<ClayAutocomplete.DropDown active={active && !loading}>
							<div
								className="autocomplete-items"
								ref={dropdownNode}
							>
								<ClayDropDown.ItemList className="mb-0">
									{items && items.length === 0 && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-items-were-found'
											)}
										</ClayDropDown.Item>
									)}
									{items &&
										items.length > 0 &&
										items.map((item) => (
											<ClayAutocomplete.Item
												key={String(
													item[props.itemsKey]
												)}
												onClick={() => {
													updateSelectedItem(item);
													setActive(false);
												}}
												value={String(
													getValueFromItem(
														item,
														props.itemsLabel
													)
												)}
											/>
										))}
								</ClayDropDown.ItemList>
							</div>
						</ClayAutocomplete.DropDown>
					)}
					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</FocusScope>
			{CustomView && (
				<CustomView
					items={items}
					lastPage={lastPage}
					page={page}
					pageSize={pageSize}
					totalCount={totalCount}
					updatePage={updatePage}
					updatePageSize={updatePageSize}
				/>
			)}
		</>
	);
}

Autocomplete.propTypes = {
	alwaysActive: PropTypes.bool,
	apiUrl: PropTypes.string.isRequired,
	autofill: PropTypes.bool,
	customView: PropTypes.func,
	customViewModuleUrl: PropTypes.string,
	fetchDataDebounce: PropTypes.number,
	id: PropTypes.string,
	infinityScrollMode: PropTypes.bool,
	initialLabel: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	initialValue: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	inputClass: PropTypes.string,

	// inputIcon: PropTypes.string,

	inputId: PropTypes.string,
	inputName: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemsKey: PropTypes.string.isRequired,
	itemsLabel: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]).isRequired,
	onItemsUpdated: PropTypes.func,
	onValueUpdated: PropTypes.func,
	required: PropTypes.bool,

	// spritemap: PropTypes.string,
};

Autocomplete.defaultProps = {
	alwaysActive: true,
	autofill: false,
	fetchDataDebounce: 200,
	infinityScrollMode: false,
	initialLabel: '',
	initialValue: '',
	inputPlaceholder: Liferay.Language.get('type-here'),
	pageSize: 20,
};

export default Autocomplete;
