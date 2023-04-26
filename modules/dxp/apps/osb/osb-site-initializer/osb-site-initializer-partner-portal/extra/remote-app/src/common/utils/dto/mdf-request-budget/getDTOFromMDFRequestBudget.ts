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

import MDFRequestBudgetDTO from '../../../interfaces/dto/mdfRequestBudgetDTO';
import LiferayAccountBrief from '../../../interfaces/liferayAccountBrief';
import MDFRequestBudget from '../../../interfaces/mdfRequestBudget';

export default function getDTOFromMDFRequestBudget(
	budget: MDFRequestBudget,
	activityId: number,
	company?: LiferayAccountBrief
): MDFRequestBudgetDTO {
	const mdfRequestBudget = {...budget};

	delete mdfRequestBudget?.creator;
	delete mdfRequestBudget?.externalReferenceCode;
	delete mdfRequestBudget?.status;

	return {
		...mdfRequestBudget,
		r_accToBgts_accountEntryId: company?.id,
		r_actToBgts_c_activityId: activityId,
	};
}
