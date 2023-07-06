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

import MDFClaimActivityDocumentDTO from '../../../interfaces/dto/mdfClaimActivityDocumentDTO';
import LiferayFile from '../../../interfaces/liferayFile';
import LiferayPicklist from '../../../interfaces/liferayPicklist';

export default function getDocumentDTOFromLiferayFile(
	liferayFile: LiferayFile,
	proofOfPerformanceType: LiferayPicklist,
	companyId: number,
	dtoMDFClaimActivityId: number
): MDFClaimActivityDocumentDTO {
	return {
		id: liferayFile.id,
		proofOfPerformanceFile: liferayFile.documentId,
		proofOfPerformanceType,
		r_accToMDFClmActDocs_accountEntryId: companyId,
		r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
	};
}
