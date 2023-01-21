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

/* eslint-disable no-undef */

adtSpatialNavigationProvider = new navigation.default.SpatialNavigationProvider(
	'.adt-submenu-item-link'
);

spatialNavigationProvider = new navigation.default.SpatialNavigationProvider(
	'.adt-nav-text'
);

const primaryNav = fragmentElement.querySelector('.primary-nav');

spatialNavigationProvider.addFocusableClasses(primaryNav);

window.addEventListener('load', () => {
	new navigation.default.DropdownProvider(
		'.account',
		'.account',
		'menu-open'
	);

	new navigation.default.DropdownProvider(
		'.sites',
		'.liferay-sites-dropdown',
		'show',
		true
	);

	new navigation.default.DropdownProvider(
		'.menu-button-group',
		'.menu-button-group',
		'menu-open'
	);

	new navigation.default.DropdownProvider(
		'.menu-button-group',
		'.tablet-mobile-nav-section',
		'menu-open',
		true
	);

	new navigation.default.DropdownProvider(
		'.adt-nav-text',
		'.adt-submenu',
		'dropdown-open',
		false,
		(menu) => {
			adtSpatialNavigationProvider.addFocusableClasses(menu);
		},
		(menu) => {
			adtSpatialNavigationProvider.removeFocusableClasses(menu);
		}
	);

	new navigation.default.DropdownProvider(
		'.language',
		'.language-selector',
		'list-open',
		true
	);

	new navigation.default.DropdownProvider(
		'.language',
		'.language-dropdown-list-container',
		'list-open',
		true
	);

	new navigation.default.DropdownProvider(
		'.search-icon, .close-search',
		'.search-wrapper',
		'search-open',
		true
	);
});

const searchSuggestionsInput = fragmentElement.querySelector(
	'.search-suggestions-input'
);
const suggestions = fragmentElement.querySelector('.suggestions');
const searchSuggestions = fragmentElement.querySelector('.search-suggestions');

const searchSuggestionItemTemplate = suggestions.querySelector('template');

const searchSuggestionItem = searchSuggestionItemTemplate.content.querySelector(
	'a'
);

searchSuggestionsInput.oninput = function () {
	searchSuggestions.innerHTML = '';

	if (searchSuggestionsInput.value) {
		suggestions.classList.add('performing-search');
		performSearch(searchSuggestionsInput.value);
	}
	else {
		suggestions.classList.remove(
			'performing-search',
			'search-results-found'
		);
	}
};

function performSearch(query) {
	const postDataURL = `/o/portal-search-rest/v1.0/suggestions?currentURL=${
		window.location.href
	}&destinationFriendlyURL=/search&groupId=${Liferay.ThemeDisplay.getScopeGroupId()}&plid=${Liferay.ThemeDisplay.getPlid()}&scope=this-site&search=${query}`;

	postData(postDataURL, [
		{
			attributes: {
				includeAssetSearchSummary: true,
				includeassetURL: true,
				sxpBlueprintId: configuration.searchBlueprintId,
			},
			contributorName: 'sxpBlueprint',
			displayGroupName: 'Public Nav Search Recommendations',
			size: '3',
		},
	]).then((data) => {
		if (data && data.items && data.items[0]) {
			const items = JSON.parse(JSON.stringify(data.items[0]));
			if (items) {
				searchSuggestions.innerHTML = '';

				const searchTermRegExp = new RegExp(query, 'ig');

				for (const suggestion of items.suggestions) {
					const suggestionLink = document.importNode(
						searchSuggestionItem,
						true
					);

					const assetURL = suggestion.attributes.assetURL;

					suggestionLink.href = assetURL;

					const suggestionTitle = suggestionLink.querySelector(
						'.search-suggestion-item-title'
					);

					suggestionTitle.appendChild(
						document.createTextNode(suggestion.text)
					);

					const suggestionContent = suggestionLink.querySelector(
						'.search-suggestion-item-content'
					);

					let suggestionContentTextValue =
						suggestion.attributes.assetSearchSummary;

					if (suggestionContentTextValue) {
						suggestionContentTextValue = suggestionContentTextValue.substring(
							0,
							500
						);

						suggestionContent.innerHTML = suggestionContentTextValue.replace(
							searchTermRegExp,
							`<b>${query}</b>`
						);
					}

					const suggestionURL = suggestionLink.querySelector(
						'.search-suggestion-item-link'
					);

					suggestionURL.appendChild(
						document.createTextNode(assetURL.replace(/\?.*$/, ''))
					);

					searchSuggestions.appendChild(suggestionLink);

					suggestions.classList.add('search-results-found');
				}
			}
		}
		else {
			suggestions.classList.remove('search-results-found');
		}
	});
}

async function postData(url = '', data = {}) {
	const response = await Liferay.Util.fetch(url, {
		body: JSON.stringify(data),
		credentials: 'include',
		headers: {
			'Accept': 'application/json',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});

	return response.json();
}
