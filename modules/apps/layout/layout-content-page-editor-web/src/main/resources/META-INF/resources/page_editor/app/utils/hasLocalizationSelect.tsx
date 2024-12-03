/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import isLocalizationSelect from './isLocalizationSelect';

export function hasLocalizationSelect(
	fragmentEntryLinks: FragmentEntryLinkMap
): boolean {
	return Object.values(fragmentEntryLinks).some(
		(fragmentEntryLink) =>
			!fragmentEntryLink?.removed &&
			isLocalizationSelect(fragmentEntryLink)
	);
}
