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

import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.form.web.internal.search.FieldLibrarySearch;
import com.liferay.dynamic.data.lists.form.web.internal.search.FieldLibrarySearchTerms;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormAdminFieldLibraryDisplayContext {

	public DDLFormAdminFieldLibraryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDLFormAdminRequestHelper ddlFormAdminRequestHelper,
		DDMStructureService ddmStructureService, String displayStyle,
		String orderByCol, String orderByType, String keywords) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlFormAdminRequestHelper = ddlFormAdminRequestHelper;
		_ddmStructureService = ddmStructureService;
		_displayStyle = displayStyle;
		_orderByCol = orderByCol;
		_orderByType = orderByType;
		_keywords = keywords;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public FieldLibrarySearch getFieldLibrarySearch() throws PortalException {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", _displayStyle);

		FieldLibrarySearch fieldLibrarySearch = new FieldLibrarySearch(
			_renderRequest, portletURL);

		OrderByComparator<DDMStructure> orderByComparator =
			getDDMStructureOrderByComparator(_orderByCol, _orderByType);

		fieldLibrarySearch.setOrderByCol(_orderByCol);
		fieldLibrarySearch.setOrderByComparator(orderByComparator);
		fieldLibrarySearch.setOrderByType(_orderByType);

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

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddlFormAdminRequestHelper.getScopeGroupId()));
		portletURL.setParameter("tabs1", "field-library");

		return portletURL;
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
		if (Validator.isNotNull(_keywords)) {
			return true;
		}

		return false;
	}

	protected void setFieldLibrarySearchResults(
		FieldLibrarySearch fieldLibrarySearch) {

		FieldLibrarySearchTerms fieldLibrarySearchTerms =
			(FieldLibrarySearchTerms)fieldLibrarySearch.getSearchTerms();

		List<DDMStructure> results = null;

		if (fieldLibrarySearchTerms.isAdvancedSearch()) {
			results = _ddmStructureService.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				new long[] {_ddlFormAdminRequestHelper.getScopeGroupId()},
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
			results = _ddmStructureService.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				new long[] {_ddlFormAdminRequestHelper.getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getKeywords(),
				fieldLibrarySearchTerms.getKeywords(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY, false,
				fieldLibrarySearch.getStart(), fieldLibrarySearch.getEnd(),
				fieldLibrarySearch.getOrderByComparator());
		}

		fieldLibrarySearch.setResults(results);
	}

	protected void setFieldLibrarySearchTotal(
		FieldLibrarySearch fieldLibrarySearch) {

		FieldLibrarySearchTerms fieldLibrarySearchTerms =
			(FieldLibrarySearchTerms)fieldLibrarySearch.getSearchTerms();

		int total = 0;

		if (fieldLibrarySearchTerms.isAdvancedSearch()) {
			total = _ddmStructureService.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				new long[] {_ddlFormAdminRequestHelper.getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getName(),
				fieldLibrarySearchTerms.getDescription(),
				StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY,
				fieldLibrarySearchTerms.isAndOperator());
		}
		else {
			total = _ddmStructureService.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				new long[] {_ddlFormAdminRequestHelper.getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldLibrarySearchTerms.getKeywords(),
				fieldLibrarySearchTerms.getKeywords(),
				StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY, false);
		}

		fieldLibrarySearch.setTotal(total);
	}

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private final DDMStructureService _ddmStructureService;
	private final String _displayStyle;
	private final String _keywords;
	private final String _orderByCol;
	private final String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}