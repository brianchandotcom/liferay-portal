/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {engagementChartDevEnvData} from '../../../dev_env_data';

export default {
	devEnvData: engagementChartDevEnvData,
	query: `query SitesMetricQuery($channelId: String, $interval: String!, $rangeEnd: String, $rangeKey: Int, $rangeStart: String) {
    site(
        channelId: $channelId
    interval: $interval
    rangeEnd: $rangeEnd
    rangeKey: $rangeKey
    rangeStart: $rangeStart
) {
        visitorsMetric {
        ...HistogramFragment
            __typename
        }
        __typename
    }
}

fragment HistogramFragment on Metric {
    histogram {
        asymmetricComparison
        metrics {
            key
            value
            valueKey
            __typename
        }
        total
        __typename
    }
    __typename
}`,
};
