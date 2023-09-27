/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCountries, getRegions} from 'frontend-js-web';
import PropTypes from 'prop-types';

function CountryRegionDynamicSelect({
	countrySelect,
	countrySelectVal = 0,
	regionSelect,
	regionSelectVal = 0,
}) {
	let japanCountryId;

	new Liferay.DynamicSelect([
		{
			select: countrySelect,
			selectData(callback) {
				getCountries((countries) => {
					const countryJP = countries.find(
						(country) => country.a2 === 'JP'
					);

					if (countryJP !== undefined) {
						japanCountryId = countryJP.countryId;
					}

					callback(countries);
				});
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: true,
			selectVal: countrySelectVal,
		},
		{
			select: regionSelect,
			selectData(callback, selectKey) {
				getRegions((regions) => {
					if (
						selectKey === japanCountryId &&
						Liferay.ThemeDisplay.getLanguageId() === 'ja_JP'
					) {
						regions.sort((region1, region2) => {
							if (
								Number(region1.regionCode) >
								Number(region2.regionCode)
							) {
								return 1;
							}

							if (
								Number(region1.regionCode) <
								Number(region2.regionCode)
							) {
								return -1;
							}

							return 0;
						});
					}

					callback(regions);
				}, selectKey);
			},
			selectDesc: 'title',
			selectDisableOnEmpty: true,
			selectId: 'regionId',
			selectVal: regionSelectVal,
		},
	]);
}

CountryRegionDynamicSelect.prototypes = {
	countrySelect: PropTypes.string.isRequired,
	countrySelectId: PropTypes.string.isRequired,
	countrySelectVal: PropTypes.string,
	regionSelect: PropTypes.string.isRequired,
	regionSelectId: PropTypes.string.isRequired,
	regionSelectVal: PropTypes.string,
};

export default CountryRegionDynamicSelect;
