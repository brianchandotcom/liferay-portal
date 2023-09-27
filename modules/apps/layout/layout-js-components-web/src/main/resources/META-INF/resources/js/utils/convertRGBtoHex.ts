/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
