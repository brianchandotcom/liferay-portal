/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RangeSelectors} from '../types';

export function formatDate(rangeSelector: RangeSelectors) {
	const date = new Date();

	date.setDate(date.getDate() - Number(rangeSelector));

	const year = date.getFullYear();
	const month = date.toLocaleString('en-US', {month: 'short'});
	const day = date.getDate();

	return `${year} ${month} ${day}`;
}
