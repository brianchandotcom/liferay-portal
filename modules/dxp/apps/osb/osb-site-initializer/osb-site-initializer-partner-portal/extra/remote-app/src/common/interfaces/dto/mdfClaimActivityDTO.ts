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

import MDFClaimActivity from '../mdfClaimActivity';

export default interface MDFClaimActivityDTO
	extends Omit<
		MDFClaimActivity,
		'metrics' | 'name' | 'selected' | 'totalCost'
	> {
	metrics?: string;
	name?: string;
	r_activityToMDFClaimActivities_c_activityId?: number;
	r_mdfClaimToMdfClaimActivities_c_mdfClaimId?: number;
	totalCost?: number;
}
