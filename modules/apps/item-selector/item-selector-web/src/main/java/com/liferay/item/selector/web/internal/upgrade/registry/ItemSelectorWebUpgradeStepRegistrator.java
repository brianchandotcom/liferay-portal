/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.web.internal.upgrade.registry;

import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jose A. Jimenez
 */
@Component(service = UpgradeStepRegistrator.class)
public class ItemSelectorWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		UpgradeStep upgradePortletId = new BasePortletIdUpgradeProcess() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					{"200", ItemSelectorPortletKeys.ITEM_SELECTOR}
				};
			}

		};

		registry.register("0.0.1", "1.0.0", upgradePortletId);
	}

}