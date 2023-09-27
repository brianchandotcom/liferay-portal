/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import {addParams, fetch, navigate} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

import useDebounceCallback from '../hooks/useDebounceCallback';
import cleanSuggestionsContributorConfiguration from '../utils/clean_suggestions_contributor_configuration';

export default function SearchBar({
	destinationFriendlyURL,
	emptySearchEnabled,
	isDXP = true,
	isSearchExperiencesSupported = true,
	keywords = '',
	keywordsParameterName = 'q',
	letUserChooseScope = false,
	paginationStartParameterName,
	scopeParameterName,
	scopeParameterStringCurrentSite,
	scopeParameterStringEverything,
	searchURL,
	selectedEverythingSearchScope = false,
	suggestionsContributorConfiguration = '[]',
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
	const [loading, setLoading] = useState(false);
	const [scope, setScope] = useState(
		selectedEverythingSearchScope
			? scopeParameterStringEverything
			: scopeParameterStringCurrentSite
	);
	const [suggestionsResponseItems, setSuggestionsResponseItems] = useState(
		[]
	);

	const alignElementRef = useRef();
	const dropdownRef = useRef();

	/**
	 * Returns the lowest suggestions display threshold available.
	 * If a suggestions contributor does not set its own threshold,
	 * it uses the global one.
	 */

	const _getLowestSuggestionsDisplayThreshold = useCallback(() => {
		const characterThresholdArray = cleanSuggestionsContributorConfiguration(
			suggestionsContributorConfiguration,
			isDXP,
			isSearchExperiencesSupported
		).map((config) =>
			config.attributes?.characterThreshold
				? parseInt(config.attributes.characterThreshold, 10)
				: parseInt(suggestionsDisplayThreshold, 10)
		);

		return Math.min(...characterThresholdArray);
	}, [
		isDXP,
		isSearchExperiencesSupported,
		suggestionsContributorConfiguration,
		suggestionsDisplayThreshold,
	]);

	/**
	 * Filters out blueprint suggestion contributors if search
	 * experiences is not supported.
	 */
	const _getSuggestionsContributorConfiguration = useCallback(
		() =>
			JSON.stringify(
				cleanSuggestionsContributorConfiguration(
					suggestionsContributorConfiguration,
					isDXP,
					isSearchExperiencesSupported
				)
			),
		[
			isDXP,
			isSearchExperiencesSupported,
			suggestionsContributorConfiguration,
		]
	);

	const _fetchSuggestions = (searchValue, scopeValue) => {
		fetch(
			addParams(
				{
					currentURL: window.location.href,
					destinationFriendlyURL: destinationFriendlyURL.trim().length
						? destinationFriendlyURL
						: '/search',
					groupId: Liferay.ThemeDisplay.getScopeGroupId(),
					keywordsParameterName,
					plid: Liferay.ThemeDisplay.getPlid(),
					scope: scopeValue,
					search: searchValue,
				},
				fetchURL.href
			),
			{
				body: _getSuggestionsContributorConfiguration(),
				headers: new Headers({
					'Accept': 'application/json',
					'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				}),
				method: 'POST',
			}
		)
			.then((response) => response.json())
			.then((data) => {
				setSuggestionsResponseItems(data?.items || []);
			})
			.catch(() => {
				setSuggestionsResponseItems([]);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const [fetchSuggestionsDebounced] = useDebounceCallback(
		_fetchSuggestions,
		500
	);

	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			_handleSubmit(event);
		}
	};

	const _handleChangeScope = (event) => {
		setScope(event.target.value);
	};

	const _handleFocus = () => {
		if (
			_getLowestSuggestionsDisplayThreshold() === 0 &&
			inputValue === ''
		) {
			setLoading(true);

			_fetchSuggestions(inputValue, scope);

			setActive(true);
		}
	};

	const _handleSubmit = (event) => {
		event.preventDefault();
		event.stopPropagation();

		if (!!inputValue.trim().length || emptySearchEnabled) {
			const queryString = _updateQueryString(document.location.search);

			navigate(searchURL + queryString);
		}
	};

	const _handleValueChange = (event) => {
		const {value} = event.target;

		setInputValue(value);

		if (value.trim().length >= _getLowestSuggestionsDisplayThreshold()) {

			// Immediately show loading spinner unless the value hasn't changed.
			// If the value hasn't changed, no new request will be made and the
			// loading spinner will not be shown.

			if (value.trim() !== autocompleteSearchValue) {
				setLoading(true);

				fetchSuggestionsDebounced(value.trim(), scope);
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
					onFocus={_handleFocus}
					onKeyDown={_handleKeyDown}
					placeholder={Liferay.Language.get('search-...')}
					title={Liferay.Language.get('search')}
					type="text"
					value={inputValue}
				/>

				{loading ? (
					<ClayAutocomplete.LoadingIndicator />
				) : (
					<ClayInput.GroupInsetItem after>
						<ClayButton
							aria-label={Liferay.Language.get('search')}
							displayType="unstyled"
							onClick={_handleSubmit}
							type="submit"
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
							onFocus={_handleFocus}
							onKeyDown={_handleKeyDown}
							placeholder={Liferay.Language.get('search-...')}
							type="text"
							value={inputValue}
						/>

						<ClayInput.GroupInsetItem after>
							<span className="c-mr-2 inline-item">
								<ClayLoadingIndicator
									className={getCN({
										invisible: !loading,
									})}
									small
								/>
							</span>
						</ClayInput.GroupInsetItem>
					</ClayInput.Group>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem prepend shrink>
					<ClaySelect
						aria-label={Liferay.Language.get('scope')}
						name={scopeParameterName}
						onChange={_handleChangeScope}
						title={Liferay.Language.get('scope')}
						value={scope}
					>
						<ClaySelect.Option
							key={scopeParameterStringCurrentSite}
							label={Liferay.Language.get('this-site')}
							value={scopeParameterStringCurrentSite}
						/>

						<ClaySelect.Option
							key={scopeParameterStringEverything}
							label={Liferay.Language.get('everything')}
							value={scopeParameterStringEverything}
						/>
					</ClaySelect>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append className="c-mr-0" shrink>
					<ClayButton
						aria-label={Liferay.Language.get('search')}
						displayType="secondary"
						onClick={_handleSubmit}
						type="submit"
					>
						<ClayIcon symbol="search" />
					</ClayButton>
				</ClayInput.GroupItem>
			</>
		);
	};

	const _updateQueryString = (queryString) => {
		const searchParams = new URLSearchParams(queryString);

		if (emptySearchEnabled || inputValue) {
			searchParams.set(
				keywordsParameterName,
				inputValue.replace(/^\s+|\s+$/, '')
			);
		}

		if (paginationStartParameterName) {
			searchParams.delete(paginationStartParameterName);
		}

		if (letUserChooseScope) {
			searchParams.set(scopeParameterName, scope);
		}

		searchParams.delete('p_p_id');
		searchParams.delete('p_p_state');
		searchParams.delete('start');

		return '?' + searchParams.toString();
	};

	return (
		<ClayAutocomplete className="search-bar-suggestions">
			<ClayInput.Group ref={alignElementRef}>
				{letUserChooseScope
					? _renderSearchBarWithScope()
					: _renderSearchBar()}
			</ClayInput.Group>

			<ClayDropDown.Menu
				active={active && !!suggestionsResponseItems.length}
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
				{suggestionsResponseItems.map((group, groupIndex) => (
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
	);
}
