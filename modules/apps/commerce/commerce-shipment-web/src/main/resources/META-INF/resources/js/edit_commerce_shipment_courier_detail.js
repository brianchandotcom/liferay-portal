/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const select = document.getElementById(`${namespace}shippingMethod`);

	const updateTrackingURL = () => {
		const data = select.options[select.selectedIndex].dataset.trackingurl;
		const trackingURL = document.getElementById(`${namespace}trackingURL`);
		trackingURL.value = data;
	};

	select.addEventListener('change', updateTrackingURL);
}
