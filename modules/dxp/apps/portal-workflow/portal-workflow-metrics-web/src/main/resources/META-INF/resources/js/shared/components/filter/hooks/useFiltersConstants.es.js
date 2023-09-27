/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import filterConstants from '../util/filterConstants.es';

const useFiltersConstants = (filterKeys) => {
	const keys = [];
	const pinnedValues = [];
	const titles = [];

	filterKeys.forEach((filterKey) => {
		if (filterConstants[filterKey]) {
			keys.push(filterConstants[filterKey].key);
			pinnedValues.push(filterConstants[filterKey].pinned);
			titles.push(filterConstants[filterKey].title);
		}
	});

	return {keys, pinnedValues, titles};
};

export {useFiltersConstants};
