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

import MDFClaimDTO from '../../../interfaces/dto/mdfClaimDTO';
import MDFRequestDTO from '../../../interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../interfaces/mdfClaim';

export function getDTOFromMDFClaim(
	mdfClaim: MDFClaim,
	mdfRequest: MDFRequestDTO,
	externalReferenceCodeSF?: string
): MDFClaimDTO {
	return {
		amountClaimed: mdfClaim.totalClaimAmount,
		claimStatus: mdfClaim.claimStatus,
		companyName: mdfRequest.r_accountToMDFRequests_accountEntry?.name,
		externalReferenceCodeSF,
		mdfRequestExternalReferenceCodeSF: mdfRequest?.externalReferenceCodeSF,
		mdfRequestTotalCostOfExpense: mdfRequest.totalCostOfExpense,
		mdfRequestedAmount: mdfClaim.totalrequestedAmount,
		partial: mdfClaim.partial,
		r_accountToMdfClaims_accountEntryId:
			mdfRequest.r_accountToMDFRequests_accountEntry?.id,
		r_mdfRequestToMdfClaims_c_mdfRequestId:
			mdfClaim.r_mdfRequestToMdfClaims_c_mdfRequestId,
	};
}
