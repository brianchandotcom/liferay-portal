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

import DealRegistration from '../../../common/interfaces/dealRegistration';
import createDealRegistration from '../../../common/services/liferay/object/deal-registration/createDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function createDealRegistrationProxyAPI(
	values: DealRegistration
) {
	const dtoLeadSFResponse = await createDealRegistration(
		ResourceName.LEADS_SALESFORCE,
		values
	);

	if (dtoLeadSFResponse.externalReferenceCode) {
		const dtoLeadProjectSFResponse = await createDealRegistration(
			ResourceName.PROJECT_SALESFORCE,
			values,
			dtoLeadSFResponse.externalReferenceCode
		);

		if (dtoLeadProjectSFResponse) {
			await createDealRegistration(
				ResourceName.LEAD_NOTIFICATION,
				values,
				dtoLeadSFResponse.externalReferenceCode
			);
		}
	}
}
