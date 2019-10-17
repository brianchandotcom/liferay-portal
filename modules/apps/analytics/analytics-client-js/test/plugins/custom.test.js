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

import {expect} from 'chai';
import fetchMock from 'fetch-mock';
import dom from 'metal-dom';

import AnalyticsClient from '../../src/analytics';

const applicationId = 'Custom';

const googleUrl = 'http://google.com/';

let Analytics;

const createCustomAssetElement = () => {
	const customAssetElement = document.createElement('div');
	customAssetElement.dataset.analyticsAssetCategory = 'custom-asset-category';
	customAssetElement.dataset.analyticsAssetId = 'assetId';
	customAssetElement.dataset.analyticsAssetTitle = 'Custom Asset Title 1';
	customAssetElement.dataset.analyticsAssetType = 'custom';
	customAssetElement.innerText =
		'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';

	document.body.appendChild(customAssetElement);

	return customAssetElement;
};

const createCustomAssetElementWithForm = () => {
	const customAssetElement = document.createElement('div');
	customAssetElement.dataset.analyticsAssetCategory = 'custom-asset-category';
	customAssetElement.dataset.analyticsAssetId = 'assetId';
	customAssetElement.dataset.analyticsAssetTitle = 'Custom Asset Title 1';
	customAssetElement.dataset.analyticsAssetType = 'custom';
	customAssetElement.innerHTML = `
		<form><input type="text" /><button type="submit" /></form>
	`;

	document.body.appendChild(customAssetElement);

	return customAssetElement;
};

describe('Custom Asset Plugin', () => {
	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();
	});

	beforeEach(() => {
		// Force attaching DOM Content Loaded event
		Object.defineProperty(document, 'readyState', {
			value: 'loading',
			writable: false
		});

		fetchMock.mock('*', () => 200);
		Analytics = AnalyticsClient.create();
	});

	describe('assetViewed event', () => {
		it('is fired for every custom asset on the page', () => {
			const customAssetElement = createCustomAssetElement();

			const domContentLoaded = new Event('DOMContentLoaded');
			document.dispatchEvent(domContentLoaded);

			const events = Analytics.events.filter(
				({eventId}) => eventId === 'assetViewed'
			);

			expect(events.length).to.be.at.least(
				1,
				'At least one event should have been fired'
			);

			events[0].should.deep.include({
				applicationId,
				eventId: 'assetViewed'
			});
			expect(events[0].properties.assetId).to.equal('assetId');

			document.body.removeChild(customAssetElement);
		});

		it('is fired with formEnabled if there is form element every custom asset on the page', () => {
			const customAssetElement = createCustomAssetElementWithForm();

			const domContentLoaded = new Event('DOMContentLoaded');
			document.dispatchEvent(domContentLoaded);

			const events = Analytics.events.filter(
				({eventId}) => eventId === 'assetViewed'
			);

			expect(events.length).to.be.at.least(
				1,
				'At least one event should have been fired'
			);

			events[0].should.deep.include({
				applicationId,
				eventId: 'assetViewed'
			});

			expect(events[0].properties.assetId).to.equal('assetId');
			expect(events[0].properties.formEnabled).to.equal(true);

			document.body.removeChild(customAssetElement);
		});
	});

	describe('assetClicked event', () => {
		it('is fired when clicking an image inside a custom asset', () => {
			const customAssetElement = createCustomAssetElement();

			const imageInsideCustomAsset = document.createElement('img');
			imageInsideCustomAsset.src = googleUrl;
			customAssetElement.appendChild(imageInsideCustomAsset);
			dom.triggerEvent(imageInsideCustomAsset, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'assetClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				assetId: 'assetId',
				src: googleUrl,
				tagName: 'img'
			});

			document.body.removeChild(customAssetElement);
		});

		it('is fired when clicking a link inside a custom asset', () => {
			const customAssetElement = createCustomAssetElement();
			const text = 'Link inside a Custom Asset';

			const linkInsideCustomAsset = document.createElement('a');
			linkInsideCustomAsset.href = googleUrl;
			linkInsideCustomAsset.innerHTML = text;
			customAssetElement.appendChild(linkInsideCustomAsset);
			dom.triggerEvent(linkInsideCustomAsset, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'assetClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				assetId: 'assetId',
				href: googleUrl,
				tagName: 'a',
				text
			});

			document.body.removeChild(customAssetElement);
		});

		it('is fired when clicking any other element inside a custom asset', () => {
			const customAssetElement = createCustomAssetElement();

			const paragraphInsideCustomAsset = document.createElement('p');
			paragraphInsideCustomAsset.href = googleUrl;
			paragraphInsideCustomAsset.innerHTML =
				'Paragraph inside a Custom Asset';
			customAssetElement.appendChild(paragraphInsideCustomAsset);
			dom.triggerEvent(paragraphInsideCustomAsset, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'assetClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				assetId: 'assetId',
				tagName: 'p'
			});

			document.body.removeChild(customAssetElement);
		});
	});

	describe('assetDownloaded', () => {
		it('is fired when clicking a link inside a custom asset', () => {
			const customAssetElement = createCustomAssetElement();
			const text = 'Link inside a Custom Asset';

			const linkInsideCustomAsset = document.createElement('a');
			linkInsideCustomAsset.href = '#';
			linkInsideCustomAsset.innerHTML = text;
			linkInsideCustomAsset.setAttribute(
				'data-analytics-asset-action',
				'download'
			);
			customAssetElement.appendChild(linkInsideCustomAsset);
			dom.triggerEvent(linkInsideCustomAsset, 'click');

			expect(Analytics.events.length).to.equal(2);

			Analytics.events[1].should.deep.include({
				applicationId,
				eventId: 'assetDownloaded'
			});

			document.body.removeChild(customAssetElement);
		});
	});
});
