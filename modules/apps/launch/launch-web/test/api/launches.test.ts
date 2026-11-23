/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Launch,
	getLaunchStatus,
} from '../../src/main/resources/META-INF/resources/js/api/launches';

const LAUNCH: Launch = {
	id: 1,
	name: 'Halloween Campaign',
	publishErrorsCount: '0',
	status: {code: 2},
};

describe('getLaunchStatus', () => {
	it('returns failed only for a draft launch with publish errors', () => {
		expect(getLaunchStatus({...LAUNCH, publishErrorsCount: '2'})).toBe(
			'failed'
		);
		expect(getLaunchStatus(LAUNCH)).toBe('open');
		expect(
			getLaunchStatus({
				...LAUNCH,
				publishErrorsCount: '2',
				status: {code: 1},
			})
		).toBe('open');
		expect(
			getLaunchStatus({
				...LAUNCH,
				publishErrorsCount: '2',
				status: {code: 4},
			})
		).toBe('open');
	});

	it('returns published for an approved launch', () => {
		expect(getLaunchStatus({...LAUNCH, status: {code: 0}})).toBe(
			'published'
		);
	});

	it('returns scheduled for a scheduled launch', () => {
		expect(
			getLaunchStatus({
				...LAUNCH,
				publishErrorsCount: '2',
				status: {code: 7},
			})
		).toBe('scheduled');
	});
});
