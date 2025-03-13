/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AnalyticsClient from '../src/analytics';
import {INITIAL_ANALYTICS_CONFIG} from './helpers';

describe('Analytics Plugin Integration', () => {
	let Analytics: AnalyticsClient;

	beforeEach(() => {
		Analytics = AnalyticsClient.create(INITIAL_ANALYTICS_CONFIG);
	});

	afterEach(() => {
		Analytics.reset();
		AnalyticsClient.dispose();
	});

	describe('registerPlugin()', () => {
		it('is exposed as an Analytics static method', () => {
			expect(typeof Analytics.registerPlugin).toBe('function');
		});

		it('processes the given plugin and execute its initialization logic', () => {
			const plugin = jest.fn();

			Analytics.registerPlugin(plugin);

			expect(plugin).toHaveBeenCalledWith(Analytics);
			expect(plugin.mock.calls.length).toBe(1);
		});
	});
});
