/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as audiences from '../src/main/resources/META-INF/resources/main/implementation';
import {store} from '../src/main/resources/META-INF/resources/main/store';

describe('implementation', () => {
	afterEach(async () => {
		store.clear();
	});

	describe('runHandlers', () => {
		it('runs the handlers in the order they were registered and only once', async () => {
			const executionOrder: string[] = [];

			const audienceIds = ['a', 'b', 'c', 'd', 'e'];

			// Store audiences in reverse order so that we really test the registration is honored

			store.setPageAudienceIds(new Set(audienceIds.reverse()));

			for (const audienceId of audienceIds) {
				audiences.on(audienceId, () => {
					executionOrder.push(audienceId);
				});
			}

			await audiences.runHandlers();

			expect(executionOrder).toEqual(audienceIds);
			expect(executionOrder).toHaveLength(audienceIds.length);
		});

		it('clears the registered handlers after running', async () => {
			let runCount = 0;

			store.setPageAudienceIds(new Set(['a']));

			audiences.on('a', () => {
				runCount += 1;
			});

			await audiences.runHandlers();

			expect(runCount).toBe(1);

			// The handler was cleared, so a second run does not invoke it again

			await audiences.runHandlers();

			expect(runCount).toBe(1);
		});
	});
});
