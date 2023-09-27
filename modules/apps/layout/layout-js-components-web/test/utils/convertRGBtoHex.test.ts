/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {default as convertRGBtoHex} from '../../src/main/resources/META-INF/resources/js/utils/convertRGBtoHex';

describe('convertRGBtoHex', () => {
	it('converts rgb colors', () => {
		expect(convertRGBtoHex('rgb(100, 100, 100)')).toBe('#646464');
		expect(convertRGBtoHex('rgb(100,100,   100)')).toBe('#646464');
	});

	it('converts rgba colors', () => {
		expect(convertRGBtoHex('rgba(100, 100, 100,   0)')).toBe('#64646400');
		expect(convertRGBtoHex('rgba(100,100,   100,1)')).toBe('#646464FF');
		expect(convertRGBtoHex('rgba(100, 100, 100, 0.827)')).toBe('#646464D2');
	});

	it('ignores invalid colors', () => {
		expect(convertRGBtoHex('rgb(0, 0, 0, 1)')).toBe('rgb(0, 0, 0, 1)');
		expect(convertRGBtoHex('rgba(0, 0, 0)')).toBe('rgba(0, 0, 0)');
		expect(convertRGBtoHex('rgb(1, 2, -2)')).toBe('rgb(1, 2, -2)');
		expect(convertRGBtoHex('rgba(0,0,0,-0.1)')).toBe('rgba(0,0,0,-0.1)');
	});

	it('ignores unknown colors', () => {
		expect(convertRGBtoHex('#646464')).toBe('#646464');
		expect(convertRGBtoHex('#646464AA')).toBe('#646464AA');
		expect(convertRGBtoHex('hsl(0, 0%, 50%)')).toBe('hsl(0, 0%, 50%)');
		expect(convertRGBtoHex('var(--my-red)')).toBe('var(--my-red)');
	});
});
