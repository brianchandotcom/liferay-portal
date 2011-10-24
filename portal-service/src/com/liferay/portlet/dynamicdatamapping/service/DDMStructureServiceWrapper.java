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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureService
 * @generated
 */
public class DDMStructureServiceWrapper implements DDMStructureService,
	ServiceWrapper<DDMStructureService> {
	public DDMStructureServiceWrapper(DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.addStructure(groupId, classNameId,
			structureKey, nameMap, descriptionMap, xsd, storageType,
			serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.copyStructure(structureId, serviceContext);
	}

	public void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureService.deleteStructure(structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.fetchStructure(groupId, structureKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructure(structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.updateStructure(structureId, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.updateStructure(groupId, structureKey,
			nameMap, descriptionMap, xsd, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DDMStructureService getWrappedDDMStructureService() {
		return _ddmStructureService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDDMStructureService(
		DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	public DDMStructureService getWrappedService() {
		return _ddmStructureService;
	}

	public void setWrappedService(DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	private DDMStructureService _ddmStructureService;
}