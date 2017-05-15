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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
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
 * Provides the remote service interface for CPAttachmentFileEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryServiceUtil
 * @see com.liferay.commerce.product.service.base.CPAttachmentFileEntryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPAttachmentFileEntry"}, service = CPAttachmentFileEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPAttachmentFileEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPAttachmentFileEntryServiceUtil} to access the cp attachment file entry remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPAttachmentFileEntry addCPAttachmentFileEntry(long classNameId,
		long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, java.lang.String json,
		int priority, int type, ServiceContext serviceContext)
		throws PortalException;

	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		CPAttachmentFileEntry cpAttachmentFileEntry) throws PortalException;

	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		long cpAttachmentFileEntryId) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPAttachmentFileEntry updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		java.lang.String json, int priority, int type,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPAttachmentFileEntriesCount(long classNameId, int classPK);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, int classPK, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, int classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws PortalException;
}