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

import LiferayFile from '../liferayFile';
import MDFClaimActivityDocument from '../mdfClaimActivityDocument';

export default interface MDFClaimActivityDocumentDTO
	extends Omit<
		MDFClaimActivityDocument,
		| 'allContents'
		| 'eventCollaterals'
		| 'eventInvitations'
		| 'eventPhotos'
		| 'images'
	> {
	allContents?: LiferayFile & number;
	eventCollaterals?: LiferayFile & number;
	eventInvitations?: LiferayFile & number;
	eventPhotos?: LiferayFile & number;
	id?: number;
	images?: LiferayFile & number;
	proofOfPerformanceFile?: LiferayFile & number;
	r_accToMDFClmActDocs_accountEntryId?: number;
	r_mdfClmActToMDFActDocs_c_mdfClaimActivityId?: number;
}
