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
import mdfRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import Role from '../../../common/interfaces/role';
import {Liferay} from '../../../common/services/liferay';
import deleteMDFRequestActivities from '../../../common/services/liferay/object/activity/deleteMDFRequestActivities';
import deleteMDFRequestActivitiesSF from '../../../common/services/liferay/object/activity/deleteMDFRequestActivitiesSF';
import createMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/createMDFRequestActivityBudgets';
import deleteMDFRequestActivityBudgets from '../../../common/services/liferay/object/budgets/deleteMDFRequestActivityBudgets';
import updateMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/updateMDFRequestActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';
import {Status} from '../../../common/utils/constants/status';
import updateStatus from '../../../common/utils/updateStatus';
import createMDFRequestActivitiesProxyAPI from './createMDFRequestActivitiesProxyAPI';
import createMDFRequestProxyAPI from './createMDFRequestProxyAPI';

export default async function submitForm(
	values: MDFRequest,
	formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
	siteURL: string,
	currentRequestStatus?: LiferayPicklist,
	roles?: Role[]
) {
	formikHelpers.setSubmitting(true);

	const updatedStatus = updateStatus(
		values.mdfRequestStatus,
		currentRequestStatus,
		roles,
		values.id,
		values.totalMDFRequestAmount
	);

	values.mdfRequestStatus = updatedStatus && updatedStatus;

	let dtoMDFRequest: mdfRequestDTO | undefined = undefined;

	if (values.mdfRequestStatus !== Status.DRAFT) {
		dtoMDFRequest = await createMDFRequestProxyAPI(values);
	} else if (values.id) {
		dtoMDFRequest = await updateMDFRequest(
			ResourceName.MDF_REQUEST_DXP,
			values,
			values.id
		);
	} else {
		dtoMDFRequest = await createMDFRequest(
			ResourceName.MDF_REQUEST_DXP,
			values
		);
	}

	if (values?.activities?.length && dtoMDFRequest?.id) {
		const dtoMDFRequestActivities = await Promise.all(
			values?.activities?.map(async (activity) => {
				if (values.mdfRequestStatus !== Status.DRAFT) {
					return createMDFRequestActivitiesProxyAPI(
						activity,
						values.company,
						dtoMDFRequest?.id,
						dtoMDFRequest?.externalReferenceCode
					);
				}

				if (activity.id && activity.removed) {
					if (activity.externalReferenceCodeSF) {
						await deleteMDFRequestActivitiesSF(
							ResourceName.ACTIVITY_SALESFORCE,
							activity.externalReferenceCodeSF as string
						);
					}

					await deleteMDFRequestActivities(
						ResourceName.ACTIVITY_DXP,
						activity.id as number
					);

					return null;
				}
			})
		);

		if (dtoMDFRequestActivities?.length) {
			values.activities.map((activity, index) => {
				const dtoActivity = dtoMDFRequestActivities[index];

				if (activity.budgets?.length && dtoActivity?.id) {
					activity.budgets?.map(async (budget) => {
						if (budget?.id) {
							await updateMDFRequestActivityBudget(
								dtoActivity.id as number,
								budget
							);
						} else {
							await createMDFRequestActivityBudget(
								dtoActivity.id as number,
								budget
							);
						}

						if (budget.id && budget.removed) {
							return deleteMDFRequestActivityBudgets(
								ResourceName.BUDGET,
								budget.id as number
							);
						}
					});
				}
			});
		}
	}

	if (values.id) {
		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}?edit-success=true`
		);

		return;
	}

	Liferay.Util.navigate(
		`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}/?new-success=true`
	);
}
