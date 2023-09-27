/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate} from 'frontend-js-web';

export default function ({maxValue, namespace}) {
	const handleSubmitPriceRange = () => {
		let max = document.getElementById(`${namespace}maximum`).value;

		if (max === '') {
			max = maxValue;
		}

		let min = document.getElementById(`${namespace}minimum`).value;

		if (min === '' || min <= 0) {
			min = 0;
		}

		if (Number(min) > Number(max)) {
			const tempMin = max;
			max = min;
			min = tempMin;
		}

		const url = new URL(window.location.href);

		const queryString = url.search;

		const searchParams = new URLSearchParams(queryString);

		searchParams.set('basePrice', `[${min} TO ${max}]`);
		searchParams.set('max', max);
		searchParams.set('min', min);

		url.search = searchParams;

		navigate(url.toString());
	};
	const priceRangeButton = document.getElementById(
		`${namespace}priceRangeButton`
	);
	priceRangeButton.addEventListener('click', handleSubmitPriceRange);

	return {
		dispose() {
			priceRangeButton.removeEventListener(
				'click',
				handleSubmitPriceRange
			);
		},
	};
}
