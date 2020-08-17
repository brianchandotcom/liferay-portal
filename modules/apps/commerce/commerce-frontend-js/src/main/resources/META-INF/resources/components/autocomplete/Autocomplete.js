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
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {debouncePromise} from '../../utilities/debounce';
import {AUTOCOMPLETE_VALUE_UPDATED} from '../../utilities/eventsDefinitions';
import {getData, getValueFromItem} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';

function Autocomplete({onValueUpdated, ...props}) {
	const [query, setQuery] = useState(props.initialLabel || '');
	const [initialised, setInitialised] = useState(false);
	const [debouncedGetItems, updateDebouncedGetItems] = useState(null);
	const [active, setActive] = useState(false);
	const [selectedItem, updateSelectedItem] = useState(props.initialValue);
	const [items, updateItems] = useState(null);
	const [loading, setLoading] = useState(false);
	const node = useRef();
	const dropdownNode = useRef();
	const inputNode = useRef();

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

		Liferay.fire(AUTOCOMPLETE_VALUE_UPDATED, {
			id: props.id,
			itemData: selectedItem,
			value,
		});

		if (onValueUpdated) {
			onValueUpdated(value, selectedItem);
		}
	}, [selectedItem, props.id, props.itemsKey, onValueUpdated]);

	useEffect(() => {
		if (initialised) {
			setLoading(true);

			debouncedGetItems(props.apiUrl, query)
				.then((jsonResponse) => {
					updateItems(jsonResponse.items);
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
		initialised,
		query,
		props.apiUrl,
		debouncedGetItems,
		props.itemsLabel,
		props.showErrorNotification,
	]);

	useEffect(() => {
		if (query) {
			setInitialised(true);
		}
	}, [query]);

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

	return (
		<FocusScope>
			<ClayAutocomplete ref={node}>
				<input
					id={props.inputId || props.inputName}
					name={props.inputName}
					type="hidden"
					value={currentValue || ''}
				/>
				<ClayAutocomplete.Input
					onChange={(event) => {
						updateSelectedItem(null);
						if (event.target.value !== query) {
							setQuery(event.target.value);
						}
					}}
					onFocus={(_e) => {
						setActive(true);
						setInitialised(true);
					}}
					onKeyUp={(e) => {
						if (e.keyCode === 27) {
							setActive(false);
						}
						else {
							setActive(true);
						}
					}}
					placeholder={props.inputPlaceholder}
					ref={inputNode}
					required={props.required || false}
					value={currentLabel || query}
				/>
				<ClayAutocomplete.DropDown active={active && !loading}>
					<div className="autocomplete-items" ref={dropdownNode}>
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
										key={String(item[props.itemsKey])}
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
				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>
		</FocusScope>
	);
}

Autocomplete.propTypes = {
	apiUrl: PropTypes.string.isRequired,
	autofill: PropTypes.bool,
	fetchDataDebounce: PropTypes.number,
	id: PropTypes.string.isRequired,
	initialLabel: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	initialValue: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	inputId: PropTypes.string,
	inputName: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemsKey: PropTypes.string.isRequired,
	itemsLabel: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]).isRequired,
	onValueUpdated: PropTypes.func,
	required: PropTypes.bool,
};

Autocomplete.defaultProps = {
	autofill: false,
	fetchDataDebounce: 200,
	initialLabel: '',
	initialValue: '',
	inputPlaceholder: Liferay.Language.get('type-here'),
};

export default Autocomplete;
