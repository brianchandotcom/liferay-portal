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
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import React, {useEffect, useRef, useState} from 'react';

/**
 * When Hugging Face is selected as the Text Embedding Provider, this input
 * will query for applicable models.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
function ModelAutocomplete({
	label,
	name,
	onBlur,
	onChange,
	required,
	value = '',
}) {
	const [autocompleteSearchValue, setAutocompleteSearchValue] = useState(
		value
	);
	const [networkState, setNetworkState] = useState(() => ({
		error: false,
		loading: false,
		networkStatus: 4,
	}));
	const [showDropDown, setShowDropDown] = useState(false);

	const currentItemSelectedRef = useRef(value);

	const {resource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			}),
		},
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}${Liferay.ThemeDisplay.getPathContext()}
		/o/search-experiences-rest/v1.0/sentence-transformer/ml-models`,
		onNetworkStatusChange: (status) => {
			setNetworkState({
				error: status === 5,
				loading: status > 1 && status < 4,
				networkStatus: status,
			});
		},
		variables: {
			limit: 20,
			pipeline_tag: 'feature-extraction',
			query: autocompleteSearchValue.trim(),
		},
	});

	const _handleInputChange = (event) => {

		// Immediately show loading spinner when typing.

		if (!networkState.loading) {
			setNetworkState({
				error: false,
				loading: true,
				networkStatus: 4,
			});
		}

		if (!event.target.value) {
			currentItemSelectedRef.current = '';
			onChange('');
		}

		setAutocompleteSearchValue(event.target.value);
		setShowDropDown(true);
	};

	const _handleInputFocus = () => {
		setShowDropDown(true);
	};

	const _handleInputKeyDown = (event) => {

		// Prevent form submission to prevent saving an input that isn't one of
		// the autocomplete items from the models endpoint.

		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	const _handleItemChange = (item) => {
		currentItemSelectedRef.current = item;
		onChange(item);

		setAutocompleteSearchValue(item);
		setShowDropDown(false);
	};

	const _renderAutocompleteDropdownItems = () => {

		// Loading

		if (networkState.loading) {
			return (
				<ClayDropDown.Item disabled>
					{Liferay.Language.get('loading')}
				</ClayDropDown.Item>
			);
		}

		// No Results

		if (!resource?.items?.length) {
			return (
				<ClayDropDown.Item disabled>
					{Liferay.Language.get('no-results-found')}
				</ClayDropDown.Item>
			);
		}

		// Has Results

		if (resource?.items) {
			return (resource?.items || []).map(({modelId}) => (
				<ClayDropDown.Item
					key={modelId}
					onClick={() => _handleItemChange(modelId)}
				>
					{modelId}
				</ClayDropDown.Item>
			));
		}
	};

	useEffect(() => {
		if (!showDropDown) {
			setAutocompleteSearchValue(currentItemSelectedRef.current);
		}
	}, [showDropDown]);

	return (
		<ClayAutocomplete>
			<ClayAutocomplete.Input
				aria-label={label}
				id={name}
				name={name}
				onBlur={onBlur}
				onChange={_handleInputChange}
				onFocus={_handleInputFocus}
				onKeyDown={_handleInputKeyDown}
				required={required}
				value={autocompleteSearchValue}
			/>

			<ClayAutocomplete.DropDown
				active={showDropDown}
				onSetActive={setShowDropDown}
			>
				<ClayDropDown.ItemList>
					{_renderAutocompleteDropdownItems()}
				</ClayDropDown.ItemList>
			</ClayAutocomplete.DropDown>
		</ClayAutocomplete>
	);
}

export default ModelAutocomplete;
