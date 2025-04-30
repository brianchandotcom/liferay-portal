/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function closeSessions(apiHelpers, page) {
	await apiHelpers.jsonWebServicesOSBAsah.closeSessions();

	// This timeout is required because backend needs this time to close sessions properly.

	await page.waitForTimeout(10000);
}
