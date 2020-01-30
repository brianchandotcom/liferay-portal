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
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.search.StructureSearch;
import com.liferay.document.library.web.internal.search.StructureSearchTerms;
import com.liferay.document.library.web.internal.security.permission.resource.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

/**
 * @author Rafael Praxedes
 */
public class DLViewFileEntryMetadataSetsDisplayContext {

	public DLViewFileEntryMetadataSetsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DDMStructureLinkLocalService ddmStructureLinkLocalService,
		DDMStructureService ddmStructureService, Portal portal) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
		_ddmStructureService = ddmStructureService;

		_portal = portal;

		_dlRequestHelper = new DLRequestHelper(
			_portal.getHttpServletRequest(liferayPortletRequest));
	}

	public PortletURL getCopyDDMStructurePortletURL(DDMStructure ddmStructure) {
		RenderURL renderURL = _liferayPortletResponse.createRenderURL();

		renderURL.setParameter(
			"mvcPath", "/document_library/ddm/copy_ddm_structure.jsp");
		renderURL.setParameter(
			"redirect",
			String.valueOf(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse)));
		renderURL.setParameter(
			"ddmStructureId", String.valueOf(ddmStructure.getStructureId()));

		return renderURL;
	}

	public PortletURL getDeleteDDMStructurePortletURL(
		DDMStructure ddmStructure) {

		ActionURL actionURL = _liferayPortletResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/document_library/ddm/delete_ddm_structure");

		actionURL.setParameter("navigation", "file_entry_metadata_sets");
		actionURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		actionURL.setParameter(
			"ddmStructureId", String.valueOf(ddmStructure.getStructureId()));

		return actionURL;
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

	public boolean isShowAddStructureButton() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (!group.hasLocalOrRemoteStagingGroup() ||
			(group.isStagingGroup() &&
			 DDMStructurePermission.containsAddDDMStructurePermission(
				 _dlRequestHelper.getPermissionChecker(),
				 _dlRequestHelper.getScopeGroupId(),
				 getStructureClassNameId()))) {

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
			resourceClassNameId = _portal.getClassNameId(
				PortletDisplayTemplate.class);
		}

		return resourceClassNameId;
	}

	protected String getScopedStructureLabel() {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				_dlRequestHelper.getLocale(), getClass()),
			"documents-and-media");
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
		return _portal.getClassNameId(DLFileEntryMetadata.class.getName());
	}

	protected void setDDMStructureSearchResults(StructureSearch structureSearch)
		throws Exception {

		StructureSearchTerms searchTerms =
			(StructureSearchTerms)structureSearch.getSearchTerms();

		long[] groupIds = {
			_portal.getScopeGroupId(
				_dlRequestHelper.getRequest(), DLPortletKeys.DOCUMENT_LIBRARY,
				true)
		};

		groupIds = _portal.getCurrentAndAncestorSiteGroupIds(groupIds);

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
			_portal.getScopeGroupId(
				_dlRequestHelper.getRequest(), DLPortletKeys.DOCUMENT_LIBRARY,
				true)
		};

		groupIds = _portal.getCurrentAndAncestorSiteGroupIds(groupIds);

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

	private final DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private final DDMStructureService _ddmStructureService;
	private final DLRequestHelper _dlRequestHelper;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;

}