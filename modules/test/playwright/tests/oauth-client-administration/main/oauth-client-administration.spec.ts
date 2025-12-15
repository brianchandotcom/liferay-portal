/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {AuthServerLocalMetadatasPage} from '../../../pages/oauth-client-administration-web/AuthServerLocalMetadatasPage';

const test = mergeTests(loginTest());

test.describe('LPD-67473 Enable Configuration of oauth-authorization-server Well-Known URIs in the OAuthClient Portlet', () => {
	test('Creation a oauth-authorization-server and validations', async ({
		page,
	}) => {
		const authServerLocalMetadatasPage = new AuthServerLocalMetadatasPage(
			page
		);

		await authServerLocalMetadatasPage.goTo();

		await authServerLocalMetadatasPage.deleteAuthServerLocalMetadata();

		await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
			'',
			'The Issuer field is required.'
		);

		await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
			'https://localhost.com'
		);

		await authServerLocalMetadatasPage.addAuthServerLocalMetadata(
			'https://localhost.com',
			'Duplicate'
		);

		await authServerLocalMetadatasPage.checkResult(
			'https://localhost.com/o/.well-known/oauth-authorization-server',
			'https://localhost.com/.well-known/openid-configuration/1B2M2Y8AsgTpgAmY7PhCfg**/local'
		);

		await authServerLocalMetadatasPage.deleteAuthServerLocalMetadata();
	});
});
