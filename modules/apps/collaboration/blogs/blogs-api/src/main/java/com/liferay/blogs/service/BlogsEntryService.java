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

package com.liferay.blogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blogs.kernel.model.BlogsEntry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * Provides the remote service interface for BlogsEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryServiceUtil
 * @see com.liferay.blogs.service.base.BlogsEntryServiceBaseImpl
 * @see com.liferay.blogs.service.impl.BlogsEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=blogs", "json.web.service.context.path=BlogsEntry"}, service = BlogsEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BlogsEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsEntryServiceUtil} to access the blogs entry remote service. Add custom service methods to {@link com.liferay.blogs.service.impl.BlogsEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(String, String,
	String, String, int, int, int, int, int, boolean, boolean,
	String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public BlogsEntry addEntry(java.lang.String title,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName, InputStream smallImageInputStream,
		ServiceContext serviceContext) throws PortalException;

	public BlogsEntry addEntry(java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long groupId, java.lang.String urlTitle)
		throws PortalException;

	public BlogsEntry moveEntryToTrash(long entryId) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public BlogsEntry updateEntry(long entryId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName, InputStream smallImageInputStream,
		ServiceContext serviceContext) throws PortalException;

	public BlogsEntry updateEntry(long entryId, java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, Date displayDate, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId,
		int[] statuses);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCompanyEntriesRSS(long companyId,
		Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		ThemeDisplay themeDisplay) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getGroupEntriesRSS(long groupId, Date displayDate,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getOrganizationEntriesRSS(long organizationId,
		Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		ThemeDisplay themeDisplay) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getCompanyEntries(long companyId, Date displayDate,
		int status, int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, Date displayDate,
		int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, Date displayDate,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupUserEntries(long groupId, long userId,
		int status, int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupUserEntries(long groupId, long userId,
		int[] statuses, int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupsEntries(long companyId, long groupId,
		Date displayDate, int status, int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getOrganizationEntries(long organizationId,
		Date displayDate, int status, int max) throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	public void restoreEntryFromTrash(long entryId) throws PortalException;

	public void subscribe(long groupId) throws PortalException;

	public void unsubscribe(long groupId) throws PortalException;
}