/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OPEN_KEYBOARD_SHORTCUTS_MODAL} from './types';

export default function openKeyboardShortcutsModal(isOpen: boolean) {
	return {
		isOpen,
		type: OPEN_KEYBOARD_SHORTCUTS_MODAL,
	} as const;
}
