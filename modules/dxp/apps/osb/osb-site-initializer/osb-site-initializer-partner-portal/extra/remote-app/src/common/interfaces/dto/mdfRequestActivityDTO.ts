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

import MDFRequestActivity from '../mdfRequestActivity';

export default interface MDFRequestActivityDTO
	extends Omit<
		MDFRequestActivity,
		| 'budgets'
		| 'leadFollowUpStrategies'
		| 'mdfRequestId'
		| 'tactic'
		| 'typeActivity'
	> {
	leadFollowUpStrategies?: string;
	r_mdfRequestToActivities_c_mdfRequestId?: number;
	r_tacticToActivities_c_tacticId?: number;
	r_typeActivityToActivities_c_typeActivityId?: number;
}
