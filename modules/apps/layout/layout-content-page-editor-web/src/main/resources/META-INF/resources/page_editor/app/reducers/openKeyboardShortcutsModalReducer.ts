/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openKeyboardShortcutsModal from '../actions/openKeyboardShortcutsModal';
import {OPEN_KEYBOARD_SHORTCUTS_MODAL} from '../actions/types';

export default function selectFragmentForNameEditingReducer(
	isOpen: false,
	action: ReturnType<typeof openKeyboardShortcutsModal>
) {
	if (action.type === OPEN_KEYBOARD_SHORTCUTS_MODAL) {
		return action.isOpen;
	}

	return false;
}
