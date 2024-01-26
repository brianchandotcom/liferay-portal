/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function ({activePage, collectionId}) {
	const buttons = document.getElementById(
		`paginationButtons_${collectionId}`
	);
	const searchParams = new URLSearchParams(window.location.search);

	const onPageChange = (pageNumber) => {
		searchParams.set(`page_number_${collectionId}`, pageNumber);

		window.location.search = searchParams;
	};

	const clickDelegateNextButton = delegate(buttons, 'click', '.next', () =>
		onPageChange(activePage + 1)
	);

	const clickDelegatePreviousButton = delegate(
		buttons,
		'click',
		'.previous',
		() => onPageChange(activePage - 1)
	);

	return {
		dispose() {
			clickDelegateNextButton.dispose();
			clickDelegatePreviousButton.dispose();
		},
	};
}
