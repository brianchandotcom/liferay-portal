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

const RGB_REGEXP = /^rgb\((?<red>\d{1,3}),\s*(?<green>\d{1,3}),\s*(?<blue>\d{1,3})\)/;
const RGBA_REGEXP = /^rgba\((?<red>\d{1,3}),\s*(?<green>\d{1,3}),\s*(?<blue>\d{1,3}),\s*(?<alpha>(1|0(\.\d+)?))\)/;

export default function convertRGBtoHex(rgbColor: string) {
	const groups =
		rgbColor.match(RGB_REGEXP)?.groups ||
		rgbColor.match(RGBA_REGEXP)?.groups ||
		{};

	const alpha = parseFloat(groups.alpha);
	const blue = parseInt(groups.blue, 10);
	const green = parseInt(groups.green, 10);
	const red = parseInt(groups.red, 10);

	if (isNaN(blue) || isNaN(green) || isNaN(red)) {
		return rgbColor;
	}

	const hexColor = [red, green, blue]
		.map((number) => number.toString(16).padStart(2, '0').toUpperCase())
		.join('');

	if (isNaN(alpha)) {
		return `#${hexColor}`;
	}

	return `#${hexColor}${parseInt(`${alpha * 255}`, 10)
		.toString(16)
		.padStart(2, '0')
		.toUpperCase()}`;
}
