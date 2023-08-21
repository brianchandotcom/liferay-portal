/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fragment} from '../constants/Fragment';

export default function getComponentType(fragment: Fragment) {
	if (fragment.itemType === 'collection') {
		return Liferay.Language.get('collection');
	}

	return fragment.isPortlet
		? Liferay.Language.get('widget')
		: Liferay.Language.get('fragment');
}
