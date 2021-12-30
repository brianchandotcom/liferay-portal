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
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.display.DDMDataProviderDisplay;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.display.DDMDataProviderDisplayTracker;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.display.context.helper.DDMDataProviderRequestHelper;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.search.DDMDataProviderSearch;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.security.permission.resource.DDMDataProviderInstancePermission;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.util.DDMDataProviderPortletUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

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
		DDMDataProviderDisplayTracker ddmDataProviderDisplayTracker,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		UserLocalService userLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmDataProviderDisplayTracker = ddmDataProviderDisplayTracker;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
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
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteDataProviderInstances");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_ddmDataProviderRequestHelper.getRequest(), "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getClearResultsURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		if (!_isShowAddDataProviderButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				for (String ddmDataProviderType : _getDDMDataProviderTypes()) {
					addPrimaryDropdownItem(
						_getAddDataProviderDropdownItem(ddmDataProviderType));
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
			_createDDMFormRenderingContext();

		if (_ddmDataProviderInstance != null) {
			DDMFormValues ddmFormValues = _deserialize(
				ddmDataProviderInstance.getDefinition(), ddmForm);

			Set<String> passwordDDMFormFieldNames =
				DDMDataProviderPortletUtil.getDDMFormFieldNamesByType(
					ddmForm, "password");

			_obfuscateDDMFormFieldValues(
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
			_displayStyle = getDisplayStyle(_renderRequest, _getDisplayViews());
		}

		return _displayStyle;
	}

	public List<DropdownItem> getEmptyResultMessageActionItemsDropdownItems() {
		if (!_isShowAddDataProviderButton() || _isSearch()) {
			return null;
		}

		return new DropdownItemList() {
			{
				for (String ddmDataProviderType : _getDDMDataProviderTypes()) {
					add(_getAddDataProviderDropdownItem(ddmDataProviderType));
				}
			}
		};
	}

	public EmptyResultMessageKeys.AnimationType
		getEmptyResultMessageAnimationType() {

		if (_isSearch()) {
			return EmptyResultMessageKeys.AnimationType.SUCCESS;
		}

		return EmptyResultMessageKeys.AnimationType.EMPTY;
	}

	public String getEmptyResultMessageDescription() {
		if (_isSearch()) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest =
			_ddmDataProviderRequestHelper.getRequest();

		return LanguageUtil.get(
			httpServletRequest,
			"create-a-data-provider-to-automatically-populate-your-select-" +
				"fields");
	}

	public String getEmptyResultsMessage() {
		SearchContainer<?> search = getSearch();

		HttpServletRequest httpServletRequest =
			_ddmDataProviderRequestHelper.getRequest();

		return LanguageUtil.get(
			httpServletRequest, search.getEmptyResultsMessage());
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			_ddmDataProviderRequestHelper.getRequest();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	public List<NavigationItem> getNavigationItems(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return new NavigationItemList() {
			{
				DDMDataProviderDisplay ddmDataProviderDisplay =
					_getDDMDataProviderDisplay();

				for (DDMDisplayTabItem ddmDisplayTabItem :
						ddmDataProviderDisplay.getDDMDisplayTabItems()) {

					if (!ddmDisplayTabItem.isShow(liferayPortletRequest)) {
						continue;
					}

					String ddmDisplayTabItemTitle = GetterUtil.getString(
						ddmDisplayTabItem.getTitle(
							liferayPortletRequest, liferayPortletResponse));

					DDMDisplayTabItem defaultDDMDisplayTabItem =
						ddmDataProviderDisplay.getDefaultDDMDisplayTabItem();

					String defaultDDMDisplayTabItemTitle = GetterUtil.getString(
						defaultDDMDisplayTabItem.getTitle(
							liferayPortletRequest, liferayPortletResponse));

					String ddmDisplayTabItemHREF = GetterUtil.getString(
						ddmDisplayTabItem.getURL(
							liferayPortletRequest, liferayPortletResponse));

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(
									ddmDisplayTabItemTitle,
									defaultDDMDisplayTabItemTitle));
							navigationItem.setHref(ddmDisplayTabItemHREF);
							navigationItem.setLabel(ddmDisplayTabItemTitle);
						});
				}
			}
		};
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, DDMPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
			"modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, DDMPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
			"asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).setKeywords(
			() -> {
				String keywords = _getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"delta",
			() -> {
				String delta = ParamUtil.getString(_renderRequest, "delta");

				if (Validator.isNotNull(delta)) {
					return delta;
				}

				return null;
			}
		).setParameter(
			"displayStyle",
			() -> {
				String displayStyle = getDisplayStyle();

				if (Validator.isNotNull(displayStyle)) {
					return displayStyle;
				}

				return null;
			}
		).setParameter(
			"groupId", _ddmDataProviderRequestHelper.getScopeGroupId()
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).setParameter(
			"refererPortletName", _getRefererPortletName()
		).buildPortletURL();
	}

	public SearchContainer<?> getSearch() {
		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"displayStyle", getDisplayStyle()
		).buildPortletURL();

		DDMDataProviderSearch ddmDataProviderSearch = new DDMDataProviderSearch(
			_renderRequest, portletURL);

		if (ddmDataProviderSearch.isSearch()) {
			ddmDataProviderSearch.setEmptyResultsMessage(
				"no-data-providers-were-found");
		}
		else {
			ddmDataProviderSearch.setEmptyResultsMessage(
				"there-are-no-data-providers");
		}

		ddmDataProviderSearch.setOrderByCol(getOrderByCol());
		ddmDataProviderSearch.setOrderByComparator(
			DDMDataProviderPortletUtil.getDDMDataProviderOrderByComparator(
				getOrderByCol(), getOrderByType()));
		ddmDataProviderSearch.setOrderByType(getOrderByType());

		_setDDMDataProviderInstanceSearchResults(ddmDataProviderSearch);
		_setDDMDataProviderInstanceSearchTotal(ddmDataProviderSearch);

		return ddmDataProviderSearch;
	}

	public String getSearchContainerId() {
		return "dataProviderInstance";
	}

	public String getSortingURL() throws Exception {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = ParamUtil.getString(
					_renderRequest, "orderByType");

				if (orderByType.equals("asc")) {
					return "desc";
				}

				return "asc";
			}
		).buildString();
	}

	public String getTitle() {
		DDMDataProviderDisplay ddmDataProviderDisplay =
			_getDDMDataProviderDisplay();

		return ddmDataProviderDisplay.getTitle(_renderRequest.getLocale());
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
				String[] viewTypes = _getDisplayViews();

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

	public boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() {
		if (hasResults() || _isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowBackIcon() {
		return ParamUtil.getBoolean(_renderRequest, "showBackIcon", true);
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

	protected String getDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
				"display-style", "descriptive");
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
				"display-style", displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	private DDMFormRenderingContext _createDDMFormRenderingContext() {
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

	private DDMFormValues _deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddDataProviderDropdownItem(String ddmDataProviderType) {

		HttpServletRequest httpServletRequest =
			_ddmDataProviderRequestHelper.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/edit_data_provider.jsp", "redirect",
				PortalUtil.getCurrentURL(httpServletRequest), "groupId",
				String.valueOf(themeDisplay.getScopeGroupId()), "type",
				ddmDataProviderType);

			dropdownItem.setLabel(
				LanguageUtil.get(httpServletRequest, ddmDataProviderType));
		};
	}

	private DDMDataProviderDisplay _getDDMDataProviderDisplay() {
		return _ddmDataProviderDisplayTracker.getDDMDataProviderDisplay(
			_getRefererPortletName());
	}

	private Set<String> _getDDMDataProviderTypes() {
		return _ddmDataProviderTracker.getDDMDataProviderTypes();
	}

	private String[] _getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);

				dropdownItem.setHref(getPortletURL(), "navigation", "all");

				dropdownItem.setLabel(
					LanguageUtil.get(
						_ddmDataProviderRequestHelper.getRequest(), "all"));
			}
		).build();
	}

	private long[] _getGroupIds() {
		long scopeGroupId = _ddmDataProviderRequestHelper.getScopeGroupId();

		ThemeDisplay themeDisplay =
			_ddmDataProviderRequestHelper.getThemeDisplay();

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			scopeGroupId = scopeGroup.getGroupId();
		}

		return new long[] {scopeGroupId};
	}

	private String _getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_ddmDataProviderRequestHelper.getRequest(), orderByCol));
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			_getOrderByDropdownItem("modified-date")
		).add(
			_getOrderByDropdownItem("name")
		).build();
	}

	private String _getRefererPortletName() {
		return ParamUtil.getString(
			_ddmDataProviderRequestHelper.getRequest(), "refererPortletName",
			_ddmDataProviderRequestHelper.getPortletName());
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private boolean _isShowAddDataProviderButton() {
		return DDMFormPermission.contains(
			_ddmDataProviderRequestHelper.getPermissionChecker(),
			_ddmDataProviderRequestHelper.getScopeGroupId(),
			DDMActionKeys.ADD_DATA_PROVIDER_INSTANCE);
	}

	private void _obfuscateDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		for (Locale availableLocale : value.getAvailableLocales()) {
			value.addString(availableLocale, Portal.TEMP_OBFUSCATION_VALUE);
		}
	}

	private void _obfuscateDDMFormFieldValues(
		Set<String> ddmFormFieldNamesToBeObfuscated,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (ddmFormFieldNamesToBeObfuscated.contains(
					ddmFormFieldValue.getName())) {

				_obfuscateDDMFormFieldValue(ddmFormFieldValue);
			}

			_obfuscateDDMFormFieldValues(
				ddmFormFieldNamesToBeObfuscated,
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}
	}

	private void _setDDMDataProviderInstanceSearchResults(
		DDMDataProviderSearch ddmDataProviderSearch) {

		List<DDMDataProviderInstance> results =
			_ddmDataProviderInstanceService.search(
				_ddmDataProviderRequestHelper.getCompanyId(), _getGroupIds(),
				_getKeywords(), ddmDataProviderSearch.getStart(),
				ddmDataProviderSearch.getEnd(),
				ddmDataProviderSearch.getOrderByComparator());

		ddmDataProviderSearch.setResults(results);
	}

	private void _setDDMDataProviderInstanceSearchTotal(
		DDMDataProviderSearch ddmDataProviderSearch) {

		int total = _ddmDataProviderInstanceService.searchCount(
			_ddmDataProviderRequestHelper.getCompanyId(), _getGroupIds(),
			_getKeywords());

		ddmDataProviderSearch.setTotal(total);
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private final DDMDataProviderDisplayTracker _ddmDataProviderDisplayTracker;
	private DDMDataProviderInstance _ddmDataProviderInstance;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderRequestHelper _ddmDataProviderRequestHelper;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private String _displayStyle;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserLocalService _userLocalService;

}