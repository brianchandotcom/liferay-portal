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

import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import {RequestStatus} from '../../../common/enums/requestStatus';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import {Liferay} from '../../../common/services/liferay';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import createMDFRequestActivityBudgets from '../../../common/services/liferay/object/budgets/createMDFRequestActivityBudgets';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';

export default async function submitForm(
	values: MDFRequest,
	formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
	siteURL: string,
	currentRequestStatus?: RequestStatus
) {
	formikHelpers.setSubmitting(true);

	if (currentRequestStatus) {
		values.requestStatus = currentRequestStatus;
	}

	const dtoMDFRequest = await createMDFRequest(values);

	if (values.activities.length && dtoMDFRequest?.id) {
		const dtoMDFRequestActivities = await createMDFRequestActivities(
			dtoMDFRequest.id,
			values.activities
		);

		if (dtoMDFRequestActivities?.length) {
			await Promise.all(
				values.activities.map(async (activity, index) => {
					const dtoActivity = dtoMDFRequestActivities[index];

					if (activity.budgets?.length && dtoActivity?.id) {
						return await createMDFRequestActivityBudgets(
							dtoActivity.id,
							activity.budgets
						);
					}
				})
			);
		}
	}

	Liferay.Util.navigate(`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`);
}
