/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.layout.prototype.internal.upgrade.registry;

import com.liferay.blogs.layout.prototype.internal.upgrade.v1_0_0.UpgradeLocalizedColumn;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leon Chi
 */
@Component(service = UpgradeStepRegistrator.class)
public class BlogsLayoutPrototypeUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("1.0.0", "1.0.1", new UpgradeLocalizedColumn());
	}

}