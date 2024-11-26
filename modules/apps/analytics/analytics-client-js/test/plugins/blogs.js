/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import userEvent from '@testing-library/user-event';
import fetchMock from 'fetch-mock';

import AnalyticsClient from '../../src/analytics';
import {blogTypes} from '../../src/plugins/blogs';
import {wait} from '../helpers';

const applicationId = 'Blog';

const googleUrl = 'http://google.com/';

const createBlogElement = (assetId, assetTitle) => {
	const blogElement = document.createElement('div');

	blogElement.dataset.analyticsAssetId = assetId || 'assetId';
	blogElement.dataset.analyticsAssetTitle = assetTitle || 'Blog Title 1';
	blogElement.dataset.analyticsAssetType = 'blog';
	blogElement.innerText =
		'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';

	document.body.appendChild(blogElement);

	return blogElement;
};

function createDynamicBlogElement(attrs) {
	const element = document.createElement('div');

	for (let index = 0; index < Object.keys(attrs).length; index++) {
		element.dataset[Object.keys(attrs)[index]] = attrs[index];
	}

	element.innerText =
		'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';

	document.body.appendChild(element);

	const paragraph = document.createElement('p');

	paragraph.href = googleUrl;

	setInnerHTML(paragraph, 'Paragraph inside a Blog');

	element.appendChild(paragraph);

	return [element, paragraph];
}

describe('Blogs Plugin', () => {
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

	describe('blogViewed event', () => {
		it('is fired for every blog on the page', async () => {
			const blogElement = createBlogElement();

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'blogViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId,
					eventId: 'blogViewed',
					properties: expect.objectContaining({
						entryId: 'assetId',
					}),
				})
			);

			document.body.removeChild(blogElement);
		});

		it('remove spaces between assetTitle and assetId', async () => {
			const blogElement = createBlogElement(
				' myAssetId ',
				' my asset title '
			);

			const domContentLoaded = new Event('DOMContentLoaded');

			await document.dispatchEvent(domContentLoaded);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'blogViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(1);

			expect(events[0]).toEqual(
				expect.objectContaining({
					applicationId,
					eventId: 'blogViewed',
					properties: expect.objectContaining({
						entryId: 'myAssetId',
						title: 'my asset title',
					}),
				})
			);

			document.body.removeChild(blogElement);
		});
	});

	describe('blogClicked event', () => {
		it('is fired when clicking an image inside a blog', async () => {
			const blogElement = createBlogElement();

			const imageInsideBlog = document.createElement('img');

			imageInsideBlog.src = googleUrl;

			blogElement.appendChild(imageInsideBlog);

			await userEvent.click(imageInsideBlog);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'blogClicked',
					properties: expect.objectContaining({
						entryId: 'assetId',
						src: googleUrl,
						tagName: 'img',
					}),
				}),
			]);

			document.body.removeChild(blogElement);
		});

		it('is fired when clicking a link inside a blog', async () => {
			const blogElement = createBlogElement();

			const text = 'Link inside a Blog';

			const linkInsideBlog = document.createElement('a');

			linkInsideBlog.href = googleUrl;

			setInnerHTML(linkInsideBlog, text);

			blogElement.appendChild(linkInsideBlog);

			await userEvent.click(linkInsideBlog);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'blogClicked',
					properties: expect.objectContaining({
						entryId: 'assetId',
						href: googleUrl,
						tagName: 'a',
						text,
					}),
				}),
			]);

			document.body.removeChild(blogElement);
		});

		it('is fired when clicking any other element inside a blog', async () => {
			const blogElement = createBlogElement();

			const paragraphInsideBlog = document.createElement('p');

			paragraphInsideBlog.href = googleUrl;

			setInnerHTML(paragraphInsideBlog, 'Paragraph inside a Blog');

			blogElement.appendChild(paragraphInsideBlog);

			await userEvent.click(paragraphInsideBlog);

			expect(Analytics.getEvents()).toEqual([
				expect.objectContaining({
					applicationId,
					eventId: 'blogClicked',
					properties: expect.objectContaining({
						entryId: 'assetId',
						tagName: 'p',
					}),
				}),
			]);

			document.body.removeChild(blogElement);
		});
	});

	describe('blogClicked required attributes', () => {
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
				const [element, paragraph] =
					await createDynamicBlogElement(attrs);

				await userEvent.click(paragraph);

				expect(Analytics.getEvents()).toEqual([]);

				document.body.removeChild(element);
			}
		);
	});

	describe('blog events with actions', () => {
		const createBlogElementWithAction = (action, type) => {
			const setDataset = (element, data) => {
				Object.entries(data).forEach(([key, value]) => {
					element.dataset[key] = value;
				});
			};

			const blogElement = document.createElement('div');

			setDataset(blogElement, {
				analyticsAssetAction: action,
				analyticsAssetId: 'assetId',
				analyticsAssetSubtype: 'basic-blog',
				analyticsAssetTitle: 'assetTitle',
				analyticsAssetType: type,
			});

			blogElement.innerText = `Lorem ipsum dolor, sit amet consectetur adipisicing elit.`;

			document.body.appendChild(blogElement);

			return blogElement;
		};

		it('is not fired when view blog with an incorrect action value', async () => {
			const element = createBlogElementWithAction('unknown', 'blog');

			jest.spyOn(element, 'getBoundingClientRect').mockImplementation(
				() => ({
					bottom: 500,
					height: 500,
					left: 0,
					right: 500,
					top: 0,
					width: 500,
				})
			);

			const domContentLoaded = new Event('DOMContentLoaded');

			document.dispatchEvent(domContentLoaded);

			await wait(250);

			const events = Analytics.getEvents().filter(
				({eventId}) => eventId === 'blogViewed'
			);

			expect(events.length).toBeGreaterThanOrEqual(0);

			document.body.removeChild(element);
		});

		it('is fired when view a blog with view and impression actions and correct types', async () => {
			blogTypes.forEach(async (type) => {
				[
					{action: 'view', eventId: 'blogViewed'},
					{action: 'impression', eventId: 'blogImpressionMade'},
				].forEach(async (props) => {
					const element = createBlogElementWithAction(
						props.action,
						type
					);

					jest.spyOn(
						element,
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

					document.dispatchEvent(domContentLoaded);

					await wait(250);

					const events = Analytics.getEvents().filter(
						({eventId}) => eventId === props.eventId
					);

					expect(events.length).toBeGreaterThanOrEqual(1);

					expect(events[0]).toEqual(
						expect.objectContaining({
							applicationId,
							eventId: props.eventId,
							properties: expect.objectContaining({
								action: props.action,
								articleId: 'assetId',
								subtype: 'basic-blog',
								type,
							}),
						})
					);

					document.body.removeChild(element);
				});
			});
		});
	});
});
