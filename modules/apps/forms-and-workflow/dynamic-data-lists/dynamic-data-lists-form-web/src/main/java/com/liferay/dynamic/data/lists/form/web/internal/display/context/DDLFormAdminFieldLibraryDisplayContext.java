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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.form.web.internal.search.FieldLibrarySearch;
import com.liferay.dynamic.data.lists.form.web.internal.search.FieldLibrarySearchTerms;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormAdminFieldLibraryDisplayContext {

	public DDLFormAdminFieldLibraryDisplayContext(
		DDLFormAdminDisplayContext ddlFormAdminDisplayContext) {

		_ddlFormAdminDisplayContext = ddlFormAdminDisplayContext;
	}

	public DDLFormAdminDisplayContext getDDLFormAdminDisplayContext() {
		return _ddlFormAdminDisplayContext;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		long structureId = ParamUtil.getLong(getRenderRequest(), "structureId");

		if (structureId > 0) {
			try {
				DDMStructureService ddmStructureService =
					getDDMStructureService();

				_ddmStructure = ddmStructureService.getStructure(structureId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe);
				}
			}
		}

		return _ddmStructure;
	}

	public String getDisplayStyle() {
		return _ddlFormAdminDisplayContext.getDisplayStyle();
	}

	public String[] getDisplayViews() {
		return _ddlFormAdminDisplayContext.getDisplayViews();
	}

	public FieldLibrarySearch getFieldLibrarySearch() throws PortalException {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		FieldLibrarySearch fieldLibrarySearch = new FieldLibrarySearch(
			getRenderRequest(), portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			getDDMStructureOrderByComparator(orderByCol, orderByType);

		fieldLibrarySearch.setOrderByCol(orderByCol);
		fieldLibrarySearch.setOrderByComparator(orderByComparator);
		fieldLibrarySearch.setOrderByType(orderByType);

		if (fieldLibrarySearch.isSearch()) {
			fieldLibrarySearch.setEmptyResultsMessage(
				"no-custom-fields-were-found");
		}
		else {
			fieldLibrarySearch.setEmptyResultsMessage(
				"there-are-no-custom-fields");
		}

		setFieldLibrarySearchResults(fieldLibrarySearch);
		setFieldLibrarySearchTotal(fieldLibrarySearch);

		return fieldLibrarySearch;
	}

	public String getOrderByCol() {
		return _ddlFormAdminDisplayContext.getOrderByCol();
	}

	public String getOrderByType() {
		return _ddlFormAdminDisplayContext.getOrderByType();
	}

	public PortletURL getPortletURL() {
		RenderResponse renderResponse = getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "field-library");

		return portletURL;
	}

	public String getSerializedDDMForm() throws PortalException {
		String definition = ParamUtil.getString(
			getRenderRequest(), "definition");

		if (Validator.isNotNull(definition)) {
			return definition;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(getSiteDefaultLocale());
		ddmForm.setDefaultLocale(getSiteDefaultLocale());

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		DDMFormJSONSerializer ddmFormJSONSerializer =
			getDDMFormJSONSerializer();

		return ddmFormJSONSerializer.serialize(ddmForm);
	}

	public String getSerializedDDMFormLayout() throws PortalException {
		String layout = ParamUtil.getString(getRenderRequest(), "layout");

		if (Validator.isNotNull(layout)) {
			return layout;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		if (ddmStructure != null) {
			ddmFormLayout = ddmStructure.getDDMFormLayout();
		}

		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer =
			getDDMFormLayoutJSONSerializer();

		return ddmFormLayoutJSONSerializer.serialize(ddmFormLayout);
	}

	public boolean isShowAddButton() {
		return DDLPermission.contains(
			getPermissionChecker(), getScopeGroupId(), "ADD_STRUCTURE");
	}

	public boolean isShowSearch() throws PortalException {
		if (hasResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	protected long getCompanyId() {
		return _ddlFormAdminDisplayContext.getCompanyId();
	}

	protected DDMFormJSONSerializer getDDMFormJSONSerializer() {
		return _ddlFormAdminDisplayContext.getDDMFormJSONSerializer();
	}

	protected DDMFormLayoutJSONSerializer getDDMFormLayoutJSONSerializer() {
		return _ddlFormAdminDisplayContext.getDDMFormLayoutJSONSerializer();
	}

	protected OrderByComparator<DDMStructure> getDDMStructureOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMStructure> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new StructureCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new StructureNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected DDMStructureService getDDMStructureService() {
		return _ddlFormAdminDisplayContext.getDDMStructureService();
	}

	protected String getKeywords() {
		return ParamUtil.getString(getRenderRequest(), "keywords");
	}

	protected PermissionChecker getPermissionChecker() {
		return _ddlFormAdminDisplayContext.getPermissionChecker();
	}

	protected RenderRequest getRenderRequest() {
		return _ddlFormAdminDisplayContext.getRenderRequest();
	}

	protected RenderResponse getRenderResponse() {
		return _ddlFormAdminDisplayContext.getRenderResponse();
	}

	protected long getScopeGroupId() {
		return _ddlFormAdminDisplayContext.getScopeGroupId();
	}

	protected Locale getSiteDefaultLocale() {
		return _ddlFormAdminDisplayContext.getSiteDefaultLocale();
	}

	protected int getTotal() throws PortalException {
		FieldLibrarySearch fieldLibrarySearch = getFieldLibrarySearch();

		return fieldLibrarySearch.getTotal();
	}

	protected boolean hasResults() throws PortalException {
		if (getTotal() > 0) {
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

	protected void setFieldLibrarySearchResults(
		FieldLibrarySearch fieldLibrarySearch) {

		FieldLibrarySearchTerms fieldLibrarySearchTerms =
			(FieldLibrarySearchTerms)fieldLibrarySearch.getSearchTerms();

		List<DDMStructure> results = null;

		DDMStructureService ddmStructureService = getDDMStructureService();

		if (fieldLibrarySearchTerms.isAdvancedSearch()) {
			results = ddmStructureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getName(),
				fieldLibrarySearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldLibrarySearchTerms.isAndOperator(),
				fieldLibrarySearch.getStart(), fieldLibrarySearch.getEnd(),
				fieldLibrarySearch.getOrderByComparator());
		}
		else {
			results = ddmStructureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY, fieldLibrarySearch.getStart(),
				fieldLibrarySearch.getEnd(),
				fieldLibrarySearch.getOrderByComparator());
		}

		fieldLibrarySearch.setResults(results);
	}

	protected void setFieldLibrarySearchTotal(
		FieldLibrarySearch fieldLibrarySearch) {

		FieldLibrarySearchTerms fieldLibrarySearchTerms =
			(FieldLibrarySearchTerms)fieldLibrarySearch.getSearchTerms();

		DDMStructureService ddmStructureService = getDDMStructureService();

		int total = 0;

		if (fieldLibrarySearchTerms.isAdvancedSearch()) {
			total = ddmStructureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getName(),
				fieldLibrarySearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldLibrarySearchTerms.isAndOperator());
		}
		else {
			total = ddmStructureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY);
		}

		fieldLibrarySearch.setTotal(total);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormAdminFieldLibraryDisplayContext.class);

	private final DDLFormAdminDisplayContext _ddlFormAdminDisplayContext;
	private DDMStructure _ddmStructure;

}