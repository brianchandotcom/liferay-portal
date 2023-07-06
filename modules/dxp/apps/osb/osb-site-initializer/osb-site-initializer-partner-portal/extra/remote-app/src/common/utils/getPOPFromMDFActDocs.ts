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
import {getPOPDocument} from './getPOPDocument';

export default function getPOPFromMDFActDocs(
	activityItem: MDFClaimActivityDTO
) {
	return activityItem.mdfClmActToMDFActDocs?.reduce(
		(accumulatorDocuments, currentDocument) => {
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.ALL_CONTENTS.key
			) {
				accumulatorDocuments.allContents?.push(
					getPOPDocument(currentDocument)
				);
			}

			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_COLLATERALS.key
			) {
				accumulatorDocuments.eventCollaterals?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_INVITATIONS.key
			) {
				accumulatorDocuments.eventInvitations?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_PHOTOS.key
			) {
				accumulatorDocuments.eventPhotos?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.IMAGES.key
			) {
				accumulatorDocuments.images?.push(
					getPOPDocument(currentDocument)
				);
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
