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

import {
	accountRolesQueryTypePolicy,
	accountRolesTypePolicy,
} from './account-roles/typePolicy';
import {
	accountSubscriptionGroupsQueryTypePolicy,
	accountSubscriptionGroupsTypePolicy,
} from './account-subscription-groups/typePolicy';
import {
	accountSubscriptionsQueryTypePolicy,
	accountSubscriptionsTypePolicy,
} from './account-subscriptions/typePolicy';
import {
	koroneikiAccountsQueryTypePolicy,
	koroneikiAccountsTypePolicy,
} from './koroneiki-accounts/typePolicy';
import {
	orderItemsQueryTypePolicy,
	orderItemsTypePolicy,
} from './order-items/typePolicy';
import {userAccountsTypePolicy} from './user-accounts/typePolicy';

export const liferayTypePolicies = {
	...accountSubscriptionsTypePolicy,
	...accountSubscriptionGroupsTypePolicy,
	...accountRolesTypePolicy,
	...userAccountsTypePolicy,
	...koroneikiAccountsTypePolicy,
	...orderItemsTypePolicy,
	Mutationc: {
		merge: true,
	},
	Query: {
		fields: {
			...accountRolesQueryTypePolicy,
			...orderItemsQueryTypePolicy,
		},
	},
	c: {
		fields: {
			...accountSubscriptionsQueryTypePolicy,
			...accountSubscriptionGroupsQueryTypePolicy,
			...koroneikiAccountsQueryTypePolicy,
		},
		merge: true,
	},
};
