/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {performUserSwitchViaApi, userData} from '../../../utils/performLogin';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	messageBoardsPagesTest
);

async function addAdministratorWithLocale(
	apiHelpers: ApiHelpers,
	languageId: string
) {
	const administratorRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToRole(
		administratorRole.externalReferenceCode,
		user.id
	);

	await apiHelpers.headlessAdminUser.patchUserAccount(user, {languageId});

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	return user;
}

test(
	'Message boards categories and threads can be viewed in Arabic and Japanese',
	{tag: ['@LPS-136929', '@LPS-136930', '@LPS-136931', '@LPS-136932']},
	async ({apiHelpers, messageBoardsPage, page, site}) => {
		const locales = [
			{
				categoryCount: '0件のカテゴリ',
				categoryName: '種別名',
				header: '掲示板',
				languageId: 'ja_JP',
				replyCount: '返信数：0',
				threadSubject: 'スレッドメッセージの件名',
			},
			{
				categoryCount: '0 فئات',
				categoryName: 'اسم الفئة',
				header: 'لوحات الرسائل',
				languageId: 'ar_SA',
				replyCount: '0 من الردود',
				threadSubject: 'موضوع رسالة الموضوع',
			},
		];

		// Seed a localized category and thread for each locale

		for (const locale of locales) {
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: locale.categoryName,
			});

			await apiHelpers.headlessDelivery.postMessageBoardThread({
				articleBody: locale.categoryName,
				headline: locale.threadSubject,
				siteId: site.id,
			});
		}

		// Each localized administrator sees the message boards admin in their
		// language

		for (const locale of locales) {
			const user = await addAdministratorWithLocale(
				apiHelpers,
				locale.languageId
			);

			await performUserSwitchViaApi(page, user.alternateName);

			await messageBoardsPage.goto(site.friendlyUrlPath);

			await expect(page.getByText(locale.header).first()).toBeVisible();
			await expect(
				page.getByText(locale.categoryName).first()
			).toBeVisible();
			await expect(
				page.getByText(locale.categoryCount).first()
			).toBeVisible();
			await expect(
				page.getByText(locale.threadSubject).first()
			).toBeVisible();
			await expect(
				page.getByText(locale.replyCount).first()
			).toBeVisible();
		}
	}
);
