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

import MDFClaimActivityDocumentDTO from '../../../common/interfaces/dto/mdfClaimActivityDocumentDTO';
import MDFClaimActivityDocument from '../../../common/interfaces/mdfClaimActivityDocument';
import createMDFClaimActivityDocument from '../../../common/services/liferay/object/mdf-claim-activity-documents/createMDFClaimActivityDocument';
import updateMDFClaimActivityDocument from '../../../common/services/liferay/object/mdf-claim-activity-documents/updateMDFClaimActivityDocument.';
import {ProofOfPerformanceType} from '../constants/proofOfPerformanceType';

const submitMDFClaimActivityDocuments = async (
	proofOfPerformance: MDFClaimActivityDocument,
	companyId: number,
	dtoMDFClaimActivityId: number
) => {
	const dtoMDFClaimActivityDocumentsCreate: MDFClaimActivityDocumentDTO[] = [];
	const dtoMDFClaimActivityDocumentsUpdate: MDFClaimActivityDocumentDTO[] = [];

	if (proofOfPerformance.allContents?.length) {
		proofOfPerformance.allContents.map(async (allContentDocument) => {
			if (allContentDocument.documentId) {
				const dtoMDFClaimActivityDocument: MDFClaimActivityDocumentDTO = {
					id: allContentDocument.id,
					proofOfPerformanceFile: allContentDocument.documentId,
					proofOfPerformanceType: ProofOfPerformanceType.ALL_CONTENTS,
					r_accToMDFClmActDocs_accountEntryId: companyId,
					r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
				};
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}

	if (proofOfPerformance.eventCollaterals?.length) {
		proofOfPerformance.eventCollaterals.map(
			async (eventCollateralsDocument) => {
				if (eventCollateralsDocument.documentId) {
					const dtoMDFClaimActivityDocument: MDFClaimActivityDocumentDTO = {
						id: eventCollateralsDocument.id,
						proofOfPerformanceFile:
							eventCollateralsDocument.documentId,
						proofOfPerformanceType:
							ProofOfPerformanceType.EVENT_COLLATERALS,
						r_accToMDFClmActDocs_accountEntryId: companyId,
						r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
					};
					dtoMDFClaimActivityDocument.id
						? dtoMDFClaimActivityDocumentsUpdate.push(
								dtoMDFClaimActivityDocument
						  )
						: dtoMDFClaimActivityDocumentsCreate.push(
								dtoMDFClaimActivityDocument
						  );
				}
			}
		);
	}
	if (proofOfPerformance.eventInvitations?.length) {
		proofOfPerformance.eventInvitations.map(
			async (eventInvitationsDocument) => {
				if (eventInvitationsDocument.documentId) {
					const dtoMDFClaimActivityDocument: MDFClaimActivityDocumentDTO = {
						id: eventInvitationsDocument.id,
						proofOfPerformanceFile:
							eventInvitationsDocument.documentId,
						proofOfPerformanceType:
							ProofOfPerformanceType.EVENT_INVITATIONS,
						r_accToMDFClmActDocs_accountEntryId: companyId,
						r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
					};
					dtoMDFClaimActivityDocument.id
						? dtoMDFClaimActivityDocumentsUpdate.push(
								dtoMDFClaimActivityDocument
						  )
						: dtoMDFClaimActivityDocumentsCreate.push(
								dtoMDFClaimActivityDocument
						  );
				}
			}
		);
	}
	if (proofOfPerformance.eventPhotos?.length) {
		proofOfPerformance.eventPhotos.map(async (eventPhotosDocument) => {
			if (eventPhotosDocument.documentId) {
				const dtoMDFClaimActivityDocument: MDFClaimActivityDocumentDTO = {
					id: eventPhotosDocument.id,
					proofOfPerformanceFile: eventPhotosDocument.documentId,
					proofOfPerformanceType: ProofOfPerformanceType.EVENT_PHOTOS,
					r_accToMDFClmActDocs_accountEntryId: companyId,
					r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
				};
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}
	if (proofOfPerformance.images?.length) {
		proofOfPerformance.images.map(async (imagesDocument) => {
			if (imagesDocument.documentId) {
				const dtoMDFClaimActivityDocument: MDFClaimActivityDocumentDTO = {
					id: imagesDocument.id,
					proofOfPerformanceFile: imagesDocument.documentId,
					proofOfPerformanceType: ProofOfPerformanceType.IMAGES,
					r_accToMDFClmActDocs_accountEntryId: companyId,
					r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
				};
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}

	if (dtoMDFClaimActivityDocumentsCreate.length) {
		await createMDFClaimActivityDocument(
			dtoMDFClaimActivityDocumentsCreate
		);
	}

	if (dtoMDFClaimActivityDocumentsUpdate.length) {
		await updateMDFClaimActivityDocument(
			dtoMDFClaimActivityDocumentsUpdate
		);
	}
};

export default submitMDFClaimActivityDocuments;
