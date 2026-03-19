/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {InstanceSettingsPage} from '../pages/configuration-admin-web/InstanceSettingsPage';
import {ContactInformationInstanceSettingsPage} from '../pages/users-admin-web/ContactInformationInstanceSettingsPage';
import {DefaultUserAssociationsPage} from '../pages/users-admin-web/DefaultUserAssociationsPage';
import {EmailInstanceSettingsPage} from '../pages/users-admin-web/EmailInstanceSettingsPage';
import {MailHostNamesInstanceSettingsPage} from '../pages/users-admin-web/MailHostNamesInstanceSettingsPage';
import {TermsOfUseInstanceSettingsPage} from '../pages/users-admin-web/TermsOfUseInstanceSettingsPage';

const instanceSettingsPagesTest = test.extend<{
	contactInformationInstanceSettingsPage: ContactInformationInstanceSettingsPage;
	defaultUserAssociationsPage: DefaultUserAssociationsPage;
	emailInstanceSettingsPage: EmailInstanceSettingsPage;
	instanceSettingsPage: InstanceSettingsPage;
	mailHostNamesInstanceSettingsPage: MailHostNamesInstanceSettingsPage;
	termsOfUseInstanceSettingsPage: TermsOfUseInstanceSettingsPage;
}>({
	contactInformationInstanceSettingsPage: async ({page}, use) => {
		await use(new ContactInformationInstanceSettingsPage(page));
	},
	defaultUserAssociationsPage: async ({page}, use) => {
		await use(new DefaultUserAssociationsPage(page));
	},
	emailInstanceSettingsPage: async ({page}, use) => {
		await use(new EmailInstanceSettingsPage(page));
	},
	instanceSettingsPage: async ({page}, use) => {
		await use(new InstanceSettingsPage(page));
	},
	mailHostNamesInstanceSettingsPage: async ({page}, use) => {
		await use(new MailHostNamesInstanceSettingsPage(page));
	},
	termsOfUseInstanceSettingsPage: async ({page}, use) => {
		await use(new TermsOfUseInstanceSettingsPage(page));
	},
});

export {instanceSettingsPagesTest};
