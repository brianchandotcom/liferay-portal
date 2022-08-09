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

import {useEffect, useState} from 'react';

import LiferayPicklist from '../../../../../common/interfaces/liferayPicklist';
import useGetCompanyExtenderByAccountEntryId from '../../../../../common/services/liferay/object/company-extenders/useGetCompanyExtenderByAccountEntryId';

export default function useCountryCompanyExtender(
	updateCountryValue: (country: LiferayPicklist) => void
) {
	const [selectedAccountEntryId, setSelectedAccountEntryId] = useState<
		string
	>();

	const {data: companyExtender} = useGetCompanyExtenderByAccountEntryId(
		selectedAccountEntryId
	);

	useEffect(() => {
		if (companyExtender?.country) {
			updateCountryValue(companyExtender?.country);
		}
	}, [updateCountryValue, companyExtender?.country]);

	return {setSelectedAccountEntryId};
}
