/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const {cost, expense, id} = budget;

	return {
		cost,
		expense,
		id,
		r_accToBgts_accountEntryERC: mdfRequest.company?.externalReferenceCode,
		r_accToBgts_accountEntryId: mdfRequest.company?.id,
		r_actToBgts_c_activityERC: activityDTO.externalReferenceCode,
		r_actToBgts_c_activityId: activityDTO.id,
	};
}
