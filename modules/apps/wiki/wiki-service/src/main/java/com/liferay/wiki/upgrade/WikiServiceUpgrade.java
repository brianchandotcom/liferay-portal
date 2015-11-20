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

package com.liferay.wiki.upgrade;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeWikiPageResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class WikiServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.wiki.service", "0.0.1", "1.0.0", new UpgradeSchema(),
			new UpgradePortletId(), new UpgradePortletPreferences(),
			new UpgradeClassNames(), new UpgradeCompanyId(),
			new UpgradeLastPublishDate(),
			new UpgradePortletSettings(_settingsFactory),
			new UpgradeWikiPageResource());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private volatile SettingsFactory _settingsFactory;

}