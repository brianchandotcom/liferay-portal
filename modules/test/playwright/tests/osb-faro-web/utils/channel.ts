/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page } from "@playwright/test";

export async function createChannel(apiHelpers, name: string) {
	const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

	const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

	const channel = await apiHelpers.jsonWebServicesOSBFaro.createChannel(
		name,
		project.groupId
	);

	return {
		channel,
		project,
	};
}

export async function switchChannel(page: Page, channelName: string) {
	await page.locator('.channels-menu.button-root').click();
	await page.getByRole('link', {name: channelName}).click();
}
