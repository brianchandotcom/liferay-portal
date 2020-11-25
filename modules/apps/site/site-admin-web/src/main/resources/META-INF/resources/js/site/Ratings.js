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
		if (
			ratingTypeChanged &&
			!confirm(
				Liferay.Language.get(
					'existing-ratings-data-values-will-be-adapted-to-match-the-new-ratings-type-even-though-it-may-not-be-accurate'
				)
			)
		) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}
	};

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			changeDelegate.dispose();

			form.removeEventListener('submit', onSubmit);
		},
	};
}
