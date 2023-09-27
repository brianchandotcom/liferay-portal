/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes} from 'frontend-js-web';

export default function ({namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	function fulfillCommerceChannelIds() {
		const values = getCheckedCheckboxes(form, `${namespace}allRowIds`);

		form[`${namespace}commerceChannelIds`].value = values;
	}

	fulfillCommerceChannelIds();

	document.querySelectorAll('.channel-id-input').forEach((input) => {
		input.addEventListener('change', fulfillCommerceChannelIds);
	});
}
