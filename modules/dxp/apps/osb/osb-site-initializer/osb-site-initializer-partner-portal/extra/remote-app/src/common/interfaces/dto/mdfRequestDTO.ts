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
import MDFClaimDTO from './mdfClaimDTO';
import MDFRequestActivityDTO from './mdfRequestActivityDTO';

export default interface MDFRequestDTO
	extends Omit<
		MDFRequest,
		| 'activities'
		| 'liferayBusinessSalesGoals'
		| 'targetAudienceRoles'
		| 'targetMarkets'
		| 'company'
	> {
	companyName?: string;
	emailAddress?: string;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	liferayBusinessSalesGoals?: string;
	liferayBusinessSalesGoalsOther?: string;
	liferaysUserIdSF?: number;
	mdfReqToActs?: MDFRequestActivityDTO[];
	mdfReqToMDFClms?: MDFClaimDTO[];
	r_accToMDFReqs_accountEntry?: AccountEntry;
	r_accToMDFReqs_accountEntryERC?: string;
	r_accToMDFReqs_accountEntryId?: number;
	r_usrToMDFReqs_user?: User;
	r_usrToMDFReqs_userId?: number;
	targetAudienceRoles?: string;
	targetMarkets?: string;
	totalClaimedRequest?: number;
	totalCostOfExpense: number;
	totalMDFRequestAmount: number;
	totalPaidAmount?: number;
	totalRequested?: number;
}
