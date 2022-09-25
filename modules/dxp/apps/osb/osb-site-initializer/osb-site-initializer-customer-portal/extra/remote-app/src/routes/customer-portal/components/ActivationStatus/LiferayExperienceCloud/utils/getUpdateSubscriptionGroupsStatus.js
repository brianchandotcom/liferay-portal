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

import {actionTypes} from '../../../../context/reducer';
import {STATUS_TAG_TYPE_NAMES} from '../../../../utils/constants';

export default function getUpdateSubscriptionGroupsStatus(
	dispatch,
	handleFinishUpdate,
	handleStatusLxcActivation,
	project,
	projectIdValue,
	subscriptionGroupLxcEnvironment,
	subscriptionGroups,
	updateAccountSubscriptionGroup
) {
	updateAccountSubscriptionGroup({
		context: {
			displaySuccess: false,
		},
		variables: {
			AccountSubscriptionGroup: {
				accountKey: project.accountKey,
				activationStatus: STATUS_TAG_TYPE_NAMES.active,
				manageContactsURL: `http://${projectIdValue}.lxc.liferay.com`,
			},
			accountSubscriptionGroupId:
				subscriptionGroupLxcEnvironment?.accountSubscriptionGroupId,
		},
	});

	handleStatusLxcActivation();
	handleFinishUpdate();

	const newSubscriptionGroups = subscriptionGroups.map((subscription) => {
		if (
			subscription.accountSubscriptionGroupId ===
			subscriptionGroupLxcEnvironment?.accountSubscriptionGroupId
		) {
			return {
				...subscription,
				activationStatus: STATUS_TAG_TYPE_NAMES.active,
			};
		}

		return subscription;
	});

	dispatch({
		payload: newSubscriptionGroups,
		type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
	});
}
