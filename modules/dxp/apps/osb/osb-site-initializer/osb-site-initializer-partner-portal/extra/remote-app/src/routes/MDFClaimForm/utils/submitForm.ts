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
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Liferay} from '../../../common/services/liferay';
import createMDFClaimActivities from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivities';
import createMDFClaimActivityBudgets from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFClaimDocuments from '../../../common/services/liferay/object/mdf-claim-documents/createMDFClaimDocuments';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';
import createMDFClaimProxyAPI from './createMDFClaimProxyAPI';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	mdfRequest: MDFRequestDTO
) {
	formikHelpers.setSubmitting(true);

	const dtoMDFClaim = Liferay.FeatureFlags['LPS-164528']
		? await createMDFClaimProxyAPI(values, mdfRequest)
		: await createMDFClaim(
				ResourceName.MDF_REQUEST_DXP,
				values,
				mdfRequest
		  );

	if (values.reimbursementInvoice) {
		await createMDFClaimDocuments(
			values.reimbursementInvoice,
			dtoMDFClaim?.id
		);
	}

	if (values.activities?.length && dtoMDFClaim?.id) {
		const dtoMDFClaimActivities = await createMDFClaimActivities(
			dtoMDFClaim.id,
			values.activities?.filter((activity) => {
				return activity.selected;
			})
		);

		if (dtoMDFClaimActivities?.length) {
			await Promise.all(
				values.activities.map(async (activity, index) => {
					const dtoActivity = dtoMDFClaimActivities[index];

					if (
						activity.budgets?.length &&
						dtoActivity?.id &&
						activity.listQualifiedLeads
					) {
						await createMDFClaimDocuments(
							activity?.listQualifiedLeads,
							dtoActivity?.id
						);

						const dtoMDFClaimBudgets = await createMDFClaimActivityBudgets(
							dtoActivity.id,
							activity.budgets?.filter((budget) => {
								return budget.invoice;
							})
						);

						activity.budgets.map(async (budgets, index) => {
							const dtoBudget = dtoMDFClaimBudgets[index];

							if (dtoBudget?.id && budgets.invoice) {
								await createMDFClaimDocuments(
									budgets?.invoice,
									dtoBudget?.id
								);
							}
						});
					}
				})
			);
		}
	}
}
