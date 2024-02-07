/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Try to replace interpolated url arguments with item properties
 *
 * @param url URI with an optional number of interpolated parameters
 * @param item object with properties that could match interpolated parameters
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

const formatActionURL = function (url: string | undefined, item: any): string {
	if (!url) {
		return '';
	}

	const replacedURL = url.replace(new RegExp('{(.*?)}', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('.')
		)
	);

	return replacedURL.replace(new RegExp('(%7B.*?%7D)', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('.')
		)
	);
};

export default formatActionURL;
