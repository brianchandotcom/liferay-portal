import AnalyticsClient from '../../src/analytics';
import dom from 'metal-dom';
import {expect} from 'chai';

const applicationId = 'WebContent';

const googleUrl = 'http://google.com/';

let Analytics;

const createWebContentElement = () => {
	const webContentElement = document.createElement('div');
	webContentElement.dataset.analyticsAssetId = 'assetId';
	webContentElement.dataset.analyticsAssetTitle = 'Web Content Title 1';
	webContentElement.dataset.analyticsAssetType = 'web-content';
	webContentElement.innerText = 'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';
	document.body.appendChild(webContentElement);
	return webContentElement;
};

describe('WebContent Plugin', () => {
	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();
	});

	beforeEach(() => {
		// Force attaching DOM Content Loaded event
		Object.defineProperty(document, 'readyState', {
			value: 'loading',
			writable: false,
		});

		fetchMock.mock('*', () => 200);
		Analytics = AnalyticsClient.create();
	});

	describe('webContentViewed event', () => {
		it('should be fired for every webContent on the page', () => {
			const webContentElement = createWebContentElement();

			const domContentLoaded = new Event('DOMContentLoaded');
			document.dispatchEvent(domContentLoaded);

			const events = Analytics.events.filter(
				({eventId}) => eventId === 'webContentViewed'
			);

			expect(events.length).to.be.at.least(1, 'At least one event should have been fired');

			events[0].should.deep.include({
				applicationId,
				eventId: 'webContentViewed',
			});

			expect(events[0].properties.articleId).to.equal('assetId');

			document.body.removeChild(webContentElement);
		});
	});

	describe('webContentClicked event', () => {
		it('should be fired when clicking an image inside a webContent', () => {
			const webContentElement = createWebContentElement();

			const imageInsideWebContent = document.createElement('img');
			imageInsideWebContent.src = googleUrl;
			webContentElement.appendChild(imageInsideWebContent);
			dom.triggerEvent(imageInsideWebContent, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'webContentClicked',
			});

			Analytics.events[0].properties.should.deep.include({
				articleId: 'assetId',
				src: googleUrl,
				tagName: 'img',
			});

			document.body.removeChild(webContentElement);
		});

		it('should be fired when clicking a link inside a webContent', () => {
			const webContentElement = createWebContentElement();
			const text = 'Link inside a WebContent';

			const linkInsideWebContent = document.createElement('a');
			linkInsideWebContent.href = googleUrl;
			linkInsideWebContent.innerHTML = text;
			webContentElement.appendChild(linkInsideWebContent);
			dom.triggerEvent(linkInsideWebContent, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'webContentClicked',
			});

			Analytics.events[0].properties.should.deep.include({
				articleId: 'assetId',
				href: googleUrl,
				tagName: 'a',
				text,
			});

			document.body.removeChild(webContentElement);
		});

		it('should be fired when clicking any other element inside a webContent', () => {
			const webContentElement = createWebContentElement();

			const paragraphInsideWebContent = document.createElement('p');
			paragraphInsideWebContent.href = googleUrl;
			paragraphInsideWebContent.innerHTML = 'Paragraph inside a WebContent';
			webContentElement.appendChild(paragraphInsideWebContent);
			dom.triggerEvent(paragraphInsideWebContent, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId,
				eventId: 'webContentClicked',
			});

			Analytics.events[0].properties.should.deep.include({
				articleId: 'assetId',
				tagName: 'p',
			});

			document.body.removeChild(webContentElement);
		});
	});
});