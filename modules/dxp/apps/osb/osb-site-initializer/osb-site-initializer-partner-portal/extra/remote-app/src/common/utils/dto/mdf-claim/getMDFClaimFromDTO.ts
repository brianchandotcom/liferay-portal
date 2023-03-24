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

export function getMDFClaimFromDTO(mdfClaim: MDFClaimDTO): MDFClaim {
	return {
		...mdfClaim,

		activities:
			mdfClaim?.mdfClmToMDFClmActs?.map((activityItem) => {
				const {
					currency,
					id,
					listOfQualifiedLeads,
					metrics,
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					totalCost,
				} = activityItem;

				return {
					allContents: activityItem.mdfClmActToMDFActDocs?.map(
						(allContentItem) =>
							allContentItem.allContents &&
							({
								...(allContentItem.allContents as Object),
								name: allContentItem.allContents.name
									.split('#')
									.reverse()
									.splice(1)
									.join(''),
							} as LiferayFile & number)
					) as LiferayFile[],
					budgets: activityItem.mdfClmActToMDFClmBgts?.map(
						(budgetItem) => {
							const {
								expenseName,
								id,
								invoice,
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							} = budgetItem;

							return {
								expenseName,
								id,
								invoice:
									invoice &&
									({
										...(invoice as Object),
										name: invoice.name
											.split('#')
											.reverse()
											.splice(1)
											.join(''),
									} as LiferayFile & number),
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							};
						}
					),
					currency,
					id,
					listOfQualifiedLeads:
						listOfQualifiedLeads &&
						({
							...(listOfQualifiedLeads as Object),
							name: listOfQualifiedLeads.name
								.split('#')
								.reverse()
								.splice(1)
								.join(''),
						} as LiferayFile & number),
					metrics,

					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					totalCost,
				};
			}) || [],
		reimbursementInvoice:
			mdfClaim.reimbursementInvoice &&
			({
				...(mdfClaim.reimbursementInvoice as Object),
				name: mdfClaim.reimbursementInvoice.name
					.split('#')
					.reverse()
					.splice(1)
					.join(''),
			} as LiferayFile & number),
	};
}
