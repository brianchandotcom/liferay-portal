/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ActivityMetrics} from '../types/ActivityMetrics';

const MOCK_ACTIVITY_METRICS: ActivityMetrics = {
	agentsCount: 18,
	averageResponseTimeMs: 2600,
	chatbotsCount: 4,
	monthlyAllowanceLRT: 10_000,
	monthlyConsumedLRT: 8972,
	prepaidBalanceLRT: 2500,
	prepaidExpiresAt: '2027-05-21T00:00:00Z',
	totalInteractions: 1245,
};

export async function getActivityMetrics(
	accountEntryExternalReferenceCode: string | undefined
): Promise<ActivityMetrics> {
	void accountEntryExternalReferenceCode;

	return Promise.resolve(MOCK_ACTIVITY_METRICS);
}
