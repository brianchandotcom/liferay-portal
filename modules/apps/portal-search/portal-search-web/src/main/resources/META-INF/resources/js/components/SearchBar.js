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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {navigate} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export default function SearchBar({
	destinationFriendlyURL,
	emptySearchEnabled,
	keywords = '',
	keywordsParameterName = 'q',
	paginationStartParameterName,
	scope = '',
	scopeParameterName,
	searchURL = '/search',
	suggestionsContributorConfiguration,
	suggestionsDisplayThreshold, // eslint-disable-line no-unused-vars
	suggestionsURL = '/o/portal-search-rest/v1.0/suggestions',
}) {
	const fetchURL = new URL(
		`${Liferay.ThemeDisplay.getPathContext()}${suggestionsURL}`,
		Liferay.ThemeDisplay.getPortalURL()
	);

	const [active, setActive] = useState(false);
	const [value, setValue] = useState(keywords);
	const [networkStatus, setNetworkStatus] = useState(4);

	const alignElementRef = useRef();
	const dropdownRef = useRef();

	const {resource} = useResource({
		fetchOptions: {
			body: suggestionsContributorConfiguration,
			headers: {
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-type': 'application/json',
			},
			method: 'POST',
		},
		fetchPolicy: 'cache-first',
		link: fetchURL.href,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			currentURL: window.location.href,
			destinationFriendlyURL,
			groupId: Liferay.ThemeDisplay.getScopeGroupId(),
			plid: Liferay.ThemeDisplay.getPlid(),
			scope,
			search: value,
		},
	});

	const initialLoading = networkStatus === 1;
	const loading = networkStatus < 4;
	const error = networkStatus === 5;

	const _handleSubmit = () => {
		if (!!value || emptySearchEnabled) {
			const queryString = _updateQueryString(document.location.search);

			navigate(searchURL + queryString);
		}
	};

	const _updateQueryString = (queryString) => {
		const searchParams = new URLSearchParams(queryString);

		if (value) {
			searchParams.set(
				keywordsParameterName,
				value.replace(/^\s+|\s+$/, '')
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
		<ClayAutocomplete>
			<ClayInput.Group>
				<ClayInput.GroupItem ref={alignElementRef}>
					<ClayAutocomplete.Input
						aria-label={Liferay.Language.get('search')}
						className="input-group-inset input-group-inset-after search-bar-keywords-input"
						data-qa-id="searchInput"
						name={keywordsParameterName}
						onChange={(event) => {
							setActive(true);
							setValue(event.target.value);
						}}
						onKeyDown={(event) => {
							if (event.key === 'Enter') {
								_handleSubmit();
							}
						}}
						placeholder={Liferay.Language.get('search-...')}
						title={Liferay.Language.get('search')}
						type="text"
						value={value}
					/>

					{loading ? (
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
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<ClayDropDown.Menu
				active={active && ((!!resource && !!value) || initialLoading)}
				alignElementRef={alignElementRef}
				autoBestAlign={false}
				className="autocomplete-dropdown-menu"
				closeOnClickOutside
				onSetActive={setActive}
				ref={dropdownRef}
				style={{
					maxHeight: '25rem',
					maxWidth: 'none',
					width:
						alignElementRef.current &&
						alignElementRef.current.clientWidth + 'px',
				}}
			>
				{(error || resource?.error || !resource?.items?.length) && (
					<ClayDropDown.ItemList>
						<ClayDropDown.Item className="disabled">
							{Liferay.Language.get('no-results-found')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				)}

				{!error &&
					resource &&
					resource.items &&
					!!resource.items.length &&
					resource.items.map((group, groupIndex) => (
						<ClayDropDown.ItemList
							className="searchbar-suggestions-dropdown"
							key={groupIndex}
						>
							<ClayDropDown.Group header={group.displayGroupName}>
								{group.suggestions.map(
									({text, attributes = {}}, index) => (
										<ClayDropDown.Item
											href={attributes.assetURL}
											key={index}
										>
											<div>
												<strong>{text}</strong>
											</div>

											{attributes.assetSearchSummary && (
												<div className="text-truncate-inline">
													<div className="text-truncate">
														{attributes.assetSearchSummary ||
															''}
													</div>
												</div>
											)}
										</ClayDropDown.Item>
									)
								)}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					))}
			</ClayDropDown.Menu>
		</ClayAutocomplete>
	);
}
