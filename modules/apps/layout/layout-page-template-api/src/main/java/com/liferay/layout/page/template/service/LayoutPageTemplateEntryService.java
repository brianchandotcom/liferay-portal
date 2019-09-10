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

package com.liferay.layout.page.template.service;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for LayoutPageTemplateEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface LayoutPageTemplateEntryService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateEntryServiceUtil} to access the layout page template entry remote service. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int type, int status, ServiceContext serviceContext)
		throws PortalException;

	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int status, long classNameId, long classTypeId,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, String, int, int,
	 ServiceContext)} ()}
	 */
	@Deprecated
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int type, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, String, int, int,
	 ServiceContext)} ()}
	 */
	@Deprecated
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateCollectionId, String name,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds)
		throws PortalException;

	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId, long classTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
			long groupId, String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntryByUuidAndGroupId(
		String uuid, long groupId);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId, int status);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId, String name);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, int type, boolean defaultTemplate);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, String name, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntriesByType(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(long groupId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, int type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, int type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, String name,
		int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, String name, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(
		long groupId, String name, int type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCountByType(
		long groupId, long layoutPageTemplateCollectionId, int type);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, boolean defaultTemplate)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long previewFileEntryId)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long classNameId, long classTypeId)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long[] fragmentEntryIds,
			String editableValues, ServiceContext serviceContext)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name,
			long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException;

	public LayoutPageTemplateEntry updateStatus(
			long layoutPageTemplateEntryId, int status)
		throws PortalException;

}