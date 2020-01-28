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

import AnalyticsClient from '../src/analytics';

describe('Analytics Plugin Integration', () => {
	let Analytics;

	beforeEach(() => {
		Analytics = AnalyticsClient.create();
	});

	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();
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
