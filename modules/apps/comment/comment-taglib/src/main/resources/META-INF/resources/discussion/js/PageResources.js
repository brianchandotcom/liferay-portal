/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function PageResources({
	hideMoreComments,
	index,
	originalNamespace,
	randomNamespace,
	rootIndexPage,
}) {
	const indexInput = document.getElementById(
		`${originalNamespace}${randomNamespace}index`
	);

	if (indexInput) {
		indexInput.value = index;
	}

	const rootIndexPageInput = document.getElementById(
		`${originalNamespace}${randomNamespace}rootIndexPage`
	);

	if (rootIndexPageInput) {
		rootIndexPageInput.value = rootIndexPage;
	}

	if (hideMoreComments) {
		const moreCommentsContainer = document.getElementById(
			`${originalNamespace}moreCommentsContainer`
		);

		if (moreCommentsContainer) {
			moreCommentsContainer.classList.add('hide');
		}
	}
}
