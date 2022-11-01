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
