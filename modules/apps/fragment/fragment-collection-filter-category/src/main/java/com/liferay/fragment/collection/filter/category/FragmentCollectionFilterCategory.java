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

package com.liferay.fragment.collection.filter.category;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.category.constants.FragmentCollectionFilterCategoryWebKeys;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	immediate = true, property = "fragment.collection.filter.key=category",
	service = FragmentCollectionFilter.class
)
public class FragmentCollectionFilterCategory
	implements FragmentCollectionFilter {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "category");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String label = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
				"label"));

		httpServletRequest.setAttribute(
			FragmentCollectionFilterCategoryWebKeys.LABEL, label);

		boolean showLabel = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
				"showLabel"));

		httpServletRequest.setAttribute(
			FragmentCollectionFilterCategoryWebKeys.SHOW_LABEL, showLabel);

		boolean showSearch = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
				"showSearch"));

		httpServletRequest.setAttribute(
			FragmentCollectionFilterCategoryWebKeys.SHOW_SEARCH, showSearch);

		boolean singleSelection = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
				"singleSelection"));

		httpServletRequest.setAttribute(
			FragmentCollectionFilterCategoryWebKeys.SINGLE_SELECTION,
			singleSelection);

		Object sourceObject = _fragmentEntryConfigurationParser.getFieldValue(
			fragmentEntryLink.getConfiguration(),
			fragmentEntryLink.getEditableValues(),
			themeDisplay.getLocale(), "source");

		if (Validator.isNull(sourceObject) ||
			!JSONUtil.isValid(sourceObject.toString())) {

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			try {
				requestDispatcher.include(
					httpServletRequest, httpServletResponse);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to render collection filter fragment", exception);
			}

			return;
		}

		JSONObject sourceJSONObject = null;

		try {
			sourceJSONObject = _jsonFactory.createJSONObject(
				sourceObject.toString());
		}
		catch (JSONException jsonException) {
			return;
		}

		long assetCategoryTreeNodeId = GetterUtil.getLong(
			sourceJSONObject.getString("categoryTreeNodeId"));

		if (assetCategoryTreeNodeId == 0) {
			return;
		}

		String assetCategoryTreeNodeType = sourceJSONObject.getString(
			"categoryTreeNodeType");

		List<AssetCategory> assetCategories = new ArrayList<>();

		try {
			if (assetCategoryTreeNodeType.equals("Category")) {
				assetCategories = _assetCategoryService.getChildCategories(
					assetCategoryTreeNodeId);

				AssetCategory assetCategory =
					_assetCategoryService.fetchCategory(
						assetCategoryTreeNodeId);

				httpServletRequest.setAttribute(
					FragmentCollectionFilterCategoryWebKeys.ASSET_CATEGORY,
					assetCategory);

				httpServletRequest.removeAttribute(
					FragmentCollectionFilterCategoryWebKeys.ASSET_VOCABULARY);
			}
			else if (assetCategoryTreeNodeType.equals("Vocabulary")) {
				AssetVocabulary assetVocabulary =
					_assetVocabularyService.fetchVocabulary(
						assetCategoryTreeNodeId);

				assetCategories =
					_assetCategoryService.getVocabularyRootCategories(
						assetVocabulary.getGroupId(), assetCategoryTreeNodeId,
						0,
						_assetCategoryService.getVocabularyCategoriesCount(
							assetVocabulary.getGroupId(),
							assetCategoryTreeNodeId),
						null);

				httpServletRequest.setAttribute(
					FragmentCollectionFilterCategoryWebKeys.ASSET_VOCABULARY,
					assetVocabulary);

				httpServletRequest.removeAttribute(
					FragmentCollectionFilterCategoryWebKeys.ASSET_CATEGORY);
			}

			httpServletRequest.setAttribute(
				FragmentCollectionFilterCategoryWebKeys.ASSET_CATEGORIES,
				assetCategories);

			httpServletRequest.setAttribute(
				FragmentCollectionFilterCategoryWebKeys.FRAGMENT_ENTRY_LINK_ID,
				fragmentEntryLink.getFragmentEntryLinkId());

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
		FragmentCollectionFilterCategory.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.collection.filter.category)"
	)
	private ServletContext _servletContext;

}