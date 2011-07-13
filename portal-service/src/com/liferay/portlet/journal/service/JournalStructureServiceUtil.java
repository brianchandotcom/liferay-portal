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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the journal structure remote service. This utility wraps {@link com.liferay.portlet.journal.service.impl.JournalStructureServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalStructureService
 * @see com.liferay.portlet.journal.service.base.JournalStructureServiceBaseImpl
 * @see com.liferay.portlet.journal.service.impl.JournalStructureServiceImpl
 * @generated
 */
public class JournalStructureServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.journal.service.impl.JournalStructureServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		long groupId, java.lang.String structureId, boolean autoStructureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructure(groupId, structureId, autoStructureId,
			parentStructureId, name, description, xsd, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalStructure copyStructure(
		long groupId, java.lang.String oldStructureId,
		java.lang.String newStructureId, boolean autoStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyStructure(groupId, oldStructureId, newStructureId,
			autoStructureId);
	}

	public static void deleteStructure(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructure(groupId, structureId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructure(groupId, structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, keywords, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long[] groupIds, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, structureId, name, description,
			andOperator, start, end, obc);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, groupIds, keywords);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, structureId, name,
			description, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId,
		java.lang.String parentStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructure(groupId, structureId, parentStructureId,
			name, description, xsd, serviceContext);
	}

	public static JournalStructureService getService() {
		if (_service == null) {
			_service = (JournalStructureService)PortalBeanLocatorUtil.locate(JournalStructureService.class.getName());

			ReferenceRegistry.registerReference(JournalStructureServiceUtil.class,
				"_service");
			MethodCache.remove(JournalStructureService.class);
		}

		return _service;
	}

	public void setService(JournalStructureService service) {
		MethodCache.remove(JournalStructureService.class);

		_service = service;

		ReferenceRegistry.registerReference(JournalStructureServiceUtil.class,
			"_service");
		MethodCache.remove(JournalStructureService.class);
	}

	private static JournalStructureService _service;
}