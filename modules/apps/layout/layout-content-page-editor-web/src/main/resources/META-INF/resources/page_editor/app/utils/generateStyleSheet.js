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

import {config} from '../config/index';
import {getFrontendTokenValue} from './getFrontendTokenValue';
import getLayoutDataItemTopperUniqueClassName from './getLayoutDataItemTopperUniqueClassName';
import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';

const DEFAULT_SPACING_VALUES = {
	0: '0',
	1: '0.25',
	2: '0.5',
	3: '1',
	4: '1.5',
	5: '3',
	6: '4.5',
	7: '6',
	8: '7.5',
	9: '9',
	10: '10',
};

const SPACING_OPTIONS = [
	'marginBottom',
	'marginLeft',
	'marginRight',
	'marginTop',
	'paddingBottom',
	'paddingLeft',
	'paddingRight',
	'paddingTop',
];

const TOPPER_STYLES = [
	'display',
	'marginBottom',
	'marginLeft',
	'marginRight',
	'marginTop',
	'maxWidth',
	'minWidth',
	'width',
	'shadow',
];

export default function generateStyleSheet(styles, {itemsWithTopper} = {}) {
	let css = '';

	Object.entries(styles).forEach(([itemId, itemStyles]) => {
		let itemCSS = '';
		let topperCSS = '';

		Object.entries(itemStyles).forEach(([styleName, styleValue]) => {
			if (!config.commonStylesFields[styleName]) {
				return;
			}

			const {cssTemplate} = config.commonStylesFields[styleName];

			if (
				itemsWithTopper.has(itemId) &&
				TOPPER_STYLES.includes(styleName)
			) {
				topperCSS += `${replaceValue(
					cssTemplate,
					getValue(itemId, styleName, styleValue)
				)}\n`;
			}
			else {
				itemCSS += `${replaceValue(
					cssTemplate,
					getValue(itemId, styleName, styleValue)
				)}\n`;
			}
		});

		if (itemCSS) {
			css += `.${getLayoutDataItemUniqueClassName(
				itemId
			)} {\n${itemCSS}}\n`;
		}

		if (topperCSS) {
			css += `.${getLayoutDataItemTopperUniqueClassName(
				itemId
			)} {\n${topperCSS}}\n`;
		}
	});

	return css;
}

function getValue(itemId, styleName, styleValue) {

	// Spacing values are saved as numbers [0-10] but we need to use
	// the CSS variable --spacer-x which is used by the mx-x and px-x clay classes

	if (SPACING_OPTIONS.includes(styleName)) {
		return `var(--spacer-${styleValue}, ${DEFAULT_SPACING_VALUES[styleValue]}rem)`;
	}

	// Instead of trying to calculate the backgroundImage here, we rely on the item
	// setting this CSS variable

	if (styleName === 'backgroundImage') {
		return `var(--lfr-background-image-${itemId})`;
	}

	if (styleName === 'opacity') {
		return styleValue / 100;
	}

	return getFrontendTokenValue(styleValue);
}

function replaceValue(template, value) {
	return template.replace('{value}', value);
}
