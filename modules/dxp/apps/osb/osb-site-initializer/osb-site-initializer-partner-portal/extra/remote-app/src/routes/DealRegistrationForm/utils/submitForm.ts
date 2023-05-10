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
import DealRegistration from '../../../common/interfaces/dealRegistration';
import {Liferay} from '../../../common/services/liferay';
import createDealRegistrationProxyAPI from './createDealRegistrationProxyAPI';

export default async function submitForm(
	values: DealRegistration,
	formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
	siteURL: string
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	try {
		await createDealRegistrationProxyAPI(values);

		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.DEAL_REGISTRATION_LISTING}`
		);

		Liferay.Util.openToast({
			message: 'Deal successfully registered!',
			title: 'Success',
			type: 'success',
		});
	}
	catch (error: unknown) {
		formikHelpers.setSubmitting(false);
		formikHelpers.setStatus(false);

		Liferay.Util.openToast({
			message: 'Deal can not be registered.',
			title: 'Error',
			type: 'danger',
		});
	}
}
