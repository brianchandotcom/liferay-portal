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
