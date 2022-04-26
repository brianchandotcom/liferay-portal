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

package com.liferay.site.navigation.menu.item.asset.vocabulary.internal.type;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=600",
		"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY
	},
	service = SiteNavigationMenuItemType.class
)
public class AssetVocabularySiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "vocabulary");
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.asset.vocabulary)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}