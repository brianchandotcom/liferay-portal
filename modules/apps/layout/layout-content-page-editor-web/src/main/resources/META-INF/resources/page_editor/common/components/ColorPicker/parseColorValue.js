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

import {convertRGBtoHex} from '../../../app/utils/convertRGBtoHex';
import {getValidHexColor} from '../../../app/utils/getValidHexColor';

const ERROR_MESSAGES = {
	mutuallyReferenced: Liferay.Language.get(
		'tokens-cannot-be-mutually-referenced'
	),
	selfReferenced: Liferay.Language.get('tokens-cannot-reference-itself'),
	valueNotExist: Liferay.Language.get('this-token-does-not-exist'),
};

export function parseColorValue({editedTokenValues, field, token, value}) {
	let validValue = token?.name;
	let tokenLabel = null;
	let pickerColor = null;

	if (token) {
		if (token.name === field.name) {
			return {error: ERROR_MESSAGES.selfReferenced};
		}

		if (editedTokenValues?.[token.name]?.name === field.name) {
			return {error: ERROR_MESSAGES.mutuallyReferenced};
		}

		tokenLabel = token.label;
	}
	else if (value.startsWith('#')) {
		validValue = getValidHexColor(value);

		if (!validValue) {
			return {};
		}

		pickerColor = validValue.replace('#', '');
	}
	else {
		const element = document.createElement('div');

		element.style.background = value;
		element.style.display = 'none';

		document.body.appendChild(element);

		validValue = element.style.background;

		if (validValue) {
			pickerColor = convertRGBtoHex(
				window.getComputedStyle(element).backgroundColor
			);
		}
		else {
			return {error: ERROR_MESSAGES.valueNotExist};
		}

		element.parentElement.removeChild(element);
	}

	return {
		label: tokenLabel,
		pickerColor,
		value: validValue,
		...(token && {color: token.value}),
	};
}
