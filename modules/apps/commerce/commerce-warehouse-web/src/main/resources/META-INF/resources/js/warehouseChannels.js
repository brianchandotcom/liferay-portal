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
