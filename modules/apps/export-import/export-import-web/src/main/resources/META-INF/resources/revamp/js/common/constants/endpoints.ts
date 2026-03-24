/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const ENDPOINTS = {
	VALIDATE_IMPORT_FILE: '/o/export-import/v1.0/validate-import-file',
	VALIDATE_SITE_IMPORT_FILE: (groupId: number) =>
		`/o/export-import/v1.0/sites/${groupId}/validate-import-file`,
};
