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
