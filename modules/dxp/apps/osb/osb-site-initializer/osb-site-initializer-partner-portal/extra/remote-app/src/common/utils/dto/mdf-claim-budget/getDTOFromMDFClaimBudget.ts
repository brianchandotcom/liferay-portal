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

import MDFClaimBudgetDTO from '../../../interfaces/dto/mdfClaimBudgetDTO';
import MDFClaimBudget from '../../../interfaces/mdfClaimBudget';

export default function getDTOFromMDFClaimBudget(
	budget: MDFClaimBudget,
	activityId: number
): MDFClaimBudgetDTO {
	return {
		expenseName: budget.expenseName,
		invoice: budget.invoice,
		invoiceAmount: budget.invoiceAmount,
		r_bgtToMDFClmBgts_c_budgetId: budget.id,
		r_mdfClmActToMDFClmBgts_c_mdfClaimActivityId: activityId,
		selected: budget.selected,
	};
}
