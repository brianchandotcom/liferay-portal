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

import MDFClaimBudget from '../../../common/interfaces/mdfClaimBudget';
import createMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudget';
import updateMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/updateMDFClaimActivityBudget';

const submitMDFClaimBudget = async (
	mdfClaimBudget: MDFClaimBudget,
	companyId: number,
	dtoMDFClaimActivityId: number
) => {
	mdfClaimBudget.id
		? await updateMDFClaimActivityBudget(
				mdfClaimBudget,
				dtoMDFClaimActivityId,
				companyId
		  )
		: await createMDFClaimActivityBudget(
				mdfClaimBudget,
				dtoMDFClaimActivityId,
				companyId
		  );
};

export default submitMDFClaimBudget;
