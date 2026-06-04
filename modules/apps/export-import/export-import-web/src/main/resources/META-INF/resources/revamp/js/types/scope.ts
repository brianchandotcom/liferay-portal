/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const SCOPES = {
	ASSET_LIBRARY: 'assetLibrary',
	COMPANY: 'company',
	SITE: 'site',
} as const;

export type Scope = (typeof SCOPES)[keyof typeof SCOPES];
