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

import MDFClaimDTO from '../../../interfaces/dto/mdfClaimDTO';
import LiferayFile from '../../../interfaces/liferayFile';
import MDFClaim from '../../../interfaces/mdfClaim';
import getNameFromMDFClaimDocument from '../../getNameFromMDFClaimDocument';
import getPopFromMDFActDocs from '../../getPopFromMDFActDocs';

export function getMDFClaimFromDTO(mdfClaim: MDFClaimDTO): MDFClaim {
	return {
		...mdfClaim,

		activities:
			mdfClaim?.mdfClmToMDFClmActs?.map((activityItem) => {
				const {
					currency,
					eventProgram,
					externalReferenceCode,
					id,
					listOfQualifiedLeads,
					metrics,
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScript,
					totalCost,
					typeActivity,
					videoLink,
				} = activityItem;

				return {
					budgets: activityItem.mdfClmActToMDFClmBgts?.map(
						(budgetItem) => {
							const {
								expenseName,
								externalReferenceCode,
								id,
								invoice,
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							} = budgetItem;

							return {
								expenseName,
								externalReferenceCode,
								id,
								invoice:
									invoice &&
									({
										...(invoice as Object),
										name:
											invoice.name &&
											getNameFromMDFClaimDocument(
												invoice.name
											),
									} as LiferayFile & number),
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							};
						}
					),
					currency,
					eventProgram,
					externalReferenceCode,
					id,
					listOfQualifiedLeads:
						listOfQualifiedLeads &&
						({
							...(listOfQualifiedLeads as Object),
							name:
								listOfQualifiedLeads.name &&
								getNameFromMDFClaimDocument(
									listOfQualifiedLeads.name
								),
						} as LiferayFile & number),
					metrics,
					proofOfPerformance: getPopFromMDFActDocs(activityItem),
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScript,
					totalCost,
					typeActivity,
					videoLink,
				};
			}) || [],
		reimbursementInvoice:
			mdfClaim.reimbursementInvoice &&
			({
				...(mdfClaim.reimbursementInvoice as Object),
				name:
					mdfClaim.reimbursementInvoice.name &&
					getNameFromMDFClaimDocument(
						mdfClaim.reimbursementInvoice.name
					),
			} as LiferayFile & number),
	};
}
