/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function getSettingValue(
	defaultValue: boolean,
	sessionClicksValue: boolean | undefined | null,
	key: string
): boolean;
export declare function isNullOrUndefined(
	value: boolean | string | undefined | null
): boolean;
export declare function toggleClassName(
	className: string,
	value: boolean
): void;
