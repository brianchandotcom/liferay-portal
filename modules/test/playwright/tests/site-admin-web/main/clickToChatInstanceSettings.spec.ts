/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {clickToChatPagesTest} from '../../site-admin-web/main/fixtures/clickToChatPagesTest';
import {clickToChatConfig} from './clickToChat.config';

export const test = mergeTests(
	clickToChatPagesTest,
	instanceSettingsPagesTest,
	loginTest()
);

test.afterEach(async ({clickToChatInstanceSettingsPage, page}) => {
	await clickToChatInstanceSettingsPage.goto();

	await page.getByLabel('Enable Click to Chat').uncheck();

	await clickToChatInstanceSettingsPage.selectChatProvider('');

	await clickToChatInstanceSettingsPage.setChatProviderPassword('');
});

test.beforeEach(async ({clickToChatInstanceSettingsPage}) => {
	await clickToChatInstanceSettingsPage.enableClickToChat();
});

const providers = [
	{
		iconKey: 'chatwootIcon',
		name: 'Chatwoot',
		passwordKey: 'chatwoot',
	},
	{
		iconKey: 'crispIcon',
		name: 'Crisp',
		passwordKey: 'crisp',
	},
	{
		iconKey: 'hubspotIcon',
		name: 'Hubspot',
		passwordKey: 'hubspot',
	},
	{
		iconKey: 'jivoChatIcon',
		name: 'JivoChat',
		passwordKey: 'jivochat',
	},
	{
		iconKey: 'liveChatIcon',
		name: 'LiveChat',
		passwordKey: 'livechat',
	},
	{
		iconKey: 'livePersonIcon',
		name: 'LivePerson',
		passwordKey: 'liveperson',
		skip: true,
	},
	{
		iconKey: 'smartsuppIcon',
		name: 'Smartsupp',
		passwordKey: 'smartsupp',
	},
	{
		iconKey: 'tawkToIcon',
		name: 'TawkTo',
		passwordKey: 'tawkto',
		skip: true,
	},
	{
		iconKey: 'tidioIcon',
		name: 'Tidio',
		passwordKey: 'tidio',
	},
	{
		iconKey: 'zendeskIcon',
		name: 'Zendesk Web Widget Classic',
		passwordKey: 'zendesk',
		skip: true,
	},
];

for (const provider of providers) {
	const runTest = provider.skip ? test.skip : test;

	runTest(
		`${provider.name} can be enabled and disabled`,
		{
			tag: '@LPS-129042',
		},
		async ({clickToChatInstanceSettingsPage, page}) => {
			await clickToChatInstanceSettingsPage.selectChatProvider(
				provider.name
			);

			await clickToChatInstanceSettingsPage.setChatProviderPassword(
				clickToChatConfig.password[provider.passwordKey]
			);

			await expect(
				clickToChatInstanceSettingsPage[provider.iconKey].first()
			).toBeAttached({timeout: 1000});

			await page.getByLabel('Enable Click to Chat').uncheck();

			await clickToChatInstanceSettingsPage.saveConfiguration();

			await expect(
				clickToChatInstanceSettingsPage[provider.iconKey]
			).not.toBeAttached({timeout: 1000});
		}
	);

	runTest(
		`${provider.name} provider keeps enabled after logout and login`,
		{
			tag: '@LPS-133453',
		},
		async ({clickToChatInstanceSettingsPage, page}) => {
			await clickToChatInstanceSettingsPage.selectChatProvider(
				provider.name
			);

			await clickToChatInstanceSettingsPage.setChatProviderPassword(
				clickToChatConfig.password[provider.passwordKey]
			);

			await expect(
				clickToChatInstanceSettingsPage[provider.iconKey].first()
			).toBeAttached({timeout: 1000});

			await performLogout(page);

			await performLoginViaApi({page, screenName: 'test'});

			await expect(
				clickToChatInstanceSettingsPage[provider.iconKey]
			).toBeAttached({timeout: 1000});
		}
	);
}

test(
	'Can hide chat widget in control panel',
	{
		tag: '@LPS-145280',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		try {
			await page.getByLabel('Hide in Control Panel').check();

			await clickToChatInstanceSettingsPage.saveConfiguration();

			await expect(
				clickToChatInstanceSettingsPage.jivoChatIcon
			).not.toBeAttached();
		}
		finally {
			await page.getByLabel('Hide in Control Panel').uncheck();

			await clickToChatInstanceSettingsPage.saveButton.click();
		}
	}
);

test.skip(
	'Can hubspot access required scopes',
	{
		tag: '@LPS-137169',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Hubspot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			'nonexistent/66b6b4b2-d096-45a1-b25d-7dab3f332167'
		);

		await waitForAlert(
			page,
			`Error:This app hasn't been granted all required scopes to make this call.`,
			{type: 'danger'}
		);
	}
);

test(
	'Hide Chat Provider',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Override'});

		await expect(
			clickToChatInstanceSettingsPage.chatProvider
		).not.toBeVisible();

		await expect(
			clickToChatInstanceSettingsPage.chatProviderAccountId
		).not.toBeVisible();
	}
);

test(
	'Is api key invalid for hubspot',
	{
		tag: '@LPS-137169',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Hubspot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			'19907868/1-d096-45a1-b25d-7dab3f332167'
		);

		await waitForAlert(page, `Error:The API key provided is invalid.`, {
			type: 'danger',
		});
	}
);
