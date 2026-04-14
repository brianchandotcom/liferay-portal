/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {frequencyChartDevEnvData} from '../../../dev_env_data';

export default {
	devEnvData: frequencyChartDevEnvData,
	query: `query VisitFrequency($channelId: String!, $rangeKey: Int) {
    visitFrequency(channelId: $channelId, rangeKey: $rangeKey) {
        visitFrequency {
            count
            name
        },
        totalCount
    }
}`,
};
