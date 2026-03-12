/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TinyColorInstance, tinycolor} from '@clayui/color-picker';

import {Field, Token} from '../../types/ColorPicker';
import convertRGBtoHex from '../../utils/convertRGBtoHex';

const ERROR_MESSAGES = {
	mutuallyReferenced: Liferay.Language.get(
		'tokens-cannot-be-mutually-referenced'
	),
	selfReferenced: Liferay.Language.get('tokens-cannot-reference-itself'),
};

const HEX_LENGTH_6 = 6;

const HEX_LENGTH_8 = 8;

interface ColorValue {
	color?: string;
	label?: string;
	pickerColor: string;
	value?: string;
}

interface Error {
	error: string;
}

export function parseColorValue({
	editedTokenValues,
	field,
	token,
	value,
}: {
	editedTokenValues: Record<string, Token>;
	field: Field;
	token: Token | undefined;
	value: string;
}): ColorValue | Error | Record<string, never> {
	let validValue = token?.name;
	let tokenLabel: string | undefined = undefined;
	let pickerColor: string = '';

	const isHexFormat = (color: TinyColorInstance) => {
		const colorFormat = color.getFormat();

		return colorFormat === 'hex' || colorFormat === 'hex8';
	};

	const color = tinycolor(value);

	if (token) {
		if (token.name === field.name) {
			return {error: ERROR_MESSAGES.selfReferenced};
		}

		if (editedTokenValues?.[token.name]?.name === field.name) {
			return {error: ERROR_MESSAGES.mutuallyReferenced};
		}

		tokenLabel = token.label;
	}
	else if (color.isValid()) {
		if (isHexFormat(color)) {
			validValue = internalToHex(color);
		}
		else if (color.toString() !== value) {
			validValue = color.toString();
		}
		else {
			const element = document.createElement('div');

			element.style.background = value;
			element.style.display = 'none';

			document.body.appendChild(element);

			validValue = element.style.background;

			if (!validValue) {
				return {};
			}

			pickerColor = convertRGBtoHex(
				window.getComputedStyle(element).backgroundColor
			).replace(/^#/, '');

			element.remove();
		}
	}
	else {

		// Hexadecimals with 7 characters or more than 8 are invalid. Here the
		// value is truncated to 6 or 8 characters, and it is checked to ensure
		// the resulting value is still a valid hexadecimal.

		if (value.length === 7) {
			value = value.substring(0, HEX_LENGTH_6);
		}
		else if (value.length > 8) {
			value = value.substring(0, HEX_LENGTH_8);
		}

		const color = tinycolor(value);

		if (!isHexFormat(color)) {
			return {};
		}

		validValue = internalToHex(color);
	}

	return {
		color: token?.value,
		label: tokenLabel,
		pickerColor,
		value: validValue,
	};
}

function internalToHex(color: TinyColorInstance) {
	if (color.getAlpha() < 1) {
		return color.toHex8().toUpperCase();
	}

	return color.toHex().toUpperCase();
}
