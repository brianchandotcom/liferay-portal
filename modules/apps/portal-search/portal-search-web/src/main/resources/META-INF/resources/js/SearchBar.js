/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace: portletNamespace}) {
	const form = document.getElementById(`${portletNamespace}fm`);

	const emptySearchInput = form.querySelector(
		'.search-bar-empty-search-input'
	);

	const emptySearchEnabled =
		emptySearchInput && emptySearchInput.value === 'true';

	const keywordsInput = form.querySelector('.search-bar-keywords-input');

	const resetStartPage = form.querySelector('.search-bar-reset-start-page');

	const scopeSelect = form.querySelector('.search-bar-scope-select');

	function getKeywords() {
		if (!keywordsInput) {
			return '';
		}

		const keywords = keywordsInput.value;

		return keywords.trim();
	}

	function isSubmitEnabled() {
		return getKeywords() !== '' || emptySearchEnabled;
	}

	function updateQueryString(queryString) {
		const searchParams = new URLSearchParams(queryString);

		if (keywordsInput) {
			searchParams.set(keywordsInput.name, getKeywords());
		}

		if (resetStartPage) {
			searchParams.delete(resetStartPage.name);
		}

		if (scopeSelect) {
			searchParams.set(scopeSelect.name, scopeSelect.value);
		}

		searchParams.delete('p_p_id');
		searchParams.delete('p_p_state');
		searchParams.delete('start');

		return '?' + searchParams.toString();
	}

	function search() {
		if (isSubmitEnabled()) {
			const searchURL = form.action;

			const queryString = updateQueryString(document.location.search);

			document.location.href = searchURL + queryString;
		}
	}

	function onSubmit(event) {
		event.stopPropagation();

		search();
	}

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			form.removeEventListener('submit', onSubmit);
		},
	};
}
