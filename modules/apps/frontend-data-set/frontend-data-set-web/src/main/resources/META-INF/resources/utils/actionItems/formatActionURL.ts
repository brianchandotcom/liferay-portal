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
 * It also admits encoded URL
 * url = '/o/data-sample/%7Bid%7D'
 *
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

	const replacedURL = url.replace(
		/(?:%7B|{)(.*?)(?:%7D|})/g,
		(match, key) => {
			const value = getValueFromItem(item, key.split('.'));
			const isFullyWrapped = match.length === url.length;

			return isFullyWrapped
				? encodeURI(value)
				: encodeURIComponent(value);
		}
	);

	if (target === 'link' && replacedURL.includes('?')) {
		const redirectionURL = window.location.href;
		const searchParams = new URLSearchParams(
			replacedURL.slice(replacedURL.indexOf('?'))
		);

		const backURL = 'backURL';
		const redirect = 'redirect';
		const backURLRegexp = new RegExp(backURL);
		const redirectRegexp = new RegExp(redirect);

		const p_p_id = searchParams.get('p_p_id');

		if (redirectRegexp.test(url) || backURLRegexp.test(url)) {
			for (const key of searchParams.keys()) {
				if (redirectRegexp.test(key) || backURLRegexp.test(key)) {
					searchParams.set(key, redirectionURL);
				}
			}
		}
		else if (p_p_id) {
			const backURLParam = `_${p_p_id}_${backURL}`;
			const redirectParam = `_${p_p_id}_${redirect}`;

			searchParams.set(redirectParam, redirectionURL);
			searchParams.set(backURLParam, redirectionURL);
		}

		const updatedURL = decodeURIComponent(
			`${replacedURL.slice(
				0,
				replacedURL.indexOf('?')
			)}?${searchParams.toString()}`
		);

		return updatedURL;
	}

	return replacedURL;
};

export default formatActionURL;
