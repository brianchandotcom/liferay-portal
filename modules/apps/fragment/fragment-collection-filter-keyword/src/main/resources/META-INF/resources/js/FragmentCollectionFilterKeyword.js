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
