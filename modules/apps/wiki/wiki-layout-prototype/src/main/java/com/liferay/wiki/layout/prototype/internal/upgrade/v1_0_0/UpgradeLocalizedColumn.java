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

package com.liferay.wiki.layout.prototype.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.BaseLocalizedColumnUpgradeProcess;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PortalInstances;

/**
 * @author Leon Chi
 */
public class UpgradeLocalizedColumn extends BaseLocalizedColumnUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		upgradeLocalizedColumn(
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER, "LayoutPrototype",
			"name", _NAME, "layout-prototype-wiki-title", "Name", companyIds);

		upgradeLocalizedColumn(
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER, "LayoutPrototype",
			"description", _DESCRIPTION, "layout-prototype-wiki-description",
			"Description", companyIds);
	}

	private static final String _DESCRIPTION =
		"Collaborate with members through the wiki on this page. Discover " +
			"related content through tags, and navigate quickly and easily " +
				"with categories.";

	private static final String _NAME =
		"<?xml version='1.0' encoding='UTF-8'?><root available-locales=" +
			"\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">" +
				"Wiki</Name></root>";

}