/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickToChatPagesTest} from '../../site-admin-web/main/fixtures/clickToChatPagesTest';
import {clickToChatConfig} from './clickToChat.config';

export const test = mergeTests(
	clickToChatPagesTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest()
);

test.afterEach(async ({clickToChatSettingsPage, page}) => {
	await clickToChatSettingsPage.gotoInstanceSettings();

	await page.getByLabel('Enable Click to Chat').uncheck();

	if (clickToChatSettingsPage.chatProvider.isHidden()) {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});
	}

	await clickToChatSettingsPage.selectChatProvider('');

	await clickToChatSettingsPage.setChatProviderPassword('');
});

test.beforeEach(async ({clickToChatSettingsPage}) => {
	await clickToChatSettingsPage.enableClickToChat();
});

test(
	'Check Strategy Always Inherit Persistence',
	{
		tag: '@LPS-132716',
	},
	async ({clickToChatSettingsPage, page}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});

		await clickToChatSettingsPage.saveConfiguration();

		await clickToChatSettingsPage.selectChatProvider('JivoChat');

		await clickToChatSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(clickToChatSettingsPage.jivoChatIcon).toBeAttached();

		await clickToChatSettingsPage.gotoSiteSettings();

		await expect(
			page.getByText('Always Inherit', {exact: true})
		).toBeVisible();

		await expect(
			clickToChatSettingsPage.chatProvider.filter({
				hasText: 'JivoChat',
			})
		).toBeVisible();

		await expect(clickToChatSettingsPage.chatProviderAccountId).toHaveValue(
			clickToChatConfig.password.jivochat
		);
	}
);

test(
	'Check Strategy Always Override Persistence',
	{
		tag: '@LPS-132716',
	},
	async ({clickToChatSettingsPage, page}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Override'});

		await clickToChatSettingsPage.saveConfiguration();

		await clickToChatSettingsPage.gotoSiteSettings();

		await expect(
			page.getByText('Always Override', {exact: true})
		).toBeVisible();

		await clickToChatSettingsPage.selectChatProvider('JivoChat');

		await clickToChatSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(clickToChatSettingsPage.jivoChatIcon).toBeAttached();

		await clickToChatSettingsPage.gotoInstanceSettings();

		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});
	}
);

test(
	'Check Strategy Inherit Or Override Persistence',
	{
		tag: '@LPS-132716',
	},
	async ({clickToChatSettingsPage, page}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Inherit or Override'});

		await clickToChatSettingsPage.selectChatProvider('JivoChat');

		await clickToChatSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(clickToChatSettingsPage.jivoChatIcon).toBeAttached();

		await clickToChatSettingsPage.gotoSiteSettings();

		await expect(
			page.getByText('Inherit or Override', {exact: true})
		).toBeVisible();

		await expect(
			clickToChatSettingsPage.chatProvider.filter({
				hasText: 'JivoChat',
			})
		).toBeVisible();

		await expect(clickToChatSettingsPage.chatProviderAccountId).toHaveValue(
			clickToChatConfig.password.jivochat
		);

		await clickToChatSettingsPage.selectChatProvider('Chatwoot');

		await clickToChatSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.chatwoot
		);

		await expect(clickToChatSettingsPage.chatwootIcon).toBeAttached();
	}
);
