/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.upgrade.registry;

import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.layout.admin.web.internal.upgrade.v_1_0_0.LayoutUpgradeProcess;
import com.liferay.layout.admin.web.internal.upgrade.v_1_0_1.LayoutTypeUpgradeProcess;
import com.liferay.layout.admin.web.internal.upgrade.v_1_0_2.LayoutSetTypeSettingsUpgradeProcess;
import com.liferay.layout.admin.web.internal.upgrade.v_1_0_3.LayoutTemplateIdUpgradeProcess;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.rmi.registry.Registry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = UpgradeStepRegistrator.class)
public class LayoutAdminWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new LayoutUpgradeProcess());

		registry.register(
			"1.0.0", "1.0.1",
			new LayoutTypeUpgradeProcess(_journalArticleResourceLocalService));

		registry.register(
			"1.0.1", "1.0.2",
			new LayoutSetTypeSettingsUpgradeProcess(
				_groupLocalService, _layoutSetLocalService));

		registry.register(
			"1.0.2", "1.0.3", new LayoutTemplateIdUpgradeProcess());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}