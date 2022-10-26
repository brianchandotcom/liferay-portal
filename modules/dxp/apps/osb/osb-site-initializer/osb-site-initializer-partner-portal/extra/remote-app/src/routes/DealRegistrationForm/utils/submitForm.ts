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
import DealRegistration from '../../../common/interfaces/dealRegistration';
import {Liferay} from '../../../common/services/liferay';
import createDealRegistration from '../../../common/services/liferay/object/deal-registration/createDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function submitForm(
	values: DealRegistration,
	formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
	siteURL: string,
	currentRequestStatus?: RequestStatus
) {
	formikHelpers.setSubmitting(true);

	if (currentRequestStatus) {
		values.registrationStatus = currentRequestStatus;
	}

	await createDealRegistration(ResourceName.DEAL_REGISTRATION_DXP, values);

	Liferay.Util.navigate(
		`${siteURL}/${PRMPageRoute.DEAL_REGISTRATION_LISTING}`
	);
}
