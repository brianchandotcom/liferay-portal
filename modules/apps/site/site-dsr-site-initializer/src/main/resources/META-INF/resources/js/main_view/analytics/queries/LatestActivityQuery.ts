/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {latestActivityDevEnvData} from '../../../dev_env_data';

export default {
	devEnvData: latestActivityDevEnvData,
	query: `query EventQuery($channelId: String!, $includeAnonymousUsers: Boolean, $individualId: String, $keywords: String, $page: Int!, $rangeEnd: String, $rangeKey: Int, $rangeStart: String, $size: Int!) {
    events(channelId: $channelId, includeAnonymousUsers: $includeAnonymousUsers, individualId: $individualId, keywords: $keywords, page: $page, rangeEnd: $rangeEnd, rangeKey: $rangeKey, rangeStart: $rangeStart, size: $size) {
        events {
            emailAddressHashed
            name
            createDate
        }
    }
}`,
};
