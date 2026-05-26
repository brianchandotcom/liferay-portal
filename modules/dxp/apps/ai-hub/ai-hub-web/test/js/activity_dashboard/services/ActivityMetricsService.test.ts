/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getActivityMetrics} from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/services/ActivityMetricsService';

describe('ActivityMetricsService', () => {
	it('returns the mocked ActivityMetrics shape', async () => {
		const metrics = await getActivityMetrics('ACCOUNT_ERC');

		expect(metrics).toEqual({
			agentsCount: expect.any(Number),
			averageResponseTimeMs: expect.any(Number),
			chatbotsCount: expect.any(Number),
			monthlyAllowanceLRT: expect.any(Number),
			monthlyConsumedLRT: expect.any(Number),
			prepaidBalanceLRT: expect.any(Number),
			prepaidExpiresAt: expect.any(String),
			totalInteractions: expect.any(Number),
		});
	});
});
