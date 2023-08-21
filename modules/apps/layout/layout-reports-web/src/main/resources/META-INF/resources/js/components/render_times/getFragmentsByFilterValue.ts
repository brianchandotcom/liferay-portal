/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fragment, FragmentFilter} from '../../constants/Fragment';

export default function getFragmentsByFilterValue(
	filters: FragmentFilter,
	fragments: Fragment[]
) {
	let filteredFragments = fragments;

	if (filters.origin === 'fromMaster') {
		filteredFragments = filteredFragments.filter(
			({fromMaster}) => fromMaster
		);
	}

	if (filters.status) {
		filteredFragments = filteredFragments.filter(({cached}) =>
			filters.status === 'cached' ? cached : !cached
		);
	}

	if (filters.type) {
		filteredFragments = filteredFragments.filter(({isPortlet}) =>
			filters.type === 'fragment' ? !isPortlet : isPortlet
		);
	}

	return filteredFragments;
}
