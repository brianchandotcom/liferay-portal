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

import AnalyticsClient from '../src/analytics';

let Analytics;
let EVENT_ID = 0;

const ANALYTICS_IDENTITY = {email: 'foo@bar.com'};
const ENDPOINT_URL = 'https://ac-server.io';
const FLUSH_INTERVAL = 100;
const INITIAL_CONFIG = {
	dataSourceId: '1234',
	endpointUrl: ENDPOINT_URL
};
const MOCKED_REQUEST_DURATION = 5000;

// Local Storage keys
const STORAGE_KEY_EVENTS = 'ac_client_batch';
const STORAGE_KEY_USER_ID = 'ac_client_user_id';
const STORAGE_KEY_IDENTITY = 'ac_client_identity';

/**
 * Sends dummy events to test the Analytics API
 * @param {number} eventsNumber Number of events to send
 */
function sendDummyEvents(client, eventsNumber = 5) {
	for (let i = 0; i < eventsNumber; i++) {
		const applicationId = 'test';
		const eventId = EVENT_ID++;
		const properties = {
			a: 1,
			b: 2,
			c: 3
		};

		client.send(eventId, applicationId, properties);
	}
}

describe('Analytics Client', () => {
	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();
		fetchMock.restore();
	});

	beforeEach(() => {
		fetchMock.mock(/ac-server/gi, () => Promise.resolve(200));

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

		localStorage.removeItem(STORAGE_KEY_EVENTS);
		localStorage.removeItem(STORAGE_KEY_USER_ID);
	});

	it('is exposed in the global scope', () => {
		expect(Analytics).to.be.a('object');
	});

	it('exposes a "create" instantiation method', () => {
		Analytics.create.should.be.a('function');
	});

	it('accepts a configuration object', () => {
		const config = {a: 1, b: 2, c: 3};

		Analytics.reset();
		Analytics.dispose();

		Analytics = Analytics.create(config);
		Analytics.config.should.deep.equal(config);
	});

	describe('.flush', () => {
		it('is exposed as an Analytics static method', () => {
			Analytics.flush.should.be.a('function');
		});

		it('prevents overlapping requests', done => {
			fetchMock.restore();

			let fetchCalled = 0;

			fetchMock.mock(/ac-server/gi, () => {
				fetchCalled += 1;

				return new Promise(resolve => {
					setTimeout(() => resolve({}), MOCKED_REQUEST_DURATION);
				});
			});

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create({
				flushInterval: FLUSH_INTERVAL,
				...INITIAL_CONFIG
			});

			const spy = sinon.spy(Analytics, 'flush');

			sendDummyEvents(Analytics, 10);

			setTimeout(() => {
				// Flush must be called at least 3 times

				expect(spy.callCount).to.be.at.least(2);

				// Without sending another Fetch Request

				expect(fetchCalled).to.equal(1);

				Analytics.flush.restore();

				done();
			}, FLUSH_INTERVAL * 3);
		});

		it('regenerates the stored identity if the identity changed', () => {
			fetchMock.mock(/identity$/gi, () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			Analytics.setIdentity(ANALYTICS_IDENTITY);

			const previousIdentityHash = localStorage.getItem(
				STORAGE_KEY_IDENTITY
			);

			return Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			}).then(() => {
				const currentIdentityHash = localStorage.getItem(
					STORAGE_KEY_IDENTITY
				);

				expect(currentIdentityHash).not.to.equal(previousIdentityHash);
			});
		});

		it('reports identity changes to the Identity Service', () => {
			fetchMock.mock('*', () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			let identityCalled = 0;

			return Analytics.setIdentity(ANALYTICS_IDENTITY)
				.then(() => {
					fetchMock.restore();
					fetchMock.mock(/identity$/, () => {
						identityCalled += 1;
						return '';
					});
				})
				.then(() => Analytics.setIdentity({email: 'john@liferay.com'}))
				.then(() => expect(identityCalled).to.equal(1));
		});

		it("does not request the Identity Service when identity hasn't changed", () => {
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			let identityCalled = 0;

			return Analytics.setIdentity(ANALYTICS_IDENTITY)
				.then(() => {
					fetchMock.restore();
					fetchMock.mock(/identity$/, () => {
						identityCalled += 1;
						return '';
					});
				})
				.then(() => Analytics.setIdentity(ANALYTICS_IDENTITY))
				.then(() => expect(identityCalled).to.equal(0));
		});

		it('only clears the persisted events when done', () => {
			fetchMock.restore();
			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create({
				flushInterval: FLUSH_INTERVAL * 10,
				...INITIAL_CONFIG
			});

			fetchMock.mock(/ac-server/gi, () => {
				// Send events while flush is in progress
				sendDummyEvents(Analytics, 7);

				return new Promise(resolve => {
					setTimeout(() => resolve({}), 300);
				});
			});

			sendDummyEvents(Analytics, 5);

			return Analytics.flush().then(() => {
				const events = Analytics.events;

				events.should.have.lengthOf(7);
			});
		});

		it('preserves the user id whenever the set identity is called after a anonymous navigation', () => {
			fetchMock.mock(/ac-server/gi, () => Promise.resolve(200));
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			sendDummyEvents(Analytics, 1);

			Analytics.flush();

			const userId = localStorage.getItem(STORAGE_KEY_USER_ID);

			return Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			}).then(() => {
				expect(localStorage.getItem(STORAGE_KEY_USER_ID)).to.equal(
					userId
				);
			});
		});

		it('regenerates the user id on logouts or session expirations ', () => {
			fetchMock.mock(/ac-server/gi, () => Promise.resolve(200));
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			sendDummyEvents(Analytics, 1);

			Analytics.flush();

			const userId = localStorage.getItem(STORAGE_KEY_USER_ID);

			Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			});

			Analytics.reset();
			Analytics.dispose();

			sendDummyEvents(Analytics, 1);

			Analytics.flush().then(() => {
				expect(localStorage.getItem(STORAGE_KEY_USER_ID)).not.to.equal(
					userId
				);
			});
		});
	});

	describe('.send', () => {
		it('is exposed as an Analytics static method', () => {
			Analytics.send.should.be.a('function');
		});

		it('adds the given event to the event queue', () => {
			const eventId = 'eventId';
			const applicationId = 'applicationId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.send(eventId, applicationId, properties);

			const events = Analytics.events;

			events.should.have.lengthOf(1);

			events[0].should.deep.include({
				applicationId,
				eventId,
				properties
			});
		});

		it('persists the given events to the LocalStorage', () => {
			const eventsNumber = 5;

			sendDummyEvents(Analytics, eventsNumber);

			const events = JSON.parse(localStorage.getItem(STORAGE_KEY_EVENTS));

			expect(events).to.have.lengthOf.at.least(eventsNumber);
		});
	});
});
