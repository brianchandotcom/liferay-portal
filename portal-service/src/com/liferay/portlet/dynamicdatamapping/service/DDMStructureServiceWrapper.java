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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.addStructure(userId, groupId, classNameId,
			nameMap, descriptionMap, xsd, serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.addStructure(groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, xsd,
			storageType, type, serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.copyStructure(structureId, nameMap,
			descriptionMap, serviceContext);
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

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructure(groupId, structureKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey,
		boolean includeGlobalStructures)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructure(groupId, structureKey,
			includeGlobalStructures);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructures(groupId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructures(groupIds);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.search(companyId, groupIds, classNameIds,
			keywords, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.search(companyId, groupIds, classNameIds,
			name, description, storageType, type, andOperator, start, end,
			orderByComparator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.searchCount(companyId, groupIds,
			classNameIds, keywords);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.searchCount(companyId, groupIds,
			classNameIds, name, description, storageType, type, andOperator);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.updateStructure(structureId,
			parentStructureId, nameMap, descriptionMap, xsd, serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, long parentStructureId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.updateStructure(groupId, parentStructureId,
			structureKey, nameMap, descriptionMap, xsd, serviceContext);
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