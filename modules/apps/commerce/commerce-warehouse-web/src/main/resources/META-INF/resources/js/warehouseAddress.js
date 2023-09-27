/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({
	commerceRegionCode,
	companyId,
	countryTwoLettersISOCode,
	namespace,
}) {
	new Liferay.DynamicSelect([
		{
			select: `${namespace}countryTwoLettersISOCode`,
			selectData: (callback) => {
				Liferay.Service(
					'/country/get-company-countries',
					{
						active: true,
						companyId,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'a2',
			selectSort: true,
			selectVal: countryTwoLettersISOCode,
		},
		{
			select: `${namespace}commerceRegionCode`,
			selectData: (callback, selectKey) => {
				Liferay.Service(
					'/region/get-regions',
					{
						a2: selectKey,
						active: true,
						companyId,
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionCode',
			selectVal: commerceRegionCode,
		},
	]);
}
