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

package com.liferay.fragment.renderer.collection.asset.categories.filter.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.collection.asset.categories.filter.internal.configuration.FFFragmentRendererCollectionAssetCategoryFilterConfiguration;
import com.liferay.fragment.renderer.collection.asset.categories.filter.internal.constants.CollectionAssetCategoryFilterFragmentRendererWebKeys;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.fragment.renderer.collection.asset.categories.filter.internal.configuration.FFFragmentRendererCollectionAssetCategoryFilterConfiguration",
	service = FragmentRenderer.class
)
public class CollectionAssetCategoryFilterFragmentRenderer
	implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					getClass(),
					"/com/liferay/fragment/renderer/collection/asset" +
						"/categories/filter/internal/dependencies" +
							"/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject, resourceBundle);
		}
		catch (JSONException jsonException) {
			return StringPool.BLANK;
		}
	}

	@Override
	public String getIcon() {
		return "filter";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return LanguageUtil.get(resourceBundle, "collection-category-filter");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		if (!_ffFragmentRendererCollectionAssetCategoryFilterConfiguration.
				enabled()) {

			return false;
		}

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
			return true;
		}

		return false;
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

		boolean multipleSelection = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
				"multipleSelection"));

		httpServletRequest.setAttribute(
			CollectionAssetCategoryFilterFragmentRendererWebKeys.
				MULTIPLE_SELECTION,
			multipleSelection);

		Object sourceObject = _fragmentEntryConfigurationParser.getFieldValue(
			getConfiguration(fragmentRendererContext),
			fragmentEntryLink.getEditableValues(), themeDisplay.getLocale(),
			"source");

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
					CollectionAssetCategoryFilterFragmentRendererWebKeys.
						ASSET_CATEGORY,
					assetCategory);

				httpServletRequest.removeAttribute(
					CollectionAssetCategoryFilterFragmentRendererWebKeys.
						ASSET_VOCABULARY);
			}
			else if (assetCategoryTreeNodeType.equals("Vocabulary")) {
				assetCategories =
					_assetCategoryService.getVocabularyRootCategories(
						themeDisplay.getScopeGroupId(), assetCategoryTreeNodeId,
						0,
						_assetCategoryService.getVocabularyCategoriesCount(
							themeDisplay.getScopeGroupId(),
							assetCategoryTreeNodeId),
						null);

				AssetVocabulary assetVocabulary =
					_assetVocabularyService.fetchVocabulary(
						assetCategoryTreeNodeId);

				httpServletRequest.setAttribute(
					CollectionAssetCategoryFilterFragmentRendererWebKeys.
						ASSET_VOCABULARY,
					assetVocabulary);

				httpServletRequest.removeAttribute(
					CollectionAssetCategoryFilterFragmentRendererWebKeys.
						ASSET_CATEGORY);
			}

			httpServletRequest.setAttribute(
				CollectionAssetCategoryFilterFragmentRendererWebKeys.
					ASSET_CATEGORIES,
				assetCategories);

			httpServletRequest.setAttribute(
				CollectionAssetCategoryFilterFragmentRendererWebKeys.
					FRAGMENT_ENTRY_LINK_ID,
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

	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffFragmentRendererCollectionAssetCategoryFilterConfiguration =
			ConfigurableUtil.createConfigurable(
				FFFragmentRendererCollectionAssetCategoryFilterConfiguration.
					class,
				properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionAssetCategoryFilterFragmentRenderer.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	private volatile
		FFFragmentRendererCollectionAssetCategoryFilterConfiguration
			_ffFragmentRendererCollectionAssetCategoryFilterConfiguration;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.collection.asset.categories.filter.impl)"
	)
	private ServletContext _servletContext;

}