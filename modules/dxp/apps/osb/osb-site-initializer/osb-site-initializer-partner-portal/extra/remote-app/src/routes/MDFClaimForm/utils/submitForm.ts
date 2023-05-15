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

import mdfClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayFile from '../../../common/interfaces/liferayFile';
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
import updateStatus from '../../../common/utils/updateStatus';
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
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	const submitValues = {...values};

	const updatedStatus = updateStatus(
		submitValues.mdfClaimStatus,
		currentClaimStatus,
		false,
		submitValues.id
	);

	submitValues.mdfClaimStatus = updatedStatus;

	submitValues.partial = submitValues.activities?.some((activity) =>
		Boolean(activity.budgets?.some((budget) => !budget.selected))
	);

	let dtoMDFClaim: mdfClaimDTO | undefined = undefined;

	try {
		if (submitValues.mdfClaimStatus.key !== Status.DRAFT.key) {
			dtoMDFClaim = await createMDFClaimProxyAPI(
				submitValues,
				mdfRequest
			);
		}
		else if (submitValues.id) {
			dtoMDFClaim = await updateMDFClaim(
				ResourceName.MDF_CLAIM_DXP,
				submitValues,
				mdfRequest,
				submitValues.id
			);
		}
		else {
			dtoMDFClaim = await createMDFClaim(
				ResourceName.MDF_CLAIM_DXP,
				submitValues,
				mdfRequest
			);
		}

		if (
			submitValues.reimbursementInvoice &&
			!submitValues.reimbursementInvoice.id &&
			dtoMDFClaim?.id
		) {
			const reimbursementInvoiceRenamed = renameFileKeepingExtention(
				submitValues.reimbursementInvoice,
				`${submitValues.reimbursementInvoice.name}#${dtoMDFClaim.id}`
			);

			if (reimbursementInvoiceRenamed) {
				const dtoReimbursementInvoice = await createDocumentFolderDocument(
					claimParentFolderId,
					reimbursementInvoiceRenamed
				);

				if (dtoReimbursementInvoice.id) {
					await updateMDFClaim(
						ResourceName.MDF_CLAIM_DXP,
						submitValues,
						mdfRequest,
						dtoMDFClaim.id,
						dtoReimbursementInvoice.id as LiferayFile & number,
						dtoMDFClaim.externalReferenceCode
					);
				}
			}
		}

		if (submitValues.activities?.length && dtoMDFClaim?.id) {
			await Promise.all(
				submitValues.activities.map(async (activity) => {
					const dtoMDFClaimActivity = activity.id
						? await updateMDFClaimActivity(
								activity,
								dtoMDFClaim?.id,
								activity.id,
								mdfRequest.r_accToMDFReqs_accountEntry?.id
						  )
						: await createMDFClaimActivity(
								activity,
								dtoMDFClaim?.id,
								mdfRequest.r_accToMDFReqs_accountEntry?.id
						  );

					if (
						activity.listOfQualifiedLeads &&
						!activity.listOfQualifiedLeads.id &&
						dtoMDFClaimActivity.id
					) {
						const listOfQualifiedLeadsRenamed = renameFileKeepingExtention(
							activity.listOfQualifiedLeads,
							`${activity.listOfQualifiedLeads.name}#${dtoMDFClaimActivity.id}`
						);

						if (listOfQualifiedLeadsRenamed) {
							const dtoListOfQualifiedLeads = await createDocumentFolderDocument(
								claimParentFolderId,
								listOfQualifiedLeadsRenamed
							);

							if (dtoListOfQualifiedLeads.id) {
								updateMDFClaimActivity(
									activity,
									dtoMDFClaim?.id,
									dtoMDFClaimActivity.id,
									mdfRequest.r_accToMDFReqs_accountEntry?.id,
									dtoListOfQualifiedLeads.id as LiferayFile &
										number
								);
							}
						}
					}

					if (
						activity.allContents?.length &&
						dtoMDFClaimActivity.id
					) {
						await Promise.all(
							activity.allContents.map(
								async (allContentDocument) => {
									if (!allContentDocument.id) {
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
												createMDFClaimActivityDocument(
													dtoAllContentDocument.id as LiferayFile &
														number,
													dtoMDFClaimActivity.id,
													mdfRequest
														.r_accToMDFReqs_accountEntry
														?.id
												);
											}
										}
									}
								}
							)
						);
					}

					if (activity.budgets?.length && dtoMDFClaimActivity.id) {
						await Promise.all(
							activity.budgets.map(async (budget) => {
								const dtoMDFClaimBudget = budget.id
									? await updateMDFClaimActivityBudget(
											budget,
											dtoMDFClaimActivity.id,
											budget.id,
											mdfRequest
												.r_accToMDFReqs_accountEntry?.id
									  )
									: await createMDFClaimActivityBudget(
											budget,
											dtoMDFClaimActivity.id,
											mdfRequest
												.r_accToMDFReqs_accountEntry?.id
									  );

								if (
									budget.invoice &&
									!budget.invoice.id &&
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
											updateMDFClaimActivityBudget(
												budget,
												dtoMDFClaimActivity.id,
												dtoMDFClaimBudget.id,
												mdfRequest
													.r_accToMDFReqs_accountEntry
													?.id,
												dtoBudgetInvoice.id as LiferayFile &
													number
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

		if (submitValues.id) {
			Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

			Liferay.Util.openToast({
				message: 'MDF Claim was successfully edited.',
				type: 'success',
			});

			return;
		}

		if (submitValues.mdfClaimStatus.key === Status.DRAFT.key) {
			Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

			Liferay.Util.openToast({
				message: 'MDF Claim was successfully saved as draft.',
				type: 'success',
			});

			return;
		}

		Liferay.Util.openToast({
			message: 'MDF Claim was successfully submitted.',
			type: 'success',
		});

		Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);
	}
	catch (error: unknown) {
		formikHelpers.setStatus(false);
		formikHelpers.setSubmitting(false);

		Liferay.Util.openToast({
			message: 'MDF Claim could not be submitted.',
			title: 'Error',
			type: 'danger',
		});
	}
}
