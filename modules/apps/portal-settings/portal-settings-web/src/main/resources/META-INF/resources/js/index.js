/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CountryRegionDynamicSelect} from '@liferay/address-web';

function main({namespace}) {
	AUI().use('liferay-auto-fields', () => {
		new Liferay.AutoFields({
			contentBox: `#${namespace}addresses`,
			fieldIndexes: `${namespace}addressesIndexes`,
			namespace,
			on: {
				clone(event) {
					const guid = event.guid;
					const row = event.row;

					const dynamicSelects = row.one(
						'select[data-componentType=dynamic_select]'
					);

					if (dynamicSelects) {
						dynamicSelects.detach('change');
					}

					CountryRegionDynamicSelect.default({
						countrySelect: `${namespace}addressCountryId${guid}`,
						regionSelect: `${namespace}addressRegionId${guid}`,
					});
				},
			},
		}).render();
	});
}

export {CountryRegionDynamicSelect, main};
