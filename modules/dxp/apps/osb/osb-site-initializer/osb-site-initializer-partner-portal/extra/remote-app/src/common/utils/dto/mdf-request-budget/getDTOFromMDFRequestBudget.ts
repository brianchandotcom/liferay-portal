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

import MDFRequestActivityDTO from '../../../interfaces/dto/mdfRequestActivityDTO';
import MDFRequestBudgetDTO from '../../../interfaces/dto/mdfRequestBudgetDTO';
import MDFRequest from '../../../interfaces/mdfRequest';
import MDFRequestBudget from '../../../interfaces/mdfRequestBudget';

export default function getDTOFromMDFRequestBudget(
	budget: MDFRequestBudget,
	activityDTO: MDFRequestActivityDTO,
	mdfRequest: MDFRequest
): MDFRequestBudgetDTO {
	const mdfRequestBudget = {...budget};

	delete mdfRequestBudget?.creator;
	delete mdfRequestBudget?.externalReferenceCode;
	delete mdfRequestBudget?.status;

	return {
		...mdfRequestBudget,
		r_accToBgts_accountEntryERC: mdfRequest.company?.externalReferenceCode,
		r_accToBgts_accountEntryId: mdfRequest.company?.id,
		r_actToBgts_c_activityERC: activityDTO.externalReferenceCode,
		r_actToBgts_c_activityId: activityDTO.id,
	};
}
