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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.display.context.util.DDMDataProviderRequestHelper;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.search.DDMDataProviderSearch;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.security.permission.resource.DDMDataProviderInstancePermission;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.util.DDMDataProviderPortletUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderDisplayContext {

	public DDMDataProviderDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		UserLocalService userLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_userLocalService = userLocalService;

		_ddmDataProviderRequestHelper = new DDMDataProviderRequestHelper(
			renderRequest);
	}

	public DDMDataProviderInstance fetchDataProviderInstance()
		throws PortalException {

		if (_ddmDataProviderInstance != null) {
			return _ddmDataProviderInstance;
		}

		long dataProviderInstanceId = ParamUtil.getLong(
			_renderRequest, "dataProviderInstanceId");

		_ddmDataProviderInstance =
			_ddmDataProviderInstanceService.fetchDataProviderInstance(
				dataProviderInstanceId);

		return _ddmDataProviderInstance;
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return new DropdownItemList() {

			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteDataProviderInstances");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddmDataProviderRequestHelper.getRequest(),
								"recycle-bin"));
						dropdownItem.setQuickAction(true);
					});
			}

		};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddDataProviderButton()) {
			return null;
		}

		return new CreationMenu() {

			{
				for (String ddmDataProviderType : getDDMDataProviderTypes()) {
					addPrimaryDropdownItem(
						getCreationMenuDropdownItem(ddmDataProviderType));
				}
			}

		};
	}

	public String getDataProviderInstanceDDMFormHTML() throws PortalException {
		DDMDataProviderInstance ddmDataProviderInstance =
			fetchDataProviderInstance();

		String type = BeanParamUtil.getString(
			ddmDataProviderInstance, _renderRequest, "type");

		DDMDataProvider ddmDataProvider =
			_ddmDataProviderTracker.getDDMDataProvider(type);

		Class<?> clazz = ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(clazz);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		if (_ddmDataProviderInstance != null) {
			DDMFormValues ddmFormValues =
				_ddmFormValuesJSONDeserializer.deserialize(
					ddmForm, _ddmDataProviderInstance.getDefinition());

			Set<String> passwordDDMFormFieldNames =
				DDMDataProviderPortletUtil.getDDMFormFieldNamesByType(
					ddmForm, "password");

			obfuscateDDMFormFieldValues(
				passwordDDMFormFieldNames,
				ddmFormValues.getDDMFormFieldValues());

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		}

		DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(clazz);

		ddmFormLayout.setPaginationMode(DDMFormLayout.SINGLE_PAGE_MODE);

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	public String getDataProviderInstanceDescription() throws PortalException {
		DDMDataProviderInstance ddmDataProviderInstance =
			fetchDataProviderInstance();

		if (ddmDataProviderInstance == null) {
			return StringPool.BLANK;
		}

		return ddmDataProviderInstance.getDescription(
			_renderRequest.getLocale());
	}

	public String getDataProviderInstanceName() throws PortalException {
		DDMDataProviderInstance ddmDataProviderInstance =
			fetchDataProviderInstance();

		if (ddmDataProviderInstance == null) {
			return StringPool.BLANK;
		}

		return ddmDataProviderInstance.getName(_renderRequest.getLocale());
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(_renderRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest request = _ddmDataProviderRequestHelper.getRequest();

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(
							getPortletURL(), "currentTab", "data-providers");
						navigationItem.setLabel(
							LanguageUtil.get(
								_ddmDataProviderRequestHelper.getRequest(),
								"data-providers"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddmDataProviderRequestHelper.getScopeGroupId()));

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer<?> getSearch() {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		DDMDataProviderSearch ddmDataProviderSearch = new DDMDataProviderSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMDataProviderInstance> orderByComparator =
			DDMDataProviderPortletUtil.getDDMDataProviderOrderByComparator(
				orderByCol, orderByType);

		ddmDataProviderSearch.setOrderByCol(orderByCol);
		ddmDataProviderSearch.setOrderByComparator(orderByComparator);
		ddmDataProviderSearch.setOrderByType(orderByType);

		if (ddmDataProviderSearch.isSearch()) {
			ddmDataProviderSearch.setEmptyResultsMessage(
				"no-data-providers-were-found");
		}
		else {
			ddmDataProviderSearch.setEmptyResultsMessage(
				"there-are-no-data-providers");
		}

		setDDMDataProviderInstanceSearchResults(ddmDataProviderSearch);
		setDDMDataProviderInstanceSearchTotal(ddmDataProviderSearch);

		return ddmDataProviderSearch;
	}

	public String getSearchContainerId() {
		return "dataProviderInstance";
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public String getUserPortraitURL(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		return user.getPortraitURL(
			_ddmDataProviderRequestHelper.getThemeDisplay());
	}

	public List<ViewTypeItem> getViewTypesItems() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {

			{
				String[] viewTypes = getDisplayViews();

				for (String viewType : viewTypes) {
					if (viewType.equals("descriptive")) {
						addListViewTypeItem();
					}
					else {
						addTableViewTypeItem();
					}
				}
			}

		};
	}

	public boolean isDisabledManagementBar() {
		if (hasResults() || isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowDeleteDataProviderIcon(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.DELETE);
	}

	public boolean isShowEditDataProviderIcon(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.UPDATE);
	}

	public boolean isShowPermissionsIcon(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return DDMDataProviderInstancePermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			dataProviderInstance, ActionKeys.PERMISSIONS);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext() {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			_ddmDataProviderRequestHelper.getRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_renderResponse));
		ddmFormRenderingContext.setLocale(
			_ddmDataProviderRequestHelper.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			_renderResponse.getNamespace());
		ddmFormRenderingContext.setShowRequiredFieldsWarning(false);

		return ddmFormRenderingContext;
	}

	protected Consumer<DropdownItem> getCreationMenuDropdownItem(
		String ddmDataProviderType) {

		HttpServletRequest request = _ddmDataProviderRequestHelper.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/edit_data_provider.jsp", "redirect",
				PortalUtil.getCurrentURL(request), "groupId",
				String.valueOf(themeDisplay.getScopeGroupId()), "type",
				ddmDataProviderType);

			dropdownItem.setLabel(
				LanguageUtil.get(request, ddmDataProviderType));
		};
	}

	protected Set<String> getDDMDataProviderTypes() {
		return _ddmDataProviderTracker.getDDMDataProviderTypes();
	}

	protected String getDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
				"display-style", "descriptive");
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
				"display-style", displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	protected String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {

			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);

						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");

						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddmDataProviderRequestHelper.getRequest(),
								"all"));
					});
			}

		};
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected Consumer<DropdownItem> getOrderByDropdownItem(String orderByCol) {
		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_ddmDataProviderRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {

			{
				add(getOrderByDropdownItem("modified-date"));
				add(getOrderByDropdownItem("name"));
			}

		};
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected boolean isShowAddDataProviderButton() {
		return DDMFormPermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			_ddmDataProviderRequestHelper.getScopeGroupId(),
			DDMActionKeys.ADD_DATA_PROVIDER_INSTANCE);
	}

	protected void obfuscateDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		for (Locale availableLocale : value.getAvailableLocales()) {
			value.addString(availableLocale, Portal.TEMP_OBFUSCATION_VALUE);
		}
	}

	protected void obfuscateDDMFormFieldValues(
		Set<String> ddmFormFieldNamesToBeObfuscated,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (ddmFormFieldNamesToBeObfuscated.contains(
					ddmFormFieldValue.getName())) {

				obfuscateDDMFormFieldValue(ddmFormFieldValue);
			}

			obfuscateDDMFormFieldValues(
				ddmFormFieldNamesToBeObfuscated,
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}
	}

	protected void setDDMDataProviderInstanceSearchResults(
		DDMDataProviderSearch ddmDataProviderSearch) {

		List<DDMDataProviderInstance> results =
			_ddmDataProviderInstanceService.search(
				_ddmDataProviderRequestHelper.getCompanyId(),
				new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
				getKeywords(), ddmDataProviderSearch.getStart(),
				ddmDataProviderSearch.getEnd(),
				ddmDataProviderSearch.getOrderByComparator());

		ddmDataProviderSearch.setResults(results);
	}

	protected void setDDMDataProviderInstanceSearchTotal(
		DDMDataProviderSearch ddmDataProviderSearch) {

		int total = _ddmDataProviderInstanceService.searchCount(
			_ddmDataProviderRequestHelper.getCompanyId(),
			new long[] {_ddmDataProviderRequestHelper.getScopeGroupId()},
			getKeywords());

		ddmDataProviderSearch.setTotal(total);
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private DDMDataProviderInstance _ddmDataProviderInstance;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderRequestHelper _ddmDataProviderRequestHelper;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private String _displayStyle;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserLocalService _userLocalService;

}