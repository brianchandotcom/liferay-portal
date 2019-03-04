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

package com.liferay.blogs.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayField;
import com.liferay.asset.display.contributor.BaseAssetDisplayContributor;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayContributor.class)
public class BlogsEntryAssetDisplayContributor
	extends BaseAssetDisplayContributor<BlogsEntry> {

	@Override
	public Set<AssetDisplayField> getAssetDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<AssetDisplayField> fields = super.getAssetDisplayFields(
			classTypeId, locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, BlogsEntryAssetDisplayContributor.class);

		fields.add(
			new AssetDisplayField(
				"content", LanguageUtil.get(resourceBundle, "content"),
				"text"));
		fields.add(
			new AssetDisplayField(
				"coverImage", LanguageUtil.get(resourceBundle, "cover-image"),
				"image"));
		fields.add(
			new AssetDisplayField(
				"subtitle", LanguageUtil.get(resourceBundle, "subtitle"),
				"text"));
		fields.add(
			new AssetDisplayField(
				"title", LanguageUtil.get(resourceBundle, "title"), "text"));

		return fields;
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		BlogsEntry blogsEntry, Locale locale) {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("content", blogsEntry.getContent());
		fieldValues.put("coverImage", _getCoverImageURL(blogsEntry));
		fieldValues.put("subtitle", blogsEntry.getSubtitle());
		fieldValues.put("title", blogsEntry.getTitle());

		return fieldValues;
	}

	private String _getCoverImageURL(BlogsEntry blogsEntry) {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			try {
				return blogsEntry.getCoverImageURL(themeDisplay);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);

				return null;
			}
		}
		else {
			return blogsEntry.getCoverImageURL();
		}
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryAssetDisplayContributor.class);

}