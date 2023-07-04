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
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_INCREASED_TEXT_SPACING: {
			readonly className: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_REDUCED_MOTION: {
			readonly className: string;
			readonly key: KEYS;
			readonly label: string;
			readonly updating?: boolean | undefined;
			readonly value: boolean;
		};
		readonly ACCESSIBILITY_SETTING_UNDERLINED_LINKS: {
			readonly className: string;
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
