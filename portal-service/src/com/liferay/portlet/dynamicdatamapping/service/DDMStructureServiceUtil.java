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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m structure remote service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureServiceImpl
 * @generated
 */
public class DDMStructureServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructure(groupId, classNameId, structureKey, nameMap,
			descriptionMap, xsd, storageType, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().copyStructure(structureId, serviceContext);
	}

	public static void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructure(structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchStructure(groupId, structureKey);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructure(structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructure(structureId, nameMap, descriptionMap, xsd,
			serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructure(groupId, structureKey, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	public static DDMStructureService getService() {
		if (_service == null) {
			_service = (DDMStructureService)PortalBeanLocatorUtil.locate(DDMStructureService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureService.class);
		}

		return _service;
	}

	public void setService(DDMStructureService service) {
		MethodCache.remove(DDMStructureService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureService.class);
	}

	private static DDMStructureService _service;
}