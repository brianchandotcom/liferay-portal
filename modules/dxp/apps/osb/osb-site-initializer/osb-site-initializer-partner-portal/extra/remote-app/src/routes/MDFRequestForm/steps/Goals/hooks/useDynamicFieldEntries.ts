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

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../../../common/services/liferay/list-type-definitions/useGetListTypeDefinitions';
import useGetMyUserAccount from '../../../../../common/services/liferay/user-account/useGetMyUserAccount';
import getEntriesByListTypeDefinitions from '../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries(skipCompanies?: boolean) {
	const {data: userAccount} = useGetMyUserAccount(skipCompanies);

	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.ADDITIONAL_OPTIONS,
		LiferayPicklistName.COUNTRIES,
		LiferayPicklistName.LIFERAY_BUSINESS_SALES_GOALS,
		LiferayPicklistName.TARGET_AUDIENCE_ROLES,
		LiferayPicklistName.TARGET_MARKETS,
		LiferayPicklistName.CURRENCIES,
	]);

	const companiesEntries = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				defaultValue: accountBrief.id,
				label: accountBrief.name,
				value: accountBrief.externalReferenceCode,
			})) as React.OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
		[userAccount?.accountBriefs]
	);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		companiesEntries,
		fieldEntries,
	};
}
