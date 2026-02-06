/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../src/main/resources/META-INF/resources/liferay/index';

describe('works for combo URLs', () => {
	it('ending in /', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/combo/?browserId=firefox&minifierType=js&languageId=en_US&t=1771414670767&/o/frontend-js-aui-web/aui/aui/aui-min.js&/o/frontend-js-aui-web/liferay/modules.js',
			'frontend-js-web',
			'index'
		);

		expect(url).toEqual(
			'http://localhost/combo/../o/frontend-js-web/__liferay__/index.js'
		);
	});

	it('not ending in /', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/combo?browserId=firefox&minifierType=js&languageId=en_US&t=1771414670767&/o/frontend-js-aui-web/aui/aui/aui-min.js&/o/frontend-js-aui-web/liferay/modules.js',
			'frontend-js-web',
			'index'
		);

		expect(url).toEqual(
			'http://localhost/combo/../o/frontend-js-web/__liferay__/index.js'
		);
	});
});

describe('works for hashed web context URLs', () => {
	it('without query string or fragment', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/o/js/-/frontend-js-web(HASH)/__liferay__/index.js',
			'frontend-js-web',
			'legacy'
		);

		expect(url).toEqual(
			'http://localhost/o/js/-/frontend-js-web(HASH)/__liferay__/index.js/../../../frontend-js-web/__liferay__/legacy.js'
		);
	});

	it('with query string and fragment', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/o/js/-/frontend-js-web(HASH)/__liferay__/index.js?q=1#frag',
			'frontend-js-web',
			'legacy'
		);

		expect(url).toEqual(
			'http://localhost/o/js/-/frontend-js-web(HASH)/__liferay__/index.js/../../../frontend-js-web/__liferay__/legacy.js'
		);
	});
});

describe('works for regular URLs', () => {
	it('without query string or fragment', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/o/frontend-js-web/__liferay__/index.js',
			'frontend-js-web',
			'legacy'
		);

		expect(url).toEqual(
			'http://localhost/o/frontend-js-web/__liferay__/index.js/../../../frontend-js-web/__liferay__/legacy.js'
		);
	});

	it('with query string and fragment', () => {
		const url = Liferay.FrontendESM.buildURL(
			'http://localhost/o/frontend-js-web/__liferay__/index.js?q=1#frag',
			'frontend-js-web',
			'legacy'
		);

		expect(url).toEqual(
			'http://localhost/o/frontend-js-web/__liferay__/index.js/../../../frontend-js-web/__liferay__/legacy.js'
		);
	});
});
