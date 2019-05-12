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

package com.liferay.blogs.web.internal.info.display.contributor.field;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.contributor.field.BaseInfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = InfoDisplayContributorField.class
)
public class BlogsEntryCoverImageInfoDisplayContributorField
	extends BaseInfoDisplayContributorField<BlogsEntry> {

	@Override
	public String getKey() {
		return "coverImage";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, "cover-image");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.IMAGE;
	}

	@Override
	public Object getValue(BlogsEntry blogsEntry, Locale locale) {
		ThemeDisplay themeDisplay = getThemeDisplay();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (themeDisplay != null) {
			try {
				jsonObject.put(
					"url", blogsEntry.getCoverImageURL(themeDisplay));
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
		else {
			jsonObject.put("url", blogsEntry.getCoverImageURL());
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryCoverImageInfoDisplayContributorField.class);

}