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

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.lists.form.web.internal.search.FieldSetSearch;
import com.liferay.dynamic.data.lists.form.web.internal.search.FieldSetSearchTerms;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormAdminFieldSetDisplayContext
	extends DDLFormAdminDisplayContext {

	public DDLFormAdminFieldSetDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetService ddlRecordSetService,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureService ddmStructureService, JSONFactory jsonFactory,
		StorageEngine storageEngine,
		WorkflowEngineManager workflowEngineManager) {

		super(
			renderRequest, renderResponse,
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
			ddlFormWebConfiguration, ddlRecordLocalService, ddlRecordSetService,
			ddmFormFieldTypeServicesTracker, ddmFormFieldTypesJSONSerializer,
			ddmFormRenderer, ddmFormValuesFactory, ddmFormValuesMerger,
			ddmStructureLocalService, ddmStructureService, jsonFactory,
			storageEngine, workflowEngineManager);
	}

	@Override
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

	@Override
	public String getFormDescription() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			ThemeDisplay themeDisplay =
				ddlFormAdminRequestHelper.getThemeDisplay();

			return LocalizationUtil.getLocalization(
				ddmStructure.getDescription(), themeDisplay.getLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	@Override
	public String getFormLocalizedDescription() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (ddmStructure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap =
				ddmStructure.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormLocalizedName() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (ddmStructure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = ddmStructure.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormName() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			ThemeDisplay themeDisplay =
				ddlFormAdminRequestHelper.getThemeDisplay();

			return LocalizationUtil.getLocalization(
				ddmStructure.getName(), themeDisplay.getLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	@Override
	public PortletURL getPortletURL() {
		RenderResponse renderResponse = getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "field-set");

		return portletURL;
	}

	@Override
	public SearchContainer<?> getSearch() {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		FieldSetSearch fieldSetsSearch = new FieldSetSearch(
			getRenderRequest(), portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			getDDMStructureOrderByComparator(orderByCol, orderByType);

		fieldSetsSearch.setOrderByCol(orderByCol);
		fieldSetsSearch.setOrderByComparator(orderByComparator);
		fieldSetsSearch.setOrderByType(orderByType);

		if (fieldSetsSearch.isSearch()) {
			fieldSetsSearch.setEmptyResultsMessage("no-field-sets-were-found");
		}
		else {
			fieldSetsSearch.setEmptyResultsMessage("there-are-no-field-sets");
		}

		setFieldSetsSearchResults(fieldSetsSearch);
		setFieldSetsSearchTotal(fieldSetsSearch);

		return fieldSetsSearch;
	}

	@Override
	public String getSearchContainerId() {
		return "ddmStructure";
	}

	@Override
	public boolean isShowAddButton() {
		return DDLPermission.contains(
			getPermissionChecker(), getScopeGroupId(), "ADD_STRUCTURE");
	}

	@Override
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

	protected void setFieldSetsSearchResults(FieldSetSearch fieldSetsSearch) {
		FieldSetSearchTerms fieldSetsSearchTerms =
			(FieldSetSearchTerms)fieldSetsSearch.getSearchTerms();

		List<DDMStructure> results = null;

		DDMStructureService ddmStructureService = getDDMStructureService();

		if (fieldSetsSearchTerms.isAdvancedSearch()) {
			results = ddmStructureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldSetsSearchTerms.getName(),
				fieldSetsSearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldSetsSearchTerms.isAndOperator(),
				fieldSetsSearch.getStart(), fieldSetsSearch.getEnd(),
				fieldSetsSearch.getOrderByComparator());
		}
		else {
			results = ddmStructureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldSetsSearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY, fieldSetsSearch.getStart(),
				fieldSetsSearch.getEnd(),
				fieldSetsSearch.getOrderByComparator());
		}

		fieldSetsSearch.setResults(results);
	}

	protected void setFieldSetsSearchTotal(FieldSetSearch fieldSetsSearch) {
		FieldSetSearchTerms fieldSetsSearchTerms =
			(FieldSetSearchTerms)fieldSetsSearch.getSearchTerms();

		int total = 0;

		DDMStructureService ddmStructureService = getDDMStructureService();

		if (fieldSetsSearchTerms.isAdvancedSearch()) {
			total = ddmStructureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldSetsSearchTerms.getName(),
				fieldSetsSearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldSetsSearchTerms.isAndOperator());
		}
		else {
			total = ddmStructureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDLRecordSet.class),
				fieldSetsSearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY);
		}

		fieldSetsSearch.setTotal(total);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormAdminFieldSetDisplayContext.class);

	private DDMStructure _ddmStructure;

}