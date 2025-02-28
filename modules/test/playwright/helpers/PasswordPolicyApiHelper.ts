/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type TPasswordPolicy = {
	allowDictionaryWordsToggle?: boolean;
	changeableToggle?: boolean;
	checkSyntaxToggle?: boolean;
	description?: string;
	expireableToggle?: boolean;
	historyToggle?: boolean;
	lockout?: boolean;
	minAlphanumeric?: number;
	minLength?: number;
	minLowerCase?: number;
	minNumbers?: number;
	minSymbols?: number;
	minUpperCase?: number;
	name?: string;
	regex?: string;
	resetTicketMaxAge?: boolean;
};
