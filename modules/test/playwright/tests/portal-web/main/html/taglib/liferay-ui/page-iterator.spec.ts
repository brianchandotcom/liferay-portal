/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../../fixtures/loginTest';
import {MessageId} from '../../../../../../helpers/LanguageApiHelper';
import getBasicWebContentStructureId from '../../../../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../../../../journal-web/main/fixtures/journalPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

test(
	'Page iterator tag should not treat numbers as translatable keys',
	{
		tag: '@LPD-80589',
	},
	async ({apiHelpers, journalPage, page, site}) => {
		const messageId: MessageId = {
			key: '1',
			languageId: 'en_US',
		};

		try {
			await test.step(`Create language override with key '${messageId.key}'`, async () => {
				await apiHelpers.language.putMessage({
					...messageId,
					value: 'One',
				});
			});

			await test.step(`Create two web contents`, async () => {
				const basicWebContentStructureId =
					await getBasicWebContentStructureId(apiHelpers);

				for (let i = 0; i < 2; i++) {
					await apiHelpers.jsonWebServicesJournal.addWebContent({
						ddmStructureId: basicWebContentStructureId,
						groupId: site.id,
						titleMap: {en_US: `Web Content ${i}`},
					});
				}
			});

			await test.step(`Check pagination text`, async () => {
				await journalPage.goto(site.friendlyUrlPath);

				await expect(page.getByText('Showing 1 to')).toHaveCount(1);
			});
		}
		finally {
			await test.step(`Remove language override with key '${messageId.key}'`, async () => {
				await apiHelpers.language.deleteMessage(messageId);
			});
		}
	}
);
