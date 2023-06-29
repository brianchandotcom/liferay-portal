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

import MDFClaimActivity from '../../../common/interfaces/mdfClaimActivity';
import createMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivity';
import updateMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/updateMDFClaimActivity';

const submitMDFClaimActivity = async (
	mdfClaimActivity: MDFClaimActivity,
	companyId: number,
	dtoMDFClaimId: number
) => {
	const dtoMDFClaimActivity = mdfClaimActivity.id
		? await updateMDFClaimActivity(
				mdfClaimActivity,
				dtoMDFClaimId,
				companyId
		  )
		: await createMDFClaimActivity(
				mdfClaimActivity,
				dtoMDFClaimId,
				companyId
		  );

	return dtoMDFClaimActivity;
};

export default submitMDFClaimActivity;
