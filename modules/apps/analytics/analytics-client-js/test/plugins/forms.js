/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'fetch-mock';

import AnalyticsClient from '../../src/analytics';

const applicationId = 'Form';

const createDynamicFormElement = async (attrs) => {
	const element = document.createElement('div');

	for (let index = 0; index < Object.keys(attrs).length; index++) {
		element.dataset[Object.keys(attrs)[index]] = attrs[index];
	}

	document.body.appendChild(element);

	element.addEventListener('submit', (event) => event.preventDefault());

	const event = new Event('submit', {
		cancelable: true,
	});

	await element.dispatchEvent(event);

	return element;
};

describe('Forms Plugin', () => {
	let Analytics;
	let duration;

	beforeEach(() => {

		// Force attaching DOM Content Loaded event

		Object.defineProperty(document, 'readyState', {
			value: 'loading',
			writable: false,
		});

		if (!global.performance.clearMarks) {
			global.performance.clearMarks = () => {};
		}

		if (!global.performance.mark) {
			global.performance.mark = () => {};
		}

		if (!global.performance.measure) {
			global.performance.measure = () => {};
		}

		if (!global.performance.getEntriesByName) {
			global.performance.getEntriesByName = () => [
				{
					duration: duration || 1,
				},
			];
		}

		fetchMock.mock('*', () => 200);

		Analytics = AnalyticsClient.create();
	});

	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();

		fetchMock.restore();
	});

	describe('formViewed event', () => {
		it('is fired for every form on the page', async () => {
			const formElement = document.createElement('form');

			formElement.dataset.analyticsAssetId = 'assetId';
			formElement.dataset.analyticsAssetTitle = 'Form Title 1';
			formElement.dataset.analyticsAssetType = 'form';

			document.body.appendChild(formElement);

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'formViewed'
			);

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'formViewed',
					properties: expect.objectContaining({
						formId: 'assetId',
						title: 'Form Title 1',
					}),
				}),
			]);

			document.body.removeChild(formElement);
		});

		it('remove spaces between assetTitle and assetId', async () => {
			const formElement = document.createElement('form');

			formElement.dataset.analyticsAssetId = ' assetId ';
			formElement.dataset.analyticsAssetTitle = ' Form Title 1 ';
			formElement.dataset.analyticsAssetType = 'form';

			document.body.appendChild(formElement);

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'formViewed'
			);

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'formViewed',
					properties: expect.objectContaining({
						formId: 'assetId',
						title: 'Form Title 1',
					}),
				}),
			]);

			document.body.removeChild(formElement);
		});
	});

	describe('formSubmitted event', () => {
		it('is fired when a form is submitted', async () => {
			const form = document.createElement('form');

			form.dataset.analyticsAssetId = 'formId';
			form.dataset.analyticsAssetTitle = 'Form Title';
			form.dataset.analyticsAssetType = 'form';

			document.body.appendChild(form);

			form.addEventListener('submit', (event) => event.preventDefault());

			const event = new Event('submit', {
				cancelable: true,
			});

			await form.dispatchEvent(event);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'formSubmitted'
			);

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'formSubmitted',
					properties: {
						formId: 'formId',
						title: 'Form Title',
					},
				}),
			]);
		});
	});

	describe('fieldFocused event', () => {
		it('is fired whenever a field is focused', async () => {
			const form = document.createElement('form');

			form.dataset.analyticsAssetId = 'formId';
			form.dataset.analyticsAssetTitle = 'Form Title';
			form.dataset.analyticsAssetType = 'form';

			document.body.appendChild(form);

			const field = document.createElement('input');

			field.name = 'myField';
			field.type = 'text';

			form.appendChild(field);

			await field.dispatchEvent(new Event('focus'));

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'fieldFocused'
			);

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'fieldFocused',
					properties: {
						fieldName: 'myField',
						formId: 'formId',
						title: 'Form Title',
					},
				}),
			]);
		});
	});

	describe('fieldBlurred event', () => {
		it('is fired whenever a field is blurred', async () => {
			const form = document.createElement('form');

			form.dataset.analyticsAssetId = 'formId';
			form.dataset.analyticsAssetTitle = 'Form Title';
			form.dataset.analyticsAssetType = 'form';

			document.body.appendChild(form);

			const field = document.createElement('input');

			field.name = 'myField';
			field.type = 'text';

			form.appendChild(field);

			field.dispatchEvent(new Event('focus'));

			// Fake timing.

			duration = 1500;

			await field.dispatchEvent(new Event('blur'));

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'fieldBlurred'
			);

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'fieldBlurred',
					properties: expect.objectContaining({
						fieldName: 'myField',
						focusDuration: expect.any(Number),
						formId: 'formId',
						title: 'Form Title',
					}),
				}),
			]);

			expect(events[0].properties.focusDuration).toBeGreaterThanOrEqual(
				1500
			);
		});
	});

	describe('formSubmitted required attributes', () => {
		it.each([
			[
				'assetId',
				{
					analyticsAssetTitle: 'assetTitle',
					analyticsAssetType: 'blog',
				},
			],
			[
				'assetTitle',
				{
					analyticsAssetId: 'assetId',
					analyticsAssetType: 'blog',
				},
			],
			[
				'assetType',
				{
					analyticsAssetId: 'assetId',
					analyticsAssetType: 'assetTitle',
				},
			],
		])(
			'is not fired if asset missing %s attribute',
			async (label, attrs) => {
				const element = await createDynamicFormElement(attrs);

				const events = Analytics.getEvents().filter(
					({eventId}) => eventId === 'formSubmitted'
				);

				expect(events).toEqual([]);

				document.body.removeChild(element);
			}
		);
	});
});
