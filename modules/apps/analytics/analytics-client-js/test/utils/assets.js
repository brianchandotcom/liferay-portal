/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getNumberOfWords, isTrackable} from '../../src/utils/assets';

describe('getNumberOfWords()', () => {
	let document;

	beforeEach(() => {
		document = global.document;
	});

	it('returns the number of words', () => {
		const content = {
			description:
				'Build portals, intranets, websites and connected experiences on the most flexible platform around.',
			title: 'Digital Experience Software Tailored to Your Needs',
		};

		const markup = `<header class="header">
							<h2>${content.title}</h2>
							<p>${content.description}</p>
						</header>`;

		const element = document.createElement('div');

		setInnerHTML(element, markup);

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).toBe(20);
	});

	it('returns 0 if the number of words is empty', () => {
		const element = document.createElement('div');

		element.innerText = '';

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).toBe(0);
	});
});

describe('isTrackable', () => {
	it('checks if asset is trackable', () => {
		const element = document.createElement('div');

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetId = 'assetId';

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetTitle = 'assetTitle';

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetType = 'blog';

		expect(isTrackable(element)).toBeTruthy();
	});

	it('checks if asset is trackable with custom dataset list', () => {
		const element = document.createElement('div');

		expect(isTrackable(element, ['analyticsCustomName'])).toBeFalsy();

		element.dataset.analyticsAssetId = 'assetId';

		expect(isTrackable(element, ['analyticsCustomName'])).toBeFalsy();

		element.dataset.analyticsAssetTitle = 'assetTitle';

		expect(isTrackable(element, ['analyticsCustomName'])).toBeFalsy();

		element.dataset.analyticsAssetType = 'blog';

		expect(isTrackable(element, ['analyticsCustomName'])).toBeFalsy();

		element.dataset.analyticsCustomName = 'assetCustomNameAttr';

		expect(isTrackable(element, ['analyticsCustomName'])).toBeTruthy();
	});
});
