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

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.categories.admin.web.internal.configuration.AssetCategoriesAdminWebConfiguration;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminDisplayStyleKeys;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminWebKeys;
import com.liferay.asset.categories.admin.web.internal.util.AssetCategoryTreePathComparator;
import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
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
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoriesPermission;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.util.comparator.AssetCategoryCreateDateComparator;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyCreateDateComparator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class AssetCategoriesDisplayContext {

	public AssetCategoriesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_assetCategoriesAdminWebConfiguration =
			(AssetCategoriesAdminWebConfiguration)
				_httpServletRequest.getAttribute(
					AssetCategoriesAdminWebKeys.
						ASSET_CATEGORIES_ADMIN_CONFIGURATION);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAddCategoryRedirect() throws PortalException {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/edit_category.jsp"
		).setParameter(
			"itemSelectorEventName",
			() -> {
				String itemSelectorEventName = getItemSelectorEventName();

				if (Validator.isNotNull(itemSelectorEventName)) {
					return itemSelectorEventName;
				}

				return null;
			}
		).setParameter(
			"parentCategoryId",
			() -> {
				long parentCategoryId = BeanParamUtil.getLong(
					getCategory(), _httpServletRequest, "parentCategoryId");

				if (parentCategoryId > 0) {
					return parentCategoryId;
				}

				return null;
			}
		).setParameter(
			"vocabularyId",
			() -> {
				long vocabularyId = getVocabularyId();

				if (vocabularyId > 0) {
					return vocabularyId;
				}

				return null;
			}
		).buildString();
	}

	public String getAssetType(AssetVocabulary vocabulary)
		throws PortalException {

		long[] selectedClassNameIds = vocabulary.getSelectedClassNameIds();
		long[] selectedClassTypePKs = vocabulary.getSelectedClassTypePKs();

		StringBundler sb = new StringBundler();

		for (int i = 0; i < selectedClassNameIds.length; i++) {
			long classNameId = selectedClassNameIds[i];
			long classTypePK = selectedClassTypePKs[i];

			String name = LanguageUtil.get(
				_httpServletRequest, "all-asset-types");

			if (classNameId != AssetCategoryConstants.ALL_CLASS_NAME_ID) {
				if (classTypePK != -1) {
					AssetRendererFactory<?> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClassNameId(classNameId);

					ClassTypeReader classTypeReader =
						assetRendererFactory.getClassTypeReader();

					try {
						ClassType classType = classTypeReader.getClassType(
							classTypePK, _themeDisplay.getLocale());

						name = classType.getName();
					}
					catch (NoSuchModelException noSuchModelException) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Unable to get asset type for class type " +
									"primary key " + classTypePK,
								noSuchModelException);
						}

						continue;
					}
				}
				else {
					name = ResourceActionsUtil.getModelResource(
						_themeDisplay.getLocale(),
						PortalUtil.getClassName(classNameId));
				}
			}

			sb.append(name);

			if (vocabulary.isRequired(classNameId, classTypePK)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.STAR);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(LanguageUtil.get(_httpServletRequest, "required"));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		if (sb.index() == 0) {
			return StringPool.BLANK;
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	public SearchContainer<AssetCategory> getCategoriesSearchContainer()
		throws PortalException {

		if (_categoriesSearchContainer != null) {
			return _categoriesSearchContainer;
		}

		SearchContainer<AssetCategory> categoriesSearchContainer =
			new SearchContainer(
				_renderRequest, _getIteratorURL(), null,
				"there-are-no-categories");

		categoriesSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		categoriesSearchContainer.setOrderByComparator(
			new AssetCategoryCreateDateComparator(orderByAsc));
		categoriesSearchContainer.setOrderByType(orderByType);

		AssetVocabulary vocabulary = getVocabulary();

		if (Validator.isNotNull(_getKeywords())) {
			Sort sort = null;

			if (isFlattenedNavigationAllowed()) {
				sort = new Sort("treePath", Sort.INT_TYPE, !orderByAsc);
			}
			else {
				sort = new Sort("createDate", Sort.LONG_TYPE, !orderByAsc);
			}

			AssetCategoryDisplay assetCategoryDisplay =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					new long[] {vocabulary.getGroupId()}, _getKeywords(),
					new long[] {vocabulary.getVocabularyId()}, new long[0],
					categoriesSearchContainer.getStart(),
					categoriesSearchContainer.getEnd(), sort);

			categoriesSearchContainer.setResultsAndTotal(
				assetCategoryDisplay::getCategories,
				assetCategoryDisplay.getTotal());
		}
		else if (isFlattenedNavigationAllowed()) {
			AssetCategory category = getCategory();

			AssetCategoryTreePathComparator assetCategoryTreePathComparator =
				AssetCategoryTreePathComparator.getInstance(orderByAsc);

			if (category == null) {
				categoriesSearchContainer.setResultsAndTotal(
					() -> AssetCategoryServiceUtil.getVocabularyCategories(
						vocabulary.getVocabularyId(),
						categoriesSearchContainer.getStart(),
						categoriesSearchContainer.getEnd(),
						assetCategoryTreePathComparator),
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						vocabulary.getGroupId(), vocabulary.getVocabularyId()));
			}
			else {
				categoriesSearchContainer.setResultsAndTotal(
					() -> AssetCategoryServiceUtil.getVocabularyCategories(
						category.getCategoryId(), vocabulary.getVocabularyId(),
						categoriesSearchContainer.getStart(),
						categoriesSearchContainer.getEnd(),
						assetCategoryTreePathComparator),
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						vocabulary.getGroupId(), category.getCategoryId(),
						vocabulary.getVocabularyId()));
			}
		}
		else {
			categoriesSearchContainer.setResultsAndTotal(
				() -> AssetCategoryServiceUtil.getVocabularyCategories(
					vocabulary.getGroupId(), getCategoryId(),
					vocabulary.getVocabularyId(),
					categoriesSearchContainer.getStart(),
					categoriesSearchContainer.getEnd(),
					categoriesSearchContainer.getOrderByComparator()),
				AssetCategoryServiceUtil.getVocabularyCategoriesCount(
					vocabulary.getGroupId(), getCategoryId(),
					vocabulary.getVocabularyId()));
		}

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_renderResponse);

		StringBundler sb = new StringBundler(7);

		sb.append("^(?!.*");
		sb.append(_renderResponse.getNamespace());
		sb.append("redirect).*(/vocabulary/");

		sb.append(vocabulary.getVocabularyId());

		sb.append("/category/");
		sb.append(getCategoryId());
		sb.append(")");

		emptyOnClickRowChecker.setRememberCheckBoxStateURLRegex(sb.toString());

		categoriesSearchContainer.setRowChecker(emptyOnClickRowChecker);

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

		_categoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");

		return _categoryId;
	}

	public String getCategoryLocalizationXML(AssetCategory category) {
		return LocalizationUtil.updateLocalization(
			category.getTitleMap(), StringPool.BLANK, "title",
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
	}

	public String getCategorySelectorURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_httpServletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter(
				"eventName", _renderResponse.getNamespace() + "selectCategory");
			portletURL.setParameter(
				"selectedCategories", "{selectedCategories}");
			portletURL.setParameter("singleSelect", "{singleSelect}");
			portletURL.setParameter("vocabularyIds", "{vocabularyIds}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	public String getDefaultRedirect() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).buildString();
	}

	public String getDisplayStyle() {
		if (isFlattenedNavigationAllowed()) {
			_displayStyle = "list";
		}

		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest,
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN, "list");

		return _displayStyle;
	}

	public String getEditCategoryRedirect() throws PortalException {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).setParameter(
			"categoryId",
			() -> {
				long parentCategoryId = BeanParamUtil.getLong(
					getCategory(), _httpServletRequest, "parentCategoryId");

				if (parentCategoryId > 0) {
					return parentCategoryId;
				}

				return null;
			}
		).setParameter(
			"vocabularyId",
			() -> {
				if (getVocabularyId() > 0) {
					return getVocabularyId();
				}

				return null;
			}
		).buildString();
	}

	public PortletURL getEditVocabularyURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/edit_vocabulary.jsp"
		).buildPortletURL();
	}

	public String getGroupName() throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(
			_themeDisplay.getScopeGroupId());

		return group.getDescriptiveName(_themeDisplay.getLocale());
	}

	public Map<String, List<AssetVocabulary>> getInheritedVocabularies()
		throws PortalException {

		if (_inheritedVocabularies != null) {
			return _inheritedVocabularies;
		}

		_inheritedVocabularies = new LinkedHashMap<>();

		Company company = _themeDisplay.getCompany();

		if (company.getGroupId() != _themeDisplay.getScopeGroupId()) {
			Group group = company.getGroup();

			_inheritedVocabularies.put(
				group.getDescriptiveName(_themeDisplay.getLocale()),
				AssetVocabularyServiceUtil.getGroupVocabularies(
					company.getGroupId(), false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new AssetVocabularyCreateDateComparator()));
		}

		List<DepotEntry> depotEntries =
			DepotEntryServiceUtil.getGroupConnectedDepotEntries(
				_themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (DepotEntry depotEntry : depotEntries) {
			Group group = depotEntry.getGroup();

			List<AssetVocabulary> groupVocabularies =
				AssetVocabularyServiceUtil.getGroupVocabularies(
					group.getGroupId(), false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new AssetVocabularyCreateDateComparator());

			if (ListUtil.isNotEmpty(groupVocabularies)) {
				_inheritedVocabularies.put(
					group.getDescriptiveName(_themeDisplay.getLocale()),
					groupVocabularies);
			}
		}

		return _inheritedVocabularies;
	}

	public String getItemSelectorEventName() {
		return ParamUtil.getString(_renderRequest, "itemSelectorEventName");
	}

	public String getLinkURL() throws Exception {
		AssetCategoriesCompanyConfiguration
			assetCategoriesCompanyConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					AssetCategoriesCompanyConfiguration.class,
					_themeDisplay.getCompanyId());

		return assetCategoriesCompanyConfiguration.linkToDocumentationURL();
	}

	public int getMaximumNumberOfCategoriesPerVocabulary() throws Exception {
		AssetCategoriesCompanyConfiguration
			assetCategoriesCompanyConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					AssetCategoriesCompanyConfiguration.class,
					_themeDisplay.getCompanyId());

		return assetCategoriesCompanyConfiguration.
			maximumNumberOfCategoriesPerVocabulary();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN, "asc");

		return _orderByType;
	}

	public List<AssetVocabulary> getVocabularies() throws PortalException {
		if (_vocabularies != null) {
			return _vocabularies;
		}

		_vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
			_themeDisplay.getScopeGroupId(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new AssetVocabularyCreateDateComparator());

		return _vocabularies;
	}

	public List<DropdownItem> getVocabulariesDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteVocabularies");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	public SearchContainer<AssetVocabulary> getVocabulariesSearchContainer()
		throws PortalException {

		if (_vocabulariesSearchContainer != null) {
			return _vocabulariesSearchContainer;
		}

		SearchContainer<AssetVocabulary> vocabulariesSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-vocabularies");

		vocabulariesSearchContainer.setOrderByCol("create-date");

		String orderByType = getOrderByType();

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		vocabulariesSearchContainer.setOrderByComparator(
			new AssetVocabularyCreateDateComparator(orderByAsc));
		vocabulariesSearchContainer.setOrderByType(orderByType);

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			Sort sort = new Sort("createDate", Sort.LONG_TYPE, !orderByAsc);

			AssetVocabularyDisplay assetVocabularyDisplay =
				AssetVocabularyServiceUtil.searchVocabulariesDisplay(
					_themeDisplay.getScopeGroupId(), keywords, false,
					vocabulariesSearchContainer.getStart(),
					vocabulariesSearchContainer.getEnd(), sort);

			vocabulariesSearchContainer.setResultsAndTotal(
				assetVocabularyDisplay::getVocabularies,
				assetVocabularyDisplay.getTotal());
		}
		else {
			vocabulariesSearchContainer.setResultsAndTotal(
				() -> AssetVocabularyServiceUtil.getGroupVocabularies(
					_themeDisplay.getScopeGroupId(), false,
					vocabulariesSearchContainer.getStart(),
					vocabulariesSearchContainer.getEnd(),
					vocabulariesSearchContainer.getOrderByComparator()),
				AssetVocabularyServiceUtil.getGroupVocabulariesCount(
					_themeDisplay.getScopeGroupId()));

			if (vocabulariesSearchContainer.getTotal() == 0) {
				vocabulariesSearchContainer.setResultsAndTotal(
					vocabulariesSearchContainer::getResults,
					AssetVocabularyServiceUtil.getGroupVocabulariesCount(
						_themeDisplay.getScopeGroupId()));
			}
		}

		vocabulariesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

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

	public List<DropdownItem> getVocabularyActionDropdownItems() {
		if (!hasAddVocabularyPermission()) {
			return null;
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(getEditVocabularyURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-vocabulary"));
			}
		).build();
	}

	public long getVocabularyId() throws PortalException {
		if (_vocabularyId != null) {
			return _vocabularyId;
		}

		_vocabularyId = ParamUtil.getLong(
			_httpServletRequest, "vocabularyId", _getDefaultVocabularyId());

		return _vocabularyId;
	}

	public boolean hasAddVocabularyPermission() {
		if (AssetCategoriesPermission.contains(
				_themeDisplay.getPermissionChecker(),
				AssetCategoriesPermission.RESOURCE_NAME,
				AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
				_themeDisplay.getSiteGroupId(), ActionKeys.ADD_VOCABULARY)) {

			return true;
		}

		return false;
	}

	public boolean hasPermission(AssetCategory category, String actionId)
		throws PortalException {

		if (category.getGroupId() != _themeDisplay.getScopeGroupId()) {
			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			_themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroupId(), AssetCategory.class.getName(),
			category.getCategoryId(),
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return AssetCategoryPermission.contains(
			_themeDisplay.getPermissionChecker(), category, actionId);
	}

	public boolean isAssetCategoriesLimitExceeded() throws Exception {
		AssetVocabulary vocabulary = getVocabulary();

		int vocabularyCategoriesCount =
			AssetCategoryLocalServiceUtil.getVocabularyCategoriesCount(
				vocabulary.getVocabularyId());

		if (vocabularyCategoriesCount >=
				getMaximumNumberOfCategoriesPerVocabulary()) {

			return true;
		}

		return false;
	}

	public boolean isFlattenedNavigationAllowed() {
		if (StringUtil.equals(
				_assetCategoriesAdminWebConfiguration.
					categoryNavigationDisplayStyle(),
				AssetCategoriesAdminDisplayStyleKeys.FLATTENED_TREE)) {

			return true;
		}

		return false;
	}

	public boolean isItemSelector() {
		return Validator.isNotNull(getItemSelectorEventName());
	}

	public boolean isSaveAndAddNewButtonDisabled() throws Exception {
		AssetVocabulary vocabulary = getVocabulary();

		int vocabularyCategoriesCount =
			AssetCategoryLocalServiceUtil.getVocabularyCategoriesCount(
				vocabulary.getVocabularyId());

		if (vocabularyCategoriesCount >=
				(getMaximumNumberOfCategoriesPerVocabulary() - 1)) {

			return true;
		}

		return false;
	}

	public boolean isSaveButtonDisabled() throws Exception {
		AssetCategory category = getCategory();

		if (category != null) {
			return false;
		}

		if (isAssetCategoriesLimitExceeded()) {
			return true;
		}

		return false;
	}

	public boolean isShowCategoriesAddButton() {
		try {
			if (isAssetCategoriesLimitExceeded()) {
				return false;
			}

			AssetVocabulary vocabulary = getVocabulary();

			if (vocabulary.getGroupId() != _themeDisplay.getScopeGroupId()) {
				return false;
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get asset vocabulary", exception);
		}

		if (AssetCategoriesPermission.contains(
				_themeDisplay.getPermissionChecker(),
				AssetCategoriesPermission.RESOURCE_NAME,
				AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
				_themeDisplay.getSiteGroupId(), ActionKeys.ADD_CATEGORY)) {

			return true;
		}

		return false;
	}

	public boolean isShowSelectAssetDisplayPage() {
		if (_showSelectAssetDisplayPage != null) {
			return _showSelectAssetDisplayPage;
		}

		boolean showSelectAssetDisplayPage = true;

		Group group = _themeDisplay.getScopeGroup();

		if (group.isCompany() || group.isDepot()) {
			showSelectAssetDisplayPage = false;
		}

		_showSelectAssetDisplayPage = showSelectAssetDisplayPage;

		return _showSelectAssetDisplayPage;
	}

	private long _getDefaultVocabularyId() throws PortalException {
		List<AssetVocabulary> vocabularies = getVocabularies();

		if (ListUtil.isNotEmpty(vocabularies)) {
			AssetVocabulary vocabulary = vocabularies.get(0);

			return vocabulary.getVocabularyId();
		}

		Map<String, List<AssetVocabulary>> inheritedVocabularies =
			getInheritedVocabularies();

		if (MapUtil.isNotEmpty(inheritedVocabularies)) {
			for (Map.Entry<String, List<AssetVocabulary>> entry :
					inheritedVocabularies.entrySet()) {

				vocabularies = entry.getValue();

				if (ListUtil.isNotEmpty(vocabularies)) {
					AssetVocabulary vocabulary = vocabularies.get(0);

					return vocabulary.getVocabularyId();
				}
			}
		}

		return 0;
	}

	private PortletURL _getIteratorURL() throws PortalException {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).setRedirect(
			currentURL
		).setKeywords(
			_getKeywords()
		).setNavigation(
			getNavigation()
		).setParameter(
			"categoryId",
			() -> {
				if (!isFlattenedNavigationAllowed()) {
					return getCategoryId();
				}

				return null;
			}
		).setParameter(
			"displayStyle", getDisplayStyle()
		).setParameter(
			"vocabularyId", getVocabularyId()
		).buildPortletURL();
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
			isFlattenedNavigationAllowed() ? "path" : "create-date");

		return _orderByCol;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoriesDisplayContext.class);

	private final AssetCategoriesAdminWebConfiguration
		_assetCategoriesAdminWebConfiguration;
	private SearchContainer<AssetCategory> _categoriesSearchContainer;
	private AssetCategory _category;
	private Long _categoryId;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private Map<String, List<AssetVocabulary>> _inheritedVocabularies;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showSelectAssetDisplayPage;
	private final ThemeDisplay _themeDisplay;
	private List<AssetVocabulary> _vocabularies;
	private SearchContainer<AssetVocabulary> _vocabulariesSearchContainer;
	private AssetVocabulary _vocabulary;
	private Long _vocabularyId;

}