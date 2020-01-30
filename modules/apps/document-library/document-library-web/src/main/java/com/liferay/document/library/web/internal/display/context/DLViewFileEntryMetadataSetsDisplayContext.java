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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.search.StructureSearch;
import com.liferay.document.library.web.internal.search.StructureSearchTerms;
import com.liferay.document.library.web.internal.security.permission.resource.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

/**
 * @author Rafael Praxedes
 */
public class DLViewFileEntryMetadataSetsDisplayContext {

	public DLViewFileEntryMetadataSetsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DDMDisplayRegistry ddmDisplayRegistry,
		DDMStructureLinkLocalService ddmStructureLinkLocalService,
		DDMStructureService ddmStructureService) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
		_ddmStructureService = ddmStructureService;

		_dlRequestHelper = new DLRequestHelper(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _liferayPortletResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(
			DLPortletKeys.DOCUMENT_LIBRARY);
	}

	public PortletURL getEditDDMStructurePortletURL(DDMStructure ddmStructure) {
		RenderURL renderURL = _liferayPortletResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/document_library/ddm/edit_ddm_structure");
		renderURL.setParameter(
			"redirect",
			String.valueOf(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse)));
		renderURL.setParameter(
			"ddmStructureId", String.valueOf(ddmStructure.getStructureId()));

		return renderURL;
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_liferayPortletRequest);

		String orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol");

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
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_liferayPortletRequest);

		String orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType");

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

	public CreationMenu getSelectStructureCreationMenu()
		throws PortalException {

		if (!isShowAddStructureButton()) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/document_library/ddm/edit_ddm_structure",
							"redirect", themeDisplay.getURLCurrent(), "groupId",
							String.valueOf(_dlRequestHelper.getScopeGroupId()));
						dropdownItem.setLabel(
							LanguageUtil.get(
								_dlRequestHelper.getRequest(), "add"));
					});
			}
		};
	}

	public SearchContainer<DDMStructure> getStructureSearch() throws Exception {
		StructureSearch structureSearch = new StructureSearch(
			_liferayPortletRequest, getPortletURL());

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
					_dlRequestHelper.getRequest(), "no-x-were-found",
					getScopedStructureLabel(), false));
		}
		else {
			structureSearch.setEmptyResultsMessage(
				LanguageUtil.format(
					_dlRequestHelper.getRequest(), "there-are-no-x",
					getScopedStructureLabel(), false));
		}

		setDDMStructureSearchResults(structureSearch);
		setDDMStructureSearchTotal(structureSearch);

		return structureSearch;
	}

	public String getStructureSearchActionURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"tabs1",
			ParamUtil.getString(_liferayPortletRequest, "tabs1", "structures"));
		portletURL.setParameter(
			"groupId", String.valueOf(_dlRequestHelper.getScopeGroupId()));

		return portletURL.toString();
	}

	public int getTotalItems() throws Exception {
		SearchContainer<?> searchContainer = getStructureSearch();

		return searchContainer.getTotal();
	}

	public boolean isDisabledManagementBar() throws Exception {
		if (hasResults() || isSearch()) {
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup()) &&
			DDMStructurePermission.containsAddDDMStructurePermission(
				_dlRequestHelper.getPermissionChecker(),
				_dlRequestHelper.getScopeGroupId(),
				getStructureClassNameId())) {

			return true;
		}

		return false;
	}

	protected long getClassNameId() {
		return ParamUtil.getLong(_liferayPortletRequest, "classNameId");
	}

	protected long getClassPK() {
		return ParamUtil.getLong(_liferayPortletRequest, "classPK");
	}

	protected String getKeywords() {
		return ParamUtil.getString(_liferayPortletRequest, "keywords");
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(_dlRequestHelper.getRequest(), orderByCol));
		};
	}

	protected PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String mvcPath = ParamUtil.getString(_liferayPortletRequest, "mvcPath");

		if (Validator.isNotNull(mvcPath)) {
			portletURL.setParameter("mvcPath", mvcPath);
		}

		String tabs1 = ParamUtil.getString(_liferayPortletRequest, "tabs1");

		if (Validator.isNotNull(tabs1)) {
			portletURL.setParameter("tabs1", tabs1);
		}

		long templateId = ParamUtil.getLong(
			_liferayPortletRequest, "templateId");

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

		String delta = ParamUtil.getString(_liferayPortletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String eventName = ParamUtil.getString(
			_liferayPortletRequest, "eventName");

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

		return portletURL;
	}

	protected long getResourceClassNameId() {
		long resourceClassNameId = ParamUtil.getLong(
			_liferayPortletRequest, "resourceClassNameId");

		if (resourceClassNameId == 0) {
			resourceClassNameId = PortalUtil.getClassNameId(
				PortletDisplayTemplate.class);
		}

		return resourceClassNameId;
	}

	protected String getScopedStructureLabel() {
		String scopeTitle = ParamUtil.getString(
			_liferayPortletRequest, "scopeTitle");

		if (Validator.isNull(scopeTitle)) {
			DDMDisplay ddmDisplay = getDDMDisplay();

			return ddmDisplay.getTitle(_dlRequestHelper.getLocale());
		}

		return scopeTitle;
	}

	protected long getSearchRestrictionClassNameId() {
		return ParamUtil.getLong(
			_dlRequestHelper.getRequest(), "searchRestrictionClassNameId");
	}

	protected long getSearchRestrictionClassPK() {
		return ParamUtil.getLong(
			_dlRequestHelper.getRequest(), "searchRestrictionClassPK");
	}

	protected long getStructureClassNameId() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return PortalUtil.getClassNameId(ddmDisplay.getStructureType());
	}

	protected boolean hasResults() throws Exception {
		if (getTotalItems() > 0) {
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
				_dlRequestHelper.getRequest(), DLPortletKeys.DOCUMENT_LIBRARY,
				true)
		};

		groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(groupIds);

		List<DDMStructure> results = null;

		if (searchTerms.isSearchRestriction()) {
			results = _ddmStructureLinkLocalService.getStructureLinkStructures(
				getSearchRestrictionClassNameId(),
				getSearchRestrictionClassPK(), structureSearch.getStart(),
				structureSearch.getEnd());
		}
		else {
			results = _ddmStructureService.getStructures(
				_dlRequestHelper.getCompanyId(), groupIds,
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
				_dlRequestHelper.getRequest(), DLPortletKeys.DOCUMENT_LIBRARY,
				true)
		};

		groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(groupIds);

		int total = 0;

		if (searchTerms.isSearchRestriction()) {
			total = _ddmStructureLinkLocalService.getStructureLinksCount(
				getSearchRestrictionClassNameId(),
				getSearchRestrictionClassPK());
		}
		else {
			total = _ddmStructureService.getStructuresCount(
				_dlRequestHelper.getCompanyId(), groupIds,
				getStructureClassNameId(), searchTerms.getKeywords(),
				searchTerms.getStatus());
		}

		structureSearch.setTotal(total);
	}

	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private final DDMStructureService _ddmStructureService;
	private final DLRequestHelper _dlRequestHelper;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}