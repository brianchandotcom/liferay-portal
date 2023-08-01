/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const CONSTANTS: {
	readonly ACCESSIBILITY_SETTING_EXPANDED_TEXT: 'ACCESSIBILITY_SETTING_EXPANDED_TEXT';
	readonly ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING: 'ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING';
	readonly ACCESSIBILITY_SETTING_REDUCED_MOTION: 'ACCESSIBILITY_SETTING_REDUCED_MOTION';
	readonly ACCESSIBILITY_SETTING_UNDERLINED_LINKS: 'ACCESSIBILITY_SETTING_UNDERLINED_LINKS';
};
declare type KEYS = keyof typeof CONSTANTS;
export declare const accessibilityMenuAtom: {
	readonly 'default': {
		readonly ACCESSIBILITY_SETTING_EXPANDED_TEXT: {
			readonly className: string;
			readonly description: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING: {
			readonly className: string;
			readonly description: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_REDUCED_MOTION: {
			readonly className: string;
			readonly description: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_UNDERLINED_LINKS: {
			readonly className: string;
			readonly description: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
	};
	readonly 'key': string;
	readonly 'Liferay.State.ATOM': true;
};
export {};
