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

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for DLFileEntryType. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DLFileEntryTypeService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the document library file entry type remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DLFileEntryTypeServiceUtil} if injection and service tracking are not available.
	 */
	public DLFileEntryType addFileEntryType(
			long groupId, long dataDefinitionId, String fileEntryTypeKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public DLFileEntryType addFileEntryType(
			long groupId, String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException;

	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFileEntryTypes(
		long[] groupIds, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntryTypesCount(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> search(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope);

	public DLFileEntryType updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public void updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException;

}