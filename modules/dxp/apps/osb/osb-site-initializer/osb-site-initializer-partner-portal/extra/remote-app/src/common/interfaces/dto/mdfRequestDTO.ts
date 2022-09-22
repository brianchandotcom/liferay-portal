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

import AccountEntry from '../accountEntry';
import MDFRequest from '../mdfRequest';
import User from '../user';

export default interface MDFRequestDTO
	extends Omit<
		MDFRequest,
		| 'activities'
		| 'liferayBusinessSalesGoals'
		| 'targetAudienceRoles'
		| 'targetMarkets'
		| 'company'
	> {
	liferayBusinessSalesGoals?: string;
	r_accountToMDFRequests_accountEntry?: AccountEntry;
	r_accountToMDFRequests_accountEntryId?: number;
	r_userToMDFRequests_user?: User;
	r_userToMDFRequests_userId?: number;
	targetAudienceRoles?: string;
	targetMarkets?: string;
	totalCostOfExpense?: number;
	totalMDFRequestAmount?: number;
	totalRequested?: number;
}
