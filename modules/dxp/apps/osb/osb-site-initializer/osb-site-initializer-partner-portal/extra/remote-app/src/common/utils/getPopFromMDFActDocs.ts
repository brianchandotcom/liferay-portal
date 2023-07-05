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

import {ProofOfPerformanceType} from '../../routes/MDFClaimForm/constants/proofOfPerformanceType';
import MDFClaimActivityDTO from '../interfaces/dto/mdfClaimActivityDTO';
import MDFClaimActivityDocument from '../interfaces/mdfClaimActivityDocument';
import getNameFromMDFClaimDocument from './getNameFromMDFClaimDocument';

export default function getPopFromMDFActDocs(
	activityItem: MDFClaimActivityDTO
) {
	return activityItem.mdfClmActToMDFActDocs?.reduce(
		(accumulatorDocuments, currentDocument) => {
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.ALL_CONTENTS.key
			) {
				accumulatorDocuments.allContents?.push({
					documentId: currentDocument.proofOfPerformanceFile?.id,
					id: currentDocument.id,
					link: currentDocument.proofOfPerformanceFile?.link,
					name:
						currentDocument.proofOfPerformanceFile?.name &&
						getNameFromMDFClaimDocument(
							currentDocument.proofOfPerformanceFile.name
						),
				});
			}

			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_COLLATERALS.key
			) {
				accumulatorDocuments.eventCollaterals?.push({
					documentId: currentDocument.proofOfPerformanceFile?.id,
					id: currentDocument.id,
					link: currentDocument.proofOfPerformanceFile?.link,
					name:
						currentDocument.proofOfPerformanceFile?.name &&
						getNameFromMDFClaimDocument(
							currentDocument.proofOfPerformanceFile.name
						),
				});
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_INVITATIONS.key
			) {
				accumulatorDocuments.eventInvitations?.push({
					documentId: currentDocument.proofOfPerformanceFile?.id,
					id: currentDocument.id,
					link: currentDocument.proofOfPerformanceFile?.link,
					name:
						currentDocument.proofOfPerformanceFile?.name &&
						getNameFromMDFClaimDocument(
							currentDocument.proofOfPerformanceFile.name
						),
				});
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_PHOTOS.key
			) {
				accumulatorDocuments.eventPhotos?.push({
					documentId: currentDocument.proofOfPerformanceFile?.id,
					id: currentDocument.id,
					link: currentDocument.proofOfPerformanceFile?.link,
					name:
						currentDocument.proofOfPerformanceFile?.name &&
						getNameFromMDFClaimDocument(
							currentDocument.proofOfPerformanceFile.name
						),
				});
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.IMAGES.key
			) {
				accumulatorDocuments.images?.push({
					documentId: currentDocument.proofOfPerformanceFile?.id,
					id: currentDocument.id,
					link: currentDocument.proofOfPerformanceFile?.link,
					name:
						currentDocument.proofOfPerformanceFile?.name &&
						getNameFromMDFClaimDocument(
							currentDocument.proofOfPerformanceFile.name
						),
				});
			}

			return accumulatorDocuments;
		},
		{
			allContents: [],
			eventCollaterals: [],
			eventInvitations: [],
			eventPhotos: [],
			images: [],
		} as MDFClaimActivityDocument
	) as MDFClaimActivityDocument;
}
