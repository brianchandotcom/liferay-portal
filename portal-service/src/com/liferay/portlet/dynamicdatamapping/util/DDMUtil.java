/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMUtil {

	public static DDM getDDM() {
		PortalRuntimePermission.checkGetBeanProperty(DDMUtil.class);

		return _ddm;
	}

	public static DDMDisplay getDDMDisplay(ServiceContext serviceContext) {
		return getDDM().getDDMDisplay(serviceContext);
	}

	public static Serializable getDisplayFieldValue(
			ThemeDisplay themeDisplay, Serializable fieldValue, String type)
		throws Exception {

		return getDDM().getDisplayFieldValue(themeDisplay, fieldValue, type);
	}

	public static Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getDDM().getFields(
			ddmStructureId, ddmTemplateId, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getDDM().getFields(
			ddmStructureId, ddmTemplateId, fieldNamespace, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getDDM().getFields(ddmStructureId, serviceContext);
	}

	public static Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public static OrderByComparator getStructureOrderByComparator(
		String orderByCol, String orderByType) {

		return getDDM().getStructureOrderByComparator(orderByCol, orderByType);
	}

	public static OrderByComparator getTemplateOrderByComparator(
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