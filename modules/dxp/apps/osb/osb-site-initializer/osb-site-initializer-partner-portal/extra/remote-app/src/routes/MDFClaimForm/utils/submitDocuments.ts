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

import MDFClaim from '../../../common/interfaces/mdfClaim';
import uploadDocument from './uploadDocument';
import uploadDocuments from './uploadDocuments';

const submitDocuments = async (
	mdfClaim: MDFClaim,
	claimParentFolderId: number
) => {
	if (
		mdfClaim.reimbursementInvoice &&
		!mdfClaim.reimbursementInvoice.documentId
	) {
		mdfClaim.reimbursementInvoice.documentId = await uploadDocument(
			mdfClaim.reimbursementInvoice,
			claimParentFolderId
		);
	}

	if (mdfClaim.activities?.length) {
		for (const activity of mdfClaim.activities) {
			if (activity.eventProgram && !activity.eventProgram.documentId) {
				activity.eventProgram.documentId = await uploadDocument(
					activity.eventProgram,
					claimParentFolderId
				);
			}

			if (
				activity.listOfQualifiedLeads &&
				!activity.listOfQualifiedLeads.documentId
			) {
				activity.listOfQualifiedLeads.documentId = await uploadDocument(
					activity.listOfQualifiedLeads,
					claimParentFolderId
				);
			}

			if (
				activity.telemarketingScript &&
				!activity.telemarketingScript.documentId
			) {
				activity.telemarketingScript.documentId = await uploadDocument(
					activity.telemarketingScript,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.allContents?.length) {
				activity.proofOfPerformance.allContents = await uploadDocuments(
					activity.proofOfPerformance.allContents,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.eventCollaterals?.length) {
				activity.proofOfPerformance.eventCollaterals = await uploadDocuments(
					activity.proofOfPerformance.eventCollaterals,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.eventInvitations?.length) {
				activity.proofOfPerformance.eventInvitations = await uploadDocuments(
					activity.proofOfPerformance.eventInvitations,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.eventPhotos?.length) {
				activity.proofOfPerformance.eventPhotos = await uploadDocuments(
					activity.proofOfPerformance.eventPhotos,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.images?.length) {
				activity.proofOfPerformance.images = await uploadDocuments(
					activity.proofOfPerformance.images,
					claimParentFolderId
				);
			}

			if (activity.budgets?.length) {
				for (const budget of activity.budgets) {
					if (budget.invoice && !budget.invoice.documentId) {
						budget.invoice.documentId = await uploadDocument(
							budget.invoice,
							claimParentFolderId
						);
					}
				}
			}
		}
	}

	return mdfClaim;
};
export default submitDocuments;
