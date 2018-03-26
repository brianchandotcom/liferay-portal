const ANALYTICS_KEY = 'ANALYTICS_KEY';
const ANALYTICS_IDENTITY = {email: 'foo@bar.com'};
const FLUSH_INTERVAL = 100;
const LOCAL_USER_ID = 'LOCAL_USER_ID';
const SERVICE_USER_ID = 'SERVICE_USER_ID';
const STORAGE_KEY_EVENTS = 'lcs_client_batch';
const STORAGE_KEY_USER_ID = 'lcs_client_user_id';

const Analytics = window.Analytics;
const assert = chai.assert;
const expect = chai.expect;
const fetchMock = window.fetchMock;

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

describe('Analytics Client', () => {
	afterEach(() => {
		Analytics.dispose();
		fetchMock.restore();
	});

	beforeEach(
		() => {
			Analytics.create();
			localStorage.removeItem(STORAGE_KEY_EVENTS);
			localStorage.removeItem(STORAGE_KEY_USER_ID);
		}
	);

	it('should be exposed in the global scope', () => {
		expect(Analytics).to.be.a('object');
	});

	it('expose a "create" instantiation method', () => {
		Analytics.create.should.be.a('function');
	});

	it('should accept a configuration object', () => {
		const config = {a: 1, b: 2, c: 3};

		Analytics.create(config);
		Analytics.config.should.deep.equal(config);
	});

	describe('.flush', () => {
		it('should be exposed as an Analytics static method', () => {
			Analytics.flush.should.be.a('function');
		});

		it('should prevent overlapping requests', function(done) {
			let fetchCalled = 0;

			fetchMock.mock(
				'*',
				function() {
					fetchCalled += 1;

					return new Promise(
						resolve => {
							setTimeout(resolve, 10000);
						}
					);
				}
			);

			Analytics.dispose();
			Analytics.create(
				{
					flushInterval: FLUSH_INTERVAL,
				}
			);

			sendDummyEvents();

			const spy = sinon.spy(Analytics, 'flush');

			setTimeout(
				() => {
					// Flush must be called 3 times

					assert.isTrue(spy.calledThrice);

					// Without sending another Fetch Request

					expect(fetchCalled).to.equal(1);

					Analytics.flush.restore();

					done();
				},
				FLUSH_INTERVAL * 3.9
			);
		});

		it('should fetch the userId from the identity Service when it is not found on storage', function(done) {
			let identityCalled = 0;
			let identityReceived = '';
			let identityUrl = '';

			fetchMock.mock(
				/identity/,
				function(url) {
					identityCalled += 1;
					identityUrl = url;

					return SERVICE_USER_ID;
				}
			);

			fetchMock.mock(
				'*',
				function(url, opts) {
					identityReceived = JSON.parse(opts.body).userId;

					return 200;
				}
			);

			Analytics.create(
				{
					analyticsKey: ANALYTICS_KEY
				}
			);

			sendDummyEvents();

			Analytics.flush()
				.then(
					() => {
						// Identity Service was called

						expect(identityCalled).to.equal(1);
						expect(identityUrl.indexOf(ANALYTICS_KEY) >= 0);

						// Analytics Service was called and passed the Service User Id

						expect(identityReceived).to.equal(SERVICE_USER_ID);

						done();
					}
				);
		});

		it('should use previously stored userIds from the Identity Service', function(done) {
			localStorage.setItem(STORAGE_KEY_USER_ID, `"${LOCAL_USER_ID}"`);

			let identityCalled = 0;
			let identityReceived = '';
			let identityUrl = '';

			fetchMock.mock(
				/identity/,
				function(url) {
					identityCalled += 1;
					identityUrl = url;

					return SERVICE_USER_ID;
				}
			);

			fetchMock.mock(
				'*',
				function(url, opts) {
					identityReceived = JSON.parse(opts.body).userId;

					return 200;
				}
			);

			Analytics.create(
				{
					analyticsKey: ANALYTICS_KEY,
					flushInterval: FLUSH_INTERVAL,
				}
			);

			sendDummyEvents();

			Analytics.flush()
				.then(
					() => {
						// Identity Service was NOT called

						expect(identityCalled).to.equal(0);

						// Analytics Service was NOT called and passed the Local User Id

						expect(identityReceived).to.equal(LOCAL_USER_ID);

						done();
					}
				);
		});

		it('should get a new userId from the Identity Service if the user identity changed', function(done) {
			localStorage.setItem(STORAGE_KEY_USER_ID, `"${LOCAL_USER_ID}"`);

			let identityCalled = 0;
			let identityReceived = '';
			let identitySent = null;
			let identityUrl = '';

			fetchMock.mock(
				/identity/,
				function(url, opts) {
					identityCalled += 1;
					identityUrl = url;
					identitySent = JSON.parse(opts.body).identity;

					return SERVICE_USER_ID;
				}
			);

			fetchMock.mock(
				'*',
				function(url, opts) {
					identityReceived = JSON.parse(opts.body).userId;

					return 200;
				}
			);

			Analytics.create(
				{
					analyticsKey: ANALYTICS_KEY,
				}
			);

			sendDummyEvents();

			Analytics.setIdentity(ANALYTICS_IDENTITY);

			Analytics.flush()
				.then(
					() => {
						// Identity Service WAS called with the user identity

						expect(identityCalled).to.equal(1);
						ANALYTICS_IDENTITY.should.deep.equal(identitySent)

						// Analytics Service was called and passed the Service User Id

						expect(identityReceived).to.equal(SERVICE_USER_ID);

						done();
					}
				);
		});
	});

	describe('.send', () => {
		it('should be exposed as an Analytics static method', () => {
			Analytics.send.should.be.a('function');
		});

		it('should add the given event to the event queue', () => {
			const eventId = 'eventId';
			const applicationId = 'applicationId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.create();
			Analytics.send(eventId, applicationId, properties);

			const events = Analytics.events;

			events.should.have.lengthOf(1);

			events[0].should.deep.include({
				eventId,
				applicationId,
				properties,
			});
		});

		it('should persist the given events to the LocalStorage', () => {
			const eventsNumber = 5;

			Analytics.create();

			sendDummyEvents(eventsNumber);

			const events = JSON.parse(localStorage.getItem(STORAGE_KEY_EVENTS));

			events.should.have.lengthOf.at.least(eventsNumber);
		});
	});
});