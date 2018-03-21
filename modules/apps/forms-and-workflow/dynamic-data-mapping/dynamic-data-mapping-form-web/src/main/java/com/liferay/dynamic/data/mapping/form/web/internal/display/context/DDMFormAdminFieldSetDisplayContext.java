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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FieldSetSearch;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FieldSetSearchTerms;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
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
public class DDMFormAdminFieldSetDisplayContext
	extends DDMFormAdminDisplayContext {

	public DDMFormAdminFieldSetDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDMFormWebConfiguration ddmFormWebConfiguration,
		DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
		DDMFormInstanceService formInstanceService,
		DDMFormFieldTypeServicesTracker formFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer formFieldTypesJSONSerializer,
		DDMFormRenderer formRenderer, DDMFormValuesFactory formValuesFactory,
		DDMFormValuesMerger formValuesMerger,
		DDMStructureLocalService structureLocalService,
		DDMStructureService structureService, JSONFactory jsonFactory,
		StorageEngine storageEngine,
		WorkflowEngineManager workflowEngineManager) {

		super(
			renderRequest, renderResponse,
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
			ddmFormWebConfiguration, formInstanceRecordLocalService,
			formInstanceService, formFieldTypeServicesTracker,
			formFieldTypesJSONSerializer, formRenderer, formValuesFactory,
			formValuesMerger, structureLocalService, structureService,
			jsonFactory, storageEngine, workflowEngineManager);
	}

	@Override
	public DDMStructure getDDMStructure() {
		if (_structure != null) {
			return _structure;
		}

		long structureId = ParamUtil.getLong(getRenderRequest(), "structureId");

		if (structureId > 0) {
			try {
				DDMStructureService structureService = getStructureService();

				_structure = structureService.getStructure(structureId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe);
				}
			}
		}

		return _structure;
	}

	@Override
	public String getFormDescription() throws PortalException {
		DDMStructure structure = getDDMStructure();

		if (structure != null) {
			return LocalizationUtil.getLocalization(
				structure.getDescription(), getDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	@Override
	public String getFormLocalizedDescription() throws PortalException {
		DDMStructure structure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (structure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap = structure.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormLocalizedName() throws PortalException {
		DDMStructure structure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (structure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = structure.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormName() throws PortalException {
		DDMStructure structure = getDDMStructure();

		if (structure != null) {
			return LocalizationUtil.getLocalization(
				structure.getName(), getDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	@Override
	public PortletURL getPortletURL() {
		RenderResponse renderResponse = getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "element-set");

		return portletURL;
	}

	@Override
	public SearchContainer<?> getSearch() {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		FieldSetSearch fieldSetSearch = new FieldSetSearch(
			getRenderRequest(), portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			getDDMStructureOrderByComparator(orderByCol, orderByType);

		fieldSetSearch.setOrderByCol(orderByCol);
		fieldSetSearch.setOrderByComparator(orderByComparator);
		fieldSetSearch.setOrderByType(orderByType);

		if (fieldSetSearch.isSearch()) {
			fieldSetSearch.setEmptyResultsMessage("no-element-sets-were-found");
		}
		else {
			fieldSetSearch.setEmptyResultsMessage("there-are-no-element-sets");
		}

		setFieldSetsSearchResults(fieldSetSearch);
		setFieldSetsSearchTotal(fieldSetSearch);

		return fieldSetSearch;
	}

	@Override
	public String getSearchContainerId() {
		return "structure";
	}

	@Override
	public boolean isShowAddButton() {
		return DDMFormPermission.contains(
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

	protected void setFieldSetsSearchResults(FieldSetSearch fieldSetSearch) {
		FieldSetSearchTerms fieldSetSearchTerms =
			(FieldSetSearchTerms)fieldSetSearch.getSearchTerms();

		List<DDMStructure> results = null;

		DDMStructureService structureService = getStructureService();

		if (fieldSetSearchTerms.isAdvancedSearch()) {
			results = structureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDMFormInstance.class),
				fieldSetSearchTerms.getName(),
				fieldSetSearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldSetSearchTerms.isAndOperator(), fieldSetSearch.getStart(),
				fieldSetSearch.getEnd(), fieldSetSearch.getOrderByComparator());
		}
		else {
			results = structureService.search(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDMFormInstance.class),
				fieldSetSearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY, fieldSetSearch.getStart(),
				fieldSetSearch.getEnd(), fieldSetSearch.getOrderByComparator());
		}

		fieldSetSearch.setResults(results);
	}

	protected void setFieldSetsSearchTotal(FieldSetSearch fieldSetSearch) {
		FieldSetSearchTerms fieldSetSearchTerms =
			(FieldSetSearchTerms)fieldSetSearch.getSearchTerms();

		int total = 0;

		DDMStructureService structureService = getStructureService();

		if (fieldSetSearchTerms.isAdvancedSearch()) {
			total = structureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDMFormInstance.class),
				fieldSetSearchTerms.getName(),
				fieldSetSearchTerms.getDescription(),
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY,
				fieldSetSearchTerms.isAndOperator());
		}
		else {
			total = structureService.searchCount(
				getCompanyId(), new long[] {getScopeGroupId()},
				PortalUtil.getClassNameId(DDMFormInstance.class),
				fieldSetSearchTerms.getKeywords(),
				DDMStructureConstants.TYPE_FRAGMENT,
				WorkflowConstants.STATUS_ANY);
		}

		fieldSetSearch.setTotal(total);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAdminFieldSetDisplayContext.class);

	private DDMStructure _structure;

}