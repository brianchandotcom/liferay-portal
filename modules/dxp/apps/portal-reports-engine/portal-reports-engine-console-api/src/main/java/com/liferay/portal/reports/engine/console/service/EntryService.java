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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.reports.engine.console.model.Entry;

import java.util.Date;
import java.util.List;

/**
 * Provides the remote service interface for Entry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EntryServiceUtil
 * @see com.liferay.portal.reports.engine.console.service.base.EntryServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.EntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=reports", "json.web.service.context.path=Entry"}, service = EntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface EntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link EntryServiceUtil} to access the entry remote service. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.EntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Entry addEntry(long groupId, long definitionId, String format,
		boolean schedulerRequest, Date startDate, Date endDate,
		boolean repeating, String recurrence, String emailNotifications,
		String emailDelivery, String portletId, String pageURL,
		String reportName, String reportParameters,
		ServiceContext serviceContext) throws PortalException;

	public void deleteAttachment(long companyId, long entryId, String fileName)
		throws PortalException;

	public Entry deleteEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Entry> getEntries(long groupId, String definitionName,
		String userName, Date createDateGT, Date createDateLT,
		boolean andSearch, int start, int end,
		OrderByComparator orderByComparator) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, String definitionName,
		String userName, Date createDateGT, Date createDateLT, boolean andSearch)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public void sendEmails(long entryId, String fileName,
		String[] emailAddresses, boolean notification)
		throws PortalException;

	public void unscheduleEntry(long entryId) throws PortalException;
}