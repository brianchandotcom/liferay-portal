/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getAssetTitle} from '../../../../src/main/resources/META-INF/resources/js/common/utils/getAssetTitle';

describe('getAssetTitle', () => {
	it.each([
		[
			'the embedded title',
			{embedded: {id: 1, title: 'Embedded Title'}, title: 'Search Title'},
			'Embedded Title',
		],
		[
			'the search title when the title field has another name',
			{
				embedded: {id: 1, specName: 'Search Title'},
				title: 'Search Title',
			},
			'Search Title',
		],
		[
			'an empty title when the search title falls back to the ID',
			{embedded: {id: 1, specName: ''}, title: '1'},
			'',
		],
		['an empty title when there is no title', {embedded: {id: 1}}, ''],
		['an empty title when there is no item', undefined, ''],
	])('returns %s', (_description, item, expectedTitle) => {
		expect(getAssetTitle(item)).toBe(expectedTitle);
	});
});
