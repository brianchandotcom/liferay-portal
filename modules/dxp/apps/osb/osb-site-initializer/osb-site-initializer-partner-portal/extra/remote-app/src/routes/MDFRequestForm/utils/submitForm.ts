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
import {Liferay} from '../../../common/services/liferay';
import createMDFRequestActivitiesSF from '../../../common/services/liferay/object/activity/createMDFRequestActivities';
import deleteMDFRequestActivities from '../../../common/services/liferay/object/activity/deleteMDFRequestActivities';
import deleteMDFRequestActivitiesSF from '../../../common/services/liferay/object/activity/deleteMDFRequestActivitiesSF';
import updateMDFRequestActivities from '../../../common/services/liferay/object/activity/updateMDFRequestActivities';
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
	changeStatus?: boolean
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	const updatedStatus = updateStatus(
		values.mdfRequestStatus,
		currentRequestStatus,
		changeStatus,
		values.id,
		values.totalMDFRequestAmount
	);

	values.mdfRequestStatus = updatedStatus;

	let dtoMDFRequest: mdfRequestDTO | undefined = undefined;

	try {
		if (values.mdfRequestStatus.key !== Status.DRAFT.key) {
			dtoMDFRequest = await createMDFRequestProxyAPI(values);
		}
		else if (values.id) {
			dtoMDFRequest = await updateMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				values,
				values.id
			);
		}
		else {
			dtoMDFRequest = await createMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				values
			);
		}

		if (values?.activities?.length && dtoMDFRequest?.id) {
			const dtoMDFRequestActivities = await Promise.all(
				values?.activities?.map(async (activity) => {
					if (activity.id && activity.removed) {
						if (activity.externalReferenceCode) {
							await deleteMDFRequestActivitiesSF(
								ResourceName.ACTIVITY_SALESFORCE,
								activity.externalReferenceCode as string
							);
						}

						await deleteMDFRequestActivities(
							ResourceName.ACTIVITY_DXP,
							activity.id as number
						);

						return null;
					}
					if (values.mdfRequestStatus.key !== Status.DRAFT.key) {
						return createMDFRequestActivitiesProxyAPI(
							activity,
							values.company,
							dtoMDFRequest?.id,
							dtoMDFRequest?.externalReferenceCode
						);
					}
					else {
						if (activity.id) {
							await updateMDFRequestActivities(
								ResourceName.ACTIVITY_DXP,
								activity,
								values.company,
								dtoMDFRequest?.id,
								dtoMDFRequest?.externalReferenceCode,
								activity.externalReferenceCode
							);
						}
						else {
							return await createMDFRequestActivitiesSF(
								ResourceName.ACTIVITY_DXP,
								activity,
								values.company,
								dtoMDFRequest?.id,
								dtoMDFRequest?.externalReferenceCode,
								activity.externalReferenceCode
							);
						}
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
									budget,
									values.company
								);
								if (budget.removed) {
									await deleteMDFRequestActivityBudgets(
										ResourceName.BUDGET,
										budget.id as number
									);
								}
							}
							else {
								await createMDFRequestActivityBudget(
									dtoActivity.id as number,
									budget,
									values.company
								);
							}
						});
					}
				});
			}
		}

		if (values.id) {
			Liferay.Util.navigate(
				`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
			);

			Liferay.Util.openToast({
				message: 'MDF Request was successfully edited.',
				type: 'success',
			});

			return;
		}

		if (values.mdfRequestStatus.key === Status.DRAFT.key) {
			Liferay.Util.navigate(
				`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
			);

			Liferay.Util.openToast({
				message: 'MDF Request was successfully saved as draft.',
				type: 'success',
			});

			return;
		}

		Liferay.Util.openToast({
			message: 'MDF Request was successfully submitted.',
			type: 'success',
		});

		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
		);
	}
	catch (error: unknown) {
		formikHelpers.setStatus(false);
		formikHelpers.setSubmitting(false);

		Liferay.Util.openToast({
			message: 'MDF Request could not be submitted.',
			title: 'Error',
			type: 'danger',
		});
	}
}
