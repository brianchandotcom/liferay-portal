/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const COLORS = [
	'#4B9FFF',
	'#FFB46E',
	'#FF5F5F',
	'#50D2A0',
	'#FF73C3',
	'#9CE269',
	'#AF78FF',
	'#FFD76E',
	'#5FC8FF',
	'#7785FF',
];

const NAMED_COLORS = {
	black: '#000000',
	blueDark: '#272833',
	gray: '#CDCED9',
	lightBlue: '#4B9BFF',
	white: '#FFFFFF',
};

export {NAMED_COLORS};

export default function colors(index) {
	return COLORS[index % COLORS.length];
}
