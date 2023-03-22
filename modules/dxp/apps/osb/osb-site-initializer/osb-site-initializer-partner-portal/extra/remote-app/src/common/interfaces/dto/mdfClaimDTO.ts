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
import LiferayPicklist from '../liferayPicklist';
import MDFClaim from '../mdfClaim';

export default interface MDFClaimDTO
	extends Omit<MDFClaim, 'activities' | 'reimbursementInvoice'> {
	amountClaimed?: number;
	companyName?: string;
	currency: LiferayPicklist;
	externalReferenceCodeSF?: string;
	mdfClaimStatus: LiferayPicklist;
	mdfRequestExternalReferenceCodeSF?: string;
	mdfRequestTotalCostOfExpense?: number;
	mdfRequestedAmount?: number;
	paymentReceived?: number;
	r_accToMDFClms_accountEntry?: AccountEntry;
	r_accToMDFClms_accountEntryId?: number;
	reimbursementInvoice?: number;
}
