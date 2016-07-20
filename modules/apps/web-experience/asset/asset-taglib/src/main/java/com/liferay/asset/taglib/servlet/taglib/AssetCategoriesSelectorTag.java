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

package com.liferay.asset.taglib.servlet.taglib;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.taglib.util.AssetCategoryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Antonio Pol
 */
public class AssetCategoriesSelectorTag extends IncludeTag {

	public void setCategoryIds(String categoryIds) {
		_categoryIds = categoryIds;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setClassTypePK(long classTypePK) {
		_classTypePK = classTypePK;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowRequiredLabel(boolean showRequiredLabel) {
		_showRequiredLabel = showRequiredLabel;
	}

	@Override
	protected void cleanUp() {
		_categoryIds = null;
		_className = null;
		_classPK = 0;
		_classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		_groupIds = null;
		_hiddenInput = "assetCategoryIds";
		_ignoreRequestValue = false;
		_showRequiredLabel = true;
	}

	protected List<String[]> getCategoryIdsTitles() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String[]> categoryIdsTitles = new ArrayList<>();

		String categoryIds = StringPool.BLANK;

		if (Validator.isNotNull(_categoryIds)) {
			categoryIds = _categoryIds;
		}

		if (Validator.isNull(_className)) {
			if (!_ignoreRequestValue) {
				String categoryIdsParam = request.getParameter(_hiddenInput);

				if (categoryIdsParam != null) {
					categoryIds = categoryIdsParam;
				}
			}

			String[] categoryIdsTitle = AssetCategoryUtil.getCategoryIdsTitles(
				categoryIds, StringPool.BLANK, 0, themeDisplay);

			categoryIdsTitles.add(categoryIdsTitle);

			return categoryIdsTitles;
		}

		try {
			for (AssetVocabulary vocabulary : getVocabularies()) {
				String categoryNames = StringPool.BLANK;

				if (Validator.isNotNull(_className) && (_classPK > 0)) {
					List<AssetCategory> categories =
						AssetCategoryServiceUtil.getCategories(
							_className, _classPK);

					categoryIds = ListUtil.toString(
						categories, AssetCategory.CATEGORY_ID_ACCESSOR);
					categoryNames = ListUtil.toString(
						categories, AssetCategory.NAME_ACCESSOR);
				}

				if (!_ignoreRequestValue) {
					String categoryIdsParam = request.getParameter(
						_hiddenInput + StringPool.UNDERLINE +
							vocabulary.getVocabularyId());

					if (Validator.isNotNull(categoryIdsParam)) {
						categoryIds = categoryIdsParam;
					}
				}

				String[] categoryIdsTitle =
					AssetCategoryUtil.getCategoryIdsTitles(
						categoryIds, categoryNames,
						vocabulary.getVocabularyId(), themeDisplay);

				categoryIdsTitles.add(categoryIdsTitle);
			}
		}
		catch (Exception e) {
		}

		return categoryIdsTitles;
	}

	protected String getEventName() {
		String portletId = PortletProviderUtil.getPortletId(
			AssetCategory.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(portletId) + "selectCategory";
	}

	protected long[] getGroupIds() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (ArrayUtil.isEmpty(_groupIds)) {
				return PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId());
			}

			return PortalUtil.getCurrentAndAncestorSiteGroupIds(_groupIds);
		}
		catch (Exception e) {
		}

		return new long[0];
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected PortletURL getPortletURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				request, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", getEventName());
			portletURL.setParameter(
				"selectedCategories", "{selectedCategories}");
			portletURL.setParameter("singleSelect", "{singleSelect}");
			portletURL.setParameter("vocabularyIds", "{vocabularyIds}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL;
		}
		catch (Exception e) {
		}

		return null;
	}

	protected List<AssetVocabulary> getVocabularies() {
		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				getGroupIds());

		if (Validator.isNotNull(_className)) {
			vocabularies = AssetUtil.filterVocabularies(
				vocabularies, _className, _classTypePK);
		}

		return ListUtil.filter(
			vocabularies,
			new PredicateFilter<AssetVocabulary>() {

				public boolean filter(AssetVocabulary vocabulary) {
					int vocabularyCategoriesCount =
						AssetCategoryServiceUtil.getVocabularyCategoriesCount(
							vocabulary.getGroupId(),
							vocabulary.getVocabularyId());

					if (vocabularyCategoriesCount > 0) {
						return true;
					}

					return false;
				}

			});
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-asset:asset-categories-selector:categoryIdsTitles",
			getCategoryIdsTitles());
		request.setAttribute(
			"liferay-asset:asset-categories-selector:className", _className);
		request.setAttribute(
			"liferay-asset:asset-categories-selector:classTypePK",
			String.valueOf(_classTypePK));
		request.setAttribute(
			"liferay-asset:asset-categories-selector:eventName",
			getEventName());
		request.setAttribute(
			"liferay-asset:asset-categories-selector:hiddenInput",
			_hiddenInput);
		request.setAttribute(
			"liferay-asset:asset-categories-selector:portletURL",
			getPortletURL());
		request.setAttribute(
			"liferay-asset:asset-categories-selector:showRequiredLabel",
			String.valueOf(_showRequiredLabel));
		request.setAttribute(
			"liferay-asset:asset-categories-selector:vocabularies",
			getVocabularies());
	}

	private static final String _PAGE = "/asset_categories_selector/page.jsp";

	private String _categoryIds;
	private String _className;
	private long _classPK;
	private long _classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
	private long[] _groupIds;
	private String _hiddenInput = "assetCategoryIds";
	private boolean _ignoreRequestValue;
	private boolean _showRequiredLabel = true;

}