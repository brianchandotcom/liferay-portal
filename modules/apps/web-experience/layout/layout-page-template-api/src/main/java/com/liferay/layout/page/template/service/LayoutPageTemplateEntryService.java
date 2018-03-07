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

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for LayoutPageTemplateEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryServiceUtil
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=layout", "json.web.service.context.path=LayoutPageTemplateEntry"}, service = LayoutPageTemplateEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutPageTemplateEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateEntryServiceUtil} to access the layout page template entry remote service. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException;

	public List<LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
		long[] layoutPageTemplateEntryIds) throws PortalException;

	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, java.lang.String name);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, long[] fragmentEntryIds,
		java.lang.String editableValues, ServiceContext serviceContext)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name)
		throws PortalException;

	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name,
		long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException;
}