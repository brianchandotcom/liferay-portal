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

export const CORE_USER_ACCOUNT_FIELDS = gql`
	fragment CoreUserAccountFields on UserAccount {
		accountBriefs {
			externalReferenceCode
			id
			name
			roleBriefs {
				id
				name
			}
		}
		emailAddress
		id
		isCurrentUser @client
		isLiferayStaff @client
		isProvisioning @client
		lastLoginDate
		name
		organizationBriefs {
			id
			name
		}
		roleBriefs {
			id
			name
		}
	}
`;
