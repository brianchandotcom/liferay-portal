/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '@liferay/frontend-js-state-web';

export const CONSTANTS = {
	ACCESSIBILITY_SETTING_EXPANDED_TEXT: 'ACCESSIBILITY_SETTING_EXPANDED_TEXT',
	ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING:
		'ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING',
	ACCESSIBILITY_SETTING_REDUCED_MOTION:
		'ACCESSIBILITY_SETTING_REDUCED_MOTION',
	ACCESSIBILITY_SETTING_UNDERLINED_LINKS:
		'ACCESSIBILITY_SETTING_UNDERLINED_LINKS',
} as const;

type KEYS = keyof typeof CONSTANTS;

type AccessibilityMenuSetting = {
	className: string;
	description: string;
	key: KEYS;
	label: string;
	updating?: boolean;
	value: boolean;
};

export const accessibilityMenuAtom = State.atom<
	Record<KEYS, AccessibilityMenuSetting>
>('accessibility-menu', {} as any);
