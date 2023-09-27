/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({companyId, countryId, namespace, regionId}) {
	new Liferay.DynamicSelect([
		{
			select: `${namespace}countryId`,
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
			selectId: 'countryId',
			selectSort: true,
			selectVal: countryId,
		},
		{
			select: `${namespace}regionId`,
			selectData: (callback, selectKey) => {
				Liferay.Service(
					'/region/get-regions',
					{
						active: true,
						countryId: Number(selectKey),
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionId',
			selectVal: regionId,
		},
	]);
}
