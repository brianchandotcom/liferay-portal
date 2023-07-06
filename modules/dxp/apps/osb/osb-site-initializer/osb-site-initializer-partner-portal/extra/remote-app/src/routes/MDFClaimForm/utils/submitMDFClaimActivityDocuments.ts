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
import getDocumentDTOFromLiferayFile from '../../../common/utils/dto/mdf-claim-activity-document/getDocumentDTOFromLiferayFile';
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
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					allContentDocument,
					ProofOfPerformanceType.ALL_CONTENTS,
					companyId,
					dtoMDFClaimActivityId
				);
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
					const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
						eventCollateralsDocument,
						ProofOfPerformanceType.EVENT_COLLATERALS,
						companyId,
						dtoMDFClaimActivityId
					);
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
					const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
						eventInvitationsDocument,
						ProofOfPerformanceType.EVENT_INVITATIONS,
						companyId,
						dtoMDFClaimActivityId
					);
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
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					eventPhotosDocument,
					ProofOfPerformanceType.EVENT_PHOTOS,
					companyId,
					dtoMDFClaimActivityId
				);
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
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					imagesDocument,
					ProofOfPerformanceType.IMAGES,
					companyId,
					dtoMDFClaimActivityId
				);
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
