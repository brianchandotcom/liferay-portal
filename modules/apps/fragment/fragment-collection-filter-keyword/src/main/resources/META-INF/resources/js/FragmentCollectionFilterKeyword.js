/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';

const ENTER_KEY = 'Enter';

export default function FragmentCollectionFilterKeyword({
	fragmentEntryLinkId,
	fragmentEntryLinkNamespace,
	isDisabled,
}) {
	const keywordsInput = document.getElementById(
		`${fragmentEntryLinkNamespace}keywordsInput`
	);

	if (keywordsInput) {
		keywordsInput.value = getCollectionFilterValue(
			'keywords',
			fragmentEntryLinkId
		);

		if (isDisabled) {
			keywordsInput.disabled = true;

			if (keywordsInput.parentElement && Liferay.Browser.isFirefox()) {
				import('../css/main.scss');

				keywordsInput.parentElement.classList.add(
					'input-group-item--disabled'
				);
			}
		}
	}

	const keywordsButton = document.getElementById(
		`${fragmentEntryLinkNamespace}keywordsButton`
	);

	const handleSearch = () => {
		setCollectionFilterValue(
			'keywords',
			fragmentEntryLinkId,
			keywordsInput.value
		);
	};

	const handleKeyDown = (event) => {
		if (event.key === ENTER_KEY) {
			handleSearch();
		}
	};

	keywordsButton?.addEventListener('click', handleSearch);
	keywordsInput?.addEventListener('keydown', handleKeyDown);

	return {
		dispose() {
			keywordsButton?.removeEventListener('click', handleSearch);
			keywordsInput?.removeEventListener('keydown', handleKeyDown);
		},
	};
}
