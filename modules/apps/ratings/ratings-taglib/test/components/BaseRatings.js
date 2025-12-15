/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import RATINGS_TYPES from '../../src/main/resources/META-INF/resources/js/RATINGS_TYPES';
import {sendVoteRequest} from '../../src/main/resources/META-INF/resources/js/components/BaseRatings';

let tempSession;

describe('BaseRatings', () => {
	describe('sendVoteRequest calls fetch when Liferay.Session is undefined', () => {
		beforeEach(() => {
			global.fetch = jest.fn(() =>
				Promise.resolve({
					json: () => Promise.resolve({id: 1, name: 'Evan'}),
				})
			);

			tempSession = Liferay.Session;

			Liferay.Session = undefined;
		});

		afterEach(() => {
			jest.resetAllMocks();

			Liferay.Session = tempSession;
		});

		it('ensures Liferay.Session is undefined', async () => {
			expect(Liferay.Session).toBeUndefined();
		});

		it('calls fetch once', async () => {
			await sendVoteRequest({
				className: 'className',
				classPK: 0,
				contentTitle: 'test',
				externalReferenceCode: 'ERC123',
				score: 3,
				type: RATINGS_TYPES.STARS,
			});

			expect(fetch).toHaveBeenCalledTimes(1);
		});
	});
});
