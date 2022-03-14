/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

interface IThemeDisplay {
	getBCP47LanguageId(): () => string;
	getCompanyGroupId: () => number;
	getLanguageId: () => string;
	getPathThemeImages: () => string;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
	getUserName: () => string;
}

interface LiferayUtil {
	openToast: (options?: any) => void;
}

interface ILiferay {
	ThemeDisplay: IThemeDisplay;
	Util: LiferayUtil;
	authToken: string;
}

declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay || {
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCompanyGroupId: () => 0,
		getLanguageId: () => 'en_US',
		getPathThemeImages: () => '',
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserName: () => 'Test Test',
	},
	authToken: '',
};

export const LIFERAY_URLS = {
	manage_roles: `${window.origin}/group/guest/~/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&p_p_lifecycle=0&p_p_state=maximized`,
	manage_server: `${window.origin}/group/guest/~/control_panel/manage?p_p_id=com_liferay_server_admin_web_portlet_ServerAdminPortlet&p_p_lifecycle=0&p_p_state=maximized`,
	manage_user_groups: `${window.origin}/group/guest/~/control_panel/manage?p_p_id=com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet&p_p_lifecycle=0&p_p_state=maximized`,
};
