/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Try to replace interpolated url arguments with item properties.
 * Set _redirect and/or backURL parameters to allow navigating back
 * to the FDS component that triggered the action
 *
 * @param url URI with an optional number of interpolated parameters
 * @param item object with properties that could match interpolated parameters
 * @param target string that indicates the type of the action: link, modal, sidepanel
 *
 * @example
 * url = '/o/data-sample/{id}
 * item = {
 *   name: 'test',
 *   id: 123
 * }
 *
 * Will return '/o/data-sample/123
 *
 * See test/utils/actionItems/formatActionURL.ts for more examples
 */

import getValueFromItem from '../getValueFromItem';

const formatActionURL = function (
	url: string | undefined,
	item: any,
	target?: string
): string {
	if (!url) {
		return '';
	}

	let fullInterpolation = false;

	const replacedURL = url.replace(
		/(?:%7B|{)(.*?)(?:%7D|})/g,
		(match, key) => {
			const value = getValueFromItem(item, key.split('.'));

			if (match.length === url.length) {
				fullInterpolation = true;
			}

			return fullInterpolation ? value : encodeURIComponent(value);
		}
	);

	const queryIndex = replacedURL.indexOf('?');

	if (target !== 'link' || queryIndex === -1 || fullInterpolation) {
		return replacedURL;
	}

	const hashIndex = replacedURL.indexOf('#');

	const searchParams = new URLSearchParams(
		replacedURL.slice(
			queryIndex,
			hashIndex > queryIndex ? hashIndex : replacedURL.length
		)
	);

	const backURL = 'backURL';
	const redirect = 'redirect';
	const backURLRegexp = new RegExp(backURL);
	const redirectRegexp = new RegExp(redirect);

	const p_p_id = searchParams.get('p_p_id');

	let modifiedParams = false;

	const redirectionURL = window.location.href;

	if (redirectRegexp.test(url) || backURLRegexp.test(url)) {
		for (const key of searchParams.keys()) {
			if (redirectRegexp.test(key) || backURLRegexp.test(key)) {
				searchParams.set(key, redirectionURL);
				modifiedParams = true;
			}
		}
	}
	else if (p_p_id) {
		searchParams.set(`_${p_p_id}_${redirect}`, redirectionURL);
		searchParams.set(`_${p_p_id}_${backURL}`, redirectionURL);

		modifiedParams = true;
	}

	if (!modifiedParams) {
		return replacedURL;
	}

	return (
		replacedURL.slice(0, queryIndex) +
		'?' +
		searchParams.toString() +
		(hashIndex > -1 ? replacedURL.slice(hashIndex) : '')
	);
};

export default formatActionURL;
