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

export const capitalize = text => {
	return text.charAt(0).toUpperCase() + text.slice(1);
};

const SPLIT_REGEX = /({\d+})/g;

const SPLIT_WORDS_REGEX = /({\w+})/g;

export function sub(langKey, args) {
	const keyArray = langKey.split(SPLIT_REGEX).filter(val => val.length !== 0);

	for (let i = 0; i < args.length; i++) {
		const arg = args[i];

		const indexKey = `{${i}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, arg);

			argIndex = keyArray.indexOf(indexKey);
		}
	}

	return keyArray.join('');
}

export function subWords(langKey, args) {
	const keyArray = langKey
		.split(SPLIT_WORDS_REGEX)
		.filter(val => val.length !== 0);

	Object.keys(args).forEach(arg => {
		const indexKey = `{${arg}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, args[arg]);

			argIndex = keyArray.indexOf(indexKey);
		}
	});

	return keyArray.join('');
}
