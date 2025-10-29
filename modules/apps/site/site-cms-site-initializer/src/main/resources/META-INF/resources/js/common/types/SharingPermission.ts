/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const SharingPermission = {
	Comment: 'ADD_DISCUSSION',
	Update: 'UPDATE',
	View: 'VIEW',
} as const;

export type SharingPermission =
	(typeof SharingPermission)[keyof typeof SharingPermission];
