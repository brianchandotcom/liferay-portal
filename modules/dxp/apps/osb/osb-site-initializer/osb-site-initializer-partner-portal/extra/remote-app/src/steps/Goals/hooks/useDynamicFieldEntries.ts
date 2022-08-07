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

import {LiferayPicklistName} from '../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../services/liferay/list-type-definitions/useGetListTypeDefinitions';
import useGetAdditionalOptions from '../../../services/liferay/object/additional-options/useGetAdditionalOptions';
import useGetMyUserAccount from '../../../services/liferay/user-account/useGetMyUserAccount';
import getEntriesByListTypeDefinitions from '../utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries() {
	const {data: userAccount} = useGetMyUserAccount();
	const {data: additionalOptions} = useGetAdditionalOptions();
	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.COUNTRIES,
		LiferayPicklistName.LIFERAY_BUSINESS_SALES_GOALS,
		LiferayPicklistName.TARGETS_AUDIENCE_ROLE,
		LiferayPicklistName.TARGETS_MARKET,
	]);

	const companiesEntries = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				label: accountBrief.name,
				value: accountBrief.id,
			})),
		[userAccount?.accountBriefs]
	);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	const additionalOptionsEntries = useMemo(
		() =>
			additionalOptions?.items.map((additionalOption) => ({
				description: additionalOption.description,
				label: additionalOption.name,
				value: String(additionalOption.id),
			})),
		[additionalOptions?.items]
	);

	return {
		additionalOptionsEntries,
		companiesEntries,
		fieldEntries,
	};
}
