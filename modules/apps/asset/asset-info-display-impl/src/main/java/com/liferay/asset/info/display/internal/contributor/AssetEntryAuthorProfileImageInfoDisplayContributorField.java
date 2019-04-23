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

package com.liferay.asset.info.display.internal.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.display.contributor.BaseInfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayContributorFieldType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = InfoDisplayContributorField.class
)
public class AssetEntryAuthorProfileImageInfoDisplayContributorField
	extends BaseInfoDisplayContributorField<AssetEntry> {

	@Override
	public String getKey() {
		return "authorProfileImage";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, "author-profile-image");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.IMAGE;
	}

	@Override
	public String getValue(AssetEntry assetEntry, Locale locale) {
		User user = _userLocalService.fetchUser(assetEntry.getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay != null) {
			try {
				return user.getPortraitURL(getThemeDisplay());
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAuthorProfileImageInfoDisplayContributorField.class);

	@Reference
	private UserLocalService _userLocalService;

}