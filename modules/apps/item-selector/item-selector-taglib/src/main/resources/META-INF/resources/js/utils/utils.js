/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Utility function for substituting variables into language keys.
 *
 * Examples:
 * sub(Liferay.Language.get('search-x'), ['all'])
 * => 'search all'
 * sub(Liferay.Language.get('search-x'), [<b>all<b>], false)
 * => 'search <b>all</b>'
 *
 * @param {string} langKey This is the language key used from our properties file
 * @param {string} args Arguments to pass into language key
 * @param {string} join Boolean used to indicate whether to call `.join()` on
 * the array before it is returned. Use `false` if subbing in JSX.
 * @return {(string|Array)}
 */
export function sub(langKey, args) {
	const SPLIT_REGEX = /({\d+})/g;

	const keyArray = langKey
		.split(SPLIT_REGEX)
		.filter((val) => val.length !== 0);

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
