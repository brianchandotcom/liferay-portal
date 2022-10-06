/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {gql} from '@apollo/client';

export const CORE_KORONEIKI_ACCOUNT_FIELDS = gql`
	fragment CoreKoroneikiAccountFields on C_KoroneikiAccount {
		accountKey
		acWorkspaceGroupId
		code
		dxpVersion
		externalReferenceCode
		hasSLAGoldPlatinum @client
		liferayContactEmailAddress
		liferayContactName
		liferayContactRole
		maxRequestors
		name
		partner
		region
		slaCurrent
		slaCurrentEndDate
		slaCurrentStartDate
		slaExpired
		slaExpiredEndDate
		slaExpiredStartDate
		slaFuture
		slaFutureEndDate
		slaFutureStartDate
		status @client
	}
`;
