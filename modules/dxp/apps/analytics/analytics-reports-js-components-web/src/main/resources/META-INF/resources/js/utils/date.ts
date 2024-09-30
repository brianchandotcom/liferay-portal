/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import {RangeSelectors} from '../types/global';

export function formatDate(date: Date) {
	const options: Intl.DateTimeFormatOptions = {
		day: 'numeric',
		month: 'short',
	};

	return date.toLocaleDateString('en-US', options).toLowerCase();
}

export function toUnix(str: string) {
	return moment.utc(str).valueOf() || null;
}

export function formatTooltipDate(date: Date, rangeSelectors: RangeSelectors) {
	if (rangeSelectors === RangeSelectors.Last24Hours) {
		return moment.utc(date).format('MMM D, h A');
	}

	return moment.utc(date).format('YYYY MMM D');
}
