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

import MDFClaimDocumentsDTO from '../../../interfaces/dto/mdfClaimDocumentsDTO';

export function getDTOFromMDFClaimDocuments(
	file?: File,
	url?: string,
	claimId?: number,
	activityId?: number,
	budgetId?: number,
	type?: string
): MDFClaimDocumentsDTO {
	return {
		url,
		r_mdfClaimActivityToMdfClaimDocuments_c_mdfClaimActivityId: activityId,
		r_mdfClaimToMdfClaimDocuments_c_mdfClaimBudgetId: budgetId,
		r_mdfClaimToMdfClaimDocuments_c_mdfClaimId: claimId,
		type,
		fileName: file?.name,
		fileSize: file?.size,
	};
}
