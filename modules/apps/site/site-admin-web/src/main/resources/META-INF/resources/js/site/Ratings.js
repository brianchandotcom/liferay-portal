/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, openConfirmModal} from 'frontend-js-web';

export default function ({namespace}) {
	const ratingSettingsContainer = document.getElementById(
		`${namespace}ratingsSettingsContainer`
	);

	let ratingTypeChanged = false;

	const changeDelegate = delegate(
		ratingSettingsContainer,
		'change',
		'select',
		() => {
			ratingTypeChanged = true;
		}
	);

	const form = document.getElementById(`${namespace}fm`);

	const onSubmit = (event) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'existing-ratings-data-values-will-be-adapted-to-match-the-new-ratings-type-even-though-it-may-not-be-accurate'
			),
			onConfirm: (isConfirmed) => {
				if (ratingTypeChanged && !isConfirmed) {
					event.preventDefault();
					event.stopImmediatePropagation();
				}
			},
		});
	};

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			changeDelegate.dispose();

			form.removeEventListener('submit', onSubmit);
		},
	};
}
