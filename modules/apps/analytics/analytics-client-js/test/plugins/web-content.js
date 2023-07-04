/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import userEvent from '@testing-library/user-event';
import fetchMock from 'fetch-mock';

import AnalyticsClient from '../../src/analytics';
import {wait} from '../helpers';

const applicationId = 'WebContent';

const googleUrl = 'http://google.com/';

const createWebContentElement = (assetId, assetTitle) => {
	const webContentElement = document.createElement('div');

	webContentElement.dataset.analyticsAssetId = assetId || 'assetId';
	webContentElement.dataset.analyticsAssetTitle =
		assetTitle || 'Web Content Title 1';
	webContentElement.dataset.analyticsAssetType = 'web-content';
	webContentElement.innerText =
		'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';

	document.body.appendChild(webContentElement);

	return webContentElement;
};

describe('WebContent Plugin', () => {
	let Analytics;

	beforeEach(() => {

		// Force attaching DOM Content Loaded event

		Object.defineProperty(document, 'readyState', {
			value: 'loading',
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

	describe('webContentViewed event', () => {
		it('is fired when web-content is in viewport', async () => {
			const webContentElement = createWebContentElement();

			jest.spyOn(
				webContentElement,
				'getBoundingClientRect'
			).mockImplementation(() => ({
				bottom: 500,
				height: 500,
				left: 0,
				right: 500,
				top: 0,
				width: 500,
			}));

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			await wait(250);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'webContentViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId,
					eventId: 'webContentViewed',
					properties: expect.objectContaining({
						articleId: 'assetId',
					}),
				})
			);

			document.body.removeChild(webContentElement);
		});

		it('is not fired when web-content is not in viewport', async () => {
			const webContentElement = createWebContentElement();

			jest.spyOn(
				webContentElement,
				'getBoundingClientRect'
			).mockImplementation(() => ({
				bottom: 1500,
				height: 500,
				left: 0,
				right: 500,
				top: 1000,
				width: 500,
			}));

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			await wait(250);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'webContentViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(0);

			document.body.removeChild(webContentElement);
		});

		it('remove spaces between assetTitle and assetId', async () => {
			const webContentElement = createWebContentElement(
				' myAssetId ',
				' my asset title '
			);

			jest.spyOn(
				webContentElement,
				'getBoundingClientRect'
			).mockImplementation(() => ({
				bottom: 500,
				height: 500,
				left: 0,
				right: 500,
				top: 0,
				width: 500,
			}));

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			await wait(250);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'webContentViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId,
					eventId: 'webContentViewed',
					properties: expect.objectContaining({
						articleId: 'myAssetId',
						title: 'my asset title',
					}),
				})
			);

			document.body.removeChild(webContentElement);
		});
	});

	describe('webContentClicked event', () => {
		it('is fired when clicking an image inside a webContent', async () => {
			const webContentElement = createWebContentElement();

			const imageInsideWebContent = document.createElement('img');

			imageInsideWebContent.src = googleUrl;

			webContentElement.appendChild(imageInsideWebContent);

			await userEvent.click(imageInsideWebContent);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'webContentClicked',
					properties: expect.objectContaining({
						articleId: 'assetId',
						src: googleUrl,
						tagName: 'img',
					}),
				}),
			]);

			document.body.removeChild(webContentElement);
		});

		it('is fired when clicking a link inside a webContent', async () => {
			const webContentElement = createWebContentElement();

			const text = 'Link inside a WebContent';

			const linkInsideWebContent = document.createElement('a');

			linkInsideWebContent.href = googleUrl;

			setInnerHTML(linkInsideWebContent, text);

			webContentElement.appendChild(linkInsideWebContent);

			await userEvent.click(linkInsideWebContent);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'webContentClicked',
					properties: expect.objectContaining({
						articleId: 'assetId',
						href: googleUrl,
						tagName: 'a',
						text,
					}),
				}),
			]);

			document.body.removeChild(webContentElement);
		});

		it('is fired when clicking any other element inside a webContent', async () => {
			const webContentElement = createWebContentElement();

			const paragraphInsideWebContent = document.createElement('p');

			paragraphInsideWebContent.href = googleUrl;

			setInnerHTML(
				paragraphInsideWebContent,
				'Paragraph inside a WebContent'
			);

			webContentElement.appendChild(paragraphInsideWebContent);

			await userEvent.click(paragraphInsideWebContent);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'webContentClicked',
					properties: expect.objectContaining({
						articleId: 'assetId',
						tagName: 'p',
					}),
				}),
			]);

			document.body.removeChild(webContentElement);
		});
	});
});
