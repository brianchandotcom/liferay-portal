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

import {PRODUCT_TYPES} from '../../../../../routes/customer-portal/utils/constants';
import {LOGO_PATH_TYPES} from './utils/constants/logoPathTypes';
import {PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES} from './utils/constants/provisionedAccountSubscriptionGroupsNames';

export const accountSubscriptionGroupsTypePolicy = {
	C_AccountSubscriptionGroup: {
		fields: {
			isProvisioned: {
				read(_, {readField}) {
					return PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES.includes(
						readField('name')
					);
				},
			},
			logoPath: {
				read(_, {readField}) {
					return `assets/navigation-menu/${
						LOGO_PATH_TYPES[readField('name')]
					}`;
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	C_AccountSubscriptionGroupPage: {
		fields: {
			hasPartnership: {
				read(_, {readField}) {
					return readField('items').some(
						(accountSubscriptionGroup) =>
							readField('name', accountSubscriptionGroup) ===
							PRODUCT_TYPES.partnership
					);
				},
			},
		},
	},
};

export const accountSubscriptionGroupsQueryTypePolicy = {
	accountSubscriptionGroups: {
		keyArgs: ['filter', 'sort'],
	},
};
