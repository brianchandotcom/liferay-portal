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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Alberto Chaparro
 */
public class UpgradeModules
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeModules {

	@Override
	public String[] getBundleSymbolicNames() {
		return _BUNDLE_SYMBOLIC_NAMES;
	}

	@Override
	public String[][] getConvertedLegacyModules() {
		return _CONVERTED_LEGACY_MODULES;
	}

	public String[][] getLegacyServiceModules() {
		return _LEGACY_SERVICE_MODULES;
	}

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateLegacyServiceModules();
	}

	protected void updateLegacyServiceModules() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] legacyServiceModule : getLegacyServiceModules()) {
				if (hasTable(legacyServiceModule[1])) {
					addRelease(legacyServiceModule[0]);
				}
			}
		}
	}

	private static final String[] _BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.change.tracking.web",
		"com.liferay.document.library.asset.auto.tagger.tensorflow",
		"com.liferay.portal.bundle.blacklist.impl",
		"com.liferay.portal.component.blacklist.impl",
		"com.liferay.portal.search", "com.liferay.template.web"
	};

	private static final String[][] _CONVERTED_LEGACY_MODULES = {
		{"opensocial-portlet", "opensocial-portlet", "OpenSocial"}
	};

	private static final String[][] _LEGACY_SERVICE_MODULES = {
		{"com.liferay.softwarecatalog.service", "SCFrameworkVersion"}
	};

}