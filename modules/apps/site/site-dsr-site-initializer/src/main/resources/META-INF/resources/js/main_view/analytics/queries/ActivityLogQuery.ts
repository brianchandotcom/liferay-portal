/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {activityLogDevEnvData} from '../../../dev_env_data';

export default {
	devEnvData: activityLogDevEnvData,
	query: `
query UserSession($channelId: String!, $entityType: EntityType!, $keywords: String, $page: Int!, $rangeEnd: String, $rangeKey: Int, $rangeStart: String, $size: Int!) {
  eventsByUserSessions(
    channelId: $channelId
    entityType: $entityType
    keywords: $keywords
    page: $page
    rangeEnd: $rangeEnd
    rangeKey: $rangeKey
    rangeStart: $rangeStart
    size: $size
  ) {
    userSessions {
      ... on UserSession {
        events {
          createDate
          emailAddressHashed
          name
          __typename
        }
        __typename
      }
      __typename
    }
    totalEvents
    __typename
  }
}
`,
};
