/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {RoleAssigneesPage} from '../pages/roles-admin-web/RoleAssigneesPage';
import {RoleDefinePermissionsPage} from '../pages/roles-admin-web/RoleDefinePermissionsPage';
import {RoleOrganizationSelectorPage} from '../pages/roles-admin-web/RoleOrganizationSelectorPage';
import {RolePage} from '../pages/roles-admin-web/RolePage';
import {RoleSegmentSelectorPage} from '../pages/roles-admin-web/RoleSegmentSelectorPage';
import {RoleSiteSelectorPage} from '../pages/roles-admin-web/RoleSiteSelectorPage';
import {RoleUserGroupSelectorPage} from '../pages/roles-admin-web/RoleUserGroupSelectorPage';
import {RoleUserSelectorPage} from '../pages/roles-admin-web/RoleUserSelectorPage';
import {RolesPage} from '../pages/roles-admin-web/RolesPage';
import {BlogsPage} from '../pages/roles-admin-web/blogs/BlogsPage';
import {BookmarksPage} from '../pages/roles-admin-web/bookmarks/BookmarksPage';

const rolesPagesTest = test.extend<{
	blogsPage: BlogsPage;
	bookmarksPage: BookmarksPage;
	roleAssigneesPage: RoleAssigneesPage;
	roleDefinePermissionsPage: RoleDefinePermissionsPage;
	roleOrganizationSelectorPage: RoleOrganizationSelectorPage;
	rolePage: RolePage;
	roleSegmentSelectorPage: RoleSegmentSelectorPage;
	roleSiteSelectorPage: RoleSiteSelectorPage;
	roleUserGroupSelectorPage: RoleUserGroupSelectorPage;
	roleUserSelectorPage: RoleUserSelectorPage;
	rolesPage: RolesPage;
}>({
	blogsPage: async ({page}, use) => {
		await use(new BlogsPage(page));
	},
	bookmarksPage: async ({page}, use) => {
		await use(new BookmarksPage(page));
	},
	roleAssigneesPage: async ({page}, use) => {
		await use(new RoleAssigneesPage(page));
	},
	roleDefinePermissionsPage: async ({page}, use) => {
		await use(new RoleDefinePermissionsPage(page));
	},
	roleOrganizationSelectorPage: async ({page}, use) => {
		await use(new RoleOrganizationSelectorPage(page));
	},
	rolePage: async ({page}, use) => {
		await use(new RolePage(page));
	},
	roleSegmentSelectorPage: async ({page}, use) => {
		await use(new RoleSegmentSelectorPage(page));
	},
	roleSiteSelectorPage: async ({page}, use) => {
		await use(new RoleSiteSelectorPage(page));
	},
	roleUserGroupSelectorPage: async ({page}, use) => {
		await use(new RoleUserGroupSelectorPage(page));
	},
	roleUserSelectorPage: async ({page}, use) => {
		await use(new RoleUserSelectorPage(page));
	},
	rolesPage: async ({page}, use) => {
		await use(new RolesPage(page));
	},
});

export {rolesPagesTest};
