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
import {ReservedCredentialsInstanceSettingsPage} from '../pages/users-admin-web/ReservedCredentialsInstanceSettingsPage';
import {TermsOfUseInstanceSettingsPage} from '../pages/users-admin-web/TermsOfUseInstanceSettingsPage';
import {UserAuthenticationGeneralPage} from '../pages/users-admin-web/UserAuthenticationGeneralPage';
import {UserFieldsInstanceSettingsPage} from '../pages/users-admin-web/UserFieldsInstanceSettingsPage';

const instanceSettingsPagesTest = test.extend<{
	contactInformationInstanceSettingsPage: ContactInformationInstanceSettingsPage;
	defaultUserAssociationsPage: DefaultUserAssociationsPage;
	emailInstanceSettingsPage: EmailInstanceSettingsPage;
	instanceSettingsPage: InstanceSettingsPage;
	mailHostNamesInstanceSettingsPage: MailHostNamesInstanceSettingsPage;
	reservedCredentialsInstanceSettingsPage: ReservedCredentialsInstanceSettingsPage;
	termsOfUseInstanceSettingsPage: TermsOfUseInstanceSettingsPage;
	userAuthenticationGeneralPage: UserAuthenticationGeneralPage;
	userFieldsInstanceSettingsPage: UserFieldsInstanceSettingsPage;
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
	reservedCredentialsInstanceSettingsPage: async ({page}, use) => {
		await use(new ReservedCredentialsInstanceSettingsPage(page));
	},
	termsOfUseInstanceSettingsPage: async ({page}, use) => {
		await use(new TermsOfUseInstanceSettingsPage(page));
	},
	userAuthenticationGeneralPage: async ({page}, use) => {
		await use(new UserAuthenticationGeneralPage(page));
	},
	userFieldsInstanceSettingsPage: async ({page}, use) => {
		await use(new UserFieldsInstanceSettingsPage(page));
	},
});

export {instanceSettingsPagesTest};
