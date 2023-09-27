/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/*
 * If a number is bigger than 10000 it will transform into thousands (K).
 * If a number is bigger than 1000000 it will transform into millions (M).
 * If a number is bigger than 1000000000 it will transform into billions (B).
 *
 * 4 => 4
 * 4000 => '4K'
 * 4000000 => '4M'
 * 4000000000 => '4B'
 */
export function shortenNumber(value) {
	if (value >= 1000000000) {
		return (value / 1000000000).toFixed(0) + 'B';
	}
	else if (value >= 1000000) {
		return (value / 1000000).toFixed(0) + 'M';
	}
	else if (value >= 10000) {
		return (value / 10000).toFixed(0) + 'K';
	}

	return value;
}
