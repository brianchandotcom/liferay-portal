/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../../src/types';
import {
	getNumberOfWords,
	isTrackable,
	transformAssetTypeToSelector,
} from '../../src/utils/assets';

describe('getNumberOfWords()', () => {
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

		const element = document.createElement(
			'div'
		) as unknown as Analytics.HTMLElement;

		element.innerHTML = markup;

		document.body.appendChild(element);

		setTimeout(() => {
			const numberOfWords = getNumberOfWords(element);

			expect(numberOfWords).toBe(20);
		}, 0);
	});

	it('returns 0 if the number of words is empty', () => {
		const element = document.createElement(
			'div'
		) as unknown as Analytics.HTMLElement;

		element.innerText = '';

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).toBe(0);
	});
});

describe('isTrackable', () => {
	it('checks if asset is trackable', () => {
		const element = document.createElement(
			'div'
		) as unknown as Analytics.HTMLElement;

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetId = 'assetId';

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetTitle = 'assetTitle';

		expect(isTrackable(element)).toBeFalsy();

		element.dataset.analyticsAssetType = Analytics.ElementType.Blog;

		expect(isTrackable(element)).toBeTruthy();
	});

	it('checks if asset is trackable with custom dataset list', () => {
		const element = document.createElement(
			'div'
		) as unknown as Analytics.HTMLElement;

		expect(
			isTrackable(element, [Analytics.DataSetList.AnalyticsAssetCategory])
		).toBeFalsy();

		element.dataset.analyticsAssetId = 'assetId';

		expect(
			isTrackable(element, [Analytics.DataSetList.AnalyticsAssetCategory])
		).toBeFalsy();

		element.dataset.analyticsAssetTitle = 'assetTitle';

		expect(
			isTrackable(element, [Analytics.DataSetList.AnalyticsAssetCategory])
		).toBeFalsy();

		element.dataset.analyticsAssetType = Analytics.ElementType.Blog;

		expect(
			isTrackable(element, [Analytics.DataSetList.AnalyticsAssetCategory])
		).toBeFalsy();

		element.dataset.analyticsAssetCategory = 'assetCategory';

		expect(
			isTrackable(element, [Analytics.DataSetList.AnalyticsAssetCategory])
		).toBeTruthy();
	});
});

describe('transformAssetTypeToSelector', () => {
	it('transforms a single asset type to a selector', () => {
		expect(
			transformAssetTypeToSelector(Analytics.ElementType.WebContent)
		).toEqual('[data-analytics-asset-type="web-content"]');
	});

	it('transforms multiple asset types to a selector', () => {
		expect(
			transformAssetTypeToSelector([
				Analytics.ElementType.WebContent,
				Analytics.ElementType.JournalArticle,
			])
		).toEqual(
			'[data-analytics-asset-type="web-content"], [data-analytics-asset-type="com.liferay.journal.model.JournalArticle"]'
		);
	});

	it('transforms a single asset type with suffix to a selector', () => {
		expect(
			transformAssetTypeToSelector(
				Analytics.ElementType.WebContent,
				':not([data-analytics-asset-viewed="true"])'
			)
		).toEqual(
			'[data-analytics-asset-type="web-content"]:not([data-analytics-asset-viewed="true"])'
		);
	});

	it('transforms multiple asset types with suffix to a selector', () => {
		expect(
			transformAssetTypeToSelector(
				[
					Analytics.ElementType.WebContent,
					Analytics.ElementType.JournalArticle,
				],
				':not([data-analytics-asset-viewed="true"])'
			)
		).toEqual(
			'[data-analytics-asset-type="web-content"]:not([data-analytics-asset-viewed="true"]), [data-analytics-asset-type="com.liferay.journal.model.JournalArticle"]:not([data-analytics-asset-viewed="true"])'
		);
	});
});
