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

package com.liferay.asset.display.template.web.internal.dynamic.data.mapping.util;

import com.liferay.asset.display.template.constants.AssetDisplayTemplatePortletKeys;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "javax.portlet.name=" + AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
	service = DDMDisplay.class
)
public class AssetDisplayTemplateDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getPortletId() {
		return AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE;
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	@Override
	public String getStructureName(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "asset-display-template");
	}

	@Override
	public String getStructureType() {
		return AssetDisplayTemplate.class.getName();
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "asset-display-templates");
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	private static final Set<String> _templateLanguageTypes = SetUtil.fromArray(
		new String[] {TemplateConstants.LANG_TYPE_FTL});

}