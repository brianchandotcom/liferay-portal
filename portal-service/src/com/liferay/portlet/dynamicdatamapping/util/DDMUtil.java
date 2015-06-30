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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
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
public class DDMUtil {

	public static DDM getDDM() {
		PortalRuntimePermission.checkGetBeanProperty(DDMUtil.class);

		return _ddm;
	}

	public static DDMDisplay getDDMDisplay(long classNameId)
		throws PortalException {

		return getDDM().getDDMDisplay(classNameId);
	}

	public static DDMForm getDDMForm(long classNameId, long classPK)
		throws PortalException {

		return getDDM().getDDMForm(classNameId, classPK);
	}

	public static DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException {

		return getDDM().getDDMForm(portletRequest);
	}

	public static JSONArray getDDMFormFieldsJSONArray(
		DDMStructure ddmStructure, String script) {

		return getDDM().getDDMFormFieldsJSONArray(ddmStructure, script);
	}

	public static JSONArray getDDMFormFieldsJSONArray(
		DDMStructureVersion ddmStructureVersion, String script) {

		return getDDM().getDDMFormFieldsJSONArray(ddmStructureVersion, script);
	}

	public static DDMFormValues getDDMFormValues(
			DDMStructure ddmStructure, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return getDDM().getDDMFormValues(
			ddmStructure, fieldNamespace, serviceContext);
	}

	public static DDMPermissionHandler getDDMPermissionHandler(
		long classNameId) {

		return getDDM().getDDMPermissionHandler(classNameId);
	}

	public static DDMFormLayout getDefaultDDMFormLayout(DDMForm ddmForm) {
		return getDDM().getDefaultDDMFormLayout(ddmForm);
	}

	public static Serializable getDisplayFieldValue(
			ThemeDisplay themeDisplay, Serializable fieldValue, String type)
		throws Exception {

		return getDDM().getDisplayFieldValue(themeDisplay, fieldValue, type);
	}

	public static Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException {

		return getDDM().getFields(
			ddmStructureId, ddmTemplateId, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return getDDM().getFields(
			ddmStructureId, ddmTemplateId, fieldNamespace, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, ServiceContext serviceContext)
		throws PortalException {

		return getDDM().getFields(ddmStructureId, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return getDDM().getFields(
			ddmStructureId, fieldNamespace, serviceContext);
	}

	public static String[] getFieldsDisplayValues(Field fieldsDisplayField)
		throws Exception {

		return getDDM().getFieldsDisplayValues(fieldsDisplayField);
	}

	public static Serializable getIndexedFieldValue(
			Serializable fieldValue, String type)
		throws Exception {

		return getDDM().getIndexedFieldValue(fieldValue, type);
	}

	public static OrderByComparator<DDMStructure> getStructureOrderByComparator(
		String orderByCol, String orderByType) {

		return getDDM().getStructureOrderByComparator(orderByCol, orderByType);
	}

	public static OrderByComparator<DDMTemplate> getTemplateOrderByComparator(
		String orderByCol, String orderByType) {

		return getDDM().getTemplateOrderByComparator(orderByCol, orderByType);
	}

	public static Fields mergeFields(Fields newFields, Fields existingFields) {
		return getDDM().mergeFields(newFields, existingFields);
	}

	public void setDDM(DDM ddm) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddm = ddm;
	}

	private static DDM _ddm;

}