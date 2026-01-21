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
	'Chatwoot can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Chatwoot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.chatwoot
		);

		await expect(
			clickToChatInstanceSettingsPage.chatwootIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.chatwootIcon
		).not.toBeAttached();
	}
);

test(
	'Chatwoot provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Chatwoot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.chatwoot
		);

		await expect(
			clickToChatInstanceSettingsPage.chatwootIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.chatwootIcon
		).toBeAttached();
	}
);

test(
	'Crisp can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Crisp');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.crisp
		);

		await expect(clickToChatInstanceSettingsPage.crispIcon).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.crispIcon
		).not.toBeAttached();
	}
);

test(
	'Crisp provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Crisp');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.crisp
		);

		await expect(clickToChatInstanceSettingsPage.crispIcon).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(clickToChatInstanceSettingsPage.crispIcon).toBeAttached();
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
	'Hubspot can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Hubspot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.hubspot
		);

		await expect(
			clickToChatInstanceSettingsPage.hubspotIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.hubspotIcon
		).not.toBeAttached();
	}
);

test(
	'Hubspot provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Hubspot');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.hubspot
		);

		await expect(
			clickToChatInstanceSettingsPage.hubspotIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.hubspotIcon
		).toBeAttached();
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

test(
	'JivoChat can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).not.toBeAttached();
	}
);

test(
	'JivoChat provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('JivoChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.jivochat
		);

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.jivoChatIcon
		).toBeAttached();
	}
);

test(
	'LiveChat can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('LiveChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.livechat
		);

		await expect(
			clickToChatInstanceSettingsPage.liveChatIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.liveChatIcon
		).not.toBeAttached();
	}
);

test(
	'LiveChat provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('LiveChat');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.livechat
		);

		await expect(
			clickToChatInstanceSettingsPage.liveChatIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.liveChatIcon
		).toBeAttached();
	}
);

test(
	'LivePerson can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('LivePerson');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.liveperson
		);

		await expect(
			clickToChatInstanceSettingsPage.livePersonIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.livePersonIcon
		).not.toBeAttached();
	}
);

test(
	'LivePerson provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('LivePerson');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.liveperson
		);

		await expect(
			clickToChatInstanceSettingsPage.livePersonIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.livePersonIcon
		).toBeAttached();
	}
);

test(
	'Smartsupp can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Smartsupp');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.smartsupp
		);

		await expect(
			clickToChatInstanceSettingsPage.smartsuppIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.smartsuppIcon
		).not.toBeAttached();
	}
);

test(
	'Smartsupp provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Smartsupp');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.smartsupp
		);

		await expect(
			clickToChatInstanceSettingsPage.smartsuppIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.smartsuppIcon
		).toBeAttached();
	}
);

test(
	'TawkTo can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('TawkTo');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.tawkto
		);

		await expect(clickToChatInstanceSettingsPage.tawktoIcon).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.tawktoIcon
		).not.toBeAttached();
	}
);

test(
	'TawkTo provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('TawkTo');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.tawkto
		);

		await expect(clickToChatInstanceSettingsPage.tawktoIcon).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(clickToChatInstanceSettingsPage.tawktoIcon).toBeAttached();
	}
);

test(
	'Tidio can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Tidio');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.tidio
		);

		await expect(clickToChatInstanceSettingsPage.tidioIcon).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.tidioIcon
		).not.toBeAttached();
	}
);

test(
	'Tidio provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider('Tidio');

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.tidio
		);

		await expect(clickToChatInstanceSettingsPage.tidioIcon).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(clickToChatInstanceSettingsPage.tidioIcon).toBeAttached();
	}
);

test(
	'Zendesk can be enabled and disabled',
	{
		tag: '@LPS-129042',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider(
			'Zendesk Web Widget Classic'
		);

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.zendesk
		);

		await expect(
			clickToChatInstanceSettingsPage.zendeskIcon
		).toBeAttached();

		await page.getByLabel('Enable Click to Chat').uncheck();

		await clickToChatInstanceSettingsPage.saveConfiguration();

		await expect(
			clickToChatInstanceSettingsPage.zendeskIcon
		).not.toBeAttached();
	}
);

test(
	'Zendesk provider keeps enabled after logout and login',
	{
		tag: '@LPS-133453',
	},
	async ({clickToChatInstanceSettingsPage, page}) => {
		await clickToChatInstanceSettingsPage.selectChatProvider(
			'Zendesk Web Widget Classic'
		);

		await clickToChatInstanceSettingsPage.setChatProviderPassword(
			clickToChatConfig.password.zendesk
		);

		await expect(
			clickToChatInstanceSettingsPage.zendeskIcon
		).toBeAttached();

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		await expect(
			clickToChatInstanceSettingsPage.zendeskIcon
		).toBeAttached();
	}
);
