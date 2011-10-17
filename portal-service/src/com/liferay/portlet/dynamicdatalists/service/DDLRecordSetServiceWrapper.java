/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordSetService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordSetService
 * @generated
 */
public class DDLRecordSetServiceWrapper implements DDLRecordSetService,
	ServiceWrapper<DDLRecordSetService> {
	public DDLRecordSetServiceWrapper(DDLRecordSetService ddlRecordSetService) {
		_ddlRecordSetService = ddlRecordSetService;
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet addRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetService.addRecordSet(groupId, ddmStructureId,
			recordSetKey, nameMap, descriptionMap, minDisplayRows, scope,
			serviceContext);
	}

	public void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetService.deleteRecordSet(recordSetId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetService.getRecordSet(recordSetId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetService.updateMinDisplayRows(recordSetId,
			minDisplayRows, serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetService.updateRecordSet(recordSetId,
			ddmStructureId, nameMap, descriptionMap, minDisplayRows,
			serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetService.updateRecordSet(groupId, ddmStructureId,
			recordSetKey, nameMap, descriptionMap, minDisplayRows,
			serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DDLRecordSetService getWrappedDDLRecordSetService() {
		return _ddlRecordSetService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {
		_ddlRecordSetService = ddlRecordSetService;
	}

	public DDLRecordSetService getWrappedService() {
		return _ddlRecordSetService;
	}

	public void setWrappedService(DDLRecordSetService ddlRecordSetService) {
		_ddlRecordSetService = ddlRecordSetService;
	}

	private DDLRecordSetService _ddlRecordSetService;
}