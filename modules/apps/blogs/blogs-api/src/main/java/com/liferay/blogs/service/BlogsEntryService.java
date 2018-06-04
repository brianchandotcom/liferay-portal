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

import com.liferay.blogs.model.BlogsEntry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
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
	* @deprecated As of 1.1.0, replaced by {@link #addEntry(String, String,
	String, String, int, int, int, int, int, boolean, boolean,
	String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	public BlogsEntry addEntry(String title, String description,
		String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks, String[] trackbacks,
		boolean smallImage, String smallImageURL, String smallImageFileName,
		InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry addEntry(String title, String subtitle,
		String description, String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowPingbacks, boolean allowTrackbacks,
		String[] trackbacks, String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry addEntry(String title, String subtitle, String urlTitle,
		String description, String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowPingbacks, boolean allowTrackbacks,
		String[] trackbacks, String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getCompanyEntries(long companyId, Date displayDate,
		int status, int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getCompanyEntriesRSS(long companyId, Date displayDate,
		int status, int max, String type, double version, String displayStyle,
		String feedURL, String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, Date displayDate,
		int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, Date displayDate,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, Date displayDate, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getGroupEntriesRSS(long groupId, Date displayDate,
		int status, int max, String type, double version, String displayStyle,
		String feedURL, String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupsEntries(long companyId, long groupId,
		Date displayDate, int status, int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupUserEntries(long groupId, long userId,
		int status, int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupUserEntries(long groupId, long userId,
		int[] statuses, int start, int end, OrderByComparator<BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId,
		int[] statuses);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getOrganizationEntries(long organizationId,
		Date displayDate, int status, int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getOrganizationEntriesRSS(long organizationId,
		Date displayDate, int status, int max, String type, double version,
		String displayStyle, String feedURL, String entryURL,
		ThemeDisplay themeDisplay) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	/**
	* Imports a blogs entry
	*
	* @param userId the blogs entry's author ID
	* @param title the blogs entry's title
	* @param subtitle the blogs entry's subtitle
	* @param urlTitle the blogs entry's urlTitle
	* @param description the blogs entry's description
	* @param content the blogs entry's content
	* @param displayDate the blogs entry's displayDate
	* @param coverImageCaption the blogs entry's cover image caption
	* @param coverImageImageSelector an object containing the data of the
	blogs's entry cover image, can be {@code null}
	* @param smallImageImageSelector an object containing the data of the
	blogs's entry small cover image, can be {@code null}
	* @param serviceContext the blogs entry's serviceContext; at least it must
	contain the {@code groupId}
	* @return the created blogs entry
	* @review
	*/
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	public BlogsEntry importEntry(long userId, String title, String subtitle,
		String urlTitle, String description, String content, Date displayDate,
		String coverImageCaption, ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry moveEntryToTrash(long entryId) throws PortalException;

	public void restoreEntryFromTrash(long entryId) throws PortalException;

	public void subscribe(long groupId) throws PortalException;

	public void unsubscribe(long groupId) throws PortalException;

	/**
	* Updates a blogs entry
	*
	* @param entryId the blogs entry's ID
	* @param userId the blogs entry's author ID
	* @param title the blogs entry's title
	* @param subtitle the blogs entry's subtitle
	* @param urlTitle the blogs entry's urlTitle
	* @param description the blogs entry's description
	* @param content the blogs entry's content
	* @param displayDate the blogs entry's displayDate
	* @param coverImageCaption the blogs entry's cover image caption
	* @param coverImageImageSelector an object containing the data of the
	blogs's entry cover image, can be {@code null}
	* @param smallImageImageSelector an object containing the data of the
	blogs's entry small cover image, can be {@code null}
	* @param serviceContext the blogs entry's serviceContext; at least it must
	contain the {@code groupId}
	* @return the updated blogs entry
	* @review
	*/
	public BlogsEntry updateEntry(long entryId, long userId, String title,
		String subtitle, String urlTitle, String description, String content,
		Date displayDate, String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link #updateEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	public BlogsEntry updateEntry(long entryId, String title,
		String description, String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowPingbacks, boolean allowTrackbacks,
		String[] trackbacks, boolean smallImage, String smallImageURL,
		String smallImageFileName, InputStream smallImageInputStream,
		ServiceContext serviceContext) throws PortalException;

	public BlogsEntry updateEntry(long entryId, String title, String subtitle,
		String description, String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowPingbacks, boolean allowTrackbacks,
		String[] trackbacks, String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry updateEntry(long entryId, String title, String subtitle,
		String urlTitle, String description, String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, String[] trackbacks, String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;
}