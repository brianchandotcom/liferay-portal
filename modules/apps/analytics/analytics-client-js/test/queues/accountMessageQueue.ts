/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore - Check possibility to install package in ts format

import fetchMock from 'fetch-mock';

import Analytics from '../../src/analytics';
import AccountMessageQueue from '../../src/queues/accountMessageQueue';
import {INITIAL_ANALYTICS_CONFIG} from '../helpers';

const analyticsInstance = Analytics.create({
	...INITIAL_ANALYTICS_CONFIG,
	endpointUrl: 'https://ac-server.io',
});

describe('AccountMessageQueue', () => {
	let accountMessageQueue: AccountMessageQueue;

	beforeEach(() => {
		fetchMock.mock(/demandbase-account$/i, () => Promise.resolve(200));

		accountMessageQueue = new AccountMessageQueue({
			analyticsInstance,
		});
	});

	afterEach(() => {
		fetchMock.restore();

		accountMessageQueue.reset();
	});

	it('posts to the demandbase-account endpoint', async () => {
		accountMessageQueue.addItem({
			company_name: 'Acme',
			emailAddressHashed: 'hashed-email',
			id: 'hash-1',
			userId: 'user-1',
		});

		await Promise.all(accountMessageQueue.onFlush());

		expect(fetchMock.called(/demandbase-account$/i)).toBe(true);
	});

	it('builds the endpoint from analytics config', () => {
		expect(analyticsInstance.config.demandbaseAccountEndpoint).toBe(
			'https://ac-server.io/demandbase-account'
		);
	});
});
