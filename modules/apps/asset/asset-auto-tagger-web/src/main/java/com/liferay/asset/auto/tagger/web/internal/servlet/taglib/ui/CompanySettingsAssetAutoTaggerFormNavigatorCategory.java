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

package com.liferay.asset.auto.tagger.web.internal.servlet.taglib.ui;

import com.liferay.asset.auto.tagger.constants.FormNavigatorAssetAutoTaggerConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = FormNavigatorCategory.class)
public class CompanySettingsAssetAutoTaggerFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_COMPANY_SETTINGS;
	}

	@Override
	public String getKey() {
		return FormNavigatorAssetAutoTaggerConstants.
			CATEGORY_KEY_COMPANY_SETTINGS_ASSET_AUTO_TAGGER;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.get(resourceBundle, getKey());
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.asset.auto.tagger.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}