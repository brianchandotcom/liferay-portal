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

package com.liferay.asset.categories.admin.web.internal.display.context;

import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetCategoryDisplay;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetPermission;
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

	public String getAssetType(AssetVocabulary vocabulary)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] selectedClassNameIds = vocabulary.getSelectedClassNameIds();
		long[] selectedClassTypePKs = vocabulary.getSelectedClassTypePKs();

		StringBundler sb = new StringBundler();

		for (int i = 0; i < selectedClassNameIds.length; i++) {
			long classNameId = selectedClassNameIds[i];
			long classTypePK = selectedClassTypePKs[i];

			String name = LanguageUtil.get(_request, "all-asset-types");

			if (classNameId != AssetCategoryConstants.ALL_CLASS_NAME_ID) {
				if (classTypePK != -1) {
					AssetRendererFactory<?> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClassNameId(classNameId);

					ClassTypeReader classTypeReader =
						assetRendererFactory.getClassTypeReader();

					ClassType classType = classTypeReader.getClassType(
						classTypePK, themeDisplay.getLocale());

					name = classType.getName();
				}
				else {
					name = ResourceActionsUtil.getModelResource(
						themeDisplay.getLocale(),
						PortalUtil.getClassName(classNameId));
				}
			}

			sb.append(name);

			if (vocabulary.isRequired(classNameId, classTypePK)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.STAR);
			}

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
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

		if (_categoriesSearchContainer != null) {
			return _categoriesSearchContainer;
		}

		SearchContainer categoriesSearchContainer = new SearchContainer(
			_renderRequest, getIteratorURL(), null, "there-are-no-categories");

		if (Validator.isNull(getKeywords())) {
			if (isShowCategoriesAddButton()) {
				categoriesSearchContainer.setEmptyResultsMessageCssClass(
					"there-are-no-categories.-you-can-add-a-category-by-" +
						"clicking-the-plus-button-on-the-bottom-right-corner");
				categoriesSearchContainer.setEmptyResultsMessageCssClass(
					"taglib-empty-result-message-header-has-plus-btn");
			}
		}
		else {
			categoriesSearchContainer.setSearch(true);
		}

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

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_renderResponse);

		StringBundler sb = new StringBundler(7);

		sb.append("^(?!.*");
		sb.append(_renderResponse.getNamespace());
		sb.append("redirect).*(/vocabulary/");
		sb.append(getVocabularyId());
		sb.append("/category/");
		sb.append(getCategoryId());
		sb.append(")");

		emptyOnClickRowChecker.setRememberCheckBoxStateURLRegex(sb.toString());

		categoriesSearchContainer.setRowChecker(emptyOnClickRowChecker);

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

		_categoriesSearchContainer = categoriesSearchContainer;

		return _categoriesSearchContainer;
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

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
			"display-style", "list");

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

	public String getSelectCategoryURL() throws Exception {
		if (_selectCategoryURL != null) {
			return _selectCategoryURL;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				themeDisplay.getScopeGroupId());

		PortletURL selectCategoryURL = PortletProviderUtil.getPortletURL(
			_request, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		selectCategoryURL.setParameter(
			"eventName", _renderResponse.getNamespace() + "selectCategory");
		selectCategoryURL.setParameter(
			"allowedSelectVocabularies", Boolean.TRUE.toString());
		selectCategoryURL.setParameter("singleSelect", Boolean.TRUE.toString());
		selectCategoryURL.setParameter(
			"vocabularyIds",
			ListUtil.toString(
				vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR));

		selectCategoryURL.setWindowState(LiferayWindowState.POP_UP);

		_selectCategoryURL = selectCategoryURL.toString();

		return _selectCategoryURL;
	}

	public SearchContainer getVocabulariesSearchContainer()
		throws PortalException {

		if (_vocabulariesSearchContainer != null) {
			return _vocabulariesSearchContainer;
		}

		SearchContainer vocabulariesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-vocabularies");

		String keywords = getKeywords();

		if (Validator.isNull(keywords)) {
			if (isShowVocabulariesAddButton()) {
				vocabulariesSearchContainer.setEmptyResultsMessage(
					"there-are-no-vocabularies.-you-can-add-a-vocabulary-by-" +
						"clicking-the-plus-button-on-the-bottom-right-corner");
				vocabulariesSearchContainer.setEmptyResultsMessageCssClass(
					"taglib-empty-result-message-header-has-plus-btn");
			}
		}
		else {
			vocabulariesSearchContainer.setSearch(true);
		}

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

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_renderResponse);

		StringBundler sb = new StringBundler(5);

		sb.append("^(?!.*");
		sb.append(_renderResponse.getNamespace());
		sb.append("redirect).*(/vocabulary/");
		sb.append(getVocabularyId());
		sb.append(")");

		emptyOnClickRowChecker.setRememberCheckBoxStateURLRegex(sb.toString());

		vocabulariesSearchContainer.setRowChecker(emptyOnClickRowChecker);

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

			if (vocabulariesCount == 0) {
				vocabulariesCount =
					AssetVocabularyServiceUtil.getGroupVocabulariesCount(
						scopeGroupId);

				vocabulariesSearchContainer.setTotal(vocabulariesCount);
			}
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

	public boolean isDisabledCategoriesManagementBar() throws PortalException {
		SearchContainer categoriesSearchContainer =
			getCategoriesSearchContainer();

		if (categoriesSearchContainer.getTotal() <= 0) {
			return true;
		}

		return false;
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

	public boolean isShowCategoriesAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (AssetPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(), ActionKeys.ADD_CATEGORY)) {

			return true;
		}

		return false;
	}

	public boolean isShowCategoriesSearch() throws PortalException {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		SearchContainer categoriesSearchContainer =
			getCategoriesSearchContainer();

		if (categoriesSearchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean isShowVocabulariesAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (AssetPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(), ActionKeys.ADD_VOCABULARY)) {

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

	private SearchContainer _categoriesSearchContainer;
	private AssetCategory _category;
	private Long _categoryId;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private String _selectCategoryURL;
	private SearchContainer _vocabulariesSearchContainer;
	private AssetVocabulary _vocabulary;
	private Long _vocabularyId;

}