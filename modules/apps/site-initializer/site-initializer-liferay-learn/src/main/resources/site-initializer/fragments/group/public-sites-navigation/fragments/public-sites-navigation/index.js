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
const searchSuggestionsResult = fragmentElement.querySelector(
	'.search-suggestions'
);
const noResultsMessage = fragmentElement.querySelector('.no-results-message');

searchSuggestionsInput.oninput = function () {
	searchSuggestionsResult.innerHTML = '';

	if (!searchSuggestionsInput.value) {
		noResultsMessage.classList.add('d-none');
	}

	const query = searchSuggestionsInput.value;
	navSearch(query);
};

function navSearch(query) {
	const postDataUrl =
		window.location.origin +
		`/o/portal-search-rest/v1.0/suggestions?currentURL=${
			window.location.origin
		}&destinationFriendlyURL=/search&groupId=${Liferay.ThemeDisplay.getScopeGroupId()}&plid=${Liferay.ThemeDisplay.getPlid()}&scope=this-site&search=${query}`;

	postData(postDataUrl, [
		{
			attributes: {
				fields: ['content_en_US'],
				includeAssetSearchSummary: true,
				includeAssetURL: true,
				sxpBlueprintId: configuration.searchBlueprintId,
			},
			contributorName: 'sxpBlueprint',
			displayGroupName: 'Public Nav Search Recommendations',
			size: '3',
		},
	]).then((data) => {
		const searchSuggestions = fragmentElement.querySelector(
			'.search-suggestions'
		);
		const searchSuggestionsSeeAllResults = fragmentElement.querySelector(
			'.search-suggestions-see-all-results'
		);
		const suggestionsPopular = fragmentElement.querySelector(
			'.suggestions-popular'
		);
		const suggestedText = fragmentElement.querySelector('.suggested-text');

		const searchSuggestionsResult = fragmentElement.querySelector(
			'.search-suggestions'
		);

		if (data && data.items && data.items[0]) {
			const myjson = JSON.parse(JSON.stringify(data.items[0]));
			if (myjson) {
				searchSuggestionsResult.innerHTML = '';

				for (const suggestion of myjson.suggestions) {
					searchSuggestionsResult.classList.remove('d-none');

					const newSuggestion = document.createElement('div');
					newSuggestion.classList.add('search-suggestion-item');

					const suggestionTitle = document.createElement('div');
					const suggestionTitleText = document.createTextNode(
						suggestion.text
					);
					suggestionTitle.classList.add(
						'search-suggestion-item-title'
					);
					suggestionTitle.appendChild(suggestionTitleText);

					const suggestionContent = document.createElement('div');
					let suggestionContentTextValue =
						suggestion.attributes.assetSearchSummary;

					if (!suggestionContentTextValue) {
						suggestionContentTextValue = Liferay.Language.get(
							'no-preview-available'
						);
					}

					const suggestionContentText = document.createTextNode(
						suggestionContentTextValue
					);
					suggestionContent.classList.add(
						'search-suggestion-item-content'
					);
					suggestionContent.appendChild(suggestionContentText);

					const assetUrl = suggestion.attributes.assetURL;

					const suggestionUrl = document.createElement('div');

					const suggestionUrlText = document.createTextNode(assetUrl);

					newSuggestion.href = assetUrl;
					suggestionUrl.classList.add('search-suggestion-item-link');
					suggestionUrl.appendChild(suggestionUrlText);

					newSuggestion.appendChild(suggestionTitle);
					newSuggestion.appendChild(suggestionContent);
					newSuggestion.appendChild(suggestionUrl);

					searchSuggestions.appendChild(newSuggestion);

					searchSuggestionsSeeAllResults.classList.remove('d-none');
					suggestedText.classList.remove('d-none');
					noResultsMessage.classList.add('d-none');
					suggestionsPopular.classList.add('d-none');
				}

				// search highlighting

				const searchSuggestionItemContents = document.querySelectorAll(
					'.search-suggestion-item-content'
				);

				const highlightedTerm = '<b>' + query + '</b>';

				if (searchSuggestionItemContents) {
					for (const searchSuggestionItemContent of searchSuggestionItemContents) {
						searchSuggestionItemContent.innerHTML = searchSuggestionItemContent.innerHTML.replaceAll(
							query,
							highlightedTerm
						);
					}
				}
			}
		}
		else {
			searchSuggestionsSeeAllResults.classList.add('d-none');
			suggestedText.classList.add('d-none');
			noResultsMessage.classList.remove('d-none');
			suggestionsPopular.classList.remove('d-none');
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

String.prototype.replaceAll = function (strReplace, strWith) {
	const esc = strReplace.replace(/[-/\\^$*+?.()|[\]{}]/g, '\\$&');
	const reg = new RegExp(esc, 'ig');

	return this.replace(reg, strWith);
};
