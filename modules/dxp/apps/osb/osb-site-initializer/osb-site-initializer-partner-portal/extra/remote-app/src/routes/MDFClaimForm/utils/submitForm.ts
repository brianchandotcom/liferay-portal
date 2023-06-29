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
import {Status} from '../../../common/utils/constants/status';
import updateStatus from '../../../common/utils/updateStatus';
import submitMDFClaim from './submitMDFClaim';
import submitMDFClaimActivity from './submitMDFClaimActivity';
import submitMDFClaimActivityDocuments from './submitMDFClaimActivityDocuments';
import submitMDFClaimBudget from './submitMDFClaimBudget';
import submitMDFClaimProxyAPI from './submitMDFClaimProxyAPI';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	mdfRequest: MDFRequestDTO,
	siteURL: string,
	currentClaimStatus?: LiferayPicklist,
	changeStatus?: boolean
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	const submitValues = {...values};

	const updatedStatus = updateStatus(
		submitValues.mdfClaimStatus,
		currentClaimStatus,
		changeStatus,
		submitValues.id
	);

	submitValues.mdfClaimStatus = updatedStatus;

	submitValues.partial = submitValues.activities?.some((activity) =>
		Boolean(activity.budgets?.some((budget) => !budget.selected))
	);

	try {
		const dtoMDFClaim =
			submitValues.mdfClaimStatus.key === Status.DRAFT.key
				? await submitMDFClaim(submitValues, mdfRequest)
				: await submitMDFClaimProxyAPI(submitValues, mdfRequest);

		if (submitValues.activities?.length) {
			await Promise.all(
				submitValues.activities.map(async (mdfClaimActivity) => {
					if (
						mdfClaimActivity.selected &&
						mdfRequest.r_accToMDFReqs_accountEntryId &&
						dtoMDFClaim?.id
					) {
						const dtoMDFClaimActivity = await submitMDFClaimActivity(
							mdfClaimActivity,
							mdfRequest.r_accToMDFReqs_accountEntryId,
							dtoMDFClaim.id
						);

						if (
							mdfClaimActivity.proofOfPerformance &&
							dtoMDFClaimActivity?.id &&
							mdfRequest.r_accToMDFReqs_accountEntryId
						) {
							submitMDFClaimActivityDocuments(
								mdfClaimActivity.proofOfPerformance,
								mdfRequest.r_accToMDFReqs_accountEntryId,
								dtoMDFClaimActivity.id
							);
						}

						if (mdfClaimActivity.budgets?.length) {
							await Promise.all(
								mdfClaimActivity.budgets.map(
									async (mdfClaimBudget) => {
										if (
											mdfClaimBudget.selected &&
											mdfRequest.r_accToMDFReqs_accountEntryId &&
											dtoMDFClaimActivity.id
										) {
											submitMDFClaimBudget(
												mdfClaimBudget,
												mdfRequest.r_accToMDFReqs_accountEntryId,
												dtoMDFClaimActivity.id
											);
										}
									}
								)
							);
						}
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

		Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

		Liferay.Util.openToast({
			message: 'MDF Claim was successfully submitted.',
			type: 'success',
		});

		return;
	} catch (error: unknown) {
		formikHelpers.setStatus(false);
		formikHelpers.setSubmitting(false);

		Liferay.Util.openToast({
			message: 'MDF Claim could not be submitted.',
			title: 'Error',
			type: 'danger',
		});
	}
}
