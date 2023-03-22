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

import {FormikHelpers} from 'formik';

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Liferay} from '../../../common/services/liferay';
import createDocumentFolderDocument from '../../../common/services/liferay/headless-delivery/createDocumentFolderDocument';
import createMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivity';
import updateMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/updateMDFClaimActivity';
import createMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudget';
import updateMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/updateMDFClaimActivityBudget';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFClaimActivityDocument from '../../../common/services/liferay/object/mdf-claim-activity-documents/createMDFClaimActivityDocument';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';
import updateMDFClaim from '../../../common/services/liferay/object/mdf-claim/updateMDFClaim';
import {Status} from '../../../common/utils/constants/status';
import renameFileKeepingExtention from './RenameFile';
import createMDFClaimProxyAPI from './createMDFClaimProxyAPI';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	mdfRequest: MDFRequestDTO,
	claimParentFolderId: number,
	siteURL: string,
	currentClaimStatus?: LiferayPicklist
) {
	if (currentClaimStatus) {
		values.mdfClaimStatus = currentClaimStatus;
	}

	formikHelpers.setSubmitting(true);

	values.partial = values.activities?.some((activity) =>
		Boolean(activity.budgets?.some((budget) => !budget.selected))
	);

	const dtoMDFClaim =
		Liferay.FeatureFlags['LPS-164528'] &&
		values.mdfClaimStatus !== Status.DRAFT
			? await createMDFClaimProxyAPI(values, mdfRequest)
			: await createMDFClaim(
					ResourceName.MDF_CLAIM_DXP,
					values,
					mdfRequest
			  );

	if (values.reimbursementInvoice && dtoMDFClaim?.id) {
		const reimbursementInvoiceRenamed = renameFileKeepingExtention(
			values.reimbursementInvoice,
			`${values.reimbursementInvoice.name}#${dtoMDFClaim.id}`
		);

		if (reimbursementInvoiceRenamed) {
			const dtoReimbursementInvoice = await createDocumentFolderDocument(
				claimParentFolderId,
				reimbursementInvoiceRenamed
			);

			if (dtoReimbursementInvoice.id) {
				await updateMDFClaim(
					ResourceName.MDF_CLAIM_DXP,
					values,
					mdfRequest,
					dtoMDFClaim.externalReferenceCodeSF,
					dtoReimbursementInvoice.id,
					dtoMDFClaim.id
				);
			}
		}
	}

	if (values.activities?.length && dtoMDFClaim?.id) {
		await Promise.all(
			values.activities
				.filter((activity) => activity.selected)
				?.map(async (activity) => {
					const dtoMDFClaimActivity = await createMDFClaimActivity(
						activity,
						dtoMDFClaim?.id
					);

					if (activity.listQualifiedLeads && dtoMDFClaimActivity.id) {
						const listQualifiedLeadsRenamed = renameFileKeepingExtention(
							activity.listQualifiedLeads,
							`${activity.listQualifiedLeads.name}#${dtoMDFClaimActivity.id}`
						);

						if (listQualifiedLeadsRenamed) {
							const dtoListQualifiedLeads = await createDocumentFolderDocument(
								claimParentFolderId,
								listQualifiedLeadsRenamed
							);

							if (dtoListQualifiedLeads.id) {
								await updateMDFClaimActivity(
									activity,
									dtoMDFClaim.id,
									dtoListQualifiedLeads.id,
									dtoMDFClaimActivity.id
								);
							}
						}
					}

					if (
						activity.allContents?.length &&
						dtoMDFClaimActivity.id
					) {
						Promise.all(
							activity.allContents.map(
								async (allContentDocument) => {
									const allContentDocumentRenamed = renameFileKeepingExtention(
										allContentDocument,
										`${allContentDocument.name}#${dtoMDFClaimActivity.id}`
									);

									if (allContentDocumentRenamed) {
										const dtoAllContentDocument = await createDocumentFolderDocument(
											claimParentFolderId,
											allContentDocumentRenamed
										);

										if (dtoAllContentDocument.id) {
											await createMDFClaimActivityDocument(
												dtoAllContentDocument.id,
												dtoMDFClaimActivity.id
											);
										}
									}
								}
							)
						);
					}

					if (activity.budgets?.length && dtoMDFClaimActivity.id) {
						await Promise.all(
							activity.budgets
								.filter((budget) => budget.selected)
								?.map(async (budget) => {
									const dtoMDFClaimBudget = await createMDFClaimActivityBudget(
										budget,
										dtoMDFClaimActivity.id
									);

									if (
										budget.invoice &&
										dtoMDFClaimBudget.id
									) {
										const budgetInvoiceRenamed = renameFileKeepingExtention(
											budget.invoice,
											`${budget.invoice.name}#${dtoMDFClaimBudget.id}`
										);

										if (budgetInvoiceRenamed) {
											const dtoBudgetInvoice = await createDocumentFolderDocument(
												claimParentFolderId,
												budgetInvoiceRenamed
											);

											if (dtoBudgetInvoice.id) {
												await updateMDFClaimActivityBudget(
													budget,
													dtoMDFClaimActivity.id,
													dtoBudgetInvoice.id,
													dtoMDFClaimBudget.id
												);
											}
										}
									}
								})
						);
					}
				})
		);
	}

	Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);
}
