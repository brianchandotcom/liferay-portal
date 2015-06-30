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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public interface DDM {

	public DDMDisplay getDDMDisplay(long classNameId) throws PortalException;

	public DDMForm getDDMForm(long classNameId, long classPK)
		throws PortalException;

	public DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException;

	public JSONArray getDDMFormFieldsJSONArray(
		DDMStructure ddmStructure, String script);

	public JSONArray getDDMFormFieldsJSONArray(
		DDMStructureVersion ddmStructureVersion, String script);

	public DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException;

	public DDMPermissionHandler getDDMPermissionHandler(long classNameId);

	public DDMFormLayout getDefaultDDMFormLayout(DDMForm ddmForm);

	public Serializable getDisplayFieldValue(
			ThemeDisplay themeDisplay, Serializable fieldValue, String type)
		throws Exception;

	public Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException;

	public Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException;

	public Fields getFields(long ddmStructureId, ServiceContext serviceContext)
		throws PortalException;

	public Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException;

	public String[] getFieldsDisplayValues(Field fieldsDisplayField)
		throws Exception;

	public Serializable getIndexedFieldValue(
			Serializable fieldValue, String type)
		throws Exception;

	public OrderByComparator<DDMStructure> getStructureOrderByComparator(
		String orderByCol, String orderByType);

	public OrderByComparator<DDMTemplate> getTemplateOrderByComparator(
		String orderByCol, String orderByType);

	public Fields mergeFields(Fields newFields, Fields existingFields);

}