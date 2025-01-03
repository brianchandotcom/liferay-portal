/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import userEvent from '@testing-library/user-event';
import fetchMock from 'fetch-mock';

import AnalyticsClient from '../../src/analytics';
import {documentType} from '../../src/plugins/documentsFragment';
import {wait} from '../helpers';

const createElement = (tmpl) =>
	new DOMParser().parseFromString(tmpl, 'text/html').body.firstChild;

const createElementTitle = () => {
	const node = createElement(`
		<div data-analytics-asset-id="myDocumentId" data-analytics-asset-title="my document title" data-analytics-asset-type="${documentType}" data-analytics-asset-action="impression">
			this is a title
		</div>
	`);

	document.body.appendChild(node);

	return node;
};

const createFragmentWithImage = () => {
	const node = createElement(`
		<img src="..." data-analytics-asset-id="myDocumentId" data-analytics-asset-title="my image" data-analytics-asset-type="${documentType}" data-analytics-asset-action="view" />
	`);

	document.body.appendChild(node);

	return node;
};

const createFragmentWithLink = () => {
	const node = createElement(`
		<a data-analytics-asset-id="myDocumentId" data-analytics-asset-title="my document with link" data-analytics-asset-type="${documentType}" data-analytics-asset-action="download" href="#">
			this is a link
		</a>
	`);

	document.body.appendChild(node);

	return node;
};

function createDynamicDocumentsElement(attrs) {
	const documentElement = document.createElement('div');

	for (let index = 0; index < Object.keys(attrs).length; index++) {
		documentElement.dataset[Object.keys(attrs)[index]] = attrs[index];
	}

	const linkElement = document.createElement('a');

	linkElement.dataset.analyticsAssetAction = 'download';
	linkElement.dataset.href = '#';
	linkElement.innerText = 'download document';

	documentElement.appendChild(linkElement);
	document.body.appendChild(documentElement);

	return [documentElement, linkElement];
}

describe('Documents Plugin', () => {
	let Analytics;

	beforeEach(() => {

		// Force attaching DOM Content Loaded event

		Object.defineProperty(document, 'readyState', {
			writable: false,
		});

		fetchMock.mock('*', () => 200);

		Analytics = AnalyticsClient.create();
	});

	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();

		fetchMock.restore();
	});

	describe('documentImpressionMade event', () => {
		it('is fired when there is a document with a title on the page', async () => {
			const documentsElement = createElementTitle();

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'documentImpressionMade'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId: 'Document',
					eventId: 'documentImpressionMade',
					properties: {
						fileEntryId: 'myDocumentId',
						title: 'my document title',
						type: documentType,
					},
				})
			);

			document.body.removeChild(documentsElement);
		});

		it('is fired when there is a document with a link on the page', async () => {
			const documentsElement = createFragmentWithLink();

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'documentImpressionMade'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId: 'Document',
					eventId: 'documentImpressionMade',
					properties: {
						fileEntryId: 'myDocumentId',
						title: 'my document with link',
						type: documentType,
					},
				})
			);

			document.body.removeChild(documentsElement);
		});
	});

	describe('documentDownloaded event', () => {
		it('is fired when clicking in a fragment with a link', async () => {
			const documentsElement = createFragmentWithLink();

			await userEvent.click(documentsElement);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId: 'Document',
					eventId: 'documentDownloaded',
					properties: expect.objectContaining({
						fileEntryId: 'myDocumentId',
						title: 'my document with link',
						type: documentType,
					}),
				}),
			]);

			document.body.removeChild(documentsElement);
		});
	});

	describe('documentDownloaded required attributes', () => {
		it.each([
			[
				'assetId',
				{
					analyticsAssetTitle: 'assetTitle',
					analyticsAssetType: documentType,
				},
			],
			[
				'assetTitle',
				{
					analyticsAssetId: 'assetId',
					analyticsAssetType: documentType,
				},
			],
			[
				'assetType',
				{
					analyticsAssetId: 'assetId',
					analyticsAssetType: documentType,
				},
			],
		])(
			'is not fired if asset missing %s attribute',
			async (label, attrs) => {
				const [element, link] =
					await createDynamicDocumentsElement(attrs);

				await userEvent.click(link);

				expect(Analytics.getEvents()).toEqual([]);

				document.body.removeChild(element);
			}
		);
	});

	describe('documentPreviewed event', () => {
		it('is fired when clicking in a fragment with a image', async () => {
			const documentsElement = createFragmentWithImage();

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'documentPreviewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId: 'Document',
					eventId: 'documentPreviewed',
					properties: {
						fileEntryId: 'myDocumentId',
						title: 'my image',
						type: documentType,
					},
				})
			);

			document.body.removeChild(documentsElement);
		});
	});

	describe('documents events with actions', () => {
		const createDocumentsElementWithAction = (action) => {
			const setDataset = (element, data) => {
				Object.entries(data).forEach(([key, value]) => {
					element.dataset[key] = value;
				});
			};

			const documentElement = document.createElement('div');

			setDataset(documentElement, {
				analyticsAssetAction: action,
				analyticsAssetId: 'assetId',
				analyticsAssetSubtype: 'basic-document',
				analyticsAssetTitle: 'assetTitle',
				analyticsAssetType: documentType,
			});

			const linkElement = document.createElement('a');

			linkElement.dataset.analyticsAssetAction = 'download';
			linkElement.dataset.href = '#';
			linkElement.innerText = 'download document';

			documentElement.appendChild(linkElement);
			document.body.appendChild(documentElement);

			return documentElement;
		};

		it('is not fired when preview a document with an incorrect action value', async () => {
			const element = createDocumentsElementWithAction('unknown');

			const domContentLoaded = new Event('DOMContentLoaded');

			document.dispatchEvent(domContentLoaded);

			await wait(250);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'documentPreviewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(0);

			document.body.removeChild(element);
		});

		[
			{action: 'view', eventId: 'documentPreviewed'},
			{action: 'impression', eventId: 'documentImpressionMade'},
			{action: 'download', eventId: 'documentImpressionMade'},
		].forEach(async (props) => {
			it(`is fired ${props.eventId} when view a document with action ${props.action} and type: ${documentType}`, async () => {
				const element = createDocumentsElementWithAction(props.action);

				const domContentLoaded = new Event('DOMContentLoaded');

				document.dispatchEvent(domContentLoaded);

				await wait(250);

				const events = Analytics.getEvents().filter(
					({eventId}) => eventId === props.eventId
				);

				expect(events.length).toBeGreaterThanOrEqual(1);

				expect(events[0]).toEqual(
					expect.objectContaining({
						applicationId: 'Document',
						eventId: props.eventId,
						properties: expect.objectContaining({
							fileEntryId: 'assetId',
							subtype: 'basic-document',
							title: 'assetTitle',
							type: documentType,
						}),
					})
				);

				document.body.removeChild(element);
			});
		});
	});
});
