/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {performLoginViaApi, userData} from '../../../utils/performLogin';

const test = mergeTests(dataApiHelpersTest, loginTest());

test('Login with different users works', async ({apiHelpers, page}) => {
	const adminUser = await apiHelpers.headlessAdminUser.postUserAccount();

	const adminRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
		adminRole.externalReferenceCode,
		adminUser.id
	);

	const regularUser = await apiHelpers.headlessAdminUser.postUserAccount();

	const sessionIds: string[] = [];

	for (const user of [
		{screenName: 'test'},
		{screenName: adminUser.alternateName},
		{screenName: regularUser.alternateName},
	]) {
		userData[user.screenName] = userData[user.screenName] || {
			name: user.screenName,
			password: userData['test'].password,
			surname: user.screenName,
		};

		const cookies = await performLoginViaApi({
			page,
			screenName: user.screenName,
		});

		const sessionId = cookies.find((c) => c.name === 'JSESSIONID')?.value;

		expect(sessionId).toBeTruthy();
		expect(sessionIds).not.toContain(sessionId);

		sessionIds.push(sessionId!);
	}
});
