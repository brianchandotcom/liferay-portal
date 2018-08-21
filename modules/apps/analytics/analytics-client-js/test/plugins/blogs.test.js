import AnalyticsClient from '../../src/analytics';
import dom from 'metal-dom';
import {assert, expect} from 'chai';

const applicationId = 'Blog';

const googleUrl = 'http://google.com/';

let Analytics;

const createBlogElement = () => {
	const blogElement = document.createElement('blog');
	blogElement.dataset.analyticsAssetId = 'assetId';
	blogElement.dataset.analyticsAssetTitle = 'Blog Title 1';
	blogElement.dataset.analyticsAssetType = 'blog';
	blogElement.innerText = 'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';
	document.body.appendChild(blogElement);
	return blogElement;
}

describe('Blogs Plugin', () => {
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

	describe('blogViewed event', () => {
		it('should be fired for every blog on the page', () => {
			const blogElement = createBlogElement();

			const domContentLoaded = new Event('DOMContentLoaded');
			document.dispatchEvent(domContentLoaded);

			const events = Analytics.events.filter(
				({eventId, properties}) => eventId === 'blogViewed'
			);

			expect(events.length).to.be.at.least(1, 'At least one event should have been fired');

			events[0].should.deep.include({
				applicationId: applicationId,
				eventId: 'blogViewed'
			});
			expect(events[0].properties.entryId).to.equal('assetId');

			document.body.removeChild(blogElement);
		});
	});

	describe('blogClicked event', () => {
		it('should be fired when clicking an image inside a blog', () => {
			const blogElement = createBlogElement();

			const imageInsideBlog = document.createElement('img');
			imageInsideBlog.src = googleUrl;
			blogElement.appendChild(imageInsideBlog);
			dom.triggerEvent(imageInsideBlog, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId: applicationId,
				eventId: 'blogClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				entryId: 'assetId',
				src: googleUrl,
				tagName: 'img'
			});

			document.body.removeChild(blogElement);
		});

		it('should be fired when clicking a link inside a blog', () => {
			const blogElement = createBlogElement();
			const text = 'Link inside a Blog';

			const linkInsideBlog = document.createElement('a');
			linkInsideBlog.href = googleUrl;
			linkInsideBlog.innerHTML = text;
			blogElement.appendChild(linkInsideBlog);
			dom.triggerEvent(linkInsideBlog, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId: applicationId,
				eventId: 'blogClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				entryId: 'assetId',
				href: googleUrl,
				tagName: 'a',
				text
			});

			document.body.removeChild(blogElement);
		});

		it('should be fired when clicking any other element inside a blog', () => {
			const blogElement = createBlogElement();

			const paragraphInsideBlog = document.createElement('p');
			paragraphInsideBlog.href = googleUrl;
			paragraphInsideBlog.innerHTML = 'Paragraph inside a Blog';
			blogElement.appendChild(paragraphInsideBlog);
			dom.triggerEvent(paragraphInsideBlog, 'click');

			expect(Analytics.events.length).to.equal(1);

			Analytics.events[0].should.deep.include({
				applicationId: applicationId,
				eventId: 'blogClicked'
			});

			Analytics.events[0].properties.should.deep.include({
				entryId: 'assetId',
				tagName: 'p'
			});

			document.body.removeChild(blogElement);
		});
	});
});