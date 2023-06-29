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

import LiferayFile from './liferayFile';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFClaimActivity from './mdfClaimActivity';

export default interface MDFClaim extends Partial<LiferayObject> {
	activities?: MDFClaimActivity[];
	currency: LiferayPicklist;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfClaimStatus: LiferayPicklist;
	partial?: boolean;
	r_mdfReqToMDFClms_c_mdfRequestId: number;
	reimbursementInvoice?: LiferayFile & number;
	submitted?: boolean;
	totalClaimAmount?: number;
	totalMDFRequestedAmount?: number;
}
