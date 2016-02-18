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

package com.liferay.asset.categories.admin.web.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryDisplay;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetCategoryCreateDateComparator;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyCreateDateComparator;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class AssetCategoriesDisplayContext {

	public AssetCategoriesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;

		_renderResponse = renderResponse;

		_request = request;
	}

	public String getCategoriesRedirect() {
		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			PortletURL backURL = _renderResponse.createRenderURL();

			AssetCategory category = getCategory();

			if (category != null) {
				backURL.setParameter("mvcPath", "/view_categories.jsp");
				backURL.setParameter(
					"categoryId",
					String.valueOf(category.getParentCategoryId()));

				long vocabularyId = getVocabularyId();

				if (vocabularyId > 0) {
					backURL.setParameter(
						"vocabularyId", String.valueOf(vocabularyId));
				}
			}

			redirect = backURL.toString();
		}

		return redirect;
	}

	public SearchContainer getCategoriesSearchContainer()
		throws PortalException {

		SearchContainer categoriesSearchContainer = new SearchContainer(
			_renderRequest, getIteratorURL(), null,
			"there-are-no-categories.-you-can-add-a-category-by-clicking-the" +
				"-plus-button-on-the-bottom-right-corner");

		categoriesSearchContainer.setSearch(Validator.isNotNull(getKeywords()));

		categoriesSearchContainer.setOrderByCol(getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetCategory> orderByComparator =
			new AssetCategoryCreateDateComparator(orderByAsc);

		categoriesSearchContainer.setOrderByComparator(orderByComparator);

		categoriesSearchContainer.setOrderByType(orderByType);
		categoriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<AssetCategory> categories = null;
		int categoriesCount = 0;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (Validator.isNotNull(getKeywords())) {
			Sort sort = new Sort("createDate", Sort.LONG_TYPE, orderByAsc);

			AssetCategoryDisplay assetCategoryDisplay =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					scopeGroupId, getKeywords(), getVocabularyId(),
					getCategoryId(), categoriesSearchContainer.getStart(),
					categoriesSearchContainer.getEnd(), sort);

			categoriesCount = assetCategoryDisplay.getTotal();

			categoriesSearchContainer.setTotal(categoriesCount);

			categories = assetCategoryDisplay.getCategories();
		}
		else {
			categoriesCount =
				AssetCategoryServiceUtil.getVocabularyCategoriesCount(
					scopeGroupId, getCategoryId(), getVocabularyId());

			categoriesSearchContainer.setTotal(categoriesCount);

			categories = AssetCategoryServiceUtil.getVocabularyCategories(
				scopeGroupId, getCategoryId(), getVocabularyId(),
				categoriesSearchContainer.getStart(),
				categoriesSearchContainer.getEnd(),
				categoriesSearchContainer.getOrderByComparator());
		}

		categoriesSearchContainer.setResults(categories);

		return categoriesSearchContainer;
	}

	public AssetCategory getCategory() {
		if (_category != null) {
			return _category;
		}

		long categoryId = getCategoryId();

		if (categoryId > 0) {
			_category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);
		}

		return _category;
	}

	public long getCategoryId() {
		if (_categoryId != null) {
			return _categoryId;
		}

		_categoryId = ParamUtil.getLong(_request, "categoryId");

		return _categoryId;
	}

	public String getCategoryTitle() throws PortalException {
		AssetCategory category = getCategory();

		AssetVocabulary vocabulary = getVocabulary();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		if (category != null) {
			return category.getTitle(locale);
		}

		return vocabulary.getTitle(locale);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "list");

		return _displayStyle;
	}

	public PortletURL getIteratorURL() {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		PortletURL iteratorURL = _renderResponse.createRenderURL();

		iteratorURL.setParameter("mvcPath", "/view_categories.jsp");
		iteratorURL.setParameter("redirect", currentURL.toString());
		iteratorURL.setParameter("categoryId", String.valueOf(getCategoryId()));
		iteratorURL.setParameter(
			"vocabularyId", String.valueOf(getVocabularyId()));
		iteratorURL.setParameter("displayStyle", getDisplayStyle());
		iteratorURL.setParameter("keywords", getKeywords());

		return iteratorURL;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public SearchContainer getVocabulariesSearchContainer()
		throws PortalException {

		if (_vocabulariesSearchContainer != null) {
			return _vocabulariesSearchContainer;
		}

		SearchContainer vocabulariesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-vocabularies.-you-can-add-a-vocabulary-by-clicking" +
				"-the-plus-button-on-the-bottom-right-corner");

		String keywords = getKeywords();

		vocabulariesSearchContainer.setSearch(Validator.isNotNull(keywords));

		vocabulariesSearchContainer.setOrderByCol(getOrderByCol());

		String orderByType = getOrderByType();

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetVocabulary> orderByComparator =
			new AssetVocabularyCreateDateComparator(orderByAsc);

		vocabulariesSearchContainer.setOrderByComparator(orderByComparator);

		vocabulariesSearchContainer.setOrderByType(orderByType);
		vocabulariesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<AssetVocabulary> vocabularies = null;
		int vocabulariesCount = 0;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (Validator.isNotNull(keywords)) {
			Sort sort = new Sort("createDate", Sort.LONG_TYPE, orderByAsc);

			AssetVocabularyDisplay assetVocabularyDisplay =
				AssetVocabularyServiceUtil.searchVocabulariesDisplay(
					scopeGroupId, keywords, true,
					vocabulariesSearchContainer.getStart(),
					vocabulariesSearchContainer.getEnd(), sort);

			vocabulariesCount = assetVocabularyDisplay.getTotal();

			vocabulariesSearchContainer.setTotal(vocabulariesCount);

			vocabularies = assetVocabularyDisplay.getVocabularies();
		}
		else {
			vocabulariesCount =
				AssetVocabularyServiceUtil.getGroupVocabulariesCount(
					scopeGroupId);

			vocabulariesSearchContainer.setTotal(vocabulariesCount);

			vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
				scopeGroupId, true, vocabulariesSearchContainer.getStart(),
				vocabulariesSearchContainer.getEnd(),
				vocabulariesSearchContainer.getOrderByComparator());
		}

		vocabulariesSearchContainer.setResults(vocabularies);

		_vocabulariesSearchContainer = vocabulariesSearchContainer;

		return _vocabulariesSearchContainer;
	}

	public AssetVocabulary getVocabulary() throws PortalException {
		if (_vocabulary != null) {
			return _vocabulary;
		}

		long vocabularyId = getVocabularyId();

		if (vocabularyId > 0) {
			_vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(
				vocabularyId);
		}

		return _vocabulary;
	}

	public long getVocabularyId() {
		if (_vocabularyId != null) {
			return _vocabularyId;
		}

		_vocabularyId = ParamUtil.getLong(_request, "vocabularyId");

		return _vocabularyId;
	}

	public boolean isDisabledVocabulariesManagementBar()
		throws PortalException {

		SearchContainer vocabulariesSearchContainer =
			getVocabulariesSearchContainer();

		if (vocabulariesSearchContainer.getTotal() <= 0) {
			return true;
		}

		return false;
	}

	public boolean isShowVocabulariesSearch() throws PortalException {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		SearchContainer vocabulariesSearchContainer =
			getVocabulariesSearchContainer();

		if (vocabulariesSearchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private AssetCategory _category;
	private Long _categoryId;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _vocabulariesSearchContainer;
	private AssetVocabulary _vocabulary;
	private Long _vocabularyId;

}