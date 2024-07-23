/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SELECT_FRAGMENT_FOR_NAME_EDITING} from './types';

export default function selectFragmentForNameEditing(itemId: string) {
	return {
		itemId,
		type: SELECT_FRAGMENT_FOR_NAME_EDITING,
	} as const;
}
