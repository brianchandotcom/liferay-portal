/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import selectFragmentForNameEditing from '../actions/selectFragmentForNameEditing';
import {SELECT_FRAGMENT_FOR_NAME_EDITING} from '../actions/types';

export default function selectFragmentForNameEditingReducer(
	itemId: null,
	action: ReturnType<typeof selectFragmentForNameEditing>
) {
	if (action.type === SELECT_FRAGMENT_FOR_NAME_EDITING) {
		return action.itemId;
	}

	return null;
}
