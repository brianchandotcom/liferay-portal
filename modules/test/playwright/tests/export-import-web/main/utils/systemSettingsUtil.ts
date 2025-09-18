/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SystemSettingsPage} from '../../../../pages/configuration-admin-web/SystemSettingsPage';

export async function getOverallMaximumUploadRequestSize(
	systemSettingsPage: SystemSettingsPage
): Promise<string> {
	await systemSettingsPage.goToSystemSetting(
		'Infrastructure',
		'Upload Servlet Request'
	);

	return systemSettingsPage.page
		.getByLabel('Overall Maximum Upload Request Size')
		.inputValue();
}

export async function setOverallMaximumUploadRequestSize({
	size,
	systemSettingsPage,
}: {
	size: string;
	systemSettingsPage: SystemSettingsPage;
}) {
	await systemSettingsPage.goToSystemSetting(
		'Infrastructure',
		'Upload Servlet Request'
	);

	await systemSettingsPage.page
		.getByLabel('Overall Maximum Upload Request Size')
		.fill(size);

	await systemSettingsPage.saveButton.click();
}
