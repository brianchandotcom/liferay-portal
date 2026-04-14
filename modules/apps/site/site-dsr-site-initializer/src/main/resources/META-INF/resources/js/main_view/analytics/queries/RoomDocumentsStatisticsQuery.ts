/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {roomDocumentsStatisticsDevEnvData} from '../../../dev_env_data';

export default {
	devEnvData: roomDocumentsStatisticsDevEnvData,
	query: `query DocumentsAndMediaList($channelId: String, $keywords: String, $rangeEnd: String, $rangeKey: Int, $rangeStart: String, $size: Int!, $sort: Sort!, $start: Int!) {
    documents(
        channelId: $channelId
    keywords: $keywords
    rangeEnd: $rangeEnd
    rangeKey: $rangeKey
    rangeStart: $rangeStart
    size: $size
    sort: $sort
    start: $start
) {
        assetMetrics {
        ... on DocumentMetric {
                assetId
                assetTitle
                commentsMetric {
                    value
                    __typename
                }
                downloadsMetric {
                    value
                    __typename
                }
                impressionMadeMetric {
                    value
                    __typename
                }
                lastViewedMetric {
                    value
                    __typename
                }
                ratingsMetric {
                    value
                    __typename
                }
                usersInvolvedMetric {
                    value
                    __typename
                }
                urls
                __typename
            }
            __typename
        }
        total
        __typename
    }
}`,
};
