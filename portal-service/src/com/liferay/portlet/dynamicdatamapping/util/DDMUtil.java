/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 */
public class DDMUtil {

	public static DDM getDDM() {
		return _ddm;
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

	public static String getFileUploadPath(BaseModel<?> baseModel) {
		return getDDM().getFileUploadPath(baseModel);
	}

	public static void sendFieldFile(
			HttpServletRequest request, HttpServletResponse response,
			Field field)
		throws Exception {

		getDDM().sendFieldFile(request, response, field);
	}

	public static String uploadFieldFile(
			long structureId, long storageId, BaseModel<?> baseModel,
			String fieldName, ServiceContext serviceContext)
		throws Exception {

		return getDDM().uploadFieldFile(
			structureId, storageId, baseModel, fieldName, serviceContext);
	}

	public static String uploadFieldFile(
			long structureId, long storageId, BaseModel<?> baseModel,
			String fieldName, String fieldNamespace,
			ServiceContext serviceContext)
		throws Exception {

		return getDDM().uploadFieldFile(
			structureId, storageId, baseModel, fieldName, fieldNamespace,
			serviceContext);
	}

	public void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	private static DDM _ddm;

}