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

package com.liferay.layout.seo.internal.upgrade;

import com.liferay.layout.seo.internal.upgrade.v2_0_0.SEOEntryUpgradeProcess;
import com.liferay.layout.seo.internal.upgrade.v2_1_0.SchemaUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = UpgradeStepRegistrator.class)
public class LayoutSEOServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "2.0.0", new SEOEntryUpgradeProcess());

		registry.register("2.0.0", "2.1.0", new SchemaUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new CTModelUpgradeProcess("LayoutSEOEntry", "LayoutSEOSite"));
	}

}