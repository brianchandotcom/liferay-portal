/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "indexer.class.name=com.liferay.site.navigation.model.SiteNavigationMenu",
	service = ModelDocumentContributor.class
)
public class SiteNavigationMenuModelDocumentContributor
	implements ModelDocumentContributor<SiteNavigationMenu> {

	@Override
	public void contribute(
		Document document, SiteNavigationMenu siteNavigationMenu) {

		document.addKeyword(
			Field.COMPANY_ID, siteNavigationMenu.getCompanyId());
		document.addDate(Field.CREATE_DATE, siteNavigationMenu.getCreateDate());
		document.addKeyword(Field.GROUP_ID, siteNavigationMenu.getGroupId());
		document.addDate(
			Field.MODIFIED_DATE, siteNavigationMenu.getModifiedDate());
		document.addText(Field.NAME, siteNavigationMenu.getName());
	}

}