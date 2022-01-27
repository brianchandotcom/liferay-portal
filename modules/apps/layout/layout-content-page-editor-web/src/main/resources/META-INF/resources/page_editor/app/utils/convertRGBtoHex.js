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

export function convertRGBtoHex(rgbColor) {
	const [, red, green, blue] = rgbColor.match(
		/^rgba?\((\d{1,3}), (\d{1,3}), (\d{1,3})/
	);

	return [red, green, blue]
		.map((number) => {
			let hex = parseInt(number, 10).toString(16);

			if (hex.length === 1) {
				hex = hex.padStart(2, '0');
			}

			return hex;
		})
		.join('');
}
