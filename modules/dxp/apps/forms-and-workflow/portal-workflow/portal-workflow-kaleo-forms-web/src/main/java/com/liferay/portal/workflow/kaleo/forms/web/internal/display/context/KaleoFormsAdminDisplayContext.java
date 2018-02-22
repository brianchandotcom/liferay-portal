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

package com.liferay.portal.workflow.kaleo.forms.web.internal.display.context;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessCreateDateComparator;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.forms.web.configuration.KaleoFormsWebConfiguration;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.util.KaleoFormsAdminRequestHelper;
import com.liferay.portal.workflow.kaleo.forms.web.internal.search.KaleoDefinitionVersionActivePredicateFilter;
import com.liferay.portal.workflow.kaleo.forms.web.internal.search.KaleoProcessSearch;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class KaleoFormsAdminDisplayContext {

	public KaleoFormsAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMDisplayRegistry ddmDisplayRegistry,
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		KaleoFormsWebConfiguration kaleoFormsWebConfiguration,
		StorageEngine storageEngine) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmDisplayRegistry = ddmDisplayRegistry;
		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_kaleoFormsWebConfiguration = kaleoFormsWebConfiguration;
		_storageEngine = storageEngine;

		_kaleoFormsAdminRequestHelper = new KaleoFormsAdminRequestHelper(
			renderRequest);
	}

	public boolean changeableDefaultLanguage() {
		return _kaleoFormsWebConfiguration.changeableDefaultLanguage();
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(
			_kaleoFormsAdminRequestHelper.getPortletId());
	}

	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException {

		return _storageEngine.getDDMFormValues(ddmStorageId);
	}

	public String getDisplayStyle() {
		if (_kaleoFormsAdminDisplayStyle != null) {
			return _kaleoFormsAdminDisplayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		_kaleoFormsAdminDisplayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle");

		if (Validator.isNull(_kaleoFormsAdminDisplayStyle)) {
			_kaleoFormsAdminDisplayStyle = portalPreferences.getValue(
				KaleoFormsPortletKeys.KALEO_FORMS_ADMIN, "display-style",
				_kaleoFormsWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(
					getDisplayViews(), _kaleoFormsAdminDisplayStyle)) {

			portalPreferences.setValue(
				KaleoFormsPortletKeys.KALEO_FORMS_ADMIN, "display-style",
				_kaleoFormsAdminDisplayStyle);
		}

		if (!ArrayUtil.contains(
				getDisplayViews(), _kaleoFormsAdminDisplayStyle)) {

			_kaleoFormsAdminDisplayStyle = getDisplayViews()[0];
		}

		return _kaleoFormsAdminDisplayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public OrderByComparator<KaleoProcess> getKaleoProcessOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<KaleoProcess> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new KaleoProcessCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new KaleoProcessModifiedDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public KaleoProcessSearch getKaleoProcessSearch(PortletURL portletURL) {
		KaleoProcessSearch kaleoProcessSearch = new KaleoProcessSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<KaleoProcess> orderByComparator =
			getKaleoProcessOrderByComparator(orderByCol, orderByType);

		kaleoProcessSearch.setOrderByCol(orderByCol);
		kaleoProcessSearch.setOrderByComparator(orderByComparator);
		kaleoProcessSearch.setOrderByType(orderByType);

		return kaleoProcessSearch;
	}

	public String getOrderByCol() {
		String orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return orderByCol;
	}

	public String getOrderByType() {
		String orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");

		return portletURL;
	}

	public List<KaleoDefinitionVersion> getSearchContainerResults(
			SearchContainer<KaleoDefinitionVersion> searchContainer, int status)
		throws PortalException {

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoFormsAdminRequestHelper.getCompanyId(), null,
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicateFilter(status));

		searchContainer.setTotal(kaleoDefinitionVersions.size());

		if (kaleoDefinitionVersions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			kaleoDefinitionVersions = ListUtil.subList(
				kaleoDefinitionVersions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		return kaleoDefinitionVersions;
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private String _kaleoFormsAdminDisplayStyle;
	private final KaleoFormsAdminRequestHelper _kaleoFormsAdminRequestHelper;
	private final KaleoFormsWebConfiguration _kaleoFormsWebConfiguration;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;

}