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
import {Status} from '../../../common/enums/status';
import MDFRequestBudgetDTO from '../../../common/interfaces/dto/mdfRequestBudgetDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import {Liferay} from '../../../common/services/liferay';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import updateMDFRequestActivities from '../../../common/services/liferay/object/activity/updateMDFRequestActivities';
import createMDFRequestActivityBudgets from '../../../common/services/liferay/object/budgets/createMDFRequestActivityBudgets';
import updateMDFRequestActivityBudgets from '../../../common/services/liferay/object/budgets/updateMDFRequestActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';
import createMDFRequestActivitiesProxyAPI from './createMDFRequestActivitiesProxyAPI';
import createMDFRequestProxyAPI from './createMDFRequestProxyAPI';

export default async function submitForm(
	values: MDFRequest,
	formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
	mdfRequestId: number,
	siteURL: string,
	currentRequestStatus?: Status
) {
	formikHelpers.setSubmitting(true);
	if (currentRequestStatus) {
		values.requestStatus = currentRequestStatus;
	}

	const dtoMDFRequest =
		Liferay.FeatureFlags['LPS-164528'] && currentRequestStatus
			? await createMDFRequestProxyAPI(values)
			: mdfRequestId
			? await updateMDFRequest(
					ResourceName.MDF_REQUEST_DXP,
					values,
					mdfRequestId
			  )
			: await createMDFRequest(ResourceName.MDF_REQUEST_DXP, values);

	if (values?.activities?.length && dtoMDFRequest?.id) {
		const dtoMDFRequestActivities = await Promise.all(
			values?.activities?.map((activity) =>
				Liferay.FeatureFlags['LPS-164528'] && currentRequestStatus
					? createMDFRequestActivitiesProxyAPI(
							activity,
							values.company,
							dtoMDFRequest.id,
							dtoMDFRequest.externalReferenceCodeSF
					  )
					: mdfRequestId
					? updateMDFRequestActivities(
							ResourceName.ACTIVITY_DXP,
							activity,
							values.company,
							dtoMDFRequest.id,
							dtoMDFRequest.externalReferenceCodeSF
					  )
					: createMDFRequestActivities(
							ResourceName.ACTIVITY_DXP,
							activity,
							values.company,
							dtoMDFRequest.id,
							dtoMDFRequest.externalReferenceCodeSF
					  )
			)
		);

		if (dtoMDFRequestActivities?.length) {
			await Promise.all(
				values.activities.map(async (activity, index) => {
					const dtoActivity = dtoMDFRequestActivities[index];
					if (activity.budgets?.length && dtoActivity?.id) {
						if (mdfRequestId) {
							activity.budgets?.map(async (budget) => {
								return await updateMDFRequestActivityBudgets(
									dtoActivity.id as number,
									activity.budgets as MDFRequestBudgetDTO[],
									budget?.id as number
								);
							});
						} else {
							return await createMDFRequestActivityBudgets(
								dtoActivity.id,
								activity.budgets
							);
						}
					}
				})
			);
		}
	}

	Liferay.Util.navigate(`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`);
}
