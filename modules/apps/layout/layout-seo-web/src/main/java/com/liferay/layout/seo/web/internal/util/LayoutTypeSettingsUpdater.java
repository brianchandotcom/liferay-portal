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

package com.liferay.layout.seo.web.internal.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = LayoutTypeSettingsUpdater.class)
public class LayoutTypeSettingsUpdater {

	public Layout updateTypeSettings(
			Layout layout, UnicodeProperties typeSettingsUnicodeProperties)
		throws Exception {

		UnicodeProperties layoutTypeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		String type = layout.getType();

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			layoutTypeSettingsUnicodeProperties.putAll(
				typeSettingsUnicodeProperties);

			boolean layoutCustomizable = GetterUtil.getBoolean(
				layoutTypeSettingsUnicodeProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));

			if (!layoutCustomizable) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.removeCustomization(
					layoutTypeSettingsUnicodeProperties);
			}

			return _layoutService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(),
				layoutTypeSettingsUnicodeProperties.toString());
		}

		layoutTypeSettingsUnicodeProperties.putAll(
			typeSettingsUnicodeProperties);

		layoutTypeSettingsUnicodeProperties.putAll(
			layout.getTypeSettingsProperties());

		return _layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layoutTypeSettingsUnicodeProperties.toString());
	}

	@Reference
	private LayoutService _layoutService;

}