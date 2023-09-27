/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function ({iconCssClass, orderingContainerId}) {
	const orderingContainer = document.getElementById(orderingContainerId);

	const callback = (event) => {
		const target = event.target;

		const orderByTypeContainer = target.closest('.order-by-type-container');

		orderByTypeContainer
			.querySelectorAll(iconCssClass)
			.forEach((element) => element.classList.toggle('hide'));

		const orderByTypeField = orderByTypeContainer.querySelector(
			'.order-by-type-field'
		);

		const newVal = orderByTypeField.value === 'ASC' ? 'DESC' : 'ASC';

		orderByTypeField.value = newVal;
	};

	const clickDelegate = delegate(
		orderingContainer,
		'click',
		iconCssClass,
		callback
	);

	return {
		dispose() {
			clickDelegate.dispose();
		},
	};
}
