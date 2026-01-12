/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {deepClone} from 'frontend-js-web';

import {IBaseFilterState} from '../types';

export function deactivateFilter(filter: IBaseFilterState) {
	const updatedFilter = deepClone(filter);

	updatedFilter.active = false;
	updatedFilter.odataFilterString = undefined;
	updatedFilter.selectedData = undefined;

	return updatedFilter;
}
