/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
