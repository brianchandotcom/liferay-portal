/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {clickToChatPagesTest} from '../../site-admin-web/main/fixtures/clickToChatPagesTest';
import {clickToChatConfig} from './clickToChat.config';

export const test = mergeTests(
	clickToChatPagesTest,
	instanceSettingsPagesTest,
	loginTest(),
	siteSettingsPagesTest
);

test.afterEach(async ({clickToChatInstanceSettingsPage, page}) => {
	await clickToChatInstanceSettingsPage.goto();

	await page.getByLabel('Enable Click to Chat').uncheck();

	if (clickToChatInstanceSettingsPage.chatProvider.isHidden()) {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});
	}

	await clickToChatInstanceSettingsPage.selectChatProvider('');

	await clickToChatInstanceSettingsPage.setChatProviderPassword('');
});

test.beforeEach(async ({clickToChatInstanceSettingsPage}) => {
	await clickToChatInstanceSettingsPage.enableClickToChat();
});

test(
	'Check Strategy Always Inherit Persistence',
	{
		tag: '@LPS-132716',
	},
	async ({clickToChatInstanceSettingsPage, page, siteSettingsPage}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		await siteSettingsPage.goToSiteSetting(
			'Click to Chat',
			'Click to Chat'
		);

		await expect(
			page.getByText('Always Inherit', {exact: true})
		).toBeVisible();

		await expect(
			clickToChatInstanceSettingsPage.chatProvider.filter({
				hasText: 'JivoChat',
			})
		).toBeVisible();

		await expect(
			clickToChatInstanceSettingsPage.chatProviderAccountId
		).toHaveValue(clickToChatConfig.password.jivochat);
	}
);

test(
	'Check Strategy Always Override Persistence',
	{
		tag: '@LPS-132716',
	},
	async ({clickToChatInstanceSettingsPage, page, siteSettingsPage}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Override'});

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await siteSettingsPage.goToSiteSetting(
			'Click to Chat',
			'Click to Chat'
		);

		await expect(
			page.getByText('Always Override', {exact: true})
		).toBeVisible();

		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		await clickToChatInstanceSettingsPage.goto();

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
	async ({clickToChatInstanceSettingsPage, page, siteSettingsPage}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Inherit or Override'});

		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		await siteSettingsPage.goToSiteSetting(
			'Click to Chat',
			'Click to Chat'
		);

		await expect(
			page.getByText('Inherit or Override', {exact: true})
		).toBeVisible();

		await expect(
			clickToChatInstanceSettingsPage.chatProvider.filter({
				hasText: 'JivoChat',
			})
		).toBeVisible();

		await expect(
			clickToChatInstanceSettingsPage.chatProviderAccountId
		).toHaveValue(clickToChatConfig.password.jivochat);

		await clickToChatInstanceSettingsPage.selectChatProvider('Chatwoot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.chatwoot
		);

		await expect(
			clickToChatInstanceSettingsPage.chatwootIcon
		).toBeAttached();
	}
);
