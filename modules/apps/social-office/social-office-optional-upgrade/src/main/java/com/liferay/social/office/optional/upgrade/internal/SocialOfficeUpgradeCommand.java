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

package com.liferay.social.office.optional.upgrade.internal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=executeAll",
		"osgi.command.function=hideTasksLayout",
		"osgi.command.function=removeTasksPortlet",
		"osgi.command.function=renameDashboardSiteTemplate",
		"osgi.command.function=renameProfileSiteTemplate",
		"osgi.command.function=updateProfileTheme",
		"osgi.command.function=updateSocialSiteTheme",
		"osgi.command.function=updateDashboardTheme", "osgi.command.scope=so"
	},
	service = Object.class
)
public class SocialOfficeUpgradeCommand {

	public void executeAll() {
		hideTasksLayout();
		removeTasksPortlet();
		renameDashboardSiteTemplate();
		renameProfileSiteTemplate();
		updateDashboardTheme();
		updateProfileTheme();
		updateSocialSiteTheme();
	}

	public void hideTasksLayout() {
		throw new UnsupportedOperationException();
	}

	public void removeTasksPortlet() {
		throw new UnsupportedOperationException();
	}

	public void renameDashboardSiteTemplate() {
		throw new UnsupportedOperationException();
	}

	public void renameProfileSiteTemplate() {
		throw new UnsupportedOperationException();
	}

	public void updateDashboardTheme() {
		throw new UnsupportedOperationException();
	}

	public void updateProfileTheme() {
		throw new UnsupportedOperationException();
	}

	public void updateSocialSiteTheme() {
		throw new UnsupportedOperationException();
	}

}