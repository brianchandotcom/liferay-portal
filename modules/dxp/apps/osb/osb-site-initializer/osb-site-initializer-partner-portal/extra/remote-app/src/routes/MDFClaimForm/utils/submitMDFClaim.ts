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

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';
import updateMDFClaim from '../../../common/services/liferay/object/mdf-claim/updateMDFClaim';

const submitMDFClaim = async (
	mdfClaim: MDFClaim,
	mdfRequest: MDFRequestDTO
) => {
	const dtoMDFClaim = mdfClaim.id
		? await updateMDFClaim(ResourceName.MDF_CLAIM_DXP, mdfClaim, mdfRequest)
		: await createMDFClaim(
				ResourceName.MDF_CLAIM_DXP,
				mdfClaim,
				mdfRequest
		  );

	return dtoMDFClaim;
};

export default submitMDFClaim;
