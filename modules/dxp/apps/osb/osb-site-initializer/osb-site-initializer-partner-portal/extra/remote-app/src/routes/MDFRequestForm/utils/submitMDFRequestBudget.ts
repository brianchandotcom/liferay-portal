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

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestBudget from '../../../common/interfaces/mdfRequestBudget';
import createMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/createMDFRequestActivityBudgets';
import updateMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/updateMDFRequestActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

const submitMDFRequestBudget = async (
	budget: MDFRequestBudget,
	activityDTO: MDFRequestActivityDTO,
	mdfRequest: MDFRequest
) => {
	const dtoMDFClaimBudget = budget.id
		? await updateMDFRequestActivityBudget(
				ResourceName.BUDGET,
				budget,
				activityDTO,
				mdfRequest
		  )
		: await createMDFRequestActivityBudget(
				ResourceName.BUDGET,
				budget,
				activityDTO,
				mdfRequest
		  );

	return dtoMDFClaimBudget;
};

export default submitMDFRequestBudget;
