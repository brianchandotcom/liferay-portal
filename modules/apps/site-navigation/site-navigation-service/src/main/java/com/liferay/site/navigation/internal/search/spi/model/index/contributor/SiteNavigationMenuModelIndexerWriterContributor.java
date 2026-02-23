/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

/**
 * @author João Victor Alves
 */
public class SiteNavigationMenuModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<SiteNavigationMenu> {

	public SiteNavigationMenuModelIndexerWriterContributor(
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {

		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _siteNavigationMenuLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;

}