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

package com.liferay.asset.publisher.web.internal.servlet.taglib.ui;

import com.liferay.asset.publisher.constants.AssetPublisherConstants;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "form.navigator.entry.order:Integer=100",
	service = FormNavigatorEntry.class
)
public class CreateAssetListFormNavigatorEntry
	extends BaseConfigurationFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return AssetPublisherConstants.CATEGORY_KEY_ASSET_SELECTION;
	}

	@Override
	public String getKey() {
		return "create-asset-list";
	}

	@Override
	public String getLabel(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public boolean isVisible(User user, Object object) {
		if (isDynamicAssetSelection() || isManualSelection()) {
			return true;
		}

		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.publisher.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/configuration/create_asset_list.jsp";
	}

}