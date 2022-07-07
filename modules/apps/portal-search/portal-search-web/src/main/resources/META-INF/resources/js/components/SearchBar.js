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
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FocusScope} from '@clayui/shared';
import getCN from 'classnames';
import {navigate} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export default function SearchBar({
	emptySearchEnabled,
	initialScope = '',
	keywords = '',
	keywordsParameterName = 'q',
	letUserChooseScope = false,
	paginationStartParameterName,
	scopeParameterName,
	scopeParameterStringCurrentSite,
	scopeParameterStringEverything,
	searchURL,
	suggestionsContributorConfiguration = '{}',
	suggestionsDisplayThreshold = '2',
	suggestionsURL = '/o/portal-search-rest/v1.0/suggestions',
}) {
	const fetchURL = new URL(
		`${Liferay.ThemeDisplay.getPathContext()}${suggestionsURL}`,
		Liferay.ThemeDisplay.getPortalURL()
	);

	const [active, setActive] = useState(false);
	const [autocompleteSearchValue, setAutocompleteSearchValue] = useState('');
	const [inputValue, setInputValue] = useState(keywords);
	const [networkState, setNetworkState] = useState(() => ({
		error: false,
		loading: false,
		networkStatus: 4,
	}));
	const [scope, setScope] = useState(initialScope);

	const alignElementRef = useRef();
	const dropdownRef = useRef();

	const {resource} = useResource({
		fetchOptions: {
			body: suggestionsContributorConfiguration,
			credentials: 'include',
			headers: new Headers({
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			}),
			method: 'POST',
		},
		fetchPolicy: 'cache-first',
		link: fetchURL.href,
		onNetworkStatusChange: (status) => {
			setNetworkState({
				error: status === 5,
				loading: status > 1 && status < 4,
				networkStatus: status,
			});
		},
		variables: {
			currentURL: window.location.href,
			destinationFriendlyURL: !searchURL.trim().length
				? searchURL
				: '/search',
			groupId: Liferay.ThemeDisplay.getScopeGroupId(),
			plid: Liferay.ThemeDisplay.getPlid(),
			scope,
			search: autocompleteSearchValue,
		},
	});

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
			event.stopPropagation();

			_handleSubmit();
		}
	};

	const _handleChangeScope = (event) => {
		setScope(event.target.value);
	};

	const _handleSubmit = () => {
		if (!!inputValue.trim().length || emptySearchEnabled) {
			const queryString = _updateQueryString(document.location.search);

			navigate(searchURL + queryString);
		}
	};

	const _handleValueChange = (event) => {
		const {value} = event.target;

		setInputValue(value);

		if (value.trim().length > parseInt(suggestionsDisplayThreshold, 10)) {

			// Immediately show loading spinner unless the value hasn't changed.
			// If the value hasn't changed, no new request will be made and the
			// loading spinner will never be hidden.

			if (value.trim() !== autocompleteSearchValue) {
				_setLoading(true);
			}

			setActive(true);
			setAutocompleteSearchValue(value.trim());
		}
		else {

			// Hide dropdown when value is below threshold.

			setActive(false);
			setAutocompleteSearchValue('');
		}
	};

	const _renderSearchBar = () => {
		return (
			<>
				<ClayAutocomplete.Input
					aria-label={Liferay.Language.get('search')}
					autoComplete="off"
					className="input-group-inset input-group-inset-after search-bar-keywords-input"
					data-qa-id="searchInput"
					name={keywordsParameterName}
					onChange={_handleValueChange}
					onKeyDown={_handleKeyDown}
					placeholder={Liferay.Language.get('search-...')}
					title={Liferay.Language.get('search')}
					type="text"
					value={inputValue}
				/>

				{networkState.loading ? (
					<ClayAutocomplete.LoadingIndicator />
				) : (
					<ClayInput.GroupInsetItem after>
						<ClayButton
							aria-label={Liferay.Language.get('submit')}
							displayType="unstyled"
							onClick={_handleSubmit}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayInput.GroupInsetItem>
				)}
			</>
		);
	};

	const _renderSearchBarWithScope = () => {
		return (
			<>
				<ClayInput.GroupItem className="search-bar-with-scope" prepend>
					<ClayInput.Group>
						<ClayAutocomplete.Input
							aria-label={Liferay.Language.get('search')}
							autoComplete="off"
							className="input-group-inset input-group-inset-after"
							data-qa-id="searchInput"
							name={keywordsParameterName}
							onChange={_handleValueChange}
							onKeyDown={_handleKeyDown}
							placeholder={Liferay.Language.get('search-...')}
							type="text"
							value={inputValue}
						/>

						<ClayInput.GroupInsetItem after>
							<ClayLoadingIndicator
								className={getCN({
									invisible: !networkState.loading,
								})}
								small
							/>
						</ClayInput.GroupInsetItem>
					</ClayInput.Group>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem prepend shrink>
					<ClaySelect onChange={_handleChangeScope} value={scope}>
						<ClaySelect.Option
							key={scopeParameterStringCurrentSite}
							label={Liferay.Language.get('current-site')}
							value={scopeParameterStringCurrentSite}
						/>

						<ClaySelect.Option
							key={scopeParameterStringEverything}
							label={Liferay.Language.get('everything')}
							value={scopeParameterStringEverything}
						/>
					</ClaySelect>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						aria-label={Liferay.Language.get('submit')}
						displayType="secondary"
						onClick={_handleSubmit}
					>
						<ClayIcon symbol="search" />
					</ClayButton>
				</ClayInput.GroupItem>
			</>
		);
	};

	const _setLoading = (loading) => {
		setNetworkState({
			error: false,
			loading,
			networkStatus: 4,
		});
	};

	const _updateQueryString = (queryString) => {
		const searchParams = new URLSearchParams(queryString);

		if (inputValue) {
			searchParams.set(
				keywordsParameterName,
				inputValue.replace(/^\s+|\s+$/, '')
			);
		}

		if (paginationStartParameterName) {
			searchParams.delete(paginationStartParameterName);
		}

		if (scope) {
			searchParams.set(scopeParameterName, scope);
		}

		searchParams.delete('p_p_id');
		searchParams.delete('p_p_state');
		searchParams.delete('start');

		return '?' + searchParams.toString();
	};

	return (
		<FocusScope>
			<ClayAutocomplete className="search-bar-suggestions">
				<ClayInput.Group ref={alignElementRef}>
					{letUserChooseScope
						? _renderSearchBarWithScope()
						: _renderSearchBar()}
				</ClayInput.Group>

				<ClayDropDown.Menu
					active={active && !!resource?.items?.length}
					alignElementRef={alignElementRef}
					autoBestAlign={false}
					className="search-bar-suggestions-dropdown-menu"
					closeOnClickOutside
					onSetActive={setActive}
					ref={dropdownRef}
					style={{
						width:
							alignElementRef.current &&
							alignElementRef.current.clientWidth + 'px',
					}}
				>
					{resource?.items?.map((group, groupIndex) => (
						<ClayDropDown.ItemList
							className="search-bar-suggestions-results-list"
							key={groupIndex}
						>
							<ClayDropDown.Group header={group.displayGroupName}>
								{group.suggestions.map(
									({text, attributes = {}}, index) => (
										<ClayDropDown.Item
											href={attributes.assetURL}
											key={index}
										>
											<div className="suggestion-item-title">
												{text}
											</div>

											{attributes.assetSearchSummary && (
												<div className="suggestion-item-description">
													<div className="text-truncate-inline">
														<div className="text-truncate">
															{attributes.assetSearchSummary ||
																''}
														</div>
													</div>
												</div>
											)}
										</ClayDropDown.Item>
									)
								)}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					))}

					<ClayDropDown.ItemList>
						<ClayDropDown.Item
							className="search-bar-suggestions-show-more"
							onClick={_handleSubmit}
						>
							{Liferay.Language.get('show-more')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown.Menu>
			</ClayAutocomplete>
		</FocusScope>
	);
}
