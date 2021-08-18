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

package com.liferay.layout.internal.upgrade.v1_2_2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Adolfo Pérez
 */
public class LayoutSEOUpgradeProcess extends UpgradeProcess {

	public LayoutSEOUpgradeProcess(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"type", LayoutConstants.TYPE_ASSET_DISPLAY)));
		actionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> _updateLayoutTypeSettingsProperties(layout));

		actionableDynamicQuery.performActions();
	}

	private void _updateLayoutTypeSettingsProperties(Layout layout) {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		for (String key : _MAPPED_ATTRIBUTE_KEYS) {
			String value = unicodeProperties.getProperty(key);

			if (Validator.isNotNull(value) &&
				!value.startsWith(StringPool.DOLLAR_AND_OPEN_CURLY_BRACE)) {

				unicodeProperties.setProperty(
					key,
					StringPool.DOLLAR_AND_OPEN_CURLY_BRACE + value +
						StringPool.CLOSE_CURLY_BRACE);
			}
		}

		layout.setTypeSettingsProperties(unicodeProperties);

		_layoutLocalService.updateLayout(layout);
	}

	private static final String[] _MAPPED_ATTRIBUTE_KEYS = {
		"mapped-description", "mapped-openGraphDescription",
		"mapped-openGraphImage", "mapped-openGraphImageAlt",
		"mapped-openGraphTitle", "mapped-title"
	};

	private final LayoutLocalService _layoutLocalService;

}