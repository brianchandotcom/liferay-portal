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

package com.liferay.dynamic.data.mapping.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.web.internal.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.web.internal.context.util.DDMWebRequestHelper;
import com.liferay.dynamic.data.mapping.web.internal.search.StructureSearch;
import com.liferay.dynamic.data.mapping.web.internal.search.StructureSearchTerms;
import com.liferay.dynamic.data.mapping.web.internal.search.TemplateSearch;
import com.liferay.dynamic.data.mapping.web.internal.search.TemplateSearchTerms;
import com.liferay.dynamic.data.mapping.web.internal.security.permission.resource.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.dynamic.data.mapping.web.internal.util.PortletDisplayTemplateUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDMDisplayContext {

	public DDMDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMDisplayRegistry ddmDisplayRegistry,
		DDMStructureLinkLocalService ddmStructureLinkLocalService,
		DDMStructureService ddmStructureService,
		DDMTemplateHelper ddmTemplateHelper,
		DDMTemplateService ddmTemplateService,
		DDMWebConfiguration ddmWebConfiguration,
		StorageAdapterRegistry storageAdapterRegistry) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
		_ddmStructureService = ddmStructureService;
		_ddmTemplateHelper = ddmTemplateHelper;
		_ddmTemplateService = ddmTemplateService;
		_ddmWebConfiguration = ddmWebConfiguration;
		_storageAdapterRegistry = storageAdapterRegistry;

		_ddmWebRequestHelper = new DDMWebRequestHelper(
			PortalUtil.getHttpServletRequest(renderRequest));
	}

	public boolean autogenerateStructureKey() {
		return _ddmWebConfiguration.autogenerateStructureKey();
	}

	public boolean autogenerateTemplateKey() {
		return _ddmWebConfiguration.autogenerateTemplateKey();
	}

	public boolean changeableDefaultLanguage() {
		return _ddmWebConfiguration.changeableDefaultLanguage();
	}

	public boolean containsAddTemplatePermission(String actualTemplateTypeValue)
		throws PortalException {

		DDMDisplay ddmDisplay = getDDMDisplay();

		String expectedTemplateTypeValue = getTemplateTypeValue();

		long scopeClassNameId = PortalUtil.getClassNameId(
			ddmDisplay.getStructureType());

		if (DDMTemplatePermission.containsAddTemplatePermission(
				_ddmWebRequestHelper.getPermissionChecker(),
				_ddmWebRequestHelper.getScopeGroupId(), getClassNameId(),
				scopeClassNameId) &&
			(Validator.isNull(expectedTemplateTypeValue) ||
			 expectedTemplateTypeValue.equals(actualTemplateTypeValue))) {

			return true;
		}

		return false;
	}

	public DDMStructure fetchStructure(DDMTemplate template) {
		return _ddmTemplateHelper.fetchStructure(template);
	}

	public List<DropdownItem> getActionItemsDropdownItems(String action) {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", action);
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddmWebRequestHelper.getRequest(), "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAutocompleteJSON(
			HttpServletRequest httpServletRequest, String language)
		throws Exception {

		return _ddmTemplateHelper.getAutocompleteJSON(
			httpServletRequest, language);
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(getRefererPortletName());
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_ddmWebRequestHelper.getRequest(),
								"filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_ddmWebRequestHelper.getRequest(), "order-by"));
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItem() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(getScopedStructureLabel());
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return new NavigationItemList() {
			{
				DDMDisplay ddmDisplay = getDDMDisplay();

				for (DDMDisplayTabItem ddmDisplayTabItem :
						ddmDisplay.getTabItems()) {

					if (!ddmDisplayTabItem.isShow(liferayPortletRequest)) {
						continue;
					}

					String ddmDisplayTabItemTitle = GetterUtil.getString(
						ddmDisplayTabItem.getTitle(
							liferayPortletRequest, liferayPortletResponse));

					DDMDisplayTabItem defaultDDMDisplayTabItem =
						ddmDisplay.getDefaultTabItem();

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
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByCol = ParamUtil.getString(_renderRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				"modified-date");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				orderByCol);
		}

		return orderByCol;
	}

	public String getOrderByType() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				"asc");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				orderByType);
		}

		return orderByType;
	}

	public String getRefererPortletName() {
		return ParamUtil.getString(
			_ddmWebRequestHelper.getRequest(), "refererPortletName",
			_ddmWebRequestHelper.getPortletName());
	}

	public CreationMenu getSelectStructureCreationMenu()
		throws PortalException {

		if (!isShowAddStructureButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				PortletURL redirect = _renderResponse.createRenderURL();

				redirect.setParameter("mvcPath", "/select_structure.jsp");
				redirect.setParameter("classPK", String.valueOf(getClassPK()));
				redirect.setParameter(
					"eventName",
					ParamUtil.getString(
						_renderRequest, "eventName", "selectStructure"));

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "mvcPath",
							"/edit_structure.jsp", "redirect", redirect,
							"groupId",
							String.valueOf(
								_ddmWebRequestHelper.getScopeGroupId()));
						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddmWebRequestHelper.getRequest(), "add"));
					});
			}
		};
	}

	public String getSelectStructureSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_structure.jsp");
		portletURL.setParameter(
			"classPK",
			String.valueOf(ParamUtil.getLong(_renderRequest, "classPK")));
		portletURL.setParameter(
			"eventName",
			ParamUtil.getString(
				_renderRequest, "eventName", "selectStructure"));

		return portletURL.toString();
	}

	public String getSelectTemplateSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_template.jsp");
		portletURL.setParameter(
			"templateId",
			String.valueOf(ParamUtil.getLong(_renderRequest, "templateId")));
		portletURL.setParameter(
			"classNameId", String.valueOf(getClassNameId()));
		portletURL.setParameter(
			"classPK",
			String.valueOf(ParamUtil.getLong(_renderRequest, "classPK")));
		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(getResourceClassNameId()));
		portletURL.setParameter(
			"eventName",
			ParamUtil.getString(_renderRequest, "eventName", "selectTemplate"));

		return portletURL.toString();
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public Set<String> getStorageTypes() {
		return _storageAdapterRegistry.getStorageTypes();
	}

	public CreationMenu getStructureCreationMenu() throws PortalException {
		if (!isShowAddStructureButton()) {
			return null;
		}

		PortletURL redirect = _renderResponse.createRenderURL();

		redirect.setParameter("mvcPath", "/view.jsp");
		redirect.setParameter(
			"groupId", String.valueOf(_ddmWebRequestHelper.getScopeGroupId()));

		PortletURL addTemplateURL = _renderResponse.createRenderURL();

		addTemplateURL.setParameter("mvcPath", "/edit_structure.jsp");
		addTemplateURL.setParameter("redirect", redirect.toString());
		addTemplateURL.setParameter(
			"groupId", String.valueOf(_ddmWebRequestHelper.getScopeGroupId()));

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					getCreationMenuDropdownItem(addTemplateURL, "add"));
			}
		};
	}

	public SearchContainer<DDMStructure> getStructureSearch() throws Exception {
		StructureSearch structureSearch = new StructureSearch(
			_renderRequest, getPortletURL());

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			DDMUtil.getStructureOrderByComparator(
				getOrderByCol(), getOrderByType());

		structureSearch.setOrderByCol(orderByCol);
		structureSearch.setOrderByComparator(orderByComparator);
		structureSearch.setOrderByType(orderByType);

		if (structureSearch.isSearch()) {
			structureSearch.setEmptyResultsMessage(
				LanguageUtil.format(
					_ddmWebRequestHelper.getRequest(), "no-x-were-found",
					getScopedStructureLabel(), false));
		}
		else {
			structureSearch.setEmptyResultsMessage(
				LanguageUtil.format(
					_ddmWebRequestHelper.getRequest(), "there-are-no-x",
					getScopedStructureLabel(), false));
		}

		setDDMStructureSearchResults(structureSearch);
		setDDMStructureSearchTotal(structureSearch);

		return structureSearch;
	}

	public String getStructureSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"tabs1",
			ParamUtil.getString(_renderRequest, "tabs1", "structures"));
		portletURL.setParameter(
			"groupId", String.valueOf(_ddmWebRequestHelper.getScopeGroupId()));

		return portletURL.toString();
	}

	public String getStructureSearchContainerId() {
		return "ddmStructures";
	}

	public CreationMenu getTemplateCreationMenu() throws PortalException {
		if (!isShowAddTemplateButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				if (getClassNameId() == PortalUtil.getClassNameId(
						DDMStructure.class)) {

					PortletURL addTemplateURL =
						_renderResponse.createRenderURL();

					addTemplateURL.setParameter(
						"mvcPath", "/edit_template.jsp");
					addTemplateURL.setParameter(
						"groupId",
						String.valueOf(_ddmWebRequestHelper.getScopeGroupId()));
					addTemplateURL.setParameter(
						"classNameId", String.valueOf(getClassNameId()));
					addTemplateURL.setParameter(
						"classPK", String.valueOf(getClassPK()));
					addTemplateURL.setParameter(
						"resourceClassNameId",
						String.valueOf(getResourceClassNameId()));
					addTemplateURL.setParameter("mode", getTemplateMode());

					String message = "add";

					if (containsAddTemplatePermission(
							DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {

						addTemplateURL.setParameter(
							"structureAvailableFields",
							_renderResponse.getNamespace() +
								"getAvailableFields");

						if (Validator.isNull(getTemplateTypeValue())) {
							message = "add-form-template";
						}

						addPrimaryDropdownItem(
							getCreationMenuDropdownItem(
								addTemplateURL, message));
					}

					if (containsAddTemplatePermission(
							DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {

						addTemplateURL.setParameter(
							"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

						if (Validator.isNull(getTemplateTypeValue())) {
							message = "add-display-template";
						}

						addPrimaryDropdownItem(
							getCreationMenuDropdownItem(
								addTemplateURL, message));
					}
				}
				else {
					List<TemplateHandler> templateHandlers =
						getTemplateHandlers();

					if (!templateHandlers.isEmpty()) {
						PortletURL addPortletDisplayTemplateURL =
							_renderResponse.createRenderURL();

						addPortletDisplayTemplateURL.setParameter(
							"mvcPath", "/edit_template.jsp");
						addPortletDisplayTemplateURL.setParameter(
							"groupId",
							String.valueOf(
								_ddmWebRequestHelper.getScopeGroupId()));
						addPortletDisplayTemplateURL.setParameter(
							"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

						for (TemplateHandler templateHandler :
								templateHandlers) {

							addPortletDisplayTemplateURL.setParameter(
								"classNameId",
								String.valueOf(
									PortalUtil.getClassNameId(
										templateHandler.getClassName())));
							addPortletDisplayTemplateURL.setParameter(
								"classPK", String.valueOf(0));
							addPortletDisplayTemplateURL.setParameter(
								"resourceClassNameId",
								String.valueOf(getResourceClassNameId()));

							addPrimaryDropdownItem(
								getCreationMenuDropdownItem(
									addPortletDisplayTemplateURL,
									templateHandler.getName(
										_ddmWebRequestHelper.getLocale())));
						}
					}
				}
			}
		};
	}

	public SearchContainer<DDMTemplate> getTemplateSearch() throws Exception {
		TemplateSearch templateSearch = new TemplateSearch(
			_renderRequest, getPortletURL());

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMTemplate> orderByComparator =
			DDMUtil.getTemplateOrderByComparator(
				getOrderByCol(), getOrderByType());

		templateSearch.setOrderByCol(orderByCol);
		templateSearch.setOrderByComparator(orderByComparator);
		templateSearch.setOrderByType(orderByType);

		if (templateSearch.isSearch()) {
			templateSearch.setEmptyResultsMessage("no-templates-were-found");
		}
		else {
			templateSearch.setEmptyResultsMessage("there-are-no-templates");
		}

		setDDMTemplateInstanceSearchResults(templateSearch);
		setDDMTemplateInstanceSearchTotal(templateSearch);

		return templateSearch;
	}

	public String getTemplateSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_template.jsp");
		portletURL.setParameter(
			"tabs1", ParamUtil.getString(_renderRequest, "tabs1", "templates"));
		portletURL.setParameter(
			"templateId",
			String.valueOf(ParamUtil.getLong(_renderRequest, "templateId")));
		portletURL.setParameter(
			"groupId", String.valueOf(_ddmWebRequestHelper.getScopeGroupId()));
		portletURL.setParameter(
			"classNameId", String.valueOf(getClassNameId()));
		portletURL.setParameter("classPK", String.valueOf(getClassPK()));
		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(getResourceClassNameId()));
		portletURL.setParameter(
			"eventName",
			ParamUtil.getString(_renderRequest, "eventName", "selectTemplate"));

		return portletURL.toString();
	}

	public String getTemplateSearchContainerId() {
		return "ddmTemplates";
	}

	public int getTotalItems(String context) throws Exception {
		SearchContainer<?> searchContainer;

		if (Objects.equals(
				context, DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE)) {

			searchContainer = getStructureSearch();
		}
		else {
			searchContainer = getTemplateSearch();
		}

		return searchContainer.getTotal();
	}

	public boolean isAutocompleteEnabled(String language) {
		return _ddmTemplateHelper.isAutocompleteEnabled(language);
	}

	public boolean isDisabledManagementBar(String context) throws Exception {
		if (hasResults(context) || isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddStructureButton() throws PortalException {
		DDMDisplay ddmDisplay = getDDMDisplay();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup()) &&
			DDMStructurePermission.containsAddStructurePermission(
				_ddmWebRequestHelper.getPermissionChecker(),
				_ddmWebRequestHelper.getScopeGroupId(),
				getStructureClassNameId())) {

			return true;
		}

		return false;
	}

	public boolean isShowAddTemplateButton() throws PortalException {
		DDMDisplay ddmDisplay = getDDMDisplay();

		long classNameId = getClassNameId();
		long resourceClassNameId = PortalUtil.getClassNameId(
			ddmDisplay.getStructureType());

		ThemeDisplay themeDisplay = _ddmWebRequestHelper.getThemeDisplay();

		if ((classNameId == 0) || (resourceClassNameId == 0)) {
			return ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup());
		}

		if (ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup()) &&
			DDMTemplatePermission.containsAddTemplatePermission(
				_ddmWebRequestHelper.getPermissionChecker(),
				_ddmWebRequestHelper.getScopeGroupId(), classNameId,
				resourceClassNameId)) {

			return true;
		}

		return false;
	}

	public String[] smallImageExtensions() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageExtensions();
	}

	public int smallImageMaxSize() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageMaxSize();
	}

	protected boolean containsAddPortletDisplayTemplatePermission(
			String resourceName)
		throws PortalException {

		if (getClassNameId() > 0) {
			return PortletPermissionUtil.contains(
				_ddmWebRequestHelper.getPermissionChecker(),
				_ddmWebRequestHelper.getLayout(), resourceName,
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE);
		}

		return PortletPermissionUtil.contains(
			_ddmWebRequestHelper.getPermissionChecker(),
			_ddmWebRequestHelper.getScopeGroupId(),
			_ddmWebRequestHelper.getLayout(), resourceName,
			ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE);
	}

	protected long getClassNameId() {
		return ParamUtil.getLong(_renderRequest, "classNameId");
	}

	protected long getClassPK() {
		return ParamUtil.getLong(_renderRequest, "classPK");
	}

	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItem(PortletURL url, String label) {

		return dropdownItem -> {
			dropdownItem.setHref(url);
			dropdownItem.setLabel(
				LanguageUtil.get(_ddmWebRequestHelper.getRequest(), label));
		};
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
								_ddmWebRequestHelper.getRequest(), "all"));
					});
			}
		};
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_ddmWebRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(getOrderByDropdownItem("modified-date"));
				add(getOrderByDropdownItem("id"));
			}
		};
	}

	protected PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String mvcPath = ParamUtil.getString(_renderRequest, "mvcPath");

		if (Validator.isNotNull(mvcPath)) {
			portletURL.setParameter("mvcPath", mvcPath);
		}

		String tabs1 = ParamUtil.getString(_renderRequest, "tabs1");

		if (Validator.isNotNull(tabs1)) {
			portletURL.setParameter("tabs1", tabs1);
		}

		long templateId = ParamUtil.getLong(_renderRequest, "templateId");

		if (templateId != 0) {
			portletURL.setParameter("templateId", String.valueOf(templateId));
		}

		long classNameId = getClassNameId();

		if (classNameId != 0) {
			portletURL.setParameter("classNameId", String.valueOf(classNameId));
		}

		if (classNameId != 0) {
			portletURL.setParameter("classPK", String.valueOf(getClassPK()));
		}

		long resourceClassNameId = getResourceClassNameId();

		if (resourceClassNameId != 0) {
			portletURL.setParameter(
				"resourceClassNameId", String.valueOf(resourceClassNameId));
		}

		String refererPortletName = getRefererPortletName();

		if (Validator.isNotNull(refererPortletName)) {
			portletURL.setParameter("refererPortletName", refererPortletName);
		}

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String eventName = ParamUtil.getString(_renderRequest, "eventName");

		if (Validator.isNotNull(eventName)) {
			portletURL.setParameter("eventName", eventName);
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

		boolean showAncestorScopes = showAncestorScopes();

		if (showAncestorScopes) {
			portletURL.setParameter(
				"showAncestorScopes", String.valueOf(showAncestorScopes));
		}

		return portletURL;
	}

	protected long getResourceClassNameId() {
		long resourceClassNameId = ParamUtil.getLong(
			_renderRequest, "resourceClassNameId");

		if (resourceClassNameId == 0) {
			resourceClassNameId = PortalUtil.getClassNameId(
				PortletDisplayTemplate.class);
		}

		return resourceClassNameId;
	}

	protected String getScopedStructureLabel() {
		String scopeTitle = ParamUtil.getString(_renderRequest, "scopeTitle");

		if (Validator.isNull(scopeTitle)) {
			DDMDisplay ddmDisplay = getDDMDisplay();

			return ddmDisplay.getTitle(_ddmWebRequestHelper.getLocale());
		}

		return scopeTitle;
	}

	protected long getSearchRestrictionClassNameId() {
		return ParamUtil.getLong(
			_ddmWebRequestHelper.getRequest(), "searchRestrictionClassNameId");
	}

	protected long getSearchRestrictionClassPK() {
		return ParamUtil.getLong(
			_ddmWebRequestHelper.getRequest(), "searchRestrictionClassPK");
	}

	protected long getStructureClassNameId() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return PortalUtil.getClassNameId(ddmDisplay.getStructureType());
	}

	protected long[] getTemplateClassNameIds() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getTemplateClassNameIds(getClassNameId());
	}

	protected List<TemplateHandler> getTemplateHandlers()
		throws PortalException {

		List<TemplateHandler> templateHandlers = new ArrayList<>();

		if (getClassNameId() > 0) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					getClassNameId());

			if (containsAddPortletDisplayTemplatePermission(
					templateHandler.getResourceName())) {

				templateHandlers.add(templateHandler);
			}
		}
		else {
			templateHandlers =
				PortletDisplayTemplateUtil.getPortletDisplayTemplateHandlers();

			Iterator<TemplateHandler> itr = templateHandlers.iterator();

			while (itr.hasNext()) {
				TemplateHandler templateHandler = itr.next();

				if (!containsAddPortletDisplayTemplatePermission(
						templateHandler.getResourceName())) {

					itr.remove();
				}
			}
		}

		ListUtil.sort(
			templateHandlers,
			new TemplateHandlerComparator(_ddmWebRequestHelper.getLocale()));

		return templateHandlers;
	}

	protected String getTemplateMode() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return ParamUtil.getString(
			_renderRequest, "mode", ddmDisplay.getTemplateMode());
	}

	protected String getTemplateTypeValue() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		String scopeTemplateType = ddmDisplay.getTemplateType();

		String templateTypeValue = StringPool.BLANK;

		if (scopeTemplateType.equals(
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {

			templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
		}
		else if (scopeTemplateType.equals(
					DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {

			templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_FORM;
		}

		return templateTypeValue;
	}

	protected boolean hasResults(String context) throws Exception {
		if (getTotalItems(context) > 0) {
			return true;
		}

		return false;
	}

	protected void setDDMStructureSearchResults(StructureSearch structureSearch)
		throws Exception {

		StructureSearchTerms searchTerms =
			(StructureSearchTerms)structureSearch.getSearchTerms();

		long[] groupIds = {
			PortalUtil.getScopeGroupId(
				_ddmWebRequestHelper.getRequest(), getRefererPortletName(),
				true)
		};

		if (showAncestorScopes()) {
			groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(groupIds);
		}

		List<DDMStructure> results = null;

		if (searchTerms.isSearchRestriction()) {
			results = _ddmStructureLinkLocalService.getStructureLinkStructures(
				getSearchRestrictionClassNameId(),
				getSearchRestrictionClassPK(), structureSearch.getStart(),
				structureSearch.getEnd());
		}
		else {
			results = _ddmStructureService.getStructures(
				_ddmWebRequestHelper.getCompanyId(), groupIds,
				getStructureClassNameId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), structureSearch.getStart(),
				structureSearch.getEnd(),
				structureSearch.getOrderByComparator());
		}

		structureSearch.setResults(results);
	}

	protected void setDDMStructureSearchTotal(StructureSearch structureSearch)
		throws Exception {

		StructureSearchTerms searchTerms =
			(StructureSearchTerms)structureSearch.getSearchTerms();

		long[] groupIds = {
			PortalUtil.getScopeGroupId(
				_ddmWebRequestHelper.getRequest(), getRefererPortletName(),
				true)
		};

		if (showAncestorScopes()) {
			groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(groupIds);
		}

		int total = 0;

		if (searchTerms.isSearchRestriction()) {
			total = _ddmStructureLinkLocalService.getStructureLinksCount(
				getSearchRestrictionClassNameId(),
				getSearchRestrictionClassPK());
		}
		else {
			total = _ddmStructureService.getStructuresCount(
				_ddmWebRequestHelper.getCompanyId(), groupIds,
				getStructureClassNameId(), searchTerms.getKeywords(),
				searchTerms.getStatus());
		}

		structureSearch.setTotal(total);
	}

	protected void setDDMTemplateInstanceSearchResults(
			TemplateSearch templateSearch)
		throws Exception {

		TemplateSearchTerms searchTerms =
			(TemplateSearchTerms)templateSearch.getSearchTerms();
		DDMDisplay ddmDisplay = getDDMDisplay();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = ddmDisplay.getTemplateGroupIds(
			themeDisplay, showAncestorScopes());

		List<DDMTemplate> results = _ddmTemplateService.search(
			_ddmWebRequestHelper.getCompanyId(), groupIds,
			getTemplateClassNameIds(), _getDDMTemplateClassPKs(),
			getResourceClassNameId(), searchTerms.getKeywords(),
			searchTerms.getType(), getTemplateMode(), searchTerms.getStatus(),
			templateSearch.getStart(), templateSearch.getEnd(),
			templateSearch.getOrderByComparator());

		templateSearch.setResults(results);
	}

	protected void setDDMTemplateInstanceSearchTotal(
			TemplateSearch templateSearch)
		throws Exception {

		TemplateSearchTerms searchTerms =
			(TemplateSearchTerms)templateSearch.getSearchTerms();
		DDMDisplay ddmDisplay = getDDMDisplay();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = ddmDisplay.getTemplateGroupIds(
			themeDisplay, showAncestorScopes());

		int total = _ddmTemplateService.searchCount(
			_ddmWebRequestHelper.getCompanyId(), groupIds,
			getTemplateClassNameIds(), _getDDMTemplateClassPKs(),
			getResourceClassNameId(), searchTerms.getKeywords(),
			searchTerms.getType(), getTemplateMode(), searchTerms.getStatus());

		templateSearch.setTotal(total);
	}

	protected boolean showAncestorScopes() {
		return ParamUtil.getBoolean(_renderRequest, "showAncestorScopes");
	}

	private long[] _getDDMTemplateClassPKs() {
		if (getClassPK() > 0) {
			return new long[] {getClassPK()};
		}

		return null;
	}

	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private final DDMStructureService _ddmStructureService;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private final DDMTemplateService _ddmTemplateService;
	private final DDMWebConfiguration _ddmWebConfiguration;
	private final DDMWebRequestHelper _ddmWebRequestHelper;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageAdapterRegistry _storageAdapterRegistry;

}