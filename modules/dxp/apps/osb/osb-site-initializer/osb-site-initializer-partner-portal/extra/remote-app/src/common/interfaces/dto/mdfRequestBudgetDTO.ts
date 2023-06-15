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

import MDFRequestBudget from '../mdfRequestBudget';

export default interface MDFRequestBudgetDTO extends MDFRequestBudget {
	r_accToBgts_accountEntryERC?: string;
	r_accToBgts_accountEntryId?: number;
	r_actToBgts_c_activityERC?: string;
	r_actToBgts_c_activityId?: number;
}
