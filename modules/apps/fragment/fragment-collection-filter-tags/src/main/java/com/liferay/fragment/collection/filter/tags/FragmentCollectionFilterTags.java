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

package com.liferay.fragment.collection.filter.tags;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.tags.display.context.FragmentCollectionFilterTagsDisplayContext;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	enabled = false, immediate = true, service = FragmentCollectionFilter.class
)
public class FragmentCollectionFilterTags implements FragmentCollectionFilter {

	@Override
	public String getConfiguration() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleUtil.getMostRelevantLocale(), getClass());

		try {
			String json = StringUtil.read(
				getClass(),
				"/com/liferay/fragment/collection/filter/tags/dependencies" +
					"/configuration.json");

			return _fragmentEntryConfigurationParser.translateConfiguration(
				_jsonFactory.createJSONObject(json), resourceBundle);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public String getFilterKey() {
		return "tags";
	}

	@Override
	public String getFilterValueLabel(String filterValue, Locale locale) {
		AssetTag assetTag = _assetTagLocalService.fetchAssetTag(
			GetterUtil.getLong(filterValue));

		if (assetTag == null) {
			return filterValue;
		}

		return assetTag.getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "tags");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				FragmentCollectionFilterTagsDisplayContext.class.getName(),
				new FragmentCollectionFilterTagsDisplayContext(
					getConfiguration(), _fragmentEntryConfigurationParser,
					fragmentRendererContext));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to render collection filter fragment", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionFilterTags.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.collection.filter.tags)"
	)
	private ServletContext _servletContext;

}