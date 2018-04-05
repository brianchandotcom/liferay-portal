import AnalyticsClient from '../src/analytics';
import {assert, expect} from 'chai';

let Analytics = AnalyticsClient.create();

/**
 * Sends dummy events to test the Analytics API
 * @param {number} eventsNumber Number of events to send
 */
function sendDummyEvents(eventsNumber = 5) {
	for (let i = 0; i <= eventsNumber; i++) {
		const applicationId = 'test';
		const eventId = i;
		const properties = {
			a: 1,
			b: 2,
			c: 3,
		};

		Analytics.send(eventId, applicationId, properties);
	}
}

describe('Analytics MiddleWare Integration', () => {
	beforeEach(() => {
		fetchMock.mock('*', () => 200);
		Analytics.create();
	});

	afterEach(() => {
		Analytics.dispose();
		fetchMock.restore();
	});

	describe('.registerMiddleware', () => {
		it('should be exposed as an Analytics static method', () => {
			Analytics.registerMiddleware.should.be.a('function');
		});

		it('shuld process the given middleware', () => {
			const middleware = (req, analytics) => {
				analytics.should.be.equal(Analytics);
				req.should.be.a('object');

				return req;
			};

			const spy = sinon.spy(middleware);

			Analytics.registerMiddleware(spy);

			sendDummyEvents();

			return Analytics.flush()
				.then(
					() => {
						assert.isTrue(spy.calledOnce);
					}
				)
				.catch(
					(e) => {
						console.log('caught', e);
					}
				)
		});
	});

	describe('default middlewares', () => {
		it('should include document metadata by default', (done) => {
			let body = null;

			fetchMock.restore();
			fetchMock.mock(
				'*',
				function(url, opts) {
					body = JSON.parse(opts.body);

					return 200;
				}
			);

			sendDummyEvents();

			Analytics.flush()
				.then(
					() => {
						expect(body.context).to.include.all.keys(
							'canonicalUrl',
							'contentLanguageId',
							'description',
							'keywords',
							'languageId',
							'referrer',
							'title',
							'url',
							'userAgent',
						);

						done();
					}
				).catch(done);
		});
	});
});