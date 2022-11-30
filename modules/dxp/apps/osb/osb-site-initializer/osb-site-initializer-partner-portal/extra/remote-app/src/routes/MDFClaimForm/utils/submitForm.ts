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

import {Status} from '../../../common/enums/status';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Liferay} from '../../../common/services/liferay';
import createDocumentFolder from '../../../common/services/liferay/headless-delivery/createDocumentFolder';
import createDocumentFolderDocument from '../../../common/services/liferay/headless-delivery/createDocumentFolderDocument';
import createMDFClaimActivities from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivities';
import createMDFClaimActivityBudgets from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFClaimDocuments from '../../../common/services/liferay/object/mdf-claim-documents/createMDFClaimDocuments';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';
import renameFileKeepingExtention from './RenameFile';
import createMDFClaimProxyAPI from './createMDFClaimProxyAPI';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	mdfRequest: MDFRequestDTO,
	claimParentFolderId: number,
	siteURL: string,
	currentClaimStatus?: Status
) {
	if (currentClaimStatus) {
		values.claimStatus = currentClaimStatus;
	}

	formikHelpers.setSubmitting(true);

	values.partial = values.activities?.some((activity) =>
		Boolean(activity.budgets?.some((budget) => !budget.selected))
	);

	const dtoMDFClaim = Liferay.FeatureFlags['LPS-164528']
		? await createMDFClaimProxyAPI(values, mdfRequest)
		: await createMDFClaim(ResourceName.MDF_CLAIM_DXP, values, mdfRequest);

	if (dtoMDFClaim?.id) {
		const claimFolder = await createDocumentFolder(
			claimParentFolderId,
			`claim#${dtoMDFClaim.id}`
		);

		if (values.reimbursementInvoice && claimFolder?.id) {
			const claimDocument = await createDocumentFolderDocument(
				claimFolder.id,
				values.reimbursementInvoice
			);

			if (claimDocument?.contentUrl) {
				await createMDFClaimDocuments(
					values.reimbursementInvoice,
					claimDocument.contentUrl,
					dtoMDFClaim?.id
				);
			}
		}

		if (values.activities?.length) {
			const dtoMDFClaimActivities = await createMDFClaimActivities(
				dtoMDFClaim.id,
				values.activities.filter((activity) => activity.selected)
			);

			if (dtoMDFClaimActivities?.length) {
				values.activities.map(async (activity, index) => {
					const dtoActivity = dtoMDFClaimActivities[index];

					if (dtoActivity?.id && claimFolder.id) {
						const activityFolder = await createDocumentFolder(
							claimFolder.id,
							`activity#${dtoActivity.id}`
						);

						if (activityFolder?.id) {
							if (activity.listQualifiedLeads) {
								const listOfQualifiedLeadsWithId = renameFileKeepingExtention(
									activity.listQualifiedLeads,
									`list_of_qualified_leads#${dtoActivity.id}`
								);

								const activityListQualifiedLeads = await createDocumentFolderDocument(
									activityFolder.id,
									listOfQualifiedLeadsWithId
								);

								if (activityListQualifiedLeads?.contentUrl) {
									createMDFClaimDocuments(
										listOfQualifiedLeadsWithId,
										activityListQualifiedLeads.contentUrl,
										dtoActivity?.id
									);
								}
							}

							if (activity.documents?.length) {
								activity.documents.map(async (document) => {
									if (activityFolder?.id) {
										const activityDocument = await createDocumentFolderDocument(
											activityFolder.id,
											document
										);

										if (activityDocument?.contentUrl) {
											createMDFClaimDocuments(
												document,
												activityDocument.contentUrl,
												dtoActivity?.id
											);
										}
									}
								});
							}

							if (activity.budgets?.length) {
								const dtoMDFClaimBudgets = await createMDFClaimActivityBudgets(
									dtoActivity.id,
									activity.budgets?.filter((budget) => {
										return budget.invoice;
									})
								);

								if (dtoMDFClaimBudgets?.length) {
									activity.budgets.map(
										async (budget, index) => {
											const dtoBudget =
												dtoMDFClaimBudgets[index];

											if (
												dtoBudget?.id &&
												activityFolder?.id
											) {
												const budgetFolder = await createDocumentFolder(
													activityFolder.id,
													`budget#${dtoBudget.id}`
												);

												if (
													budget.invoice &&
													budgetFolder?.id
												) {
													const budgetDocument = await createDocumentFolderDocument(
														budgetFolder.id,
														budget.invoice
													);

													if (
														budgetDocument?.contentUrl
													) {
														await createMDFClaimDocuments(
															budget.invoice,
															budgetDocument.contentUrl,
															dtoBudget?.id
														);
													}
												}
											}
										}
									);
								}
							}
						}
					}
				});
			}
		}
	}
	Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);
}
