/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import type {Atom} from '@liferay/frontend-js-state-web';

type AccessibilityMenuSetting = {
	className: string;
	key: string;
	label: string;
	updating: boolean;
	value: boolean;
};

export const accessibilityMenuAtom: Atom<Array<AccessibilityMenuSetting>>;

export const CONSTANTS: {
	ACCESSIBILITY_SETTING_EXPAND_TEXT: 'ACCESSIBILITY_SETTING_EXPAND_TEXT';
	ACCESSIBILITY_SETTING_INCREASE_TEXT_SPACING: 'ACCESSIBILITY_SETTING_INCREASE_TEXT_SPACING';
	ACCESSIBILITY_SETTING_REDUCED_MOTION: 'ACCESSIBILITY_SETTING_REDUCED_MOTION';
	ACCESSIBILITY_SETTING_SHOW_UNDERLINE: 'ACCESSIBILITY_SETTING_SHOW_UNDERLINE';
};
