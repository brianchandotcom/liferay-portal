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

// import {PRMPageRoute} from '../../../common/enums/prmPageRoute';

import MDFClaim from '../../../common/interfaces/mdfClaim';

// import {Liferay} from '../../../common/services/liferay';

import createMDFClaimActivities from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivities';
import createMDFClaimActivityBudgets from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudgets';
import createMDFClaimDocuments from '../../../common/services/liferay/object/mdf-claim-documents/createMDFClaimDocuments';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	_siteURL: string
) {
	formikHelpers.setSubmitting(true);

	const dtoMDFClaim = await createMDFClaim(values);

	await createMDFClaimDocuments(
		values.reimbursementInvoice,
		dtoMDFClaim?.id,
		0,
		0
	);

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

					if (activity.budgets?.length && dtoActivity?.id) {
						await createMDFClaimDocuments(
							activity?.listQualifiedLeads,
							0,
							dtoActivity?.id,
							0
						);

						const dtoMDFClaimBudgets = await createMDFClaimActivityBudgets(
							dtoActivity.id,
							activity.budgets?.filter((budget) => {
								return budget.invoice;
							}),
							dtoMDFClaim.id
						);

						activity.budgets.map(async (budgets, index) => {
							const dtoBudget = dtoMDFClaimBudgets[index];

							if (dtoBudget?.id) {
								await createMDFClaimDocuments(
									budgets?.invoice,
									0,
									0,
									dtoBudget?.id
								);
							}
						});
					}
				})
			);
		}
	}

	// Liferay.Util.navigate(`${siteURL}/${PRMPageRoute.MDF_CLAIM_LISTING}`);

}
